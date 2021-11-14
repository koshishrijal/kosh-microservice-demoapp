package com.kosh.blogservice.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class BlogDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final BlogRepository blogRepository;
    public static final String SORT_CREATED_DESC_ORDER_CLAUSE = " order by blog.created desc  ";
    public static final String BLOG_TOPIC_LIKE = "topicLike";
    public static final String BLOG_TOPIC_LIKE_CLAUSE = " AND topic like :topicLike ";

    public BlogDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, BlogRepository blogRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.blogRepository = blogRepository;
    }

    public Page<Blog> getBlogs(Pageable of, Map<String, Object> filterMap) {
        StringBuffer sql = new StringBuffer(" ");
        sql.append(" SELECT * from blog where 1=1  ");
        if (filterMap.containsKey(BLOG_TOPIC_LIKE)) {
            sql.append(BLOG_TOPIC_LIKE_CLAUSE);
        }
        String countQuery = getSQLCount(sql.toString());
        log.info("Count Query is: {}", countQuery);
        int count = namedParameterJdbcTemplate.queryForObject(countQuery, filterMap, Integer.class);
        sql.append(" LIMIT " + of.getPageSize() + " OFFSET " + of.getPageNumber() * of.getPageSize());
        log.debug("Blog  FILTER SQL IS: {}", sql);
        List<Blog> blogList = namedParameterJdbcTemplate.query(sql.toString(), filterMap, new BeanPropertyRowMapper<Blog>(Blog.class));
        return new PageImpl<>(blogList, of, count);
    }

    public String getSQLCount(String sql) {
        String sqlBak = sql.toLowerCase();
        String searchValue = "from";
        String sqlCount = "select count(DISTINCT id) from " + sql.substring(sqlBak.indexOf(searchValue) + searchValue.length(), sqlBak.length());
        return sqlCount;
    }


}
