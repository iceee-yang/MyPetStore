<%@ include file="../common/top.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2>My Action Logs</h2>

<ul style="list-style: none; padding-left: 0;">
    <c:forEach var="log" items="${logList}">
        <li style="margin-bottom: 8px;">
            <fmt:formatDate value="${log.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
            - ${log.readableDescription}
        </li>
    </c:forEach>
</ul>

<div class="pager" style="margin-top:12px;">
    <c:if test="${page > 1}">
        <a href="listLogs?page=${page - 1}&size=${size}">&laquo; Previous</a>
    </c:if>
    <span class="current">Page ${page} / ${totalPages}</span>
    <c:if test="${page < totalPages}">
        <a href="listLogs?page=${page + 1}&size=${size}">Next &raquo;</a>
    </c:if>
</div>

<%@ include file="../common/bottom.jsp" %>
