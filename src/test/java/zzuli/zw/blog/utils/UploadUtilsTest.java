package zzuli.zw.blog.utils;


import net.coobird.thumbnailator.Thumbnails;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class UploadUtilsTest {

    @Test
    public void fun01(){
        String file = "123.jpg";
        String[] split = file.split("\\.");
        System.out.println(split.length);
        System.out.println(split[0] +":::" + split[1]);
    }

    @Test
    public void fun03() throws IOException {
        BufferedImage suobanjin = UploadUtils.handleTextWaterMark("suobanjin", "2020-8-7");
        File outputfile = new File("C:/image/image.jpg");
        ImageIO.write(suobanjin, "jpg", outputfile);
    }

    @Test
    public void fun04() throws IOException {
        BufferedImage image = UploadUtils.handleTextWaterMark("索半斤", "2020-7-2");
        File outputfile = new File("C:/image/image07.jpg");
        ImageIO.write(image, "jpg", outputfile);
    }

    @Test
    public void fun05(){
        InputStream inputStream = UploadUtilsTest.class.getClassLoader().getResourceAsStream("static/images/shui.png");
        System.out.println(inputStream == null);
    }

}
