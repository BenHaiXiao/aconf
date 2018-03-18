<%--
  @author xiaobenhai.
  Date: 2017/2/7
  Time: 11:27
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="addDialogWrapper" class="hide">
    <div id="addDialog">
        <div class="col-sm-12">
            <form id="addFilterForm" class="form-horizontal">
                <input id="filterAddId" type="hidden">
                <div class="form-group">
                    <label for="filterBasis" class="col-sm-2 control-label">依据</label>
                    <div class="col-sm-10">
                        <select id="filterBasis" name="basis" class="form-control dialog-control">
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="filterOperator" class="col-sm-2 control-label">操作符</label>
                    <div class="col-sm-10">
                        <select id="filterOperator" name="operator" class="form-control dialog-control">
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="filterBoundary" class="col-sm-2 control-label">比较值</label>
                    <div class="col-sm-10">
                        <input id="filterBoundary" name="boundary" type="text"
                               class="form-control dialog-control required">
                    </div>
                </div>

                <div id="label-wrapper" class="hide">
                    <div id="filterAddLabel" class="alert alert-danger alert-dismissable">
                        <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                        未知错误，请联系负责人解决! <a class="alert-link" href="notifications.html#">了解更多</a>.
                    </div>
                </div>

                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <div class="col-sm-4 col-sm-offset-2">
                        <button id="filterAddSendBtn" type="submit" class="btn btn-primary">确认</button>
                        <button id="filterAddResetBtn" type="reset" class="btn btn-white">重置</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>