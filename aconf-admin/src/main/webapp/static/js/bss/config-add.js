/**
 * @author xiaobenhai
 * Date: 2017/1/17
 * Time: 16:56
 */
var basisList;
var operatorMap = {};

function initialSortable() {
    $('#branchContainer').sortable({
        handle: ".branchTitle"
    });
    // $('.sortable').disableSelection();
}

function initialSteps() {
    $("#addConfigForm").steps({
        bodyTag: "fieldset",
        onStepChanging: function (event, currentIndex, newIndex) {
            // Always allow going backward even if the current step contains invalid fields!
            if (currentIndex > newIndex) {
                return true;
            }

            // Forbid suppressing "Warning" step if the user is to young
            if (newIndex === 3 && Number($("#age").val()) < 18) {
                return false;
            }

            var form = $(this);

            // Clean up if user went backward before
            if (currentIndex < newIndex) {
                // To remove error styles
                $(".body:eq(" + newIndex + ") label.error", form).remove();
                $(".body:eq(" + newIndex + ") .error", form).removeClass("error");
            }

            // Disable validation on fields that are disabled or hidden.
            form.validate().settings.ignore = ":disabled,:hidden";

            // Start validation; Prevent going forward if false
            return form.valid();
        },
        onStepChanged: function (event, currentIndex, priorIndex) {
            // Suppress (skip) "Warning" step if the user is old enough.
            if (currentIndex === 2 && Number($("#age").val()) >= 18) {
                $(this).steps("next");
            }

            // Suppress (skip) "Warning" step if the user is old enough and wants to the previous step.
            if (currentIndex === 2 && priorIndex === 3) {
                $(this).steps("previous");
            }
        },
        onFinishing: function (event, currentIndex) {
            var form = $(this);

            // Disable validation on fields that are disabled.
            // At this point it's recommended to do an overall check (mean ignoring only disabled fields)
            form.validate().settings.ignore = ":disabled";

            // Start validation; Prevent form submission if false
            return form.valid();
        },
        onFinished: function (event, currentIndex) {
            var form = $(this);
            var conditions = [];
            var branchDOMs = form.find('.branchItem');
            var seq = 0;
            for (var i = branchDOMs.length - 1; i >= 0; i--) {
                var item = $(branchDOMs[i]);
                var filterDOMs = item.find('.filterItem');
                var filters = [];
                for (var j = 0; j < filterDOMs.length; j++) {
                    var filterItem = $(filterDOMs[j]);
                    var basisValue = filterItem.find('select[name="basis"]').val();
                    var basisType = 0;
                    if (basisValue === 'extension-string') {
                        basisType = 1;
                        basisValue = filterItem.find('input[name="customBasis"]').val();
                    }
                    if (basisValue === 'extension-number') {
                        basisType = 2;
                        basisValue = filterItem.find('input[name="customBasis"]').val();
                    }
                    //验证自定义的basis是否为空
                    if (!basisValue) {
                        layer.alert("有自定义拦截器字段为空");
                        return;
                    }
                    var filter = {
                        basis: basisValue,
                        operator: filterItem.find('select[name="operator"]').val(),
                        boundary: filterItem.find('input[name="boundary"]').val(),
                        id: filterItem.find('input[name="filterAddId"]').val(),
                        type: basisType
                    };
                    filters.push(filter);
                }

                var condition = {
                    id: item.find('input[name="conditionId"]').val(),
                    name: item.find('input[name="name"]').val(),
                    value: item.find('input[name="value"]').val(),
                    filters: filters,
                    seq: seq
                };
                conditions.push(condition);
                seq++;
            }

            var data = {
                key: $('#configAddName').val(),
                value: $('#configDefaultValue').val(),
                description: $('#configAddDesc').val(),
                sendDefault: $('#configSendDefault').val(),
                conditions: conditions,
                deletedFilterIds: deletedFilters,
                deletedConditionIds: deletedConditions
            };

            var id = $('#configAddId').val();
            var bssId = $('#bssid').val();
            var url = "/bss/" + bssId + "/config/v2";
            var method = "post";
            if (id) {
                url = "/bss/" + bssId + "/config/" + id + "/v2";
                method = "put"
            }
            $.ajax({
                url: url,
                method: method,
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "json",
                success: function (data) {
                    if (data && data.code == 200) {
                        alert("保存成功");
                        layer.close(editDialog);
                        if (typeof grid != 'undefined') {
                            grid.refresh(true);
                        } else {
                            window.location.reload();
                        }
                    }else{
                    	alert(data.message);
                    }
                }
            })
        },
        onCanceled: function (event) {
            layer.close(editDialog);
        }
    }).validate({
        errorPlacement: function (error, element) {
            element.before(error);
        },
        rules: {
            confirm: {
                equalTo: "#password"
            }
        }
    });
}

