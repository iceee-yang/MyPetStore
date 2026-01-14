package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Item;
import com.csu.petstore.service.CatalogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ItemFormServlet extends HttpServlet {

    private static final String ITEM_FORM = "/WEB-INF/jsp/catalog/item.jsp";
    private static final String NOT_FOUND = "/WEB-INF/jsp/catalog/categoryNotFound.jsp";

    private CatalogService catalogService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemId = req.getParameter("itemId");
        if (itemId == null) {
            itemId = "";
        }
        itemId = itemId.trim();

        if (itemId.isEmpty()) {
            req.setAttribute("keyword", itemId);
            req.getRequestDispatcher(NOT_FOUND).forward(req, resp);
            return;
        }

        catalogService = new CatalogService();
        Item item = catalogService.getItem(itemId);

        if (item == null) {
            req.setAttribute("keyword", itemId);
            req.getRequestDispatcher(NOT_FOUND).forward(req, resp);
            return;
        }

        req.setAttribute("item", item);
        req.getRequestDispatcher(ITEM_FORM).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
