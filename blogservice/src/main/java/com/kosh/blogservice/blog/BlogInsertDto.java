package com.kosh.blogservice.blog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogInsertDto {
    @NotNull(message = " topic cannot be null")
    @NotEmpty(message = " topic cannot be empty")
    private String topic;
    @NotNull(message = "blog content cannot be null")
    @NotEmpty(message = "blog content cannot be empty")
    private String contentText;
}
