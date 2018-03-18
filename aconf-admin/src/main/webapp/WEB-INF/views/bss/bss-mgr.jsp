<%--
  @author xiaobenhai.
  Date: 2017/1/11
  Time: 11:41
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>业务管理</title>
    <link rel="stylesheet" href="${ctx}/static/css/dtgrid-reset.css">
    <link rel="stylesheet" href="${ctx}/static/css/creator-common.css">
</head>
<body>
<div class="row">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>业务管理</h5>
            </div>
            <div class="ibox-content">
                <div class="row">
                    <input id="appId" type="hidden" value="${appid}">
                    <input id="token" type="hidden" value="${token}">
                    <div class="col-sm-3">
                        <input id="bssCode" type="text" placeholder="请输入业务代号" class="form-control">
                    </div>
                    <div class="col-sm-3">
                        <input id="bssName" type="text" placeholder="请输入业务名称" class="form-control">
                    </div>
                    <div class="col-sm-3">
                        <select id="bssState" class="form-control" name="account">
                            <option selected disabled>请选择状态...</option>
                            <option value="8">全部</option>
                            <option value="1">使用中</option>
                            <option value="2">未生效</option>
                            <option value="3">已结束</option>
                            <option value="0">已删除</option>
                        </select>
                    </div>
                    <div class="col-sm-3">
                        <input id="roleType" type="hidden" value="1">
                        <button id="bssAddBtn" type="button" class="btn btn-sm btn-warning">新增</button>
                        <button id="bssSearchBtn" type="button" class="btn btn-sm btn-primary">搜索</button>
                        <button id="bssResetBtn" type="button" class="btn btn-sm btn-default">重置</button>
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
<%@ include file="bss-add.jsp" %>
<script src="${ctx}/static/js/plugins/layer/laydate/laydate.js"></script>
<script src="${ctx}/static/js/bss/bss-mgr.js" type="text/javascript"></script>
<script src="${ctx}/static/js/bss/bss-add.js" type="text/javascript"></script>
</body>
</html>
