package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Item;
import com.csu.petstore.domain.Product;
import com.csu.petstore.service.CatalogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class ProductFormServlet extends HttpServlet {

    private CatalogService catalogService;

    private static final String PRODUCT_FORM = "/WEB-INF/jsp/catalog/product.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter("productId");
        catalogService = new CatalogService();
        Product product = catalogService.getProduct(productId);
        List<Item> itemList = catalogService.getItemListByProduct(productId);
        HttpSession session = req.getSession();
        session.setAttribute("product" , product);
        session.setAttribute("itemList" , itemList);
        req.getRequestDispatcher(PRODUCT_FORM).forward(req,resp);
    }
}

