<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>cpu和内存使用率</title>
    <script type="text/javascript" src="/js/jquery/core/jquery-1.9.1.js"></script>
    <script src="/js/highchart/highstock.js"></script>
    <script src="/js/highchart/modules/exporting.js"></script>
    <script type="text/javascript" src="http://www.highcharts.com/samples/data/usdeur.js"></script>
    <script type="text/javascript">
        $(function () {
            Highcharts.setOptions({
                global: {
                    useUTC: false
                },
                colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655',
                    '#FFF263', '#6AF9C4']
            });
            $.ajax({
                url:"/g/chart/${vmuuid}/initStatus",
                dataType:"json",
                async:'false',
                success:function(statusdata){
                    if(statusdata==""||statusdata==null){
                        $("#warn").text("没有数据");
                    }
                    $('#container').highcharts('StockChart', {
                        chart: {
                            events : {
                                load : function() {
                                    var series = this.series[0];
                                    setInterval(function() {
                                        $.ajax({
                                            url:"/g/chart/${vmuuid}/getStatus",
                                            dataType:"json",
                                            success:function(status){
                                                var ss;
                                                var date = parseInt(status.logTime)*1000;
                                                if(status.status=="RUNNING"){
                                                    ss=1;
                                                }else if(status.status=="NEW"){
                                                    ss=0;
                                                }else if(status.status=="DESTROYED"){
                                                    ss=-1;
                                                }
                                                series.addPoint([date,parseInt(ss)], true, true);
                                            }
                                        })
                                    }, 3000);
                                }
                            }
                        },
                        tooltip: {
                            formatter: function() {
                                var s = '<b>'+ Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'</b>';

                                $.each(this.points, function(i, point) {
                                    if(point.y==1){
                                        s += '<br/>状态:<strong style="color:green;">RUNNING</strong>';
                                    }else if(point.y==0){
                                        s += '<br/>状态:<strong style="color:yellow;">RUNNING</strong>';
                                    }else if(point.y==-1){
                                        s += '<br/>状态:<strong style="color:red;">DESTROYED</strong>';
                                    }

                                });

                                return s;
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
                            text : '虚机状态'
                        },
                        series: [{
                            name: '状态',
                            data: (function() {
                                var data = []
                                $.each(statusdata,function(index,obj){
                                    var ss;
                                    var date = parseInt(obj.logTime)*1000;
                                    if(obj.status=="RUNNING"){
                                        ss=1;
                                    }else if(obj.status=="NEW"){
                                        ss=0;
                                    }else if(obj.status=="DESTROYED"){
                                        ss=-1;
                                    }
                                    data.push([date,parseInt(ss)]);
                                });
                                return data;
                            })(),
                            shadow:true,
                            marker : {
                                enabled : true,
                                radius : 3
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
