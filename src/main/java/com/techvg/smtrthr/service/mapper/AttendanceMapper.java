package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Attendance;
import com.techvg.smtrthr.service.dto.AttendanceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attendance} and its DTO {@link AttendanceDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttendanceMapper extends EntityMapper<AttendanceDTO, Attendance> {}
