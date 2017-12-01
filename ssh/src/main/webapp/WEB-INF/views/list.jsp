<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title></title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <%--<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>--%>
<body>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-body">
                <form action="" class="form-inline">
                    <%--三者之间为and关系--%>
                   <%-- <input type="text" placeholder="商品名称" name="q_productName_like_s" class="form-control">
                    <input type="text" placeholder="价格" name="q_price_eq_bd">
                    <input type="text" placeholder="市场价格" name="q_marketPrice_eq_bd">--%>

                    <input type="text" placeholder="商品名称" name="q_like_s_productName" class="form-control" value="${param.q_like_s_productName}">
                        <%--两者之间为or关系  q_eq_bd_price_or_marketPrice--%>
                    <input type="text" placeholder="价格 或 市场价" name="q_eq_bd_price_or_marketPrice"class="form-control" value="${param.q_eq_bd_price_or_marketPrice}" >
                    <button class="btn btn-default">搜索</button>
                </form>
            </div>
        </div>
        <a href="/product/new" class="btn btn-success">添加</a>
        <table class="table">
            <thead>
            <tr>
                <th>商品名称</th>
                <th>价格</th>
                <th>市场价</th>
                <th>产地</th>
                <th>评论数量</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.items}" var="product">
                    <tr>
                        <td><a href="/product/${product.id}">${product.productName}</a></td>
                        <td>${product.price}</td>
                        <td>${product.marketPrice}</td>
                        <td>${product.place}</td>
                        <td>${product.commentNum}</td>
                        <td> <a href="javascript:;" rel="${product.id}" class="btn btn-danger delLink">删除</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <ul id="pagination" class="pagination pull-right"></ul>
    </div>

    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>
    <script src="/static/js/jquery.twbsPagination.min.js"></script>

    <script>
            $(function(){

            $(".delLink").click(function () {
                var id = $(this).attr("rel");
                if(confirm("确定要删除吗")) {
                    window.location.href = "/product/"+id+"/delete";
                }
            });
        });
            /*q_like_s_productName 搜索框中的 name值 q_like_s_productName value值 */
            $('#pagination').twbsPagination({
                totalPages: "${page.totalPageSize}",//返回对象.总页数
                visiblePages: 5,//页面显示几个页码
                first:'首页',
                last:'末页',
                prev:'上一页',
                next:'下一页',
                href:"?q_like_s_productName="+encodeURIComponent('${param.q_like_s_productName}')+"&q_eq_bd_price_or_marketPrice="+encodeURIComponent('${param.q_eq_bd_price_or_marketPrice}')+"&p={{number}}"
                });//encodeURIComponent不加时在地址栏中输入中文时，页面内容跳转，但页码不会跳到第二页
    </script>
</body>
</html>