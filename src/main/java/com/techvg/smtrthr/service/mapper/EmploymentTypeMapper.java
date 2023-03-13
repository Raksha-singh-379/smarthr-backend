package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.EmploymentType;
import com.techvg.smtrthr.service.dto.EmploymentTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmploymentType} and its DTO {@link EmploymentTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmploymentTypeMapper extends EntityMapper<EmploymentTypeDTO, EmploymentType> {}
