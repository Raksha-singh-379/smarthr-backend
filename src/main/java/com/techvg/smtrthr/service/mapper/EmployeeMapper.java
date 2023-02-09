package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Employee;
import com.techvg.smtrthr.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {}
