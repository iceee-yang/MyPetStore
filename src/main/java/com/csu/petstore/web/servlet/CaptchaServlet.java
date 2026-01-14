package com.csu.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 图形验证码生成Servlet
 */
public class CaptchaServlet extends HttpServlet {

    public static final String SESSION_KEY = "CAPTCHA_CODE";

    // 验证码字符集（包含大小写字母和数字）
    private static final char[] CODE_SEQUENCE = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private static final int WIDTH = 100;
    private static final int HEIGHT = 36;
    private static final int CODE_COUNT = 4;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 禁止缓存
        resp.setHeader("Pragma", "No-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/png");

        BufferedImage buffImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        Random random = new Random();

        // 填充背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 边框
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);

        // 干扰线
        g.setColor(Color.GRAY);
        for (int i = 0; i < 15; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        // 生成随机验证码
        StringBuilder code = new StringBuilder();
        g.setFont(new Font("Arial", Font.BOLD, 22));

        int x = 10;
        int y = 26;
        for (int i = 0; i < CODE_COUNT; i++) {
            char ch = CODE_SEQUENCE[random.nextInt(CODE_SEQUENCE.length)];
            code.append(ch);
            // 随机颜色
            g.setColor(new Color(random.nextInt(150), random.nextInt(150), random.nextInt(150)));
            g.drawString(String.valueOf(ch), x, y);
            x += 20;
        }

        // 将验证码存入 Session，统一转为小写以便后续不区分大小写比较
        HttpSession session = req.getSession();
        session.setAttribute(SESSION_KEY, code.toString().toLowerCase());

        g.dispose();

        // 输出图片
        ImageIO.write(buffImg, "png", resp.getOutputStream());
    }
}





