package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import com.csu.petstore.domain.Cart;
import com.csu.petstore.domain.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class NewOrderAjaxServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        HttpSession session = req.getSession();
        Account loginAccount = (Account) session.getAttribute("loginAccount");
        Cart cart = (Cart) session.getAttribute("cart");
        
        PrintWriter out = resp.getWriter();
        
        // 检查登录状态
        if (loginAccount == null) {
            out.print("{\"success\":false,\"message\":\"Please sign in first.\"}");
            out.flush();
            return;
        }
        
        // 检查购物车
        if (cart == null || cart.getNumberOfItems() == 0) {
            out.print("{\"success\":false,\"message\":\"Your cart is empty.\"}");
            out.flush();
            return;
        }
        
        try {
            // 读取JSON请求体
            BufferedReader reader = req.getReader();
            StringBuilder jsonBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
            
            // 简单的JSON解析（手动解析）
            String jsonStr = jsonBody.toString();
            java.util.Map<String, String> jsonData = parseJson(jsonStr);
            
            // 创建订单对象
            Order order = new Order();
            order.initOrder(loginAccount, cart);
            
            // 设置支付信息
            if (jsonData.containsKey("cardType") && jsonData.get("cardType") != null) {
                order.setCardType(jsonData.get("cardType"));
            }
            if (jsonData.containsKey("creditCard") && jsonData.get("creditCard") != null) {
                order.setCreditCard(jsonData.get("creditCard"));
            }
            if (jsonData.containsKey("expiryDate") && jsonData.get("expiryDate") != null) {
                order.setExpiryDate(jsonData.get("expiryDate"));
            }
            
            // 设置账单地址
            if (jsonData.containsKey("billToFirstName") && jsonData.get("billToFirstName") != null) {
                order.setBillToFirstName(jsonData.get("billToFirstName"));
            }
            if (jsonData.containsKey("billToLastName") && jsonData.get("billToLastName") != null) {
                order.setBillToLastName(jsonData.get("billToLastName"));
            }
            if (jsonData.containsKey("billAddress1") && jsonData.get("billAddress1") != null) {
                order.setBillAddress1(jsonData.get("billAddress1"));
            }
            if (jsonData.containsKey("billAddress2") && jsonData.get("billAddress2") != null) {
                order.setBillAddress2(jsonData.get("billAddress2"));
            }
            if (jsonData.containsKey("billCity") && jsonData.get("billCity") != null) {
                order.setBillCity(jsonData.get("billCity"));
            }
            if (jsonData.containsKey("billState") && jsonData.get("billState") != null) {
                order.setBillState(jsonData.get("billState"));
            }
            if (jsonData.containsKey("billZip") && jsonData.get("billZip") != null) {
                order.setBillZip(jsonData.get("billZip"));
            }
            if (jsonData.containsKey("billCountry") && jsonData.get("billCountry") != null) {
                order.setBillCountry(jsonData.get("billCountry"));
            }
            
            // 设置配送地址
            boolean shippingAddressRequired = jsonData.containsKey("shippingAddressRequired") && 
                    "true".equals(jsonData.get("shippingAddressRequired"));
            
            if (shippingAddressRequired) {
                if (jsonData.containsKey("shipToFirstName") && jsonData.get("shipToFirstName") != null) {
                    order.setShipToFirstName(jsonData.get("shipToFirstName"));
                }
                if (jsonData.containsKey("shipToLastName") && jsonData.get("shipToLastName") != null) {
                    order.setShipToLastName(jsonData.get("shipToLastName"));
                }
                if (jsonData.containsKey("shipAddress1") && jsonData.get("shipAddress1") != null) {
                    order.setShipAddress1(jsonData.get("shipAddress1"));
                }
                if (jsonData.containsKey("shipAddress2") && jsonData.get("shipAddress2") != null) {
                    order.setShipAddress2(jsonData.get("shipAddress2"));
                }
                if (jsonData.containsKey("shipCity") && jsonData.get("shipCity") != null) {
                    order.setShipCity(jsonData.get("shipCity"));
                }
                if (jsonData.containsKey("shipState") && jsonData.get("shipState") != null) {
                    order.setShipState(jsonData.get("shipState"));
                }
                if (jsonData.containsKey("shipZip") && jsonData.get("shipZip") != null) {
                    order.setShipZip(jsonData.get("shipZip"));
                }
                if (jsonData.containsKey("shipCountry") && jsonData.get("shipCountry") != null) {
                    order.setShipCountry(jsonData.get("shipCountry"));
                }
            }
            
            // 将订单保存到session中
            session.setAttribute("order", order);
            
            // 构建返回的订单信息JSON字符串（不包含敏感信息）
            StringBuilder orderJson = new StringBuilder();
            orderJson.append("{");
            orderJson.append("\"cardType\":\"").append(escapeJson(order.getCardType())).append("\",");
            orderJson.append("\"creditCard\":\"").append(escapeJson(maskCreditCard(order.getCreditCard()))).append("\",");
            orderJson.append("\"expiryDate\":\"").append(escapeJson(order.getExpiryDate())).append("\",");
            orderJson.append("\"billToFirstName\":\"").append(escapeJson(order.getBillToFirstName())).append("\",");
            orderJson.append("\"billToLastName\":\"").append(escapeJson(order.getBillToLastName())).append("\",");
            orderJson.append("\"billAddress1\":\"").append(escapeJson(order.getBillAddress1())).append("\",");
            orderJson.append("\"billAddress2\":\"").append(escapeJson(order.getBillAddress2() != null ? order.getBillAddress2() : "")).append("\",");
            orderJson.append("\"billCity\":\"").append(escapeJson(order.getBillCity())).append("\",");
            orderJson.append("\"billState\":\"").append(escapeJson(order.getBillState())).append("\",");
            orderJson.append("\"billZip\":\"").append(escapeJson(order.getBillZip())).append("\",");
            orderJson.append("\"billCountry\":\"").append(escapeJson(order.getBillCountry())).append("\",");
            orderJson.append("\"shipToFirstName\":\"").append(escapeJson(order.getShipToFirstName())).append("\",");
            orderJson.append("\"shipToLastName\":\"").append(escapeJson(order.getShipToLastName())).append("\",");
            orderJson.append("\"shipAddress1\":\"").append(escapeJson(order.getShipAddress1())).append("\",");
            orderJson.append("\"shipAddress2\":\"").append(escapeJson(order.getShipAddress2() != null ? order.getShipAddress2() : "")).append("\",");
            orderJson.append("\"shipCity\":\"").append(escapeJson(order.getShipCity())).append("\",");
            orderJson.append("\"shipState\":\"").append(escapeJson(order.getShipState())).append("\",");
            orderJson.append("\"shipZip\":\"").append(escapeJson(order.getShipZip())).append("\",");
            orderJson.append("\"shipCountry\":\"").append(escapeJson(order.getShipCountry())).append("\"");
            orderJson.append("}");
            
            StringBuilder responseJson = new StringBuilder();
            responseJson.append("{\"success\":true,\"order\":");
            responseJson.append(orderJson.toString());
            responseJson.append("}");
            
            out.print(responseJson.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\":false,\"message\":\"Error while processing order: " + escapeJson(e.getMessage()) + "\"}");
        }
        
        out.flush();
    }
    
    // 掩码信用卡号（只显示后4位）
    private String maskCreditCard(String creditCard) {
        if (creditCard == null || creditCard.length() <= 4) {
            return creditCard;
        }
        return "**** **** **** " + creditCard.substring(creditCard.length() - 4);
    }
    
    // 简单的JSON解析方法（改进版，能处理包含逗号的字符串值）
    private java.util.Map<String, String> parseJson(String jsonStr) {
        java.util.Map<String, String> map = new java.util.HashMap<>();
        if (jsonStr == null || jsonStr.trim().isEmpty()) {
            return map;
        }
        
        // 移除花括号
        jsonStr = jsonStr.trim();
        if (jsonStr.startsWith("{")) {
            jsonStr = jsonStr.substring(1);
        }
        if (jsonStr.endsWith("}")) {
            jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
        }
        
        // 使用更健壮的方法解析键值对
        int i = 0;
        while (i < jsonStr.length()) {
            // 跳过空白字符
            while (i < jsonStr.length() && Character.isWhitespace(jsonStr.charAt(i))) {
                i++;
            }
            if (i >= jsonStr.length()) break;
            
            // 查找键的开始（引号）
            if (jsonStr.charAt(i) != '"') break;
            int keyStart = i + 1;
            i++;
            
            // 查找键的结束（引号）
            while (i < jsonStr.length() && jsonStr.charAt(i) != '"') {
                if (jsonStr.charAt(i) == '\\') i++; // 跳过转义字符
                i++;
            }
            if (i >= jsonStr.length()) break;
            String key = jsonStr.substring(keyStart, i);
            i++; // 跳过结束引号
            
            // 跳过空白字符和冒号
            while (i < jsonStr.length() && (Character.isWhitespace(jsonStr.charAt(i)) || jsonStr.charAt(i) == ':')) {
                i++;
            }
            if (i >= jsonStr.length()) break;
            
            // 查找值的开始
            String value = "";
            if (jsonStr.charAt(i) == '"') {
                // 字符串值
                int valueStart = i + 1;
                i++;
                while (i < jsonStr.length()) {
                    if (jsonStr.charAt(i) == '\\') {
                        i += 2; // 跳过转义字符
                        continue;
                    }
                    if (jsonStr.charAt(i) == '"') {
                        value = jsonStr.substring(valueStart, i);
                        i++; // 跳过结束引号
                        break;
                    }
                    i++;
                }
            } else {
                // 非字符串值（布尔值、数字等）
                int valueStart = i;
                while (i < jsonStr.length() && jsonStr.charAt(i) != ',' && jsonStr.charAt(i) != '}') {
                    i++;
                }
                value = jsonStr.substring(valueStart, i).trim();
            }
            
            map.put(key, value);
            
            // 跳过逗号
            while (i < jsonStr.length() && (Character.isWhitespace(jsonStr.charAt(i)) || jsonStr.charAt(i) == ',')) {
                i++;
            }
        }
        
        return map;
    }
    
    // JSON字符串转义
    private String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}
