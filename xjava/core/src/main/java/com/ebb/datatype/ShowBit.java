package com.ebb.datatype;

import com.ebb.util.BitUtils;
import com.ebb.util.LoggerUtils;

/** -------------------------------------------------------------------
 *  byte的相关操作
 *  1. 正常位操作     & 位与       | 位或         ^ 异或           ! 非
 *  2. 位移操作       << 左移      >> 右移        >>> 无符号右移
 *
 *  3. byte转换为16进制表示
 *  java中的byte范围是-128 - 127， 而不是无符号的0 - 255，同时负数以补码形式存储
 *  其16进制和值的关系如下：
 *     0 - 127     '00' - '7F'       0000 0000  -  0111 1111
 *  -128 -  -1     '80' - 'FF'       1000 0000  -  1111 1111
 *
 *  实际运算中，byte会自动转型为int，so 相关的位操作都要考虑负数符号位扩充的影响
 *  -------------------------------------------------------------------
 */
public class ShowBit {
    /**
     *   @See BitUtils.byteToHex 说明
     */
    public void showByteArrayToHex(byte[] target){
        String hexString = BitUtils.byteArrayToHex(target);
        LoggerUtils.LOGGER.debug("hexString : {}",hexString);
    }
}
