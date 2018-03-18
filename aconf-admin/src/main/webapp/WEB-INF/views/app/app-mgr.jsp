<%--
  @author xiaobenhai.
  Date: 2017/1/11
  Time: 11:41
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>应用管理</title>
    <link rel="stylesheet" href="${ctx}/static/css/dtgrid-reset.css">
</head>
<body>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <c:if test="${fn:length(apps)==0}">
            <div class="col-sm-3">
                <div class="alert alert-warning">
                    没有找到任何应用，请联系管理员创建。 <a class="alert-link" href="notifications.html#">了解更多</a>.
                </div>
            </div>
        </c:if>
        <c:if test="${fn:length(apps)>0}">
            <div class="row" style="padding-left: 15px;padding-right: 15px;">
                <div class="col-sm-12">
                    <div class="alert alert-info">
                        请选择应用以进行下一步操作。<a class="alert-link" href="#">了解更多</a>.
                    </div>
                </div>
            </div>
        </c:if>
        <c:forEach items="${apps}" var="app">
            <div class="col-sm-3">
                <div class="ibox" onclick="window.location.href='/bss-mgr?appid=${app.id}&token=${token}'"
                     style="cursor:pointer;">
                    <div class="ibox-title">
                            <%--<span class="label label-primary pull-right">NEW</span>--%>
                        <h5>${app.name}</h5>
                    </div>
                    <div class="ibox-content">
                        <h4>应用简介</h4>
                        <p>${app.description}</p>
                        <div class="row  m-t-sm">
                            <div class="col-sm-4">
                                <div class="font-bold">业务数</div>
                                    ${app.bssNum}
                            </div>
                            <div class="col-sm-2">
                            </div>
                            <div class="col-sm-6 text-right">
                                <div class="font-bold">负责人</div>
                                    ${app.creator}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
