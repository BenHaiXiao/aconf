<%--
  @author xiaobenhai.
  Date: 2017/2/6
  Time: 10:27
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="addDialogWrapper" class="hide">
    <div id="addDialog">
        <div class="col-sm-12">
            <form id="addPushForm" class="form-horizontal">
                <input id="pushId" type="hidden">
                <div class="form-group">
                    <label for="pushAddTitle" class="col-sm-2 control-label">标题</label>
                    <div class="col-sm-10">
                        <input id="pushAddTitle" name="title" type="text" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label for="pushAddSids" class="col-sm-2 control-label">频道</label>
                    <div class="col-sm-10">
                        <input id="pushAddSids" name="sids" type="text" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label for="pushAddSys" class="col-sm-2 control-label">平台</label>
                    <div class="col-sm-10">
                        <select id="pushAddSys" class="form-control" name="sys">
                            <option value="0" selected>全平台</option>
                            <option value="1">PC端</option>
                            <option value="2">移动端</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="pushAddTime" class="col-sm-2 control-label">触发时间</label>
                    <div class="col-sm-10">
                        <input id="pushAddTime" name="time" class="laydate-icon form-control layer-date">
                    </div>
                </div>

                <div id="label-wrapper" class="hide">
                    <div id="pushAddLabel" class="alert alert-danger alert-dismissable">
                        <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                        未知错误，请联系负责人解决! <a class="alert-link" href="notifications.html#">了解更多</a>.
                    </div>
                </div>

                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <div class="col-sm-4 col-sm-offset-2">
                        <button id="pushAddSendBtn" type="submit" class="btn btn-primary">确认</button>
                        <button id="pushAddResetBtn" type="reset" class="btn btn-white">重置</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>