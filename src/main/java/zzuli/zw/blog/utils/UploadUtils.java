package zzuli.zw.blog.utils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class UploadUtils {

    public static String upload(MultipartFile file, String path,  HttpServletRequest request) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) return null;
        int index = originalFilename.lastIndexOf(".");
        if (index == -1)return null;
        String[] split = originalFilename.split("\\.");
        String newFileName = split[0] + UUIDUtils.uuid();
        String finalFileName = newFileName + originalFilename.substring(index);
        String childrenDir = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File localFile = new File(path,childrenDir);
        localFile.mkdir();
        try {
            InputStream inputStream = file.getInputStream();
            Thumbnails.of(inputStream).scale(1f).outputQuality(0.25f).
                    watermark(Positions.BOTTOM_LEFT,
                            ImageIO.read(Objects.requireNonNull(UploadUtils.class.getClassLoader().getResourceAsStream("static/images/shui.png"))),
                            0.8f)
                    .toFile(new File(localFile,finalFileName));
            String url = request.getScheme() +"://" + request.getServerName() +":" +request.getServerPort() +"/";
            String finalUrl =  url + childrenDir + "/" + finalFileName;
            return StringUtils.trimAllWhitespace(finalUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String uploadMovie(MultipartFile file, String path,  HttpServletRequest request) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) return null;
        int index = originalFilename.lastIndexOf(".");
        if (index == -1)return null;
        String newFileName = UUIDUtils.uuid();
        String finalFileName = newFileName + originalFilename.substring(index);
        String childrenDir = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File localFile = new File(path,childrenDir);
        localFile.mkdir();
        try {
            String copyPath = localFile+"/"+finalFileName;
            FileOutputStream outputStream = new FileOutputStream(copyPath);
            IOUtils.copy(file.getInputStream(), outputStream);
            outputStream.close();
        } catch (IOException e) {
            return null;
        }
        String url = request.getScheme() +"://" + request.getServerName() +":" +request.getServerPort() +"/";
        String finalUrl =   url + childrenDir + "/" + finalFileName;
        return StringUtils.trimAllWhitespace(finalUrl);
    }

    public static BufferedImage handleTextWaterMark(String name,String date){
        BufferedImage bimage = new BufferedImage(120, 50,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.setColor(Color.gray);
        g.setBackground(Color.white);
        g.drawString(name+"  "+date, 100 / 5, 100 / 5); // 添加水印的文字和设置水印文字出现的内容
        g.dispose();
        return bimage;
    }
}
