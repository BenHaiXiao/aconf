<%--
  @author xiaobenhai.
  Date: 2017/1/17
  Time: 12:40
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="add-dialog-wrapper" class="hide">
    <div id="add-dialog">
        <div class="col-sm-12">
            <form id="addConfigForm" class="wizard-big">
                <h1>基本信息</h1>
                <fieldset class="form-horizontal">
                    <input id="configAddId" type="hidden">
                    <div class="form-group">
                        <label for="configAddName" class="col-sm-2 control-label">KEY</label>
                        <div class="col-sm-10">
                            <input id="configAddName" name="name" type="text"
                                   class="form-control dialog-control required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="configDefaultValue" class="col-sm-2 control-label">默认值</label>
                        <div class="col-sm-3">
                            <select id="configSendDefault" class="form-control dialog-control">
                                <option value="0">不下发</option>
                                <option value="1">下发</option>
                            </select>
                        </div>
                        <div class="col-sm-7">
                            <input id="configDefaultValue" name="value" type="text"
                                   class="form-control dialog-control required">
                            <a class="input-icon" data-tools="editor"><img
                                    src="${ctx}/static/img/input-editor.png"/></a>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="configAddDesc" class="col-sm-2 control-label">简介</label>
                        <div class="col-sm-10">
                            <textarea id="configAddDesc" name="description" type="text" class="form-control"></textarea>
                        </div>
                    </div>
                </fieldset>

                <h1>条件分支</h1>
                <fieldset class="form-horizontal">
                    <a id="addBranch" class="btn btn-success">添加条件分支</a>
                    <div id="desc1" >版本拦截器支持格式: X/X.Y/X.Y.Z，其中X、Y、Z只能为数字</div>
                    <div id="desc2" >时间拦截器支持格式: yyyy-MM-dd HH:mm:ss</div>
                    <div id="branchContainer"></div>
                </fieldset>

                <div id="label-wrapper" class="hide">
                    <div id="dict-add-label" class="alert alert-danger alert-dismissable">
                        <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                        未知错误，请联系负责人解决! <a class="alert-link" href="notifications.html#">了解更多</a>.
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="editor-toolbar" class="hidden">
    <a href="#" data-toggle="tooltip" data-placement="top"
       title="JSON用户戳这里！" onclick="openJsonEditor(this)">
        <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-json01"></use>
        </svg>
    </a>
    <a href="#" onclick="openResourceEditor(this)">
        <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-yunpan"></use>
        </svg>
    </a>
</div>

<%@ include file="json-editor.jsp" %>
<%@ include file="batch-editor.jsp" %>
<%@ include file="resource-editor.jsp" %>

