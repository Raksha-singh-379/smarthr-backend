package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.WorkingHours;
import com.techvg.smtrthr.service.dto.WorkingHoursDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkingHours} and its DTO {@link WorkingHoursDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkingHoursMapper extends EntityMapper<WorkingHoursDTO, WorkingHours> {}
