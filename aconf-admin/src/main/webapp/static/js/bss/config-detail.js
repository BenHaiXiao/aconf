/**
 * Created by Administrator on 2017/3/2.
 */
var commonMargin = 200;
var currentNode;

var branchCount = 0;
var editDialog;
var jsonEditDialog;
var deletedFilters = [];
var deletedConditions = [];

var conditionColumns = [
    {id: 'id', title: 'id', type: 'number', columnClass: 'text-center', width: '10px', hideType: 'lg|md|sm|xs'},
    {id: 'name', title: '条件名称', type: 'string', columnClass: 'text-center', width: '100px'},
    {
        id: 'value', title: '下发值', type: 'string', columnClass: 'text-center', width: '10%',
        resolution: function (value, record, column, grid, dataNo, columnNo) {
            var v = value;
            if (record.valueType == 1) {
                v = "${" + v + "}";
            }
            if (value.length > 50) {
                v = value.slice(0, 49) + "...";
            }
            return v;
        }
    },
    {
        id: 'updateTime',
        title: '更新时间',
        type: 'date',
        format: 'yyyy-MM-dd hh:mm:ss',
        otype: 'time_stamp_ms',
        columnClass: 'text-center',
        hideType: 'md|sm|xs'
    }
];

var filterColumns = [
    {id: 'id', title: 'id', type: 'number', columnClass: 'text-center', width: '10px', hideType: 'lg|md|sm|xs'},
    {id: 'basis', title: '依据', type: 'string', columnClass: 'text-center', width: '100px'},
    {id: 'operator', title: '操作符', type: 'string', columnClass: 'text-center', width: '100px'},
    {id: 'boundary', title: '边界值', type: 'string', columnClass: 'text-center'},
    {
        id: 'updateTime',
        title: '更新时间',
        type: 'date',
        format: 'yyyy-MM-dd hh:mm:ss',
        otype: 'time_stamp_ms',
        columnClass: 'text-center',
        width: '100px',
        hideType: 'md|sm|xs'
    }
];

var sidColumns = [
    {id: 'sid', title: '频道号', type: 'string', columnClass: 'text-center'},
    {id: 'asid', title: '频道短号', type: 'string', columnClass: 'text-center'},
    {id: 'name', title: '名称', type: 'string', columnClass: 'text-center'}
];

var uidColumns = [
    {id: 'uid', title: 'uid', type: 'string', columnClass: 'text-center'},
    {id: 'passport', title: '通行证', type: 'string', columnClass: 'text-center'},
    {id: 'nick', title: '昵称', type: 'string', columnClass: 'text-center'},
    {id: 'yyno', title: 'YY号', type: 'string', columnClass: 'text-center'}
];

var defaultColumns = [
    {id: 'data', title: '边界值', type: 'string', columnClass: 'text-center'}
];


