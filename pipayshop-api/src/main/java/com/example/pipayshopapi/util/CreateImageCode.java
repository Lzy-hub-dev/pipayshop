package com.example.pipayshopapi.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author wzx
 */
public class CreateImageCode {
    /**
     图片宽度
     */
    private final int WIDTH = 160;
    /**
     * 图片高度
     */
    private final int HEIGHT = 40;
    /**
     * 验证码字符数量
     */
    private final int CODECOUNT = 4;
    /**
     * 干扰线数量
     */
    private final int LINECOUNT = 20;
    /**
     * 验证码可选字符
     */
    private final String CODE_CHARS = "3456789abcdefghjkmnpqrstuvwxyABCDEFGHJKLMNPQRSTUVWXY";
    /**
     * 验证码图片
     */
    private BufferedImage bufferedImage;
    /**
     * 存放随机生成的验证码字符
     */
    private char[] charArray;

    public CreateImageCode() {
        // 生成验证码
        createCode();
    }

    public void createCode() {
        // 字符数组初始化
        charArray = new char[CODECOUNT];

        // 创建绘图对象
        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();

        // 设置绘图区域
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 设置字体
        Font font = new Font("Arial", Font.BOLD | Font.ITALIC, 25);
        g.setFont(font);

        // 设置干扰线
        Random random = new Random();
        for (int i = 0; i < LINECOUNT; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);

            g.setColor(getRandomColor(150, 250));
            g.drawLine(x1, y1, x2, y2);
        }

        // 随机生成验证码字符
        for (int i = 0; i < CODECOUNT; i++) {
            int index = random.nextInt(CODE_CHARS.length());
            charArray[i] = CODE_CHARS.charAt(index);

            g.setColor(getRandomColor(30, 150));
            g.drawString(String.valueOf(charArray[i]), (i + 1) * (WIDTH / (CODECOUNT + 1)), HEIGHT / 2 + 10);
        }
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

    public String getCode() {
        return new String(charArray);
    }

    public Color getRandomColor(int lower, int upper) {
        Random random = new Random();
        int r = lower + random.nextInt(upper - lower);
        int g = lower + random.nextInt(upper - lower);
        int b = lower + random.nextInt(upper - lower);

        return new Color(r, g, b);
    }
}
