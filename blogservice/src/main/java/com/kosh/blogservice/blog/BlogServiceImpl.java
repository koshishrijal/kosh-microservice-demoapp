package com.kosh.blogservice.blog;

import com.kosh.blogservice.exception.APIException;
import com.kosh.blogservice.mapper.EntityToDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final BlogDao blogDao;
    private final EntityToDtoMapper mapper;

    public BlogServiceImpl(BlogRepository blogRepository, BlogDao blogDao, EntityToDtoMapper mapper) {
        this.blogRepository = blogRepository;
        this.blogDao = blogDao;
        this.mapper = mapper;
    }


    @Override
    public Page<Blog> getBlogs(Pageable pageable, String topicLike) {
        Map<String, Object> filterMap = new HashMap<>();
        if(topicLike!=null&&!topicLike.isEmpty()) {
            filterMap.put("topicLike", topicLike + "%");
        }
        return blogDao.getBlogs(pageable, filterMap);
    }

    @Override
    public Blog getBlogById(String id) throws APIException {
        if (!blogRepository.existsById(id)) {
            throw APIException.forNotFoundError("Not Found For given Id");
        }
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public Blog saveBlog(BlogInsertDto blogInsertDto) {
        Blog blog = mapper.blogInsertDtoToEntity(blogInsertDto);
        blog.setId(UUID.randomUUID().toString());
        blog.setCreated(new Date());
        blog.setUpdated(new Date());
        blog = blogRepository.save(blog);
        return blog;
    }

    @Override
    public Blog updateBlog(BlogInsertDto blogInsertDto, String id) throws APIException {
        if (!blogRepository.existsById(id)) {
            throw APIException.forNotFoundError("Not Found For given Id");
        }
        Blog blog = blogRepository.findById(id).get();
        blog.setTopic(blogInsertDto.getTopic());
        blog.setContentText(blogInsertDto.getContentText());
        blog.setUpdated(new Date());
        blog = blogRepository.save(blog);
        return blog;
    }

    @Override
    public void deleteBlog(String id) throws APIException {
        if (!blogRepository.existsById(id)) {
            throw APIException.forNotFoundError("Not Found For given Id");
        }
        blogRepository.deleteById(id);
    }
}
