package com.yupi.yunpicturebackend.manager.upload;

import java.io.*;
import java.net.URL;
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
        test downloader = new test();
        downloader.saveImageToLocalStorage("https://www.codefather.cn/logo.png","./a.png");
//        downloader.processFile(
//                "https://www.codefather.cn/logo.png",
//                new File("C:\\apps\\idea\\ideaProject\\yun-picture\\yun-picture-backend\\src\\main\\java\\com\\yupi\\yunpicturebackend\\manager\\upload\\pic.png")
//        );

    }
}

