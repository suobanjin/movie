package zzuli.zw.blog.service.interfaces;

import zzuli.zw.blog.domain.Visit;

import java.util.List;

public interface VisitService {
    boolean insertVisit(Visit visit);
    int findWeekCount();
    int findAllCount();
    List<Visit> findAll(int page,int limit);
}
