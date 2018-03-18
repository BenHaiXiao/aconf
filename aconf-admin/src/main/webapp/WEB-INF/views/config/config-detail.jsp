<%--
  @author xiaobenhai.
  Date: 2017/3/1
  Time: 17:38
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>配置项详情</title>
    <link rel="stylesheet" href="${ctx}/static/css/dtgrid-reset.css">
    <link rel="stylesheet" href="${ctx}/static/css/plugins/jsonview/jquery.jsonview.css">
    <link rel="stylesheet" href="${ctx}/static/css/configs/config-add.css">
    <link href="${ctx}/static/css/plugins/steps/jquery.steps.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/toolbar/jquery.toolbar.css" rel="stylesheet">
    <style>

        .node {
            cursor: pointer;
        }

        .node circle {
            fill: #fff;
            stroke: steelblue;
            stroke-width: 1.5px;
        }

        .node text {
            font: 10px sans-serif;
        }

        .link {
            fill: none;
            stroke: #ccc;
            stroke-width: 1.5px;
        }

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
    <div class="col-sm-6">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>配置项概览</h5>
                <div class="ibox-tools">
                    <button id="downloadmdbtn" type="button" class="btn btn-xs btn-primary" onclick="downloadMDFile()">导出</button>
                    <sec:authorize access="hasAnyRole('ROLE_'+${bssid})">
                    <a class="close-link" id="quickEditBtn">
                        <i class="fa fa-edit">快速编辑</i>
                    </a>
                    </sec:authorize>
                    <a id="goBackBtn" onclick="window.history.back()">
                        <i class="fa fa-undo">返回</i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <div class="row" id="treeContent">

                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>详情</h5>
            </div>
            <div class="ibox-content">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="alert alert-info">
                            说明：点击节点查看详细信息。
                            使用鼠标滚轮可以缩放，鼠标拖拽可移动图像。<a class="alert-link" href="#">了解更多</a>.
                        </div>
                    </div>
                </div>
                <strong id="detailTitle">请选择节点</strong>
                <div class="row">
                    <div class="col-sm-6">
                        <ul id="baseInfoList" class="list-group clear-list">

                        </ul>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="tabs-container">
                            <ul class="nav nav-tabs">
                                <li id="tableTab" class="active"><a data-toggle="tab" href="#tab-1"
                                                                    aria-expanded="true">数据清单</a>
                                </li>
                                <li id="valueTab" class="hide"><a data-toggle="tab" href="#tab-2" aria-expanded="false">下发值</a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div id="tab-1" class="tab-pane active">
                                    <div class="panel-body">
                                        <div id="gridContainer" class="dlshouwen-grid-container"></div>
                                        <div id="gridToolBar" class="dlshouwen-grid-toolbar-container"></div>
                                    </div>
                                </div>
                                <div id="tab-2" class="tab-pane">
                                    <div class="panel-body">
                                        <div id="jsonView"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <input id="configId" type="hidden" value="${configId}">
                <input id="bssid" type="hidden" value="${bssid}">
                <input id="token" type="hidden" value="${token}">
                <input id="appid" type="hidden" value="${appid}">
            </div>
        </div>
    </div>
</div>
<%@ include file="config-add.jsp" %>
<script src="${ctx}/static/js/iconfont.js"></script>
<script src="//d3js.org/d3.v3.min.js"></script>
<script src="${ctx}/static/js/plugins/steps/jquery.steps.min.js"></script>
<script src="${ctx}/static/js/plugins/toolbar/jquery.toolbar.js"></script>
<script src="${ctx}/static/js/plugins/jsonview/jquery.jsonview.js"></script>
<script src="${ctx}/static/js/bss/config-detail.js"></script>
<script src="${ctx}/static/js/bss/config-add.js" type="text/javascript"></script>
</body>
</html>