function loadBasisList() {
    $.get("/filter/basis", function (data) {
        basisList = data.data;
        if (!basisList) {
            return;
        }
        for (var i in basisList) {
            var basis = basisList[i].value;
            $.ajax({
                url: '/filter/operator',
                method: 'get',
                async: false,
                data: {
                    basis: basis
                },
                success: function (data) {
                    if (data && data.code === 200) {
                        operatorMap[basis] = data.data;
                    }
                }
            });
        }
    });
}
function getOperator(basis) {
    if (!basis || !basis.basis) {
        return '';
    }
    if (basis.type === 1) {
        return operatorMap['extension-string'];
    }
    if (basis.type === 2) {
        return operatorMap['extension-number'];
    }
    return operatorMap[basis.basis];
}

function getFirstBasisOperator() {
    if (basisList) {
        var b = basisList[0];
        return operatorMap[b.value];
    }
}

function bind() {
    template.helper('getOperator', getOperator);
    template.helper('getFirstBasisOperator', getFirstBasisOperator);
    $('#addBranch').on("click", function () {
        if (!basisList) {
            loadBasisList();
        }
        var data = {
            "conditions": [
                {
                    "id": "",
                    "index": branchCount,
                    "name": "",
                }
            ],
            "basisList": basisList
        };
        branchCount++;
        var html = template('configAddTemplate', data);
        $('#branchContainer').append(html);
        $('a[data-tools="editor"]').toolbar({
            content: '#editor-toolbar',
            position: 'top',
            style: 'dark',
            zIndex: 19891025,
            adjustment: 20
        });
    });

    $("body").on('change', '.filterItem select[name="basis"]', function () {
        var operatorDOM = $(this).parent().parent().find('select[name="operator"]');
        var basis = $(this).val();
        if (!operatorMap[basis]) {
            $.ajax({
                url: '/filter/operator',
                method: "get",
                data: {
                    basis: basis
                },
                success: function (data) {
                    if (data && data.code == 200) {
                        var result = data.data;
                        operatorMap[basis] = result;
                    }
                },
                error: function () {
                }
            })
        }
        var op = operatorMap[basis];
        operatorDOM.empty();
        for (var i in op) {
            operatorDOM.append('<option value="' + op[i].value + '">' + op[i].name + '</option>');
        }
        if (basis === 'extension-string' || basis === 'extension-number') {
            $(this).addClass("hide");
            $(this).next().removeClass("hide");
        }
    });
}

//删除逻辑：
//1-只有一个拦截器时不删除，只能做修改
//2-如果删除的是最后一条，需要将加号挪上去
//3-如果是第一条，第二条上移时要注意删掉前面的空隙
//4-为方便后端删除，需要记录删掉的id
function removeFilterDom(elem) {
    var parent = $(elem).parent();
    var elemItem = parent.parent();
    var filterContainer = elemItem.parent();
    if (filterContainer.find('.filterItem').length == 1) {
        elemItem.find('select[name="basis"]').val("uid");
        elemItem.find('select[name="operator"]').val("$n");
        elemItem.find('input[name="boundary"]').val("");
    } else {
        var filterId = elemItem.find('input[name="filterAddId"]').val();
        if (filterId) {
            deletedFilters.push(filterId);
        }
        if (filterContainer.find(".filterItem:last-child").is(elemItem)) {
            var prev = elemItem.prev('.filterItem');
            var btnContainer = prev.find(".btnContainer");
            btnContainer.empty();
            btnContainer.html('<a class="addFilterBtn" style="color: #1ab394;font-size: 20px;line-height: 34px;"' +
                ' onclick="addFilterDom(this);"><i class="fa fa-plus-square-o"></i></a>' +
                '<a class="removeFilterBtn" style="color: #ec4758;font-size: 20px;line-height: 34px;"' +
                ' onclick="removeFilterDom(this);"><i' +
                ' class="fa fa-minus-square-o"></i></a>');
        } else if (filterContainer.find(".filterItem").first().is(elemItem)) {
            var next = elemItem.next('.filterItem');
            var placeholder = next.find('.filterItemPlaceholder');
            placeholder.remove();
        }
        elemItem.remove();
    }
}

