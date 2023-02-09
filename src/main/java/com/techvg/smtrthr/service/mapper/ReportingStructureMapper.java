package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.ReportingStructure;
import com.techvg.smtrthr.service.dto.ReportingStructureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportingStructure} and its DTO {@link ReportingStructureDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportingStructureMapper extends EntityMapper<ReportingStructureDTO, ReportingStructure> {}
