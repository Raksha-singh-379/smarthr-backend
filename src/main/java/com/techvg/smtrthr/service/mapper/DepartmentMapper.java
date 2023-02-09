package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Department;
import com.techvg.smtrthr.service.dto.DepartmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {}