function addFilterDom(elem) {
    //获取basis和operator
    var basisDOM = '';
    basisList.forEach(function (item, index) {
        basisDOM += '<option value="' + item.value + '">' + item.name + '</option>'
    });
    var operatorDOM = '';
    var operators = getFirstBasisOperator();
    operators.forEach(function (item, index) {
        operatorDOM += '<option value="' + item.value + '">' + item.name + '</option>'
    });

    var parent = $(elem).parent();
    parent.empty();
    parent.html('<span style="font-size: 20px;line-height: 34px;">且</span>' +
        '<a class="removeFilterBtn" style="color: #ec4758;font-size: 20px;line-height: 34px;"' +
        ' onclick="removeFilterDom(this);"><i class="fa fa-minus-square-o"></i></a>');
    var grandparent = parent.parent().parent();
    grandparent.append('<div class="filterItem">' +
        '<label class="col-sm-2 control-label filterItemPlaceholder"> </label>' +
        '<div class="col-sm-3">' +
        '<select name="basis" class="form-control dialog-control">' +
        basisDOM +
        '</select>' +
        '<input type="text" name="customBasis" class="form-control dialog-control hide" placeholder="拦截字段">' +
        '</div>' +
        '<div class="col-sm-3">' +
        '<select name="operator" class="form-control dialog-control"">' +
        operatorDOM +
        '</select>' +
        '</div>' +
        '<div class="col-sm-3">' +
        '<input name="boundary" type="text" class="form-control dialog-control">' +
        '<a class="input-icon" data-toggle="tooltip" data-placement="top"' +
        'title="批量编辑戳这里!" onclick="openBatchEditor($(this).prev(\'input\'));">' +
        '<img src="/static/img/batch_editor.png"/>' +
        '</a>' +
        '</div>' +
        '<div class="col-sm-1 btnContainer" style="padding-left: 5px;">' +
        '<a class="addFilterBtn" style="color: #1ab394;font-size: 20px;line-height: 34px;"' +
        ' onclick="addFilterDom(this);"><i class="fa fa-plus-square-o"></i></a>' +
        '<a class="removeFilterBtn" style="color: #ec4758;font-size: 20px;line-height: 34px;"' +
        ' onclick="removeFilterDom(this);"><i' +
        ' class="fa fa-minus-square-o"></i></a>' +
        '</div>' +
        '</div>');
}

function delBranchDOM(elem) {
    var parent = $(elem).parent();
    var conditionId = parent.find('input[name="conditionId"]').val();
    if (conditionId) {
        deletedConditions.push(conditionId);
    }
    parent.remove();
}

/**
 * 打开json编辑器
 * @param elem 调用方法的元素
 */
function openJsonEditor(elem) {
    var dataBase = $(elem.parentNode.parentNode).attr('data-base');
    if (!dataBase) {
        layer.alert("调用异常，请联系维护人员");
        return;
    }
    var _basicEditor = $('#' + dataBase);
    var container = $('#jsonEditor');
    container.empty();
    var options = {
        mode: 'code'
    };
    var editor = new JSONEditor(container[0], options);
    var originalText = _basicEditor.val();
    try {
        var parse = JSON.parse(originalText);
        editor.set(parse);
        container.removeClass("hide");
        jsonEditDialog = layer.open({
            type: 1,
            title: '编辑JSON',
            shadeClose: false,
            shade: 0,
            scrollbar: false,
            area: ['75%', '80%'],
            content: container,
            cancel: function (index) {
                var data = editor.get();
                _basicEditor.val(JSON.stringify(data));
            }
        });
    } catch (e) {
        parent.layer.alert("不是有效的JSON字符串！");
    }
}

