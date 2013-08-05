<%--
  Created by IntelliJ IDEA.
  User: yuri
  Date: 13-7-8
  Time: 下午2:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
            <%
                request.getRequestDispatcher("./zoneList").forward(request, response);
            %>
  </body>
</html>