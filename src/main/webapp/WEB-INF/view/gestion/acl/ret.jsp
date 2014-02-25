<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>

<head>
</head>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #F8F9FA;
}
-->
</style>

<link href="${ctx}/images/skin.css" rel="stylesheet" type="text/css" />
<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0">

     <table width="100%" border="0" cellspacing="0" cellpadding="0">
     
       <tr>
         <td height="30" align="center" bgcolor="#f2f2f2" class="left_txt2">操作成功  </td>
       </tr>
            
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">

       <tr>
         <td height="30" align="center" bgcolor="#f2f2f2" class="left_txt2"><a href="#" onclick="refreshParent()">关闭窗口</a></td>
        
       </tr>
     
</table>

</body>
</html>
　<script LANGUAGE="javascript">
     function refreshParent() {
        window.opener.location.reload();
        window.close();  
      }
 </script>
