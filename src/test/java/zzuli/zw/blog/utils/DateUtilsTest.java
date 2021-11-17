package zzuli.zw.blog.utils;

import org.junit.jupiter.api.Test;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.BinaryOperator;

public class DateUtilsTest {

    @Test
    public void fun01(){
        System.out.println(DateUtils.weekOfMonth(new Date()));
    }

    @Test
    public void fun02(){
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar);
    }

    @Test
    public void fun03(){
        String s = DateUtils.weekOfYear();
        System.out.println(s);
    }

    @Test
    public void fun04() {
        Books book01 = new Books("西游记", 90, 20);
        Books book02 = new Books("水浒传", 80, 10);
        List<Books> list = Arrays.asList(book01, book02);
        Integer reduce = list.stream().reduce(0, (total, book) -> total + (int) book.getPrice(), BinaryOperator.maxBy(Comparator.reverseOrder()));
        System.out.println(reduce);
        //list.stream().reduce((books, books2) -> null)
    }
}
