package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.ApprovalSetting;
import com.techvg.smtrthr.service.dto.ApprovalSettingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApprovalSetting} and its DTO {@link ApprovalSettingDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApprovalSettingMapper extends EntityMapper<ApprovalSettingDTO, ApprovalSetting> {}
