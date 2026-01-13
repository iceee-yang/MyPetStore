package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import com.csu.petstore.domain.UserActionLog;
import com.csu.petstore.service.UserActionLogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class ListLogsServlet extends HttpServlet {

    private static final String LIST_LOGS_JSP = "/WEB-INF/jsp/account/listLogs.jsp";

    private final UserActionLogService logService = new UserActionLogService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("signonForm");
            return;
        }
        Account acc = (Account) session.getAttribute("loginAccount");
        if (acc == null) {
            resp.sendRedirect("signonForm");
            return;
        }

        int page = parseInt(req.getParameter("page"), 1);
        int size = parseInt(req.getParameter("size"), 10);
        if (size <= 0) size = 10;

        List<UserActionLog> logList = logService.getLogs(acc.getUsername(), page, size);
        int total = logService.countLogs(acc.getUsername());
        int totalPages = (total + size - 1) / size;

        req.setAttribute("logList", logList);
        req.setAttribute("page", page);
        req.setAttribute("size", size);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher(LIST_LOGS_JSP).forward(req, resp);
    }

    private int parseInt(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }
}
