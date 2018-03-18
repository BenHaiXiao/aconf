<%--
  @author xiaobenhai.
  Date: 2017/2/7
  Time: 11:27
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>公共拦截器管理</title>
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
                <h5>公共拦截器管理</h5>
            </div>
            <div class="ibox-content">
                <div class="row">
                    <input id="bssId" type="hidden" value="${bssid}">
                    <input id="token" type="hidden" value="${token}">
                    <input id="appid" type="hidden" value="${appid}">
                    <div class="col-sm-3">
                        <input id="filterId" type="text" placeholder="请输入拦截器id" class="form-control">
                    </div>
                    <div class="col-sm-3">
                    <sec:authorize access="hasAnyRole('ROLE_'+${bssid})">
                        <button id="filterAddBtn" type="button" class="btn btn-sm btn-warning">新增</button>
                        <input id="canbeEditFilter" type="hidden" value="1">
                    </sec:authorize>    
                        <button id="filterSearchBtn" type="button" class="btn btn-sm btn-primary">搜索</button>
                        <button id="filterResetBtn" type="button" class="btn btn-sm btn-default">重置</button>
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

<%@ include file="filter-add.jsp" %>
<script src="${ctx}/static/js/bss/comm-filter-mgr.js" type="text/javascript"></script>
<script src="${ctx}/static/js/bss/comm-filter-add.js" type="text/javascript"></script>
</body>
</html>

