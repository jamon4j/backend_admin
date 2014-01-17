<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String uri = request.getRequestURI();
String uri_prefix = uri.substring(0, uri.lastIndexOf("/"));


%>

<div id="header">
  <h1>金山云KPI系统</h1>
</div>
 
  <div class="menu-group">
    <h2  class="first">KPI查询</h2>
    <ul>
      <br/>
      <li><a href="<%=uri_prefix%>/kpi_list.jsp?p=pub_kvm">公有云控制台总体情况</a></li>
      <br/>
      <li><a href="<%=uri_prefix%>/kpi_list.jsp?p=pub_op">公有云操作情况</a></li>
      
      <br/>
      <li><a href="<%=uri_prefix%>/kpi_list.jsp?p=pub_cash">公有云付费统计</a></li>
      <br/>
      <li><a href="<%=uri_prefix%>/kpi_list.jsp?p=user">用户统计</a></li>
      
      <br/>
      <li><a href="<%=uri_prefix%>/kpi_list.jsp?p=ks3">ks3付费统计</a></li>
    </ul>
  </div>
      
             
</div>

