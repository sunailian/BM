<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<div class="accordion" fillSpace="sideBar">
	<c:forEach var="resource" items="${leftResourceList}">
    <div class="accordionHeader">
	  <h2><span>Folder</span>${resource.resourceName}</h2>
	</div>
	<div class="accordionContent">
	  <ul class="tree treeFolder">
	    <c:forEach var="resource" items="${resource.childrenList}">
	    <li>
	      <c:choose>
	      <c:when test="${resource.url==null}">
	      <a>${resource.resourceName}</a>
	      </c:when>
	      <c:otherwise>
	      <a href="${resource.url}" target="navTab" rel="${resource.navTabId}">${resource.resourceName}</a>
	      </c:otherwise>
	      </c:choose>
	      <c:if test="${resource.childrens>0}">
	      <ul>
	        <c:forEach var="resource" items="${resource.childrenList}">
		    <li>
		      <c:choose>
		      <c:when test="${resource.url==null}">
		      <a>${resource.resourceName}</a>
		      </c:when>
		      <c:otherwise>
		      <a href="${resource.url}" target="navTab" rel="${resource.navTabId}">${resource.resourceName}</a>
		      </c:otherwise>
		      </c:choose>
		    </li>
		    </c:forEach>
	      </ul>
	      </c:if>
	    </li>
	    </c:forEach>
	  </ul>
	</div>
    </c:forEach>
</div>

