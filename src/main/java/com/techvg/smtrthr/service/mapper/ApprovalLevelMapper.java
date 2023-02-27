package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.ApprovalLevel;
import com.techvg.smtrthr.service.dto.ApprovalLevelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApprovalLevel} and its DTO {@link ApprovalLevelDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApprovalLevelMapper extends EntityMapper<ApprovalLevelDTO, ApprovalLevel> {}
