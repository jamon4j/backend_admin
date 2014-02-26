<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<head>
   	<%@include file="../inc/meta.jspf"%>
</head>

<body>
 
 <form action="/g/acl/edit_role" method="post">
     <table width="100%" border="0" cellspacing="0" cellpadding="0">       
       <tr>
         <td  bgcolor="#f2f2f2"><input type="hidden" name="roleId" value=${roleId} /></td>
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
         <td  bgcolor="#f2f2f2"><input type="text"  size="55" name="rolePower" value=${rolePower}  /></td>
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
1、角色类型:0--超级管理员，拥有所有权限。  1--一级菜单权限      2---二级权限权限    3---KPI列表权限 </br>
2、角色权限:根据uri进行授权控制，多个权限以","号分割
</h5>

</body>
</html>
　<script LANGUAGE="javascript">

 </script>