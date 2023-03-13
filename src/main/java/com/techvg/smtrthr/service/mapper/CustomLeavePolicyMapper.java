package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.CustomLeavePolicy;
import com.techvg.smtrthr.domain.Employee;
import com.techvg.smtrthr.domain.LeavePolicy;
import com.techvg.smtrthr.service.dto.CustomLeavePolicyDTO;
import com.techvg.smtrthr.service.dto.EmployeeDTO;
import com.techvg.smtrthr.service.dto.LeavePolicyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomLeavePolicy} and its DTO {@link CustomLeavePolicyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomLeavePolicyMapper extends EntityMapper<CustomLeavePolicyDTO, CustomLeavePolicy> {
    @Mapping(target = "leavePolicy", source = "leavePolicy", qualifiedByName = "leavePolicyId")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    CustomLeavePolicyDTO toDto(CustomLeavePolicy s);

    @Named("leavePolicyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LeavePolicyDTO toDtoLeavePolicyId(LeavePolicy leavePolicy);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
