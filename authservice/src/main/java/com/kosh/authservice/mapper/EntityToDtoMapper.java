package com.kosh.authservice.mapper;

import com.kosh.authservice.dto.AppUserGetDto;
import com.kosh.authservice.dto.AppUserInsertDto;
import com.kosh.authservice.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EntityToDtoMapper {

    AppUser AppUserInsertDtoToEntity(AppUserInsertDto appUserInsertDto);

    AppUserGetDto AppUserEntityToAppUserGetDto(AppUser appUser);
}
