package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.CustomApprovar;
import com.techvg.smtrthr.service.dto.CustomApprovarDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomApprovar} and its DTO {@link CustomApprovarDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomApprovarMapper extends EntityMapper<CustomApprovarDTO, CustomApprovar> {}
