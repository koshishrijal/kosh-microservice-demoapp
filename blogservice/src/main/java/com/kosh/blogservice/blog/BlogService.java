package com.kosh.blogservice.blog;

import com.kosh.blogservice.exception.APIException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface BlogService {

    Page<Blog> getBlogs(Pageable pageable,String topicLike);

    Blog getBlogById(String id) throws APIException;

    Blog saveBlog(BlogInsertDto blogInsertDto);

    Blog updateBlog(BlogInsertDto blogInsertDto,String id) throws APIException;

    void deleteBlog(String id) throws APIException;
}
