package com.maxplus1.spark.demo.java.conf;

import com.maxplus1.spark.demo.java.utils.EncryptorUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by qxloo on 2017/3/21.
 */
@Configuration
public class EncyptConfig {
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        //实现自己的加密解析器
        return new StringEncryptor() {
            @Override
            public String encrypt(String s) {
                return EncryptorUtils.encrypt(s.getBytes());
            }

            @Override
            public String decrypt(String s) {
                return new String(EncryptorUtils.decrypt(s));
            }
        };
    }
}
