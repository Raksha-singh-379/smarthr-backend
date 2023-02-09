package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.FamilyInfo;
import com.techvg.smtrthr.service.dto.FamilyInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FamilyInfo} and its DTO {@link FamilyInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FamilyInfoMapper extends EntityMapper<FamilyInfoDTO, FamilyInfo> {}
