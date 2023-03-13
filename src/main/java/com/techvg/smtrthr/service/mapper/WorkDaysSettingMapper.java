package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.WorkDaysSetting;
import com.techvg.smtrthr.service.dto.WorkDaysSettingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkDaysSetting} and its DTO {@link WorkDaysSettingDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkDaysSettingMapper extends EntityMapper<WorkDaysSettingDTO, WorkDaysSetting> {}
