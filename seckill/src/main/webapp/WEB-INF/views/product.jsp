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
    <style>
        .clock {
            font-size: 24px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="page-header">
            <h4>${seckill.productName} <small>${seckill.productTitle}</small></h4>
        </div>
        <div class="row">
            <div class="col-md-4">
                <img src="http://ozptk4oqs.bkt.clouddn.com/${seckill.productImage}?imageView2/1/w/300/h/300" alt="">
            </div>
            <div class="col-md-8">
                <h4>${seckill.productName}</h4>
                <p>${seckill.productTitle}</p>
                <h3 class="text-danger">抢购价：￥${seckill.productPrice} <small style="text-decoration:line-through">市场价：￥ ${seckill.productMarketPice}</small></h3>
                <c:choose>
                    <c:when test="${seckill.productInventory == 0}">
                        <button class="btn btn-default btn-lg" disabled>已售罄</button>
                    </c:when>
                    <c:when test="${seckill.end}">
                        <button class="btn btn-default btn-lg" disabled>已结束</button>
                    </c:when>
                    <c:when test="${seckill.start and not seckill.end}">
                        <button id="secKillBtn" class="btn btn-lg btn-danger">立即抢购</button>
                    </c:when>
                    <c:otherwise>
                        <button id="secKillBtn" class="btn btn-lg btn-danger" disabled>等待抢购</button>
                        <div class="clock">距离抢购时间：<span id="clock">xx分xx秒</span></div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div>
            ${seckill.productDesc}
        </div>
    </div>
    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/moment.js"></script>
    <script src="/static/js/jquery.countdown.min.js"></script>
    <script>
        $(function () {
            $("#clock").countdown(${seckill.startTimeTs},function(event) {
                $(this).html(event.strftime('%D 天 %H小时%M分钟%S秒'));
            }).on("finish.countdown",function () {
                $("#secKillBtn").text("立即抢购").removeAttr("disabled");
            });
        });
    </script>
</body>
</html>