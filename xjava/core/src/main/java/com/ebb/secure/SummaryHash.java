package com.ebb.secure;

import com.ebb.util.BitUtils;
import com.ebb.util.LoggerUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *   哈希算法是一种消息摘要算法，它可以把任意长度的输入字节流，转换为统一长度的输出字节流
 *   目前主要的哈希算法分为两大家族：
 *     + MD家族
 *         + MD5
 *     + SHA家族
 *         + SHA   (又称为SHA 1)
 *         + SHA 2 (根据输出字节数分为 SHA-224，SHA-256，SHA-384, SHA-512
 */
public class SummaryHash {

    public static final String MESSAGE_DEGEST_MD2 = "MD2";
    public static final String MESSAGE_DEGEST_MD5 = "MD5";             // 16 bytes
    public static final String MESSAGE_DEGEST_SHA = "SHA";             // 20 bytes
    public static final String MESSAGE_DEGEST_SHA_224 = "SHA-224";     // 28 bytes
    public static final String MESSAGE_DEGEST_SHA_256 = "SHA-256";
    public static final String MESSAGE_DEGEST_SHA_384 = "SHA-384";
    public static final String MESSAGE_DEGEST_SHA_512 = "SHA-512";

    /**
     *  使用JSE标准库(since 1.6)的消息摘要API : MessageDigest
     */
    public void showHash(String source) throws UnsupportedEncodingException {
        LoggerUtils.LOGGER.debug("Origin String: {}",source);
        byte[] sourceByte = source.getBytes("UTF-8");

        byte[] targetByte = null;
        try {
            // 指定具体的哈希算法
            MessageDigest digest = MessageDigest.getInstance(MESSAGE_DEGEST_SHA_224);
            digest.update(sourceByte);
            targetByte = digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        LoggerUtils.LOGGER.debug("target hash bytes : {}", BitUtils.byteArrayToHex(targetByte));

    }

    /**
     *  使用commons codec封装的消息摘要API : DigestUtils
     *  1. byte[] digest()        返回byte[] 参数可以是byte[] / ByteBuffer / String / File / InputStream
     *  2. String digestAsHex()   返回16进制的字符串表示，参数同上
     */
    public void showHashCodec(String source){
        LoggerUtils.LOGGER.debug("Origin String: {}",source);

        byte [] targetByte = new DigestUtils(MESSAGE_DEGEST_SHA).digest(source);
        LoggerUtils.LOGGER.debug("target hash bytes : {}", BitUtils.byteArrayToHex(targetByte));

        String target = new DigestUtils(MESSAGE_DEGEST_SHA).digestAsHex(source);
        LoggerUtils.LOGGER.debug("target hash String : {}", target);
    }
}
