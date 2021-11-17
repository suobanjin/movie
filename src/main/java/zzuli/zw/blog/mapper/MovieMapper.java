package zzuli.zw.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import zzuli.zw.blog.domain.Movie;

import java.util.List;

@Repository
@Mapper
public interface MovieMapper {
    @Insert("insert into movie(id,title,header_image_url,create_date,description,author,movie_link) values(" +
            "#{id},#{title},#{headerImageUrl},#{createDate},#{description},#{author},#{movieLink})")
    int insertOne(Movie movie);

    @Delete("delete from movie where id = #{id}")
    int deleteOne(String id);

    @Select("select * from movie where id = #{id}")
    @Results({
            @Result(column = "header_image_url",property = "headerImageUrl"),
            @Result(column = "movie_link",property = "movieLink"),
            @Result(column = "create_date",property = "createDate")
    })
    Movie findOne(String id);

    @Select("select * from movie")
    @Results({
            @Result(column = "header_image_url",property = "headerImageUrl"),
            @Result(column = "movie_link",property = "movieLink"),
            @Result(column = "create_date",property = "createDate")
    })
    List<Movie> findAll();

    @Delete("<script> delete from movie where id in <foreach collection='array' item='id' open='(' separator=',' close=')'>#{id}</foreach> </script>")
    int deleteArray(List<String> id);

    @Update("update movie set title = #{title},header_image_url = #{headerImageUrl},description = #{description},author = #{author},movie_link = #{movieLink} where id = #{id}")
    int editMovie(Movie movie);

}
