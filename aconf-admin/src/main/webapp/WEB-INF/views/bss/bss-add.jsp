<%--
  @author xiaobenhai.
  Date: 2017/2/27
  Time: 17:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="addDialogWrapper" class="hide">
    <div id="addDialog" style="padding: 20px 15px;">
        <div class="col-sm-12">
            <form id="addBssForm" class="form-horizontal">
                <input id="bssAddId" type="hidden">
                <div class="form-group">
                    <label for="bssAddName" class="col-sm-2 control-label">业务名称</label>
                    <div class="col-sm-10">
                        <input id="bssAddName" name="bssName" type="text"
                               class="form-control dialog-control required">
                    </div>
                </div>

                <div class="form-group">
                    <label for="bssAddCode" class="col-sm-2 control-label">业务代号</label>
                    <div class="col-sm-10">
                        <input id="bssAddCode" name="bssCode" type="text"
                               class="form-control dialog-control required">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">长期有效</label>
                    <div class="col-sm-10">
                        <label class="radio-inline i-checks">
                            <input id="longTime" name="effectiveType" type="radio" value="0" checked>是
                        </label>
                        <label class="radio-inline i-checks">
                            <input id="shortTime" name="effectiveType" type="radio" value="1">否
                        </label>
                    </div>
                </div>
                <div id="timeArea" class="hide">
                    <div class="form-group">
                        <label for="effectiveTime" class="col-sm-2 control-label">生效时间</label>
                        <div class="col-sm-10">
                            <input id="effectiveTime" name="effectiveTime" class="laydate-icon form-control layer-date">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="failureTime" class="col-sm-2 control-label">结束时间</label>
                        <div class="col-sm-10">
                            <input id="failureTime" name="failureTime" class="laydate-icon form-control layer-date">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="bssAddDesc" class="col-sm-2 control-label">描述</label>
                    <div class="col-sm-10">
                        <input id="bssAddDesc" name="bssDesc" type="text"
                               class="form-control dialog-control required">
                    </div>
                </div>

                <div id="label-wrapper" class="hide">
                    <div id="bssAddLabel" class="alert alert-danger alert-dismissable">
                        <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                        未知错误，请联系负责人解决! <a class="alert-link" href="notifications.html#">了解更多</a>.
                    </div>
                </div>

                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <div class="col-sm-4 col-sm-offset-2">
                        <button id="bssAddSendBtn" type="submit" class="btn btn-primary">确认</button>
                        <button id="bssAddResetBtn" type="reset" class="btn btn-white">重置</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>