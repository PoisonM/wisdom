package com.wisdom.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import tk.mybatis.mapper.util.StringUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class Base64Utils {

    /**
     * 本地图片转换成base64字符串
     *
     * @param imgFile 图片本地路径
     * @return
     * @author zhaodeliang
     * @dateTime 2018-02-23 14:40:46
     */
    public static String ImageToBase64ByLocal(String imgFile) {
        InputStream in = null;
        byte[] data = null;

        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();

        return encoder.encode(data);
    }


    /**
     * 在线图片转换成base64字符串
     *
     * @param imgURL 图片线上路径
     * @return
     * @author zhaodeliang
     * @dateTime 2018-02-23 14:43:18
     */
    public static String ImageToBase64ByOnline(String imgURL) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(imgURL);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data.toByteArray());
    }


    /**
     * base64字符串转换成图片
     *
     * @param imgStr      base64字符串
     * @param imgFilePath 图片存放路径
     * @return
     * @author zhaodeliang
     * @dateTime 2018-02-23 14:42:17
     */
    public boolean Base64ToImage(String imgStr, String imgFilePath) {

        if (StringUtil.isEmpty(imgStr)) {
            return false;
        }
        imgStr.replace("data:image/png;base64,", "");
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            OutputStream out = new FileOutputStream(imgFilePath);

            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 字符串转InputStream
     *
     * @param imgStr
     * @return
     */
    public static InputStream getInputStream(String imgStr) throws Exception {
        imgStr = imgStr.replace("data:image/png;base64,", "");
        imgStr = imgStr.replace("\"", "");
        BASE64Decoder decoder = new BASE64Decoder();
        InputStream inputStream = null;
        // Base64解码
        byte[] b = new byte[0];
        try {
            b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            inputStream = new ByteArrayInputStream(b);
            OutputStream out = new FileOutputStream("D:\\et.png");

            out.write(b);
            out.flush();
            out.close();
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }
        return null;
    }


}