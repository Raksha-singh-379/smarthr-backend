package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.WorkinDaysSetting;
import com.techvg.smtrthr.service.dto.WorkinDaysSettingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkinDaysSetting} and its DTO {@link WorkinDaysSettingDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkinDaysSettingMapper extends EntityMapper<WorkinDaysSettingDTO, WorkinDaysSetting> {}
