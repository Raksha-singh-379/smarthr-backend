package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.CustomLeavePolicy;
import com.techvg.smtrthr.service.dto.CustomLeavePolicyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomLeavePolicy} and its DTO {@link CustomLeavePolicyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomLeavePolicyMapper extends EntityMapper<CustomLeavePolicyDTO, CustomLeavePolicy> {}
