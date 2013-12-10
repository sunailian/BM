<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<a class="button" href="javascript:;" onclick="alertMsg.correct('您的数据提交成功！')"><span>成功提示</span></a>
<a class="button" href="javascript:;" onclick="alertMsg.info('您提交的数据有误，请检查后重新提交！')"><span>信息提示</span></a>
<a class="button" href="javascript:;" onclick="alertMsg.warn('您提交的数据有误，请检查后重新提交！')"><span>警告提示</span></a>
<a class="button" href="javascript:;" onclick="alertMsg.error('您提交的数据有误，请检查后重新提交！')"><span>错误提示</span></a>
<a class="button" href="javascript:;" onclick="testConfirmMsg('demo/common/ajaxDone.html')"><span>确认（是/否）</span></a>
<script type="text/javascript">
function testConfirmMsg(url, data){
	alertMsg.confirm("您修改的资料未保存，请选择保存或取消！", {
		okCall: function(){
			$.post(url, data, DWZ.ajaxDone, "json");
		}
	});
}
</script>


