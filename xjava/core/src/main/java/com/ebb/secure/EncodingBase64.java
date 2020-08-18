package com.ebb.secure;

import com.ebb.util.LoggerUtils;

import java.io.*;
import java.util.Base64;

/**
 *  Base64并不是加密方式，它只是对给定字节流做了简单的编码转换
 *  [JSE 8]java.util.Base64工具类提供了一套静态方法获取下面三种BASE64编解码器
 *    + Basic编码器 [ a-z A-Z 0-9 + / ]  使用'='做pad
 *    + URL编码器   [ a-z A-Z 0-9 - _ ]  使用'.'做pad，对URL和FileName友好
 *    + MIME编码器  使用标准编码器，每行输出不超过76个字符并自动添加'\r\n'
 */
public class EncodingBase64 {

    /**
     *   获取编码器/解码器
     *   getEncoder() / getDecoder()
     *   getUrlEncoder() / getUrlDecoder()
     *   getMimeEncoder() / getMimeDecoder()
     */
    public void showBase64(String source) throws UnsupportedEncodingException{
        LoggerUtils.LOGGER.debug("Origin : {}",source);
        byte[] sourceByte = source.getBytes("UTF-8");

        // 编码
        byte[] target1 = java.util.Base64.getEncoder().encode(sourceByte);
        LoggerUtils.LOGGER.debug("After Encoding : {}",target1.toString());

        String target2 = java.util.Base64.getEncoder().encodeToString(sourceByte);
        LoggerUtils.LOGGER.debug("After Encoding : {}",target2);

        // 解码
        sourceByte = java.util.Base64.getDecoder().decode(target1);
        LoggerUtils.LOGGER.debug("After Decoding : {}",sourceByte);

        sourceByte = java.util.Base64.getDecoder().decode(target2);
        LoggerUtils.LOGGER.debug("After Decoding : {}",new String(sourceByte,"UTF-8"));
    }

    /**
     *   在编码和解码时提供了对流的封装，无需手工buffer
     */
    public void showWrap(){
        String src = "This is the content of any resource read from somewhere" +
                " into a stream. This can be text, image, video or any other stream.";

        // 编码器封装OutputStream, 文件/tmp/buff-base64.txt的内容是BASE64编码的形式
        try (OutputStream os = java.util.Base64.getEncoder().wrap(new FileOutputStream("/tmp/buff-base64.txt"))) {
            os.write(src.getBytes("utf-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 解码器封装InputStream, 以及以流的方式解码, 无需缓冲
        // is being consumed. There is no need to buffer the content of the file just for decoding it.
        try (InputStream is = java.util.Base64.getDecoder().wrap(new FileInputStream("/tmp/buff-base64.txt"))) {
            int len;
            byte[] bytes = new byte[100];
            while ((len = is.read(bytes)) != -1) {
                LoggerUtils.LOGGER.debug(new String(bytes, 0, len, "utf-8"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
