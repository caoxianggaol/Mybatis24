<%--
  Created by IntelliJ IDEA.
  User: xiaogao
  Date: 2017/11/9
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM | 公海客户</title>
    <%@include file="../include/css.jsp"%>
    <style>
        .name-avatar {
            display: inline-block;
            width: 50px;
            height: 50px;
            background-color: #ccc;
            border-radius: 50%;
            text-align: center;
            line-height: 50px;
            font-size: 24px;
            color: #FFF;
        }
        .table>tbody>tr:hover {
            cursor: pointer;
        }
        .table>tbody>tr>td {
            vertical-align: middle;
        }
        .star {
            font-size: 20px;
            color: #ff7400;
        }
        .pink {
            background-color: #a77a94;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/header.jsp"%>
    <!-- =============================================== -->

    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="customer_public"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">我的客户</h3>
                    <div class="box-tools pull-right">
                        <button class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 新增客户</button>
                        <button class="btn btn-primary btn-sm"><i class="fa fa-file-excel-o"></i> 导出Excel</button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tbody>
                        <tr>
                            <th width="80"></th>
                            <th>姓名</th>
                            <th>职位</th>
                            <th>跟进时间</th>
                            <th>级别</th>
                            <th>联系方式</th>
                        </tr>
                        <tr>
                            <td><span class="name-avatar">张</span></td>
                            <td>张翼飞</td>
                            <td>总经理</td>
                            <td>7月19日</td>
                            <td class="star">★★★★★</td>
                            <td><i class="fa fa-phone"></i> 18939130988 <br></td>
                        </tr>
                        <tr>
                            <td><span class="name-avatar">A</span></td>
                            <td>Aimi</td>
                            <td>销售顾问</td>
                            <td>7月18日</td>
                            <td class="star">★★★</td>
                            <td><i class="fa fa-phone"></i> 17039130988 <br></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
   <%-- <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">公海客户</h3>
                    <div class="box-tools pull-right">
                        <a href="/customer/my/new" class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 新增客户</a>
                        <button class="btn btn-primary btn-sm"><i class="fa fa-file-excel-o"></i> 导出Excel</button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tbody>
                        <tr>
                            <th width="80"></th>
                            <th>姓名</th>
                            <th>职位</th>
                            <th>跟进时间</th>
                            <th>级别</th>
                            <th>联系方式</th>
                        </tr>
                        &lt;%&ndash;如果没有查到，则处理如下&ndash;%&gt;
                        <c:if test="${empty page.list}">
                            <tr>
                                <td colspan="6">您暂无用户，可以 <a href="">新增客户</a></td>
                            </tr>
                        </c:if>
                        <c:forEach items="${page.list}" var="customer">
                            &lt;%&ndash;选择一行跳转 ，customer.id被点击的一行的&ndash;%&gt;
                            <tr class="dataRow" rel="${customer.id}">
                                    &lt;%&ndash;添加颜色&ndash;%&gt;
                                <td><span class="name-avatar ${customer.sex == '女士' ? 'pink' : ''}">${fn:substring(customer.custName, 0, 1)}</span></td>
                                <td>${customer.custName}</td>
                                <td>${customer.jobTitle}</td>
                                <td><fmt:formatDate value="${customer.lastContactTime}"/></td>
                                <td class="star">${customer.level}</td>
                                <td><i class="fa fa-phone"></i> ${customer.mobile} <br></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->--%>
    <%--底部--%>
    <!-- =============================================== -->
    <%@include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
</body>
</html>
