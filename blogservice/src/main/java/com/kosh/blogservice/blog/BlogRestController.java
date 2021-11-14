package com.kosh.blogservice.blog;

import com.kosh.blogservice.exception.APIException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
@Slf4j
public class BlogRestController {

    private final BlogService blogService;

    public BlogRestController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blog")
    public ResponseEntity getBlogs(@RequestParam("page") int page,
                                   @RequestParam("size") int size,
                                   @RequestParam(name = "topicLike",required = false) String topicLike) {
        return ResponseEntity.ok(blogService.getBlogs(PageRequest.of(page, size),topicLike));
    }

    @GetMapping("/blog/{id}")
    public ResponseEntity getBlogById(@PathVariable("id") String id) throws APIException {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @DeleteMapping("/blog/{id}")
    public ResponseEntity deleteBlogById(@PathVariable("id") String id) throws APIException {
        blogService.deleteBlog(id);
        Map<String, Object> respMap = new HashMap<>();
        respMap.put("message", "deleted");
        return ResponseEntity.ok(respMap);
    }

    @PostMapping("/blog")
    public ResponseEntity saveBlog(@RequestBody @Valid BlogInsertDto blogInsertDto, Errors errors) throws APIException {
        if (errors.hasErrors()) {
            throw APIException.forClientError(collectAndFlattenAllErrorMessages(errors));
        }
        log.debug("Blog Insert Data: {}",blogInsertDto);
        return ResponseEntity.ok(blogService.saveBlog(blogInsertDto));
    }

    @PutMapping("/blog/{id}")
    public ResponseEntity updateBlog(@RequestBody @Valid BlogInsertDto blogInsertDto, @PathVariable("id") String id, Errors errors) throws APIException {
        if (errors.hasErrors()) {
            throw APIException.forClientError(collectAndFlattenAllErrorMessages(errors));
        }
        log.debug("Blog Insert Data: {}",blogInsertDto);
        return ResponseEntity.ok(blogService.updateBlog(blogInsertDto, id));
    }

    public static String collectAndFlattenAllErrorMessages(Errors errors) {
        return errors.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(","));
    }
}
