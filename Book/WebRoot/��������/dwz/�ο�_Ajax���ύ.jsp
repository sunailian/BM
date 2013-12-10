<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<%--
    DWZ会将所有常规的表单提交转换成ajax异步提交。
    Ajax异步提交的表单时，服务器端需要返回json串。JS回调函数会根据json串做相应的后续处理，比如刷新其他navTab页，以及关闭当前页面等等。
    
    返回json串格式如下：
    {
      "statusCode":"200", 
      "message":"操作成功", 
      "navTabId":"", 
      "rel":"", 
      "callbackType":"closeCurrent",
      "confirmMsg":"",
      "forwardUrl":""
    }
    
    表单的回调函数是navTabAjaxDone时：
    statusCode   ---200代表成功，300代表失败，301代表会话超时。
    navTabId     ---成功后要刷新的navTab页。
    rel          ---当前页要局部刷新的容器,如果未指定，则会刷新当前整个页面(前提是当前页存在#pagerForm表单或rel容器内存在#pagerForm表单)
    callbackType ---如果是closeCurrent就会关闭当前页面，如果是forward就会重载当前页，如果是forwardConfirm则在重载当前页时会先让用户确认。
    confirmMsg   ---callbackType为forwardConfirm时的确认提示信息.不指定时默认提示“继续下一步!”让用户确认
    forwardUrl   ---当前页重载时的URL地址。如不指定，则默认重载的url为当前页被打开时的url
    
    表单的回调函数是dialogAjaxDone时：
    statusCode   ---200代表成功，300代表失败，301代表会话超时。
    navTabId     ---成功后要刷新的navTab页。
    rel          ---当前页要局部刷新的容器,如果未指定，则会刷新当前整个页面(前提是当前页存在#pagerForm表单或rel容器内存在#pagerForm表单)
    callbackType ---如果是closeCurrent就会关闭当前页面。
    confirmMsg   ---callbackType为forwardConfirm时的确认提示信息.不指定时默认提示“继续下一步!”让用户确认
    forwardUrl   ---表示navTabId页重载时的URL地址。如不指定，则默认重载的url为那页打开时的url
    
    Action返回json串时，请不要自行进行字符串拼接，***Action已经继承BaseAction了，调用基类的responseJson(Object obj)，传适当的构造函数即可。
    示例：responseJson(new AjaxResult("200","操作成功"));
--%>