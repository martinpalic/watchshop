package com.cleevio.watchshop.service.mapping;

import com.cleevio.watchshop.api.dto.WatchDto;
import com.cleevio.watchshop.persistence.entity.Watch;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WatchMapper {
    WatchDto toDto(Watch watch);
    Watch toWatch(WatchDto watchDto);
    List<WatchDto> toDtos(List<Watch> watch);
    List<Watch> toWatches(List<WatchDto> watchDto);
    void updateFromDto(WatchDto source, @MappingTarget Watch target);
}
