<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
</head>
<body>
    <div class="container">
        <div class="page-header">
            <h3>抢购列表</h3>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <a href="/seckill/new" class="btn btn-success">添加商品</a>
            </div>
            <div class="panel-body">
                <c:forEach items="${seckillList}" var="seckill">
                    <div class="row">
                        <div class="col-md-3">
                            <img src="http://ozptk4oqs.bkt.clouddn.com/${seckill.productImage}?imageView2/1/w/200/h/200" alt="">
                        </div>
                        <div class="col-md-9">
                            <h4><a href="/seckill/${seckill.id}">${seckill.productName}</a> </h4>
                            <p>抢购价: ￥${seckill.productPrice}</p>
                            <h4>开始时间：<fmt:formatDate value="${seckill.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></h4>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</body>
</html>