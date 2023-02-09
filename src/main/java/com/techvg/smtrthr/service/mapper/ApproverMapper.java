package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Approver;
import com.techvg.smtrthr.service.dto.ApproverDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Approver} and its DTO {@link ApproverDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApproverMapper extends EntityMapper<ApproverDTO, Approver> {}
