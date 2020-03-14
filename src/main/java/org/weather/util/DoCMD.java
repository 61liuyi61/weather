package org.weather.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class DoCMD implements Runnable{
    private final OutputStream ostrm_;
    private final InputStream istrm_;
    public DoCMD(InputStream istrm, OutputStream ostrm) {
        istrm_ = istrm;
        ostrm_ = ostrm;
    }

    public void run() {
        try {
            final byte[] buffer = new byte[1024];
            for (int length = 0; (length = istrm_.read(buffer)) != -1;) {
                ostrm_.write(buffer, 0, length);
            }
        } catch (Exception e) {
            throw new RuntimeException("处理命令出现错误" + e.getMessage());
        }
    }

    public static void docmd() {
        String[] command = { "cmd", };
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(command);
            new Thread(new DoCMD(p.getErrorStream(), System.err)).start();
            new Thread(new DoCMD(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            //命令行语句
            stdin.println("f: & cd F:\\Python\\mySpider\\mySpider\\spiders & clear.py");
            stdin.close();
        } catch (Exception e) {
            throw new RuntimeException("编译出现错误" + e.getMessage());
        }
    }

}