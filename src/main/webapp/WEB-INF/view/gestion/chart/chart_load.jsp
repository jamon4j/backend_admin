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
                colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655',
                    '#FFF263', '#6AF9C4']
            });
            $.ajax({
                url:"/g/chart/${vmuuid}/initLoad",
                dataType:"json",
                async:'false',
                success:function(cpudata){
                    if(cpudata==""||cpudata==null){
                        $("#warn").text("没有数据");
                    }
                    $('#container').highcharts('StockChart', {
                        chart : {
                            events : {
                                load : function() {
                                    var series = this.series[0];
                                    var series1 = this.series[1];
                                    setInterval(function() {
                                        $.ajax({
                                            url:"/g/chart/${vmuuid}/getLoad",
                                            dataType:"json",
                                            success:function(cpu){
                                                var date = parseInt(cpu.logTime)*1000;
                                                series1.addPoint([date, parseFloat(cpu.memoryLoad)], true, true);
                                                series.addPoint([date,parseFloat(cpu.cpuLoad)], true, true);
                                            }
                                        })
                                    }, 3000);
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
                            text : 'cpu与内存使用率'
                        },

                        exporting: {
                            enabled: false
                        },

                        series : [{
                            name : 'CPU',
                            data : (function() {
                                var data = []
                                $.each(cpudata,function(index,obj){
                                    var date = parseInt(obj.logTime)*1000;
                                    data.push([
                                        date,
                                        parseFloat(obj.cpuLoad)
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
                                valueSuffix:'%'
                            }
                        },{
                            name : 'MEMORY',
                            data : (function() {
                                var data = []
                                $.each(cpudata,function(index,obj){
                                    var date = parseInt(obj.logTime)*1000;
                                    data.push([
                                        date,
                                        parseFloat(obj.memoryLoad)
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
                                valueSuffix:'%'
                            }
                        }]
                    });
                }
            });
        });
    </script>
</head>
<body>
<div id="warn"></div>
<div id="container" style="height: 500px; min-width: 500px"></div>
</body>
</html>
