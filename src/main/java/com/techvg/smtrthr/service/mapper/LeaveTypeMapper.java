package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.LeaveType;
import com.techvg.smtrthr.service.dto.LeaveTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeaveType} and its DTO {@link LeaveTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeaveTypeMapper extends EntityMapper<LeaveTypeDTO, LeaveType> {}
