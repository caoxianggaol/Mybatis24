<%--
  Created by IntelliJ IDEA.
  User: xiaogao
  Date: 2017/11/30
  Time: 10:50
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
        <a href="/product" class="btn bg-maroon"> 返回列表</a>
        <h3>${product.productName}</h3>
        <ul>
            <li>商品价格: ${product.price}</li>
            <li>市场价格: ${product.marketPrice}</li>
            <li>产地: ${product.place}</li>
            <li>评论数量: ${product.commentNum}</li>
            <li>所属分类: ${product.kaolaType.typeName}</li>
        </ul>
        <a class="btn btn-primary" href="/product/${product.id}/edit">修改</a>
        <button id="btnDel" rel="${product.id}" class="btn btn-danger" href="javascript:;">删除</button>
        <%--<a class="btn btn-danger" href="/product/${product.id}/delete">删除</a>--%>
    </div>

    <script src="/static/js/jquery.min.js"></script>
    <script>
        $(function(){

            $("#btnDel").click(function () {
                var id = $(this).attr("rel");
                if(confirm("确定要删除吗")) {
                    window.location.href = "/product/"+id+"/delete";
                }
            });
        });
    </script>
</body>
</html>
