<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<div id="fileQueue" class="fileQueue"></div>
<input id="testFileInput" type="file" name="image" 
	uploader="/DWZ/uploadify/scripts/uploadify.swf"
	cancelImg="/DWZ/uploadify/cancel.png" 
	script="your_url" 
	scriptData="{}"
	folder=""
	fileQueue="fileQueue"
	fileExt="*.jpg;*.jpeg;*.gif;*.png;"
	fileDesc="*.jpg;*.jpeg;*.gif;*.png;"
	onComplete="uploadifyComplete"
	onAllComplete="uploadifyAllComplete" 
	/>
<%--
    uploader： flash组件uploadify.swf的访问路径
	cancelImg: 取消按钮使用的图片路径
	script:    服务器端处理上传文件的路径
	scriptData:上传文件时需要传递给服务器的其他参数，是json格式
	folder:     是服务器存储文件的目录
	fileQueue：是上传进度显示区域
	fileExt:   允许的扩展名
	fileDesc:  选择文件时出现的文件类型
	onComplete：可选参数，单个文件上传完时触发，参数有：
        event： event 事件对象
        Id：     上传进度队列的id
        fileObj: 是一个包含上传文件信息的对象，包括的信息有：
                                name: 文件名
                                filePath: 上传文件在服务器端的路径
                                 size:      文件的大小     
                                creationDate:文件创建的时间
                                 modificationDate：文件最后更改的时间
                                 type：是以"."开始的文件扩展名
        response:服务器端处理完上传文件后返回的文本
        data:    包含有两个参数的对象，
                 fileCount:上传队列中还剩下的文件数
                 speed:以KB/s为单位的文件上传平均速度
	onAllComplete：可选参数，全部文件上传完成时调用的函数，参数有：
        event:event 事件对象
        data: 是一个包含以下信息的对象,
              filesUploaded: 已经上传的文件总数
              errors:        上传出错的文件总数
              allBytesLoaded：已经上传文件的总大小
              speed:          以KB/s为单位的上传文件的平均速度
--%>
