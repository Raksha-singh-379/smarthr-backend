package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.LeaveApplication;
import com.techvg.smtrthr.service.dto.LeaveApplicationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeaveApplication} and its DTO {@link LeaveApplicationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeaveApplicationMapper extends EntityMapper<LeaveApplicationDTO, LeaveApplication> {}
