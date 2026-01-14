package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Category;
import com.csu.petstore.domain.Item;
import com.csu.petstore.domain.Product;
import com.csu.petstore.service.CatalogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import java.io.IOException;

public class SearchCategoryServlet extends HttpServlet {

    private static final String NOT_FOUND = "/WEB-INF/jsp/catalog/categoryNotFound.jsp";

    private CatalogService catalogService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }
        keyword = keyword.trim();

        if (keyword.isEmpty()) {
            req.setAttribute("keyword", keyword);
            req.getRequestDispatcher(NOT_FOUND).forward(req, resp);
            return;
        }

        catalogService = new CatalogService();

        String categoryId = keyword.toUpperCase();
        Category category = catalogService.getCategory(categoryId);

        if (category != null) {
            resp.sendRedirect("categoryForm?categoryId=" + categoryId);
            return;
        }

        String itemId = keyword.toUpperCase();
        Item item = catalogService.getItem(itemId);
        if (item != null) {
            resp.sendRedirect("itemForm?itemId=" + itemId);
            return;
        }

        List<Product> products = catalogService.searchProductList(keyword);
        if (products != null && !products.isEmpty()) {
            resp.sendRedirect("productForm?productId=" + products.get(0).getProductId());
            return;
        }

        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher(NOT_FOUND).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
