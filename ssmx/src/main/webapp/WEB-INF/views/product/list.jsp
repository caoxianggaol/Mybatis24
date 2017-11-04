<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: xiaogao
  Date: 2017/11/4
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>

    <div class="container">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">查询</h3>
            </div>
            <div class="panel-body">
                <form class="form-inline">
                    <input type="text" name="productName" placeholder="商品名称" class="form-control" value="${param.productName}">
                    <select name="place" class="form-control">
                        <option value="">--选择产地--</option>
                        <c:forEach items="${placeList}" var="place">
                            <option value="${place}" ${param.place == place ? 'selected' : ''}>${place}</option>
                        </c:forEach>
                    </select>
                    <select name="typeId" class="form-control">
                        <option value="">--选择分类--</option>
                        <c:forEach items="${typeList}" var="type">
                            <option value="${type.id}" ${param.typeId == type.id ? 'selected' : ''}>${type.typeName}</option>
                        </c:forEach>
                    </select>
                    <button class="btn btn-default">搜索</button>
                </form>
            </div>
        </div>
        <%--操作后的提醒--%>
        <c:if test="${not empty message}">
            <div class="alert alert-info">
                ${message}
            </div>
        </c:if>

        <a href="/product/new" class="btn btn-primary">添加</a>
        <table class="table">
            <thead>
                <tr>
                    <th>商品名称</th>
                    <th>分类</th>
                    <th>产地</th>
                    <th>市场价</th>
                    <th>考拉价</th>
                    <th>评论数量</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageInfo.list}" var="product">
                <tr>
                    <td><a href="/product/${product.id}">${product.productName}</a></td>
                    <td>${product.kaolaType.typeName}</td>
                    <td>${product.place}</td>
                    <td>${product.marketPrice}</td>
                    <td>${product.price}</td>
                    <td>${product.commentNum}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%--<ul id="pagination-demo" class="pagination-sm"></ul>--%>
    </div>

<script src="/static/js/jquery.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/jquery.twbsPagination.js"></script>
<%--<script>
    $(function(){
        //分页
        $('#pagination-demo').twbsPagination({
            totalPages: ${PageInfo.pages},
            visiblePages: 10,
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"?p={{number}}"
        });
    });
</script>--%>
</body>
</html>
