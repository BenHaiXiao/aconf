<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- BEGIN 页面头部 -->
<div class="row border-bottom">
    <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
            <form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                <div class="form-group">
                    <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search"
                           id="top-search">
                </div>
            </form>
        </div>
        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                    <%--<i class="fa fa-book"></i> <span class="label label-primary">8</span>--%>
                    <i class="fa fa-book"></i><span>帮助</span>
                </a>
                <ul class="dropdown-menu dropdown-alerts">
                    <li>
                        <a href="#" onclick="alert('敬请期待');return false;">
                            <div>
                                <i class="fa fa-beer fa-fw"></i> 管理后台用户手册
                                <span class="pull-right text-muted small">下载</span>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#" onclick="alert('敬请期待');return false;">
                            <div>
                                <i class="fa fa-book fa-fw"></i> SDK文档
                                <span class="pull-right text-muted small">查看</span>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#" onclick="alert('敬请期待');return false;">
                            <div>
                                <i class="fa fa-paper-plane fa-fw"></i> 管理后台API
                                <span class="pull-right text-muted small">查看</span>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#" onclick="alert('敬请期待');return false;">
                            <div>
                                <i class="fa fa-smile-o fa-fw"></i> 意见反馈
                                <span class="pull-right text-muted small">吐槽</span>
                            </div>
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </nav>
</div>
<!-- END 页面头部 -->