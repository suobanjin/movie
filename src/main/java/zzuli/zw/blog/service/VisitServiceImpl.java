package zzuli.zw.blog.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzuli.zw.blog.domain.Visit;
import zzuli.zw.blog.mapper.VisitMapper;
import zzuli.zw.blog.service.interfaces.VisitService;

import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {
    private VisitMapper visitMapper;
    @Autowired
    public void setVisitMapper(VisitMapper visitMapper) {
        this.visitMapper = visitMapper;
    }

    @Override
    public boolean insertVisit(Visit visit) {
        return visitMapper.insertVisit(visit) == 1;
    }

    @Override
    public int findWeekCount() {
        return visitMapper.findWeekCount();
    }

    @Override
    public int findAllCount() {
        return visitMapper.findAllCount();
    }

    @Override
    public List<Visit> findAll(int page,int limit) {
        PageHelper.startPage(page, limit);
        return visitMapper.findAll();
    }
}
