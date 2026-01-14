package com.csu.petstore.domain;

import java.util.Date;

public class UserActionLog {
    private Long id;
    private String username;     // 未登录可为 null
    private String action;
    private String targetType;
    private String targetId;
    private String result;       // SUCCESS / FAIL
    private String message;
    private String ip;
    private String userAgent;
    private Date createdAt;

    /**
     * 生成可读的日志描述
     * @return a human-readable string describing the action.
     */
    public String getReadableDescription() {
        String actionDesc;
        switch (this.action) {
            case "LOGIN":
                actionDesc = "logged in";
                break;
            case "VIEW_CATEGORY":
                actionDesc = "Viewed category: " + this.targetId;
                break;
            case "VIEW_PRODUCT":
                actionDesc = "Viewed product: " + this.targetId;
                break;
            case "VIEW_ITEM":
                actionDesc = "Viewed item details: " + this.targetId;
                break;
            case "ADD_TO_CART":
                actionDesc = "Added item '" + this.targetId + "' to cart";
                break;
            case "REMOVE_FROM_CART":
                actionDesc = "Removed item '" + this.targetId + "' from cart";
                break;
            case "UPDATE_CART":
                actionDesc = "Updated cart";
                break;
            case "CREATE_ORDER":
                actionDesc = "Created new order";
                break;
            case "CONFIRM_ORDER":
                actionDesc = "Confirmed order";
                break;
            default:
                actionDesc = "Performed action: " + this.action;
                break;
        }

        if ("FAIL".equals(this.result)) {
            actionDesc += " (failed)";
        }

        return actionDesc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
