package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Category;
import com.csu.petstore.domain.Product;
import com.csu.petstore.service.CatalogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class CategoryFormServlet extends HttpServlet {

    private CatalogService catalogService;

    private static final String CATEGORY_FORM = "/WEB-INF/jsp/catalog/category.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryId = req.getParameter("categoryId");
        catalogService = new CatalogService();
        Category category = catalogService.getCategory(categoryId);
        List<Product> productList = catalogService.getProductListByCategory(categoryId);

        // 调试 Category 查询
        System.out.println("Category object: " + category);
        if (category != null) {
            System.out.println("Category name: " + category.getName());
        }

        // 调试 Product 查询
        System.out.println("Product list size: " + (productList != null ? productList.size() : "null"));
        if (productList != null && !productList.isEmpty()) {
            for (Product p : productList) {
                System.out.println("Product: " + p.getProductId() + " - " + p.getName());
            }
        }

        req.setAttribute("category" , category);
        req.setAttribute("productList" , productList);

        // 转发前再次确认属性
        System.out.println("Attributes set - category: " + req.getAttribute("category"));
        System.out.println("Attributes set - productList: " + req.getAttribute("productList"));

        req.getRequestDispatcher(CATEGORY_FORM).forward(req,resp);
    }
}
