<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>统一客户端配置中心</title>

    <link rel="shortcut icon" href="${ctx}/static/favicon.ico">
    <link href="${ctx}/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx}/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <!-- Morris -->
    <link href="${ctx}/static/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">

    <link href="${ctx}/lib/dtgrid/dlshouwen.grid.css" rel="stylesheet">
    <link href="${ctx}/static/css/animate.css" rel="stylesheet">
    <link href="${ctx}/static/css/style.css?v=4.1.0" rel="stylesheet">
    <!-- iCheck -->
    <link href="${ctx}/static/css/plugins/iCheck/custom.css" rel="stylesheet">

    <%--Toast提示框--%>
    <link href="${ctx}/static/css/plugins/toastr/toastr.min.css" rel="stylesheet">

    <%--JSON Editor--%>
    <link href="${ctx}/static/css/plugins/json-editor/jsoneditor.min.css" rel="stylesheet">

    <!-- 全局js -->
    <script src="${ctx}/static/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ctx}/static/js/jquery-ui-1.10.4.min.js"></script>
    <%--<script src="${ctx}/static/js/jquery-ui.custom.min.js"></script>--%>
    <script src="${ctx}/static/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${ctx}/static/js/plugins/layer/layer.min.js"></script>

    <!-- iCheck -->
    <script src="${ctx}/static/js/plugins/iCheck/icheck.min.js"></script>

    <%--Toast提示框--%>
    <script src="${ctx}/static/js/plugins/toastr/toastr.min.js"></script>

    <!-- 自定义js -->
    <script src="${ctx}/static/js/hplus.js?v=4.1.0"></script>
    <script src="${ctx}/static/js/contabs.js"></script>

    <!-- 第三方插件 -->
    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>

    <!-- dlshouwen grid -->
    <script src="${ctx}/lib/dtgrid/dlshouwen.grid.js"></script>
    <script src="${ctx}/lib/dtgrid/i18n/zh-cn.js"></script>

    <%-- 表单验证插件 jQuery Validation--%>
    <script src="${ctx}/static/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${ctx}/static/js/plugins/validate/messages_zh.min.js"></script>
    <%-- jQuery Validation兼容Bootstarp的方法--%>
    <script src="${ctx}/static/js/custom/bootstrap-validation.js"></script>

    <%-- layer 弹窗插件--%>
    <script src="${ctx}/static/js/plugins/layer/layer.min.js"></script>

    <%-- art template 模板 --%>
    <script src="${ctx}/static/js/plugins/art-template/template.js"></script>
    <%-- JSON Editor --%>
    <script src="${ctx}/static/js/plugins/json-editor/jsoneditor.min.js"></script>

    <%--日期格式化拓展脚本--%>
    <script src="${ctx}/static/js/tools/date-format.js"></script>

    <script type="text/javascript" src="${ctx}/static/js/bss/menu.js"></script>

    <script>
        $(document).ready(function () {
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
        });
    </script>
    <sitemesh:head/>
</head>

<body class="fixed-sidebar full-height-layout gray-bg">
<div id="wrapper">
    <c:choose>
        <c:when test="${empty token}">
            <%@ include file="/WEB-INF/layouts/menu.jsp" %>
            <!-- BEGIN PAGE -->
            <%-- 添加菜单时，请为下面的div添加id: page-warpper --%>
            <div id="page-wrapper" class="gray-bg dashbard-1" style="padding-left: 0;padding-right: 0;">
                <%@ include file="/WEB-INF/layouts/header.jsp" %>
                <div class="container-fluid" style="padding-left: 0;padding-right: 0;">
                    <sitemesh:body/>
                </div>
                <%@ include file="/WEB-INF/layouts/footer.jsp" %>
            </div>
            <!-- END PAGE -->
        </c:when>
        <c:otherwise>
            <div class="gray-bg dashbard-1">
                <%@ include file="/WEB-INF/layouts/header.jsp" %>
                <div class="container-fluid" style="padding-left: 0;padding-right: 0;">
                    <sitemesh:body/>
                </div>
                <%@ include file="/WEB-INF/layouts/footer.jsp" %>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>