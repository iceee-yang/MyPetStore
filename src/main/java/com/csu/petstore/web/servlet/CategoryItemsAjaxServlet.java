package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Item;
import com.csu.petstore.domain.Product;
import com.csu.petstore.service.CatalogService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CategoryItemsAjaxServlet extends HttpServlet {

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
        categoryId = categoryId.trim();

        int limit = 5;
        String limitParam = req.getParameter("limit");
        if (limitParam != null) {
            try {
                int v = Integer.parseInt(limitParam.trim());
                if (v > 0 && v <= 20) {
                    limit = v;
                }
            } catch (NumberFormatException ignored) {
            }
        }

        List<Product> products = catalogService.getProductListByCategory(categoryId);
        List<Item> items = new ArrayList<>();

        if (products != null) {
            for (Product p : products) {
                if (p == null) {
                    continue;
                }
                List<Item> itemList = catalogService.getItemListByProduct(p.getProductId());
                if (itemList == null || itemList.isEmpty()) {
                    continue;
                }
                for (Item it : itemList) {
                    if (it == null) {
                        continue;
                    }
                    items.add(it);
                    if (items.size() >= limit) {
                        break;
                    }
                }
                if (items.size() >= limit) {
                    break;
                }
            }
        }

        StringBuilder sb = new StringBuilder(512);
        sb.append("{\"ok\":true,\"categoryId\":").append(toJsonString(categoryId)).append(",\"items\":[");
        for (int i = 0; i < items.size(); i++) {
            Item it = items.get(i);
            if (i > 0) {
                sb.append(',');
            }

            String productName = null;
            Product embeddedProduct = it.getProduct();
            if (embeddedProduct != null) {
                productName = embeddedProduct.getName();
            }

            if (productName == null || productName.trim().isEmpty()) {
                try {
                    Product p = catalogService.getProduct(it.getProductId());
                    if (p != null) {
                        productName = p.getName();
                    }
                } catch (Exception ignored) {
                }
            }

            String attributes = buildAttributes(it);
            String displayName;
            if (productName != null && !productName.trim().isEmpty()) {
                displayName = productName.trim();
                if (attributes != null && !attributes.isEmpty()) {
                    displayName = displayName + " - " + attributes;
                }
            } else {
                displayName = (attributes != null && !attributes.isEmpty()) ? attributes : it.getItemId();
            }

            sb.append('{')
                    .append("\"itemId\":").append(toJsonString(it.getItemId())).append(',')
                    .append("\"productId\":").append(toJsonString(it.getProductId())).append(',')
                    .append("\"displayName\":").append(toJsonString(displayName))
                    .append('}');
        }
        sb.append("]}");

        writeJson(resp, sb.toString());
    }

    private static String buildAttributes(Item it) {
        StringBuilder sb = new StringBuilder();
        appendIfPresent(sb, it.getAttribute1());
        appendIfPresent(sb, it.getAttribute2());
        appendIfPresent(sb, it.getAttribute3());
        appendIfPresent(sb, it.getAttribute4());
        appendIfPresent(sb, it.getAttribute5());
        return sb.toString();
    }

    private static void appendIfPresent(StringBuilder sb, String v) {
        if (v == null) {
            return;
        }
        String s = v.trim();
        if (s.isEmpty()) {
            return;
        }
        if (sb.length() > 0) {
            sb.append(' ');
        }
        sb.append(s);
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
