<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-客户级别统计</title>
    <%@ include file="../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@include file="../include/header.jsp"%>
    <!-- =============================================== -->

    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="charts_customer"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户级别数量统计</h3>
                </div>
                <div class="box-body">
                    <div id="bar" style="height: 300px;width: 80%"></div>
                </div>
            </div>



            <div class="row">
                <div class="col-md-6">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">折线图</h3>
                        </div>
                        <div class="box-body">
                            <div id="line" style="height: 300px;width: 100%"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">漏斗图</h3>
                        </div>
                        <div class="box-body">
                            <div id="funnelChart" style="height: 300px;width: 100%"></div>
                        </div>
                    </div>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/echarts/echarts.common.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script>
    $(function () {
        var bar = echarts.init(document.getElementById("bar"));
        var line = echarts.init($("#line")[0]);
        //var funnelChart = echarts.init($("#funnelChart")[0]);

        var option = {
            title: {
                text: "客户级别数量统计",
                left: 'center'
            },
            tooltip: {},
            legend: {
                data: ['人数'],
                left: 'right'
            },
            xAxis: {
                type: 'category',
                data: []
            },
            yAxis: {},
            series: {
                name: "人数",
                type: 'bar',
                data:[]
            }

        }
        bar.setOption(option);

               $.get("/charts/customer/level").done(function (resp) {
            if(resp.state == "success") {

                var nameArray = [];
                var valueArray = [];

                var dataArray = resp.data;
                for(var i = 0;i < dataArray.length;i++) {
                    var obj = dataArray[i];
                    nameArray.push(obj.level);
                    valueArray.push(obj.count);
                }

                bar.setOption({
                    xAxis:{
                        data:nameArray
                    },
                    series:{
                        data:valueArray
                    }
                });

            } else {
                layer.msg(resp.message);
            }
        }).error(function () {
            layer.msg("加载数据异常");
        });


/*==========================================================================================================*/
        line.setOption({
            title: {
                text: "每月新增客户的数量",
                left: 'center'
            },
            tooltip: {},
            legend: {
                data: ['新增数量'],
                left: 'right'
            },
            xAxis: {
                type: 'category',
                data: []
            },
            yAxis: {},
            series: {
                name: "新增数量",
                type: 'line',
                data: []
            }
        });
        $.get("/charts/customer/create").done(function (resp) {
            if(resp.state == "success") {

                var nameArray = [];
                var valueArray = [];

                var dataArray = resp.data;
                for(var i = 0;i < dataArray.length;i++) {
                    var obj = dataArray[i];
                    nameArray.push(obj.createtime + "月份");
                    valueArray.push(obj.count);
                }

               line.setOption({
                   xAxis:{
                        data:nameArray
                    },
                   series:{
                        data:valueArray
                    }
                });

            } else {
                layer.msg(resp.message);
            }
        }).error(function () {
            layer.msg("加载数据异常");
        });
/*============================================================================================*/
        var funnelChart = echarts.init($("#funnelChart")[0]);
        var funnerlOption = {
            title: {
                text: '销售机会统计',
                subtext: '漏斗图'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}  {c}%"
            },
            toolbox: {
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            legend: {
                data: []
            },
            calculable: true,
            series: [
                {
                    name:'漏斗图',
                    type:'funnel',
                    left: '10%',
                    top: 60,
                    //x2: 80,
                    bottom: 0,
                    min: 0,
                    max: 100,
                    minSize: '0%',
                    maxSize: '100%',
                    sort: 'descending',
                    gap: 2,
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        },
                        emphasis: {
                            textStyle: {
                                fontSize: 20
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            length: 10,
                            lineStyle: {
                                width: 1,
                                type: 'solid'
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    },
                    data: []
                }
            ]
        }
        funnelChart.setOption(funnerlOption)
        $.get("/charts/customer/sale").done(function (resp) {
            if(resp.state == 'success') {
                var nameArray = [];
                var valueArray = [];
                var dataArray = resp.data;
                for (var i=0;i<dataArray.length;i++) {
                    var obj = dataArray[i];
                    valueArray.push(obj.progress);
                    nameArray.push(obj.name);
                }
                funnel.setOption({
                    legend:{
                        data:nameArray
                    },
                    series:{
                        data: dataArray
                    }
                });
            } else {
                layer.msg(resp.message)
            }
        }).error(function () {
            layer.msg("加载数据异常");
        });


/*=============================================================================*/

        /* $.get("/charts/customer/bar.json").done(function (resp) {
             var levelArray = [];
             var dataArray = [];
             //[{level:'*',num:3},{level:'**',num:5}]
             for(var i = 0;i < resp.data.length;i++) {
                 var obj = resp.data[i];
                 levelArray.push(obj.level);
                 dataArray.push(obj.num);
             }

             bar.setOption({
                 xAxis: {
                     data: levelArray
                 },
                 series: {
                     data: dataArray
                 }
             });
         }).error(function () {
             layer.msg("获取数据异常");
         });*/


    });
</script>

</body>
</html>
