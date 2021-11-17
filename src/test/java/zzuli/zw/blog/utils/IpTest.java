package zzuli.zw.blog.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import zzuli.zw.blog.domain.Visit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class IpTest {
    @Test
    public void fun01() throws IOException {
        URL url = new URL("http://whois.pconline.com.cn/ipJson.jsp?json=true");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        String s = IOUtils.toString(inputStream,"GBK");
        ObjectMapper objectMapper = new ObjectMapper();
        Visit visit = objectMapper.readValue(s, Visit.class);
        System.out.println(visit);
    }

    @Test
    public void fun02(){
        Visit ip = IPUtils.ip("39.169.0.12");
        String newIp = new String(ip.getAddr().getBytes(), StandardCharsets.UTF_8);
        System.out.println(newIp);
        ip.setLocation(ip.getAddr());
        System.out.println(ip);
    }
}
