package com.ebb.util;

public class BitUtils {
    public static String byteArrayToHex(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
            sb.append(byteToHex(b));
        return sb.toString();
    }

    /** 将1个byte转换为2位16进制字符的便利方法
     *  1. 使用String.format 和 '%x'格式， 最简单的做法，api内部解决了负数影响(用256-target，转成正数)
     *  2. 使用Integer.toHexString，由于参数是int，当byte为负数时自动扩位，需要 'target & 0xFF' 来消除影响
     *  这两种方法都会省略无效的前置0，所以当target范围在0-15('00'-'0f')时，需要在前面扩充'0'，保证返回的是两位字符
     */
    public static String byteToHex(byte b){
        // String hex = String.format("%x",b);
        String hex = Integer.toHexString(b & 0xFF);
        return hex.length()==1?"0"+hex:hex;
    }
}
