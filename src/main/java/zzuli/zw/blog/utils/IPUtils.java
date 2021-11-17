package zzuli.zw.blog.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zzuli.zw.blog.domain.Visit;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;

public class IPUtils {
    private final static Logger logger = LoggerFactory.getLogger(IPUtils.class);

    public static Visit ip(String ip){
        URLConnection urlConnection;
        InputStream inputStream = null;
        try {
            URL url = new URL("http://whois.pconline.com.cn/ipJson.jsp?json=true&ip="+ip);
            urlConnection = url.openConnection();
            inputStream = urlConnection.getInputStream();
            String s = IOUtils.toString(inputStream, "GBK");
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(s, Visit.class);
        }catch (IOException e){
            logger.error("IPUtils---> {}", e.getClass());
            e.printStackTrace();
            return null;
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("IPUtils---> {}", e.getClass());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 作者：搬砖的coder
     * 链接：https://juejin.im/post/6844904068675010567
     * 来源：掘金
     */
    public static String getUserIp(HttpServletRequest request){
        String ip;
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1")){
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    logger.error("IPUtils---> {}", e.getClass());
                    e.printStackTrace();
                }
                assert inet != null;
                ip= inet.getHostAddress();
            }
        }else{
            ip = request.getHeader("x-forwarded-for");
            if(ip.length() > 15 && ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }

    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && !isValidAddress(ip)) {
            ip = null;
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (ip != null && !isValidAddress(ip)) {
                ip = null;
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            if (ip != null && !isValidAddress(ip)) {
                ip = null;
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip != null && !isValidAddress(ip)) {
                ip = null;
            }
        }
        if (ip != null && ip.length() > 15){
            return regxString(ip);
        }
        return ip;
    }
    private static boolean isValidAddress(String ip) {
        if (ip == null) {
            return false;
        } else {
            for (int i = 0; i < ip.length(); ++i) {
                char ch = ip.charAt(i);
                if ((ch < '0' || ch > '9') && (ch < 'A' || ch > 'F') && (ch < 'a' || ch > 'f') && ch != '.' && ch != ':') {
                    return false;
                }
            }

            return true;
        }
    }

    private static String regxString(String ip){
        if (ip.indexOf(",") > 0){
            ip = ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }
}