var resGridColumns = [
    {id: 'id', title: 'id', type: 'number', columnClass: 'text-center', width: '10px', hideType: 'lg|md|sm|xs'},
    {id: 'url', title: 'url', type: 'string', columnClass: 'text-center', width: '100px'},
    {id: 'description', title: '简述', type: 'string', columnClass: 'text-center'},
    {
        id: 'uploadTime',
        title: '更新时间',
        type: 'date',
        format: 'yyyy-MM-dd hh:mm:ss',
        otype: 'time_stamp_ms',
        columnClass: 'text-center',
        hideType: 'md|sm|xs'
    },
    {
        id: 'creator', title: '上次操作人', type: 'string', columnClass: 'text-center', hideType: 'md|sm|xs',
        extra: false,
        resolution: function (value, record, column, grid, dataNo, columnNo) {
            if (value && value.name) {
                var _class = 'user-detail';
                if (dataNo == 0 || dataNo == 1) {
                    _class = 'pull-down';
                }
                var name = value.name ? value.name : "无名氏";
                var email = value.email ? value.email : "未填写";
                var avatar = value.avatar ? value.avatar : "/static/img/profile.jpg";
                var phone = value.phone ? value.phone : '未填写';
                var userDetail = '<div class="' + _class + '">' +
                    '<div class="contact-box">' +
                    '<div class="col-sm-4">' +
                    '<div class="text-center">' +
                    '<img alt="image" class="img-circle m-t-xs img-responsive" src="' + avatar + '">' +
                    '</div>' +
                    '</div>' +
                    '<div class="col-sm-8">' +
                    '<h3><strong>' + name + '</strong></h3>' +
                    '<address>' +
                    'E-mail:<a href="mailto:' + email + '">' + email + '</a><br>' +
                    '<abbr title="Phone">Tel:</abbr><a href="tel:' + phone + '">' + phone +
                    '</a></address>' +
                    '</div>' +
                    '<div class="clearfix"></div>' +
                    '</div>' +
                    '</div>';
                return '<span class="userName">' + userDetail + value.name + '</span>';
            }
            return '';
        }
    },
    {
        id: 'used', title: '使用状态', type: 'number', columnClass: 'text-center',
        resolution: function (value, record, column, grid, dataNo, columnNo) {
            var content = '';
            if (record.url == currentRes) {
                content = "<span class='label label-warning'>正在使用</span>"
            }
            return content;
        }
    },
    {id: 'version', title: '版本号', type: 'number', columnClass: 'text-center', hideType: 'md|sm|xs'},
    {
        id: 'operation',
        title: '操作',
        type: 'string',
        columnClass: 'text-center', resolution: function (value, record, column, grid, dataNo, columnNo) {
        var content = '';
        content += '<button class="btn btn-xs btn-primary" onclick="selectRes(\'' + record.url + '\')"><i class="fa fa-book"></i> 选择</button>';
        content += '  ';
        content += '<button class="btn btn-xs btn-danger" onclick="delRes(' + record.id + ')"><i class="fa fa-trash-o"></i> 删除</button>';
        return content;
    }
    }
];

function selectRes(url) {
    originResEditor.val(url);
    originResEditor = null;
    layer.close(resEditor);
}

function delRes(recordId) {
    layer.alert("删除资源！");
}

var resGridOption = {
    lang: 'zh-cn',
    ajaxLoad: true,
    loadAll: false,
    loadURL: '/resource/search',
    exportFileName: '资源列表',
    columns: resGridColumns,
    gridContainer: 'resourceContainer',
    toolbarContainer: 'resourceToolBar',
    tools: "",
    pageSize: 20,
    pageSizeLimit: [20, 50, 100]
};
var resGrid = $.fn.dlshouwen.grid.init(resGridOption);
var originResEditor;
var currentRes;
/**
 * 打开资源编辑器
 * @param elem 调用方法的元素
 */
function openResourceEditor(elem) {
    var dataBase = $(elem.parentNode.parentNode).attr('data-base');
    if (!dataBase) {
        layer.alert("调用异常，请联系维护人员");
        return;
    }
    originResEditor = $('#' + dataBase);
    var container = $('#resEditorContainer');
    container.removeClass("hide");
    resEditor = layer.open({
        type: 1,
        title: '资源编辑器',
        shadeClose: false,
        shade: 0,
        scrollbar: false,
        area: ['75%', '80%'],
        content: container
    });
    currentRes = originResEditor.val();
    var configId = $('#configAddId').val();
    if (!configId) {
        configId = "0";
    }
    resGrid.parameters = {};
    resGrid.parameters['configId'] = configId;
    resGrid.load();
}

/**
 * 打开批量编辑器
 * 批量编辑器用于编辑同一条件的多个值，最终的结果将以json的形式输出，并提交给后台
 * @param basicEditor 基础input输入框
 */
function openBatchEditor(basicEditor) {
    var batchEditor = $('#batchEditor');
    var originalText = basicEditor.val();
    try {
        var list = JSON.parse(originalText);
        if (list instanceof Array) {
            list = list.join("\n");
        }
    } catch (e) {
        list = originalText;
    }
    batchEditor.val(list);
    var container = $('#batchEditorContainer');
    container.removeClass("hide");
    layer.open({
        type: 1,
        title: '批量编辑器',
        shadeClose: false,
        shade: 0,
        scrollbar: false,
        area: ['75%', '80%'],
        content: container,
        cancel: function (index) {
            var data = batchEditor.val().split('\n');
            basicEditor.val(JSON.stringify(data));
        }
    });
}

function initialSwitchery() {
    var elem = document.querySelector('.js-switch');
    var switchery = new Switchery(elem, {
        color: '#1AB394'
    });
}

$(function () {
    initialSteps();
    initialSortable();
    loadBasisList();
    bind();
});