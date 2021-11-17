package zzuli.zw.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zzuli.zw.blog.domain.Visit;

import java.util.List;

@Mapper
@Repository
public interface VisitMapper {
    int insertVisit(Visit visit);
    int findWeekCount();
    int findAllCount();
    List<Visit> findAll();
}
