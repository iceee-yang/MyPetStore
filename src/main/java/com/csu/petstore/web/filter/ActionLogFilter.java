package com.csu.petstore.web.filter;

import com.csu.petstore.service.UserActionLogService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class ActionLogFilter implements Filter {

    private final UserActionLogService logService = new UserActionLogService();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        // 你可以按需过滤静态资源
        String uri = req.getRequestURI();
        if (uri.endsWith(".css") || uri.endsWith(".js") || uri.contains("/images/")) {
            chain.doFilter(request, response);
            return;
        }

        // 根据“请求路径/参数”判断动作
        // 注意：这里的 path 取值跟你 web.xml 映射有关，你项目里有类似 "mainForm" "signonForm" 等跳转（:contentReference[oaicite:2]{index=2}）
        String path = uri.substring(uri.lastIndexOf("/") + 1);

        // 1) 浏览 Item：你说“进入 item 算一次”
        // 假设你有 ItemFormServlet / ViewItemServlet 之类，并用参数 itemId 或 workingItemId
        if (path.equals("itemForm") || path.equals("viewItem")) {
            String itemId = req.getParameter("itemId");
            if (itemId == null) itemId = req.getParameter("workingItemId");
            logService.log(req, "VIEW_ITEM", "ITEM", itemId, "SUCCESS", "enter item page");
        }

        // 2) 目录/分类/商品列表也可以记录（可选）
        if (path.equals("categoryForm")) {
            logService.log(req, "VIEW_CATEGORY", "CATEGORY", req.getParameter("categoryId"), "SUCCESS", null);
        }
        if (path.equals("productForm")) {
            logService.log(req, "VIEW_PRODUCT", "PRODUCT", req.getParameter("productId"), "SUCCESS", null);
        }

        // 3) 加购/删购/更新购物车：这里先记“尝试”
        if (path.equals("addItemToCart")) {
            logService.log(req, "ADD_TO_CART", "ITEM", req.getParameter("workingItemId"), "SUCCESS", "attempt");
        }
        if (path.equals("removeCartItem")) {
            logService.log(req, "REMOVE_FROM_CART", "ITEM", req.getParameter("workingItemId"), "SUCCESS", "attempt");
        }
        if (path.equals("updateCart")) {
            logService.log(req, "UPDATE_CART", "CART", null, "SUCCESS", "attempt");
        }

        // 4) 下单/确认订单：先记入口
        if (path.equals("newOrder")) {
            logService.log(req, "CREATE_ORDER", "ORDER", null, "SUCCESS", "attempt");
        }
        if (path.equals("confirmOrder")) {
            logService.log(req, "CONFIRM_ORDER", "ORDER", null, "SUCCESS", "attempt");
        }

        chain.doFilter(request, response);
    }
}
