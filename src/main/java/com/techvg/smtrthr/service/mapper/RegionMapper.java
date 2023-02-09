package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Region;
import com.techvg.smtrthr.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {}
