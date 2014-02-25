<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<head>
</head>

<body>
 
 <form action="/g/acl/edit_role" method="post">
     <table width="100%" border="0" cellspacing="0" cellpadding="0">       
       <tr>
         <td height="30" align="center" bgcolor="#f2f2f2" class="left_txt2">角色Id：</td>
         <td  bgcolor="#f2f2f2"><input type="text" name="roleId" value=${roleId} /></td>
       </tr>
      
       <tr>
         <td height="30" align="center" bgcolor="#f2f2f2" class="left_txt2">角色类型：</td>
         <td  bgcolor="#f2f2f2"><input type="text" name="roleType" value=${roleType} /></td>
       </tr>
       
       <tr>
         <td height="30" align="center" bgcolor="#f2f2f2" class="left_txt2">角色名称：</td>
         <td  bgcolor="#f2f2f2"><input type="text" name="roleName" value=${roleName} /></td>
       </tr>
      
       <tr>
         <td height="30" align="center" bgcolor="#f2f2f2" class="left_txt2">角色权限：</td>
         <td  bgcolor="#f2f2f2"><input type="text" name="rolePower" value=${rolePower} /></td>
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

</body>
</html>
　<script LANGUAGE="javascript">

 </script>