<script id="configAddTemplate" type="text/template">
    {{each conditions as value index}}
    <div class="branchItem">
        <div class="hr-line-dashed" style="background-color: #858789;"></div>

        <h3 class="branchTitle col-sm-11 branch-header">
            {{if value.index}}分支{{value.index+1}}{{else}}分支{{index+1}}{{/if}}
        </h3>
        <a class="btn btn-sm btn-danger col-sm-1 branch-header" onclick="delBranchDOM(this);"><i
                class="fa fa-trash-o"></i> 删除</a>

        <input type="hidden" name="conditionId" value="{{value.id}}">
        <div class="form-group">
            <label class="col-sm-2 control-label">名称</label>
            <div class="col-sm-10">
                <input name="name" type="text" class="form-control required" value="{{value.name}}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">下发值</label>
            <div class="col-sm-10">
                <input id="value{{if value.index}}{{value.index+1}}{{else}}{{index+1}}{{/if}}" name="value" type="text"
                       class="form-control required" value="{{value.value}}">
                <%--<a class="input-icon" data-toggle="tooltip" data-placement="top"--%>
                <%--title="JSON用户戳这里！" onclick="openJsonEditor($(this).prev('input'));">--%>
                <%--<img src="${ctx}/static/img/input-editor.png"/>--%>
                <%--</a>--%>
                <a class="input-icon" data-tools="editor"><img
                        src="${ctx}/static/img/input-editor.png"/></a>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">拦截器</label>
            {{if !value.filters||value.filters.length==0}}
            <div class="filterItem">
                <input type="hidden" name="filterAddId">
                <div class="col-sm-3">
                    <select name="basis" class="form-control dialog-control">
                        {{each basisList as basis index}}
                        <option value="{{basis.value}}">{{basis.name}}</option>
                        {{/each}}
                    </select>
                    <input type="text" name="customBasis" class="form-control dialog-control hide" placeholder="拦截字段">
                </div>
                <div class="col-sm-3">
                    <select name="operator" class="form-control dialog-control">
                        {{each getFirstBasisOperator() as item index}}
                        <option value="{{item.value}}">{{item.name}}
                        </option>
                        {{/each}}
                    </select>
                </div>
                <div class="col-sm-3">
                    <input name="boundary" type="text" class="form-control dialog-control required">
                    <a class="input-icon" data-toggle="tooltip" data-placement="top"
                       title="批量编辑戳这里!" onclick="openBatchEditor($(this).prev('input'));">
                        <img src="${ctx}/static/img/batch_editor.png"/>
                    </a>
                </div>
                <div class="col-sm-1 btnContainer" style="padding-left: 5px;">
                    <a class="addFilterBtn" style="color: #1ab394;font-size: 20px;line-height: 34px;"
                       onclick="addFilterDom(this);"><i
                            class="fa fa-plus-square-o"></i></a>
                    <a class="removeFilterBtn" style="color: #ec4758;font-size: 20px;line-height: 34px;"
                       onclick="removeFilterDom(this);"><i
                            class="fa fa-minus-square-o"></i></a>
                </div>
            </div>
            {{/if}}
            {{each value.filters as v i}}
            <div class="filterItem">
                {{if i!=0}}
                <label class="col-sm-2 control-label filterItemPlaceholder"> </label>
                {{/if}}
                <input type="hidden" name="filterAddId" value="{{v.id}}">
                <div class="col-sm-3">
                    {{if v.type==0}}
                    <select name="basis" class="form-control dialog-control">
                        {{each basisList as basis index}}
                        <option value="{{basis.value}}" {{if v.basis==basis.value}}selected{{/if}}>{{basis.name}}
                        </option>
                        {{/each}}
                    </select>
                    <input type="text" name="customBasis" class="form-control dialog-control hide" placeholder="拦截字段">
                    {{else if v.type==1}}
                    <select name="basis" class="form-control dialog-control hide">
                        {{each basisList as basis index}}
                        <option value="{{basis.value}}" {{if basis.value=='extension-string'}}selected{{/if}}>
                            {{basis.name}}
                        </option>
                        {{/each}}
                    </select>
                    <input type="text" name="customBasis" class="form-control dialog-control" placeholder="拦截字段"
                           value="{{v.basis}}">
                    {{else}}
                    <select name="basis" class="form-control dialog-control hide">
                        {{each basisList as basis index}}
                        <option value="{{basis.value}}" {{if basis.value=='extension-number'}}selected{{/if}}>
                            {{basis.name}}
                        </option>
                        {{/each}}
                    </select>
                    <input type="text" name="customBasis" class="form-control dialog-control" placeholder="拦截字段"
                           value="{{v.basis}}">
                    {{/if}}
                </div>
                <div class="col-sm-3">
                    <select name="operator" class="form-control dialog-control">
                        {{each getOperator(v) as item index}}
                        <option value="{{item.value}}" {{if v.operator==item.value}}selected{{/if}}>{{item.name}}
                        </option>
                        {{/each}}
                    </select>
                </div>
                <div class="col-sm-3">
                    <input name="boundary" type="text" class="form-control dialog-control required"
                           value="{{v.boundary}}">
                    <a class="input-icon" data-toggle="tooltip" data-placement="top"
                       title="批量编辑戳这里!" onclick="openBatchEditor($(this).prev('input'));">
                        <img src="${ctx}/static/img/batch_editor.png"/>
                    </a>
                </div>
                <div class="col-sm-1 btnContainer" style="padding-left: 5px;">
                    {{if i!=value.filters.length-1}}
                    <span style="font-size: 20px;line-height: 34px;">且</span>
                    <a class="removeFilterBtn" style="color: #ec4758;font-size: 20px;line-height: 34px;"
                       onclick="removeFilterDom(this);"><i
                            class="fa fa-minus-square-o"></i></a>
                    {{else}}
                    <a class="addFilterBtn" style="color: #1ab394;font-size: 20px;line-height: 34px;"
                       onclick="addFilterDom(this);"><i
                            class="fa fa-plus-square-o"></i></a>
                    <a class="removeFilterBtn" style="color: #ec4758;font-size: 20px;line-height: 34px;"
                       onclick="removeFilterDom(this);"><i
                            class="fa fa-minus-square-o"></i></a>
                    {{/if}}
                </div>
            </div>
            {{/each}}
        </div>
    </div>
    {{/each}}
</script>