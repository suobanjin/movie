package zzuli.zw.blog;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
public class DataSourceTest {
    @Autowired
    DataSource dataSource;
    @Test
    public void fun(){
        System.out.println(dataSource);
        System.out.println(dataSource.getClass());
    }
}
