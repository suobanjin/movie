package zzuli.zw.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzuli.zw.blog.domain.Archives;
import zzuli.zw.blog.mapper.BlogMapper;
import zzuli.zw.blog.service.interfaces.ArchivesService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArchivesServiceImpl implements ArchivesService {
    private BlogMapper blogMapper;

    @Autowired
    public void setBlogMapper(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    public Map<String, List<Archives>> archives() {
        List<Archives> archives = blogMapper.findArchives();
        return archives.parallelStream().collect(Collectors.groupingBy(Archives::getYear));
    }
}
