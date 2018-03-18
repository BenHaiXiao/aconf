<%--
  @author xiaobenhai.
  Date: 2017/3/9
  Time: 17:55
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="resEditorContainer" class="hide" style="width: 100%; height: 100%;">
    <div class="row">
        <div class="col-sm-12">
            <form id="addResourceForm" class="form-horizontal">
                <%--<input id="baseConfigId" type="hidden">--%>
                <div class="form-group">
                    <label for="resourceFile" class="col-sm-2 control-label">文件</label>
                    <div class="col-sm-10">
                        <input type="file" id="resourceFile" name="file">
                    </div>
                </div>
                <div class="form-group">
                    <label for="resourceDesc" class="col-sm-2 control-label">简述</label>
                    <div class="col-sm-10">
                        <input type="text" id="resourceDesc" name="description">
                    </div>
                </div>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <div class="col-sm-4 col-sm-offset-2">
                        <button id="uploadBtn" type="submit" class="btn btn-primary">上传</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div id="resourceContainer" class="dlshouwen-grid-container"></div>
            <div id="resourceToolBar" class="dlshouwen-grid-toolbar-container common-gird"></div>
        </div>
    </div>
</div>