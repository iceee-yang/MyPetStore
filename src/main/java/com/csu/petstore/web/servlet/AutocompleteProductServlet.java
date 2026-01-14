package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Product;
import com.csu.petstore.service.CatalogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AutocompleteProductServlet extends HttpServlet {

    private static final int LIMIT = 10;

    private CatalogService catalogService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        if (q == null) {
            q = "";
        }
        q = q.trim();

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        if (q.length() < 2) {
            resp.getWriter().write("[]");
            return;
        }

        catalogService = new CatalogService();
        List<Product> products = catalogService.searchProductList(q);

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int count = 0;
        if (products != null) {
            for (Product p : products) {
                if (p == null || p.getProductId() == null) {
                    continue;
                }
                if (count >= LIMIT) {
                    break;
                }
                if (count > 0) {
                    sb.append(',');
                }
                String productId = escapeJson(p.getProductId());
                String name = p.getName() == null ? "" : escapeJson(p.getName());
                sb.append('{');
                sb.append("\"productId\":\"").append(productId).append("\"");
                sb.append(',');
                sb.append("\"name\":\"").append(name).append("\"");
                sb.append(',');
                sb.append("\"label\":\"").append(name).append("\"");
                sb.append('}');
                count++;
            }
        }
        sb.append(']');

        resp.getWriter().write(sb.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private static String escapeJson(String s) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"':
                    out.append("\\\"");
                    break;
                case '\\':
                    out.append("\\\\");
                    break;
                case '\b':
                    out.append("\\b");
                    break;
                case '\f':
                    out.append("\\f");
                    break;
                case '\n':
                    out.append("\\n");
                    break;
                case '\r':
                    out.append("\\r");
                    break;
                case '\t':
                    out.append("\\t");
                    break;
                default:
                    if (c < 0x20) {
                        String hex = Integer.toHexString(c);
                        out.append("\\u");
                        for (int k = hex.length(); k < 4; k++) {
                            out.append('0');
                        }
                        out.append(hex);
                    } else {
                        out.append(c);
                    }
            }
        }
        return out.toString();
    }
}
