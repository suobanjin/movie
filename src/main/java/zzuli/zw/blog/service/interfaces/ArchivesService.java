package zzuli.zw.blog.service.interfaces;

import zzuli.zw.blog.domain.Archives;

import java.util.List;
import java.util.Map;

public interface ArchivesService {
    Map<String, List<Archives>> archives();
}
