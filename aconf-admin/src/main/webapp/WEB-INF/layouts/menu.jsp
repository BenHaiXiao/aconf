<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%--<c:if test="${showMenu==true}">--%>
<!--左侧导航开始-->
<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="nav-close"><i class="fa fa-times-circle"></i>
    </div>
    <div class="sidebar-collapse">
        <ul class="nav" id="side-menu">
            <li class="nav-header">
                <div class="dropdown profile-element">
                    <span>
                        <c:choose>
                            <c:when test="${empty user.avatar}">
                                <img alt="image" class="img-circle" src="${ctx}/static/img/profile_small.jpg"/>
                            </c:when>
                            <c:otherwise>
                                <img alt="image" class="img-circle" src="${user.avatar}"/>
                            </c:otherwise>
                        </c:choose>
                    </span>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               <span class="block m-t-xs"><strong class="font-bold">${user.realName}</strong></span>
                                <span class="text-muted text-xs block">${user.role}<b class="caret"></b></span>
                                </span>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs">
                        <li><a href="/user-mgr">修改信息</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="/j_logout">安全退出</a>
                        </li>
                    </ul>
                </div>
                <div class="logo-element">YK
                </div>
            </li>
            <li>
                <a href="/app-mgr"><i class="fa fa-home"></i> <span
                        class="nav-label">App列表</span></a>
            </li>
            <li id="bssListItem" >
                <a href="/bss-mgr"><i class="fa fa-star"></i> <span
                        class="nav-label">业务列表</span></a>
            </li>
            <li id="bssMgrItem" >
                <a href="#">
                    <i class="fa fa-forumbee"></i>
                    <span class="nav-label">业务管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level in">
                    <li>
                        <a href="/config-mgr" data-index="0">配置项管理</a>
                    </li>

                    <li>
                        <a href="/push-mgr">广播管理</a>
                    </li>

                    <li>
                        <a href="/filter-mgr">公共拦截器</a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</nav>
<!--左侧导航结束-->
<%--</c:if>--%>