package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Reporting;
import com.techvg.smtrthr.service.dto.ReportingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reporting} and its DTO {@link ReportingDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportingMapper extends EntityMapper<ReportingDTO, Reporting> {}
