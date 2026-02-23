package com.yupi.yunpicturebackend.manager.upload;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class test {

    private static final String PYTHON_PATH =
            "C:/apps/anaconda/python.exe";

    // ✅ 修改为 download.py 的绝对路径
    private static final String SCRIPT_PATH =
            "C:/apps/idea/ideaProject/yun-picture/yun-picture-backend/src/main/java/com/yupi/yunpicturebackend/manager/upload/download.py";

    /**
     * 调用 Python 脚本下载图片到指定文件
     *
     * @param url  图片网址
     * @param file 目标文件
     */
    protected boolean processFile(String url, File file) {
        try {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            ProcessBuilder pb = new ProcessBuilder(
                    PYTHON_PATH,
                    SCRIPT_PATH,
                    url,
                    file.getAbsolutePath()
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // 必须读取输出，防止缓冲区满卡死
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
            boolean success = false;
            String line;
            while ((line = reader.readLine()) != null) {
                if ("SUCCESS".equals(line)) {
                    success = true;
                }
            }
            reader.close();

            boolean finished = process.waitFor(30, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                return false;
            }

            return success && process.exitValue() == 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    protected void saveImageToLocalStorage(String imageUrl, String savePath) {
        try (InputStream in = new URL(imageUrl).openStream();
             OutputStream out = new FileOutputStream(savePath)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("图片已保存到：" + savePath);
        } catch (IOException e) {
            System.out.println("保存图片失败：" + e.getMessage());
        }
    }

    // 测试
    public static void main(String[] args) {
        String url = "https://www.codefather.cn/logo.png";
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        headers.put("Accept-Encoding", "gzip, deflate"); // 注意：这行有时会出问题，可注释
        headers.put("Connection", "keep-alive");
        headers.put("Upgrade-Insecure-Requests", "1");

        HttpResponse response = HttpRequest.get(url)
                .addHeaders(headers)
                .timeout(20000) // 超时设置
                .execute();

        System.out.println("状态码：" + response.getStatus());

        if (response.isOk()) {
            File file = new File("downloaded_file");
            response.writeBody(file);
            System.out.println("下载成功：" + file.getAbsolutePath());
        } else {
            System.out.println("下载失败，响应内容：" + response.body());
        }
    }

}