$(function () {
    var screenWidth = $("#treeContent").width();
    var screenHeight = window.screen.availHeight;
    var margin = {top: 20, right: 120, bottom: 20, left: 120},
        width = screenWidth - margin.right - margin.left,
        height = screenHeight - commonMargin - margin.top - margin.bottom;

    var i = 0,
        duration = 750,
        root;

    var tree = d3.layout.tree()
        .size([height, width]);

    var diagonal = d3.svg.diagonal()
        .projection(function (d) {
            return [d.y, d.x];
        });

    var svg = d3.select("#treeContent").append("svg")
        .attr("width", width + margin.right + margin.left)
        .attr("height", height + margin.top + margin.bottom);

    var svg_g = svg.append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    //鼠标拖拽事件
    var isMouseDown = false, mousePos_x, mousePos_y;
    var viewBox_x = 0, viewBox_y = 0, curPos_x, curPos_y;
    var oldScale = 1;

    svg.call(d3.behavior.zoom()
        .scaleExtent([0.1, 10])
        .on("zoom", function () {
            if (oldScale !== d3.event.scale) {
                var scale = oldScale / d3.event.scale;
                oldScale = d3.event.scale;
                viewBox_x = curPos_x - scale * (curPos_x - viewBox_x);
                viewBox_y = curPos_y - scale * (curPos_y - viewBox_y);
                svg.attr("viewBox", viewBox_x + " " + viewBox_y + " " + width / oldScale + " " + height / oldScale);
            }
        }));

    var configId = $("#configId").val();
    d3.json("/config/" + configId + "/view-data", function (error, flare) {
        if (error) throw error;

        if (!flare || flare.code != 200) {
            alert("获取数据出错，请稍后重试");
        }

        root = flare.data;
        root.x0 = height / 2;
        root.y0 = 0;

        function collapse(d) {
            if (d.children) {
                d._children = d.children;
                d._children.forEach(collapse);
                d.children = null;
            }
        }

        if (root.children) {
            root.children.forEach(collapse);
        }
        update(root);
        var loc = svg_g.selectAll('g')[0];
        click.call(loc[loc.length - 1], root);
    });

    d3.select(self.frameElement).style("height", "800px");

    function update(source) {

        // Compute the new tree layout.
        var nodes = tree.nodes(root).reverse(),
            links = tree.links(nodes);

        // Normalize for fixed-depth.
        nodes.forEach(function (d) {
            d.y = d.depth * 180;
        });

        // Update the nodes…
        var node = svg_g.selectAll("g.node")
            .data(nodes, function (d) {
                return d.id || (d.id = ++i);
            });

        // Enter any new nodes at the parent's previous position.
        var nodeEnter = node.enter().append("g")
            .attr("class", "node")
            .attr("transform", function (d) {
                return "translate(" + source.y0 + "," + source.x0 + ")";
            })
            .on("click", click);

        nodeEnter.append("circle")
            .attr("r", 1e-6)
            .style("fill", function (d) {
                return d._children ? "lightsteelblue" : "#fff";
            });

        nodeEnter.append("text")
            .attr("x", function (d) {
                return d.children || d._children ? -10 : 10;
            })
            .attr("dy", ".35em")
            .attr("text-anchor", function (d) {
                return d.children || d._children ? "end" : "start";
            })
            .text(function (d) {
                return d.name;
            })
            .style("fill-opacity", 1e-6);

        // Transition nodes to their new position.
        var nodeUpdate = node.transition()
            .duration(duration)
            .attr("transform", function (d) {
                return "translate(" + d.y + "," + d.x + ")";
            });

        nodeUpdate.select("circle")
            .attr("r", 4.5)
            .style("fill", function (d) {
                return d._children ? "lightsteelblue" : "#fff";
            });

        nodeUpdate.select("text")
            .style("fill-opacity", 1);

        // Transition exiting nodes to the parent's new position.
        var nodeExit = node.exit().transition()
            .duration(duration)
            .attr("transform", function (d) {
                return "translate(" + source.y + "," + source.x + ")";
            })
            .remove();

        nodeExit.select("circle")
            .attr("r", 1e-6);

        nodeExit.select("text")
            .style("fill-opacity", 1e-6);

        // Update the links…
        var link = svg_g.selectAll("path.link")
            .data(links, function (d) {
                return d.target.id;
            });

        // Enter any new links at the parent's previous position.
        link.enter().insert("path", "g")
            .attr("class", "link")
            .attr("d", function (d) {
                var o = {x: source.x0, y: source.y0};
                return diagonal({source: o, target: o});
            });

        // Transition links to their new position.
        link.transition()
            .duration(duration)
            .attr("d", diagonal);

        // Transition exiting nodes to the parent's new position.
        link.exit().transition()
            .duration(duration)
            .attr("d", function (d) {
                var o = {x: source.x, y: source.y};
                return diagonal({source: o, target: o});
            })
            .remove();

        // Stash the old positions for transition.
        nodes.forEach(function (d) {
            d.x0 = d.x;
            d.y0 = d.y;
        });

        svg.on("mousedown", function () {
            isMouseDown = true;
            mousePos_x = d3.mouse(this)[0];
            mousePos_y = d3.mouse(this)[1];
        });

        svg.on("mouseup", function () {
            isMouseDown = false;
            viewBox_x = viewBox_x - d3.mouse(this)[0] + mousePos_x;
            viewBox_y = viewBox_y - d3.mouse(this)[1] + mousePos_y;
            svg.attr("viewBox", viewBox_x + " " + viewBox_y + " " + width / oldScale + " " + height / oldScale);
        });

        svg.on("mousemove", function () {
            curPos_x = d3.mouse(this)[0];
            curPos_y = d3.mouse(this)[1];
            if (isMouseDown) {
                viewBox_x = viewBox_x - d3.mouse(this)[0] + mousePos_x;
                viewBox_y = viewBox_y - d3.mouse(this)[1] + mousePos_y;
                svg.attr("viewBox", viewBox_x + " " + viewBox_y + " " + width / oldScale + " " + height / oldScale);
            }
        });
    }

    // Toggle children on click.
    function click(d) {
        // 收起
        if (d.children) {
            if (currentNode == this) {
                d._children = d.children;
                d.children = null;
            } else {
                showDetail(d);
            }
        } else {
            // 展开
            if (currentNode != this) {
                showDetail(d);
            }
            d.children = d._children;
            d._children = null;
        }
        if (currentNode && currentNode != this) {
            $(currentNode).find('text').css("font-weight", "normal");
        }
        currentNode = this;
        $(this).find('text').css("font-weight", "bold");
        update(d);
    }

    window.onresize = function () {
        screenWidth = $("#treeContent").width();
        screenHeight = window.screen.availHeight;
        margin = {top: 20, right: 120, bottom: 20, left: 120};
        width = screenWidth - margin.right - margin.left;
        height = screenHeight - commonMargin - margin.top - margin.bottom;

        svg.attr("width", width + margin.right + margin.left)
            .attr("height", height + margin.top + margin.bottom);

        svg_g.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    };

    $('#quickEditBtn').on('click', function () {
        if (!root) {
            return false;
        }
        $('#branchContainer').empty();
        $.ajax({
            url: "/config/" + root.id,
            method: "get",
            dataType: "json",
            success: function (data) {
                if (data && data.code == 200) {
                    var result = data.data;
                    branchCount = result.conditions.length;
                    result.basisList = basisList;
                    var html = template('configAddTemplate', result);
                    $('#branchContainer').append(html);

                    $('#configAddName').val(result.key);
                    $('#configSendDefault').val(result.sendDefault);
                    $('#configDefaultValue').val(result.value);
                    $('#configAddDesc').val(result.description);
                    $('#configAddId').val(result.id);

                    deletedFilters = [];
                    deletedConditions = [];

                    $('#add-dialog-wrapper').removeClass("hide");
                    editDialog = layer.open({
                        type: 1,
                        title: '编辑配置项',
                        shadeClose: false,
                        shade: 0.8,
                        scrollbar: false,
                        area: ['75%', '80%'],
                        content: $('#add-dialog-wrapper')
                    });
                    $('[data-toggle="tooltip"]').tooltip();
                    $('a[data-tools="editor"]').toolbar({
                        content: '#editor-toolbar',
                        position: 'top',
                        style: 'dark',
                        zIndex: 19891025,   //为了覆盖在layer之上
                        adjustment: 20
                    });
                }
            }
        });
    });
});

