package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Holiday;
import com.techvg.smtrthr.service.dto.HolidayDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Holiday} and its DTO {@link HolidayDTO}.
 */
@Mapper(componentModel = "spring")
public interface HolidayMapper extends EntityMapper<HolidayDTO, Holiday> {}
