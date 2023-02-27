package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.EmployeeLeaveAccount;
import com.techvg.smtrthr.service.dto.EmployeeLeaveAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeLeaveAccount} and its DTO {@link EmployeeLeaveAccountDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeLeaveAccountMapper extends EntityMapper<EmployeeLeaveAccountDTO, EmployeeLeaveAccount> {}
