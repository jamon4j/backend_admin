<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<head>
</head>

<body>
 
 <form action="/g/acl/edit_user_role" method="post">
     <table width="100%" border="0" cellspacing="0" cellpadding="0">       
       <tr>
         <td  bgcolor="#f2f2f2"><input type="hidden" name="userId" value=${userId} /></td>
       </tr>
      
       <tr>
         <td height="30" align="center" bgcolor="#f2f2f2" class="left_txt2">角色Ids：</td>
         <td  bgcolor="#f2f2f2"><input type="text" name="roles" /></td>
       </tr>
   
       <tr>
         <td width="20%" height="30" align="center" bgcolor="#f2f2f2" class="left_txt2" colspan="2"><input type="submit" value="确定"/></td>
       </tr>    
       
</table>
</form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td height="30" align="center" bgcolor="#f2f2f2" class="left_txt2"><a href="javascript:self.close()" >关闭窗口</a></td>
      
     </tr>
</table>

<h5>
说明：
1、角色Ids可以可以写多个，中间用","号隔开
</h5>
</body>
</html>
　<script LANGUAGE="javascript">

 </script>