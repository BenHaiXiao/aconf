/** 
* Base64Utils.java Create on 2014-5-22 下午02:21:02 
* copyright (c) by DuoWan 2011 
*/
package com.github.bh.aconf.utils;
import org.apache.cxf.common.util.Base64Utility;
import org.bouncycastle.util.encoders.Base64;

public class Base64Utils {

    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;
    
    /**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     * 
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) throws Exception {
        return Base64.decode(base64.getBytes());
    }
    
    /**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     * 
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) throws Exception {
        return new String(Base64Utility.encode(bytes));
    }
   
    
    
}