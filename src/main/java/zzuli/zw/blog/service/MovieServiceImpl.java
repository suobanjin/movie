package zzuli.zw.blog.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzuli.zw.blog.domain.Movie;
import zzuli.zw.blog.mapper.MovieMapper;
import zzuli.zw.blog.service.interfaces.MovieService;
import zzuli.zw.blog.utils.UUIDUtils;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
   private MovieMapper movieMapper;
   @Autowired
    public void setMovieMapper(MovieMapper movieMapper) {
        this.movieMapper = movieMapper;
    }

    @Override
    public boolean deleteMovie(String id) {
        return movieMapper.deleteOne(id) >= 1;
    }

    @Override
    public int deleteMovies(List<String> movies) {
        return movieMapper.deleteArray(movies);
    }

    @Override
    public boolean publishMovie(Movie movie) {
        movie.setCreateDate(new Date());
        movie.setId(UUIDUtils.uuid());
        return movieMapper.insertOne(movie) >= 1;
    }

    @Override
    public Movie findById(String id) {
        return movieMapper.findOne(id);
    }

    @Override
    public List<Movie> findMovies(int page,int limit) {
        PageHelper.startPage(page, limit);
        return movieMapper.findAll();
    }


    @Override
    public boolean editMovie(Movie movie) {
        return movieMapper.editMovie(movie) >= 1;
    }
}
