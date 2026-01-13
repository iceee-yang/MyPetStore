package com.csu.petstore.persistence;

import com.csu.petstore.domain.UserActionLog;

import java.util.List;

public interface UserActionLogDao {
    void insert(UserActionLog log);

    /** 根据用户名分页查询日志 */
    List<UserActionLog> findByUsername(String username, int offset, int limit);

    /** 统计指定用户的日志条数 */
    int countByUsername(String username);
}