function NodeType(type, name, baseUrl, tableUrl, gridColumns) {
    this.type = type;
    this.name = name;
    this.baseUrl = baseUrl;
    this.tableUrl = tableUrl;
    this.gridColumns = gridColumns;
}

var configNode = new NodeType("config", "配置项", "/config/", "/condition/search", conditionColumns);
var conditionNode = new NodeType("condition", "条件分支", "/condition/", "/filter/search", filterColumns);
var filterNode = new NodeType("filter", "拦截器", "/filter/", "/boundary/search");

var nodeMap = {
    config: configNode,
    condition: conditionNode,
    filter: filterNode
};

/**
 * 让较长的数据展示到下方的下发值区域
 * @param data 节点的详情数据
 * @returns {boolean} 是否添加到了下发值区域
 */
function fitValueLength(data) {
    var value = data.value;
    if (value && value.length > 50) {
        var $jsonView = $('#jsonView');
        if (value.startsWith("{") || value.startsWith("[")) {
            $jsonView.JSONView(value);
        } else {
            $jsonView.html("<p>" + value + "</p>");
        }
        $('#valueTab').removeClass("hide");
        return true;
    }
    $('#valueTab').addClass("hide");
    return false;
}

function gotoValueTab() {
    $('#valueTab').find('a').tab('show');
}

/**
 * 添加数据到InfoList
 * @param nodeData
 * @param data
 */
function addDataToInfoList(nodeData, data) {
    var $baseInfoList = $('#baseInfoList');
    $baseInfoList.empty();
    switch (nodeData.type) {
        case "config":
            if (fitValueLength(data)) {
                $baseInfoList.append(createBaseInfoItem("默认值", "<a href='#' onclick='gotoValueTab();return false;'>见下发值选项卡</a>", true));
            } else {
                $baseInfoList.append(createBaseInfoItem("默认值", data.value, true));
            }
            $baseInfoList.append(createBaseInfoItem("下发默认值", data.sendDefault ? '下发' : '不下发'));
            $baseInfoList.append(createBaseInfoItem("值类型", data.valueType ? '表达式' : '普通文字'));
            $('#tableTab').find('a').text('条件分支');
            break;
        case "condition":
            if (fitValueLength(data)) {
                $baseInfoList.append(createBaseInfoItem("默认值", "<a href='#' onclick='gotoValueTab();return false;'>见下发值选项卡</a>", true));
            } else {
                $baseInfoList.append(createBaseInfoItem("下发值", data.value, true));
            }
            $baseInfoList.append(createBaseInfoItem("值类型", data.valueType ? '表达式' : '普通文字'));
            $('#tableTab').find('a').text('拦截器');
            break;
        case "filter":
            $baseInfoList.append(createBaseInfoItem("拦截依据", data.basis, true));
            $baseInfoList.append(createBaseInfoItem("操作符", data.operator));
            $('#tableTab').find('a').text('边界值');
            break;
        default:
            break;
    }
}

/**
 * 填充表格请求时所需的参数
 * @param grid 表格对象
 * @param nodeData 当前节点的节点数据（id, type, name, children...)
 */
function fillGridParams(grid, nodeData) {
    switch (nodeData.type) {
        case "config":
            grid.parameters['configId'] = nodeData.id;
            break;
        case "condition":
            grid.parameters['conditionId'] = nodeData.id;
            break;
        case "filter":
            grid.parameters['filterId'] = nodeData.id;
            break;
    }
}

/**
 *
 * @param node
 * @param nodeData 节点数据
 * @returns {*}
 */
function selectColumns(node, nodeData) {
    if (node.gridColumns) {
        return node.gridColumns;
    }
    var name = nodeData.name;
    var ss = name.split(" ");
    if (ss.length >= 2 && ss[1] != '$n') {
        switch (ss[0]) {
            case "sid":
                return sidColumns;
            case "uid":
                return uidColumns;
            default:
                return defaultColumns;
        }
    }
    return defaultColumns;
}

/**
 * 显示当前节点详情
 * @param nodeData 当前节点的meta数据
 */
function showDetail(nodeData) {
    var node = nodeMap[nodeData.type];
    $("#detailTitle").text(node.name + " : " + nodeData.name);
    $.ajax({
        url: node.baseUrl + nodeData.id,
        method: 'get',
        dateType: 'json',
        success: function (data) {
            if (data && data.code === 200) {
                addDataToInfoList(nodeData, data.data);
            }
        }
    });

    //更新表格内容
    $('#gridContainer').empty();
    $('#gridToolBar').empty();

    var gridColumns = selectColumns(node, nodeData);
    var gridOption = {
        lang: 'zh-cn',
        ajaxLoad: true,
        loadAll: false,
        loadURL: node.tableUrl,
        columns: gridColumns,
        gridContainer: 'gridContainer',
        toolbarContainer: 'gridToolBar',
        tools: "",
        pageSize: 20,
        pageSizeLimit: [20, 50, 100]
    };

    var grid = $.fn.dlshouwen.grid.init(gridOption);
    grid.parameters = {};
    fillGridParams(grid, nodeData);
    grid.load();
}

function createBaseInfoItem(name, data, isFirst) {
    var first = isFirst ? 'fist-item' : '';
    return '<li class="list-group-item ' + first + '">' +
        '<span class="pull-right"> ' + data + '</span> ' + name + '</li>';
}

function downloadMDFile() {
    var configId = $('#configId').attr('value');
    $.ajax(
        window.open("/configfile/download/" + configId)
    );
}