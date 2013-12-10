<%String strError=""; java.io.FileInputStream fileInputStream=null;
    
try{
	String strOutFilePath = "/htdz/apache-tomcat-7.0.34/logs/exception.log";
	String strFilePath = "";
	String strFileName = "";
	
	strFilePath = strOutFilePath;
	strFileName = "exception.log";

	response.setContentType("application/OCTET-STREAM;charset=ISO8859_1");
	response.setHeader("Content-Disposition", "attachment; filename=\""+strFileName+"\"");
	fileInputStream=new java.io.FileInputStream(strFilePath);
	boolean isOK=false;
 	int i;        
	while ((i=fileInputStream.read()) != -1)
	{
		isOK=true;
  		out.write(i);
  	}
   if(!isOK)
   {
   		response.setContentType("text/html; charset=ISO8859_1");
		strError="文件有误!请您联系管理员!"; 
		out.print("<script>alert('"+strError+"');window.close();</script>");
		return;
   }
}catch(Exception ex)
{	
	ex.printStackTrace();
	
	response.setContentType("text/html; charset=gb2312");
	strError=ex+"对不起！文件不存在或文件打开出错！请您与管理员联系。(downloadfile.jsp)";
	strError=new String(strError.getBytes("ISO8859_1"),"GBK");
	out.print(strError);
	return;
}
finally
{	
	if(fileInputStream!=null) fileInputStream.close();
}        
%>