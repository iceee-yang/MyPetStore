package com.csu.petstore.persistence.impl;

import com.csu.petstore.domain.UserActionLog;
import com.csu.petstore.persistence.DBUtil;
import com.csu.petstore.persistence.UserActionLogDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserActionLogDaoImpl implements UserActionLogDao {

    private static final String INSERT_SQL =
            "INSERT INTO user_action_log " +
                    "(username, `action`, target_type, target_id, result, message, ip, user_agent) " +
                    "VALUES (?,?,?,?,?,?,?,?)";

    private static final String SELECT_BY_USER_SQL =
            "SELECT id, username, `action`, target_type, target_id, result, message, ip, user_agent, created_at " +
                    "FROM user_action_log WHERE username = ? ORDER BY id DESC LIMIT ? OFFSET ?";

    private static final String COUNT_BY_USER_SQL =
            "SELECT COUNT(*) FROM user_action_log WHERE username = ?";

    @Override
    public void insert(UserActionLog log) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setString(1, log.getUsername());
            ps.setString(2, log.getAction());
            ps.setString(3, log.getTargetType());
            ps.setString(4, log.getTargetId());
            ps.setString(5, log.getResult());
            ps.setString(6, log.getMessage());
            ps.setString(7, log.getIp());
            ps.setString(8, log.getUserAgent());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("insert log failed", e);
        }
    }

    @Override
    public List<UserActionLog> findByUsername(String username, int offset, int limit) {
        List<UserActionLog> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_USER_SQL)) {
            ps.setString(1, username);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public int countByUsername(String username) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(COUNT_BY_USER_SQL)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private UserActionLog mapRow(ResultSet rs) throws SQLException {
        UserActionLog log = new UserActionLog();
        log.setId(rs.getLong("id"));
        log.setUsername(rs.getString("username"));
        log.setAction(rs.getString("action"));
        log.setTargetType(rs.getString("target_type"));
        log.setTargetId(rs.getString("target_id"));
        log.setResult(rs.getString("result"));
        log.setMessage(rs.getString("message"));
        log.setIp(rs.getString("ip"));
        log.setUserAgent(rs.getString("user_agent"));
        log.setCreatedAt(rs.getTimestamp("created_at"));
        return log;
    }
}
