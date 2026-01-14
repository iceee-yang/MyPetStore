<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>我的操作日志</title>
    <style>
        table {border-collapse: collapse;width: 100%;}
        th, td {border: 1px solid #ccc;padding: 8px;text-align: left;}
        th {background: #f5f5f5;}
        .pager {margin-top: 12px;text-align: center;}
        .pager a {margin: 0 4px;text-decoration: none;}
        .pager .current {font-weight: bold;}
    </style>
</head>
<body>
<h2>我的操作日志</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>时间</th>
        <th>动作</th>
        <th>对象类型</th>
        <th>对象ID</th>
        <th>结果</th>
        <th>消息</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="log" items="${logList}">
        <tr>
            <td>${log.id}</td>
            <td><fmt:formatDate value="${log.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>${log.action}</td>
            <td>${log.targetType}</td>
            <td>${log.targetId}</td>
            <td>${log.result}</td>
            <td>${log.message}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="pager">
    <c:if test="${page > 1}">
        <a href="listLogs?page=${page - 1}&size=${size}">&laquo; 上一页</a>
    </c:if>
    <span class="current">第 ${page} / ${totalPages} 页</span>
    <c:if test="${page < totalPages}">
        <a href="listLogs?page=${page + 1}&size=${size}">下一页 &raquo;</a>
    </c:if>
</div>

</body>
</html>
