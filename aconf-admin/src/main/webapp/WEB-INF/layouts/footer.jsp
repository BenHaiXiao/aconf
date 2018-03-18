<%@ page language="java" pageEncoding="UTF-8" %>

<!-- BEGIN FOOTER -->
<div class="footer fixed" style="margin-left: 0;">
    <c:choose>
        <c:when test="${empty token}">
            <div class="pull-left" style="margin-left: 220px;">图标由<a href="https://icons8.com/">Icons8</a>提供</div>
        </c:when>
        <c:otherwise>
            <div class="pull-left">图标由<a href="https://icons8.com/">Icons8</a>提供</div>
        </c:otherwise>
    </c:choose>

    <div class="pull-right">&copy; 2016-2017 <a href="#">公共业务技术中心-平台基础服务技术组</a>
    </div>
</div>
<!-- END FOOTER -->