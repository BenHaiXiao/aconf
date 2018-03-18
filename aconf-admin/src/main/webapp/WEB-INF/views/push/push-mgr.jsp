<%--
  @author xiaobenhai.
  Date: 2017/2/6
  Time: 10:16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>广播管理</title>
    <link rel="stylesheet" href="${ctx}/static/css/dtgrid-reset.css">
    <link rel="stylesheet" href="${ctx}/static/css/creator-common.css">
    <style>
        #addDialog {
            padding: 20px 15px;
        }
    </style>
</head>
<body>
<div class="row">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>广播管理</h5>
            </div>
            <div class="ibox-content">
                <div class="row">
                    <input id="bssId" type="hidden" value="${bssid}">
                    <input id="token" type="hidden" value="${token}">
                    <input id="appid" type="hidden" value="${appid}">
                    <div class="col-sm-3">
                        <input id="pushTitle" type="text" placeholder="请输入广播标题" class="form-control">
                    </div>
                    <div class="col-sm-3">
                        <input id="pushChannel" type="text" placeholder="请输入广播频道" class="form-control">
                    </div>
                    <div class="col-sm-3">
                        <select id="pushState" class="form-control" name="account">
                            <option selected disabled>请选择状态...</option>
                            <option value="101">待推送</option>
                            <option value="103">已推送</option>
                        </select>
                    </div>
                    <div class="col-sm-3">
                        <button id="pushAddBtn" type="button" class="btn btn-sm btn-warning">新增</button>
                        <button id="pushSearchBtn" type="button" class="btn btn-sm btn-primary">搜索</button>
                        <button id="pushResetBtn" type="button" class="btn btn-sm btn-default">重置</button>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <div id="gridContainer" class="dlshouwen-grid-container"></div>
                        <div id="gridToolBar" class="dlshouwen-grid-toolbar-container"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="push-add.jsp" %>
<!-- layerDate plugin javascript -->
<script src="${ctx}/static/js/plugins/layer/laydate/laydate.js"></script>
<script src="${ctx}/static/js/bss/push-mgr.js" type="text/javascript"></script>
<script src="${ctx}/static/js/bss/push-add.js" type="text/javascript"></script>
</body>
</html>
