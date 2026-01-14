package com.csu.petstore.service;

import com.csu.petstore.domain.UserActionLog;
import com.csu.petstore.persistence.UserActionLogDao;
import com.csu.petstore.persistence.impl.UserActionLogDaoImpl;

import java.util.List;

public class UserActionLogService {

    private final UserActionLogDao dao = new UserActionLogDaoImpl();

    public void log(jakarta.servlet.http.HttpServletRequest req,
                    String action,
                    String targetType,
                    String targetId,
                    String result,
                    String message) {
        jakarta.servlet.http.HttpSession session = req.getSession(false);
        String username = null;
        if (session != null) {
            com.csu.petstore.domain.Account acc = (com.csu.petstore.domain.Account) session.getAttribute("loginAccount");
            if (acc != null) username = acc.getUsername();
        }
        com.csu.petstore.domain.UserActionLog log = new com.csu.petstore.domain.UserActionLog();
        log.setUsername(username);
        log.setAction(action);
        log.setTargetType(targetType);
        log.setTargetId(targetId);
        log.setResult(result);
        log.setMessage(message);
        log.setIp(getClientIp(req));
        log.setUserAgent(req.getHeader("User-Agent"));
        dao.insert(log);
    }

    public List<UserActionLog> getLogs(String username, int page, int size) {
        int offset = (page - 1) * size;
        return dao.findByUsername(username, offset, size);
    }

    public int countLogs(String username) {
        return dao.countByUsername(username);
    }

    private String getClientIp(jakarta.servlet.http.HttpServletRequest req) {
        String xff = req.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isEmpty()) {
            return xff.split(",")[0].trim();
        }
        return req.getRemoteAddr();
    }
}
