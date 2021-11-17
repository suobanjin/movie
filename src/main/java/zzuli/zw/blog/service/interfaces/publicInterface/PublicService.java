package zzuli.zw.blog.service.interfaces.publicInterface;
import java.util.List;

public interface PublicService<T> {
    /**
     * @MethodName: findAll
     * @date: 2020/7/11 15:14
     * @author 索半斤
     * @Description: 分页，条件查询，查询所有
     */
    List<T> findAll(String name, int page, int limit);
    /**
     * @MethodName: update
     * @date: 2020/7/12 8:23
     * @author 索半斤
     * @Description: 编辑
     */
    boolean update(String id,String name);
    /**
     * @MethodName: delete
     * @date: 2020/7/12 8:29
     * @author 索半斤
     * @Description: 删除
     */
    boolean delete(String id);
    /**
     * @MethodName: delete
     * @date: 2020/7/12 11:49
     * @author 索半斤
     * @Description: 批量删除数据
     */
    int delete(List<String> list);
    /**
     * @MethodName: add
     * @date: 2020/7/12 17:22
     * @author 索半斤
     * @Description: 添加
     */
    T add(T t);
}
