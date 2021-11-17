package zzuli.zw.blog.service.interfaces;

import zzuli.zw.blog.domain.Movie;
import java.util.List;

public interface MovieService {
    /**
     * @MethodName: deleteMovie
     * @date: 2020/7/16 17:07
     * @author 索半斤
     * @Description: 删除影片
     */
    boolean deleteMovie(String id);
    /**
     * @MethodName: deletesMovie
     * @date: 2020/7/16 17:07
     * @author 索半斤
     * @Description: 批量删除影片
     */
    int deleteMovies(List<String> blog);
    /**
     * @MethodName: publishMovie
     * @date: 2020/7/16 17:08
     * @author 索半斤
     * @Description: 发布影片
     */
    boolean publishMovie(Movie blog);
    /**
     * @MethodName: findById
     * @date: 2020/7/16 17:09
     * @author 索半斤
     * @Description: 根据id查找blog
     */
    Movie findById(String id);

    List<Movie> findMovies(int page,int limit);

    boolean editMovie(Movie movie);
}
