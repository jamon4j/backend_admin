<?xml version="1.0" encoding="UTF-8"?>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="java.text.DateFormat, java.text.SimpleDateFormat, java.util.Date"
         contentType="text/html" %>
<%@ include file="/inc/acl.inc" %>
<%@ include file="/inc/kpi.inc" %>
<%@ include file="chart.inc" %>
<%


    String product = ServletUtil.getString(request, "p");
    if (product == null) product = "test";
    Vector kpi = getKPI(product);

    String item = ServletUtil.getString(request, "c");
    String[] items = item.split(",");

    String chart_type = ServletUtil.getString(request, "t");

    String month = ServletUtil.getString(request, "m");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    //取month个月之内的数据
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, Integer.parseInt('-'+month));
    Date starttime = cal.getTime();

%>
<anychart>
    <settings>
        <animation enabled="True"/>
    </settings>
    <charts>
        <chart plot_type="CategorizedVertical">
            <data_plot_settings default_series_type="<%=chart_type%>">
                <line_series>
                    <marker_settings>
                        <marker type="None"/>
                        <states>
                            <hover>
                                <marker type="Diamond"/>
                            </hover>
                        </states>
                    </marker_settings>
                    <line_style>
                        <line thickness="3"/>
                    </line_style>
                </line_series>
                <area_series>
                    <area_style>
                        <fill opacity="0.3" />
                    </area_style>
                </area_series>
            </data_plot_settings>
            <chart_settings>
                <title enabled="true">
                    <text>
                        <%=product%>----<% for (int i = 0; i < items.length; i++) {%><%=getTitle(items[i]) + " "%><%}%> 数据报告
                    </text>
                </title>
                <axes>
                    <y_axis>

                        <labels>
                            <format>
                                {%Value}{numDecimals:0}
                            </format>
                        </labels>
                        <title>
                            <text>num</text>
                        </title>
                    </y_axis>
                    <x_axis tickmarks_placement="Center">
                        <title enabled="False"/>
                    </x_axis>
                </axes>
                <legend enabled="True" position="Bottom" align="Center" elements_align="Center">
                    <title enabled="False"/>
                    <columns_separator enabled="False"/>
                </legend>
            </chart_settings>
            <data>
                <% for (int i = 0; i < items.length; i++) {%>
                <% int column = getColumn_Array(product, items[i]); %>
                <series name='<%=getTitle(items[i])%>'>
                    <tooltip enabled="true">
                        <format>
                            Date {%Name}{enabled:true}
                            {%SeriesName} - {%Value}{numDecimals:2}
                        </format>
                    </tooltip>
                    <%
                        for (int j = kpi.size() - 1; j >= 0; j--) {
                            String[] one = (String[]) kpi.get(j);
                            if (one[column] == null) {
                                continue;
                            }
                            String data = HtmlFilterUtil.filterAllHtmlTagNoRegex(one[column].toString());
                            Date datatime = df.parse(one[0].substring(0,10));
                    %>
                        <% if(datatime.getTime() >= starttime.getTime()) { %>
                            <point name="<%=one[0]%>" y="<%=data%>" />
                        <%}%>
                    <%}%>
                </series>
                <%}%>
            </data>
        </chart>
    </charts>
</anychart>