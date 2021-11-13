package com.kosh.blogservice.blog;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class BlogRestController {

    @GetMapping("/blog")
    public ResponseEntity getBlogs() {
        return ResponseEntity.ok("blogs...");
    }
}
