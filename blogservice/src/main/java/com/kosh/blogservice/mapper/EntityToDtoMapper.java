package com.kosh.blogservice.mapper;

import com.kosh.blogservice.blog.Blog;
import com.kosh.blogservice.blog.BlogInsertDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EntityToDtoMapper {
    Blog blogInsertDtoToEntity(BlogInsertDto blogInsertDto);
}
