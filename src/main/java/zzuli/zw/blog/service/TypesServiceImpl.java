package zzuli.zw.blog.service;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzuli.zw.blog.domain.Blog;
import zzuli.zw.blog.domain.Page;
import zzuli.zw.blog.domain.Type;
import zzuli.zw.blog.mapper.TypesMapper;
import zzuli.zw.blog.service.interfaces.BlogService;
import zzuli.zw.blog.service.interfaces.TypesService;
import zzuli.zw.blog.utils.UUIDUtils;
import java.util.List;
@Service("typesService")
public class TypesServiceImpl implements TypesService {
    private TypesMapper typesMapper;
    private BlogService blogService;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @Autowired
    public void setTypesMapper(TypesMapper typesMapper) {
        this.typesMapper = typesMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Type> findAll(String typeName, int page, int limit) {
        if (page == -1 || limit == -1) return typesMapper.findAll(typeName);
        PageHelper.startPage(page, limit);
        return typesMapper.findAll(typeName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(String id, String typeName) {
        int i = typesMapper.updateOne(id, typeName);
        return i >= 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(String id) {
        return typesMapper.deleteOne(id) >= 1;
    }

    @Override
    @Transactional
    public int delete(List<String> types) {
        return typesMapper.deleteBatch(types);
    }

    @Override
    @Transactional
    public Type add(Type type) {
        Type byTypeName = typesMapper.findByTypeName(type.getTypeName());
        if (byTypeName != null){
            return null;
        }
        String uuid = UUIDUtils.uuid();
        type.setId(uuid);
        return typesMapper.insertOne(type) >= 1 ? type : null;
    }

    @Override
    @Transactional(readOnly = true)
    public Type findById(String id) {
        return typesMapper.findById(id);
    }

    @Override
    public List<Type> findTypeAndCount() {
        return typesMapper.findTypeAndCount();
    }

    @Override
    public Page<Blog> findBlogByType(int page,int limit,String typeId) {
        return blogService.findByType(page, limit, typeId);
    }

    @Override
    public Integer findTypesCount() {
        return typesMapper.findTypeCount();
    }
}
