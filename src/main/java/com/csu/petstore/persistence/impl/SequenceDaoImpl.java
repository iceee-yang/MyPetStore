package com.csu.petstore.persistence.impl;

import com.csu.petstore.persistence.DBUtil;
import com.csu.petstore.persistence.SequenceDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SequenceDaoImpl implements SequenceDao {

    private static final String GET_SEQUENCE = "SELECT nextid FROM sequence WHERE name = ?";
    private static final String UPDATE_SEQUENCE = "UPDATE sequence SET nextid = ? WHERE name = ?";

    @Override
    public int getNextId(String name) {
        int nextId = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();

            // 获取当前ID
            ps = conn.prepareStatement(GET_SEQUENCE);
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                nextId = rs.getInt("nextid");
            }

            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);

            // 更新下一个ID
            if (nextId != -1) {
                ps = conn.prepareStatement(UPDATE_SEQUENCE);
                ps.setInt(1, nextId + 1);
                ps.setString(2, name);
                ps.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }

        return nextId;
    }
}