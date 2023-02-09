package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.SalarySettings;
import com.techvg.smtrthr.service.dto.SalarySettingsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SalarySettings} and its DTO {@link SalarySettingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface SalarySettingsMapper extends EntityMapper<SalarySettingsDTO, SalarySettings> {}
