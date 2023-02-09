package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.LeavePolicy;
import com.techvg.smtrthr.service.dto.LeavePolicyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeavePolicy} and its DTO {@link LeavePolicyDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeavePolicyMapper extends EntityMapper<LeavePolicyDTO, LeavePolicy> {}
