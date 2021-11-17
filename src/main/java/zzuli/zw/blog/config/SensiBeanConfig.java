package zzuli.zw.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zzuli.zw.blog.utils.sensiUtils.SensitiveFilter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Configuration
public class SensiBeanConfig {
    @Bean
    public SensitiveFilter getBean(){
        return  new SensitiveFilter(
                new BufferedReader(new InputStreamReader(
                        Objects.requireNonNull(SensiBeanConfig.class.getClassLoader().
                                getResourceAsStream("sensi_words.txt"))
                        , StandardCharsets.UTF_8)));
    }
}
