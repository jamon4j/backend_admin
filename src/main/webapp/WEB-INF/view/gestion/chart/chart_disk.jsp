<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>cpu和内存使用率</title>
    <script type="text/javascript" src="/js/jquery/core/jquery-1.9.1.js"></script>
    <script src="/js/highchart/highstock.js"></script>
    <script src="/js/highchart/modules/exporting.js"></script>
    <script type="text/javascript">
        $(function() {
            Highcharts.setOptions({
                global : {
                    useUTC : false
                },
                colors: ['#058DC7', '#50B432', '#ED561B','#24CBE5', '#64E572', '#FF9655',
                    '#FFF263', '#6AF9C4','#DDDF00']
            });
            $.ajax({
                url:"/g/chart/${vmuuid}/initDisk",
                dataType:"json",
                async:'false',
                success:function(diskdata){
                    if(diskdata==""||diskdata==null){
                        $("#warn").text("没有数据");
                    }
                    $.each(diskdata,function(key,value){
                        $("<div id='"+key+"' style='height: 500px; min-width: 500px'></div>").insertAfter("#warn");
                        $('#'+key).highcharts('StockChart', {
                            chart : {
                                events : {
                                    load : function() {

                                        var series = this.series[0];
                                        var series1 = this.series[1];
                                        var series2 = this.series[2];
                                        var series3 = this.series[3];
                                        setInterval(function() {
                                            $.ajax({
                                                url:"/g/chart/${vmuuid}/getDisk/"+key,
                                                dataType:"json",
                                                success:function(disk){
                                                    var date = parseInt(disk.logTime)*1000;
                                                    series.addPoint([date,parseInt(disk.writeTimes)], true, true);
                                                    series1.addPoint([date, parseInt(disk.readTimes)], true, true);
                                                    series2.addPoint([date, parseInt(disk.writeBytes)], true, true);
                                                    series3.addPoint([date, parseInt(disk.readBytes)], true, true);
                                                }
                                            })
                                        }, 30000);
                                    }
                                }
                            },

                            rangeSelector: {
                                buttons: [{
                                    count: 10,
                                    type: 'minute',
                                    text: '10M'
                                }, {
                                    count: 30,
                                    type: 'minute',
                                    text: '30M'
                                }, {
                                    type: 'all',
                                    text: 'All'
                                }],
                                inputEnabled: false,
                                selected: 0
                            },

                            title : {
                                text : '硬盘使用率'+key
                            },

                            exporting: {
                                enabled: false
                            },

                            series : [{
                                name : '磁盘写次数',
                                data : (function() {
                                    var data = []
                                    $.each(value,function(index,obj){
                                        var date = parseInt(obj.logTime)*1000;
                                        data.push([
                                            date,
                                            parseInt(obj.writeTimes)
                                        ]);
                                    });
                                    return data;
                                })(),
                                shadow:true,
                                marker : {
                                    enabled : true,
                                    radius : 3
                                },
                                tooltip:{
                                    valueDecimals:1,
                                    valueSuffix:'次/s'
                                }
                            },{
                                name : '硬盘读次数',
                                data : (function() {
                                    var data = []
                                    $.each(value,function(index,obj){
                                        var date = parseInt(obj.logTime)*1000;
                                        data.push([
                                            date,
                                            parseInt(obj.readTimes)
                                        ]);
                                    });
                                    return data;
                                })(),
                                shadow:true,
                                marker : {
                                    enabled : true,
                                    radius : 3
                                },
                                tooltip:{
                                    valueDecimals:1,
                                    valueSuffix:'次/s'
                                }
                            },{
                                name : '硬盘写流量',
                                data : (function() {
                                    var data = []
                                    $.each(value,function(index,obj){
                                        var date = parseInt(obj.logTime)*1000;
                                        data.push([
                                            date,
                                            parseInt(obj.writeBytes)
                                        ]);
                                    });
                                    return data;
                                })(),
                                shadow:true,
                                marker : {
                                    enabled : true,
                                    radius : 3
                                },
                                tooltip:{
                                    valueDecimals:1,
                                    valueSuffix:'bytes/s'
                                }
                            },{
                                name : '硬盘读流量',
                                data : (function() {
                                    var data = []
                                    $.each(value,function(index,obj){
                                        var date = parseInt(obj.logTime)*1000;
                                        data.push([
                                            date,
                                            parseInt(obj.readBytes)
                                        ]);
                                    });
                                    return data;
                                })(),
                                shadow:true,
                                marker : {
                                    enabled : true,
                                    radius : 3
                                },
                                tooltip:{
                                    valueDecimals:1,
                                    valueSuffix:'bytes/s'
                                }
                            }],
                            credits: {
                                enabled: true,
                                text: 'KVM.KSYUN.COM',
                                href: 'http://KVM.KSYUN.COM',
                                position: {
                                    align: 'right',
                                    x: -10,
                                    verticalAlign: 'bottom',
                                    y: -5
                                },
                                style: {
                                    cursor: 'pointer',
                                    color: '#909090',
                                    fontSize: '9px'
                                }
                            }
                        });
                    });
                }
            });
        });
    </script>
</head>
<body>
<div id="warn"></div>
<div id="container" style="height: 500px; min-width: 500px"></div>
<div><a href="/g/chart/load/${vmuuid}">查看cpu及内存</a>
    <a href="/g/chart/network/${vmuuid}">查看网络</a>
    <a href="/g/chart/status/${vmuuid}">查看状态</a></div>
</body>
</html>
