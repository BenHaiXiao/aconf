<%--
  @author xiaobenhai.
  Date: 2017/1/16
  Time: 16:27
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>配置项管理</title>
    <link rel="stylesheet" href="${ctx}/static/css/dtgrid-reset.css">
    <link rel="stylesheet" href="${ctx}/static/css/configs/config-add.css">
    <link href="${ctx}/static/css/creator-common.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/steps/jquery.steps.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/toolbar/jquery.toolbar.css" rel="stylesheet">
    <style>
        .icon {
            width: 1em;
            height: 1em;
            vertical-align: -0.15em;
            fill: currentColor;
            overflow: hidden;
        }
    </style>
</head>
<body>
<div class="row">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>配置项管理</h5>
            </div>
            <div class="ibox-content">
                <div class="row">
                    <input id="bssid" type="hidden" value="${bssid}">
                    <input id="token" type="hidden" value="${token}">
                    <input id="appid" type="hidden" value="${appid}">
                    <div class="col-sm-3">
                        <input id="configId" type="text" placeholder="请输入配置项id" class="form-control">
                    </div>
                    <div class="col-sm-3">
                        <input id="configName" type="text" placeholder="请输入配置项名称" class="form-control">
                    </div>
                    <div class="col-sm-3">
                        <select id="configState" class="form-control" name="account">
                            <option selected disabled>请选择状态...</option>
                            <option value="1">使用中</option>
                            <option value="0">已删除</option>
                        </select>
                    </div>
                    <div class="col-sm-3">
                    <sec:authorize access="hasAnyRole('ROLE_'+${bssid})">
                        <button id="configAddBtn" type="button" class="btn btn-sm btn-warning">新增</button>
                        <input id="canbeEdit" type="hidden" value="1">
                    </sec:authorize>    
                        <button id="configSearchBtn" type="button" class="btn btn-sm btn-primary">搜索</button>
                        <button id="configResetBtn" type="button" class="btn btn-sm btn-default">重置</button>
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
<%@ include file="config-add.jsp" %>

<script src="${ctx}/static/js/iconfont.js"></script>
<!-- Steps -->
<script src="${ctx}/static/js/plugins/steps/jquery.steps.min.js"></script>
<script src="${ctx}/static/js/plugins/toolbar/jquery.toolbar.js"></script>
<script src="${ctx}/static/js/bss/config-mgr.js" type="text/javascript"></script>
<script src="${ctx}/static/js/bss/config-add.js" type="text/javascript"></script>
<script src="${ctx}/static/js/bss/resource-editor.js" type="text/javascript"></script>
</body>
</html>

