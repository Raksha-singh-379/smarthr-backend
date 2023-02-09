package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Education;
import com.techvg.smtrthr.service.dto.EducationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Education} and its DTO {@link EducationDTO}.
 */
@Mapper(componentModel = "spring")
public interface EducationMapper extends EntityMapper<EducationDTO, Education> {}
