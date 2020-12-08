package com.miracle.payment.common.utils;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

@Component
@Slf4j
@Data
public class EncryptUtil {
    private final static String UTF_8 = "UTF-8";

    //@Value("${Security.encryptKey}")
    public String encryptKey;

    //@Value("${Security.signKey}")
    public String signKey;

    public String encrypt(String content){
        try{
            String baseEncryptKey = new String(Base64.decodeBase64(encryptKey.getBytes(UTF_8)));
            byte[] key = Base64.decodeBase64(baseEncryptKey.getBytes(UTF_8));
            byte[] inputData = content.getBytes(UTF_8);
            byte[] outputData = AESEncrptUtil.encrypt(inputData,key);
            content = new String(Base64.encodeBase64(outputData),UTF_8);
            return content;
        }catch(Exception e){
            log.error("Exception in encrypt--{}",e);
            return null;
        }
    }

    public String decrypt(String content){
        try{
            String baseEncryptKey = new String(Base64.decodeBase64(encryptKey.getBytes(UTF_8)));
            byte[] key =  Base64.decodeBase64(baseEncryptKey.getBytes(UTF_8));
            byte[] contentBytes = Base64.decodeBase64(content.getBytes(UTF_8));
            byte[] outputData = AESEncrptUtil.decrypt(contentBytes,key);
            content = new String(outputData,UTF_8);
            return content;
        }catch(Exception e){
            log.error("Exception in decrypt--{}",e);
            return null;
        }
    }

    public String sign(String content){
        String sign = "";
        try{
            String baseEncryptKey = new String(Base64.decodeBase64(signKey.getBytes(UTF_8)));
            content = content + baseEncryptKey;

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes(UTF_8));
            sign = new String(Base64.encodeBase64(md.digest()),UTF_8);
            if(StringUtils.isEmpty(sign)){
                log.error("Null in sign");
                return null;
            }else{
                return sign;
            }
        }catch(Exception e){
            log.error("Exception in sign--{}",e);
            return null;
        }
    }

    public boolean versifySign(String content,String sign){
        String encryptData = sign(content);
        if (encryptData.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }



    public static void main(String[] args){
        EncryptUtil encryptUtil = new EncryptUtil();
        encryptUtil.setEncryptKey("S3dLUXVrWVgzNzdsLzBEK0pYbHk2dz09");
        encryptUtil.setSignKey("eJZq0E9sSS9GN0JGZUdJVVhmeFNBZz09");
        String key = encryptUtil.encrypt("123");
        log.info(key);
        key = encryptUtil.decrypt(key);
        log.info(key);

        key = encryptUtil.sign("123");
        log.info(key);
        boolean sign = encryptUtil.versifySign("123",key);
        log.info(String.valueOf(sign));

    }

}
