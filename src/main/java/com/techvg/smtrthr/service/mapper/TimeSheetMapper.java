package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Attendance;
import com.techvg.smtrthr.domain.TimeSheet;
import com.techvg.smtrthr.service.dto.AttendanceDTO;
import com.techvg.smtrthr.service.dto.TimeSheetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TimeSheet} and its DTO {@link TimeSheetDTO}.
 */
@Mapper(componentModel = "spring")
public interface TimeSheetMapper extends EntityMapper<TimeSheetDTO, TimeSheet> {
    @Mapping(target = "attendance", source = "attendance", qualifiedByName = "attendanceId")
    TimeSheetDTO toDto(TimeSheet s);

    @Named("attendanceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttendanceDTO toDtoAttendanceId(Attendance attendance);
}
