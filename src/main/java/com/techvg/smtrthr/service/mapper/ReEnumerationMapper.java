package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.ReEnumeration;
import com.techvg.smtrthr.service.dto.ReEnumerationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReEnumeration} and its DTO {@link ReEnumerationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReEnumerationMapper extends EntityMapper<ReEnumerationDTO, ReEnumeration> {}
