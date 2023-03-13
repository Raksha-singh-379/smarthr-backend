package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.EmploymentType;
import com.techvg.smtrthr.domain.LeavePolicy;
import com.techvg.smtrthr.domain.LeaveType;
import com.techvg.smtrthr.service.dto.EmploymentTypeDTO;
import com.techvg.smtrthr.service.dto.LeavePolicyDTO;
import com.techvg.smtrthr.service.dto.LeaveTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeavePolicy} and its DTO {@link LeavePolicyDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeavePolicyMapper extends EntityMapper<LeavePolicyDTO, LeavePolicy> {
    @Mapping(target = "leaveType", source = "leaveType", qualifiedByName = "leaveTypeLeaveType")
    @Mapping(target = "employmentType", source = "employmentType", qualifiedByName = "employmentTypeName")
    LeavePolicyDTO toDto(LeavePolicy s);

    @Named("leaveTypeLeaveType")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "leaveType", source = "leaveType")
    LeaveTypeDTO toDtoLeaveTypeLeaveType(LeaveType leaveType);

    @Named("employmentTypeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    EmploymentTypeDTO toDtoEmploymentTypeName(EmploymentType employmentType);
}
