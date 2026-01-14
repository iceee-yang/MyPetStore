<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>MyPetStore</title>
    <link rel="StyleSheet" href="css/mypetstore.css" type="text/css" media="screen" />
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/top.jsp" />

<div id="Content">
    <div id="Catalog">
        <h2>未找到类别</h2>
        <p>
            你搜索的类别：
            <c:out value="${keyword}" />
            不存在。
        </p>
        <p>
            <a href="mainForm">返回首页</a>
        </p>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/bottom.jsp" />

</body>
</html>
