package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Category;
import com.csu.petstore.service.CatalogService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class CategoryInfoAjaxServlet extends HttpServlet {

    private final CatalogService catalogService = new CatalogService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json;charset=UTF-8");

        String categoryId = req.getParameter("categoryId");
        if (categoryId == null || categoryId.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeJson(resp, "{\"ok\":false,\"error\":\"Missing categoryId\"}");
            return;
        }

        Category category = catalogService.getCategory(categoryId.trim());
        if (category == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writeJson(resp, "{\"ok\":false,\"error\":\"Category not found\"}");
            return;
        }

        String json = "{\"ok\":true,\"category\":{" +
                "\"categoryId\":" + toJsonString(category.getCategoryId()) + "," +
                "\"name\":" + toJsonString(category.getName()) + "," +
                "\"description\":" + toJsonString(category.getDescription()) +
                "}}";

        writeJson(resp, json);
    }

    private static void writeJson(HttpServletResponse resp, String json) throws IOException {
        PrintWriter out = resp.getWriter();
        out.write(json);
        out.flush();
    }

    private static String toJsonString(String s) {
        if (s == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        sb.append('"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }
}
