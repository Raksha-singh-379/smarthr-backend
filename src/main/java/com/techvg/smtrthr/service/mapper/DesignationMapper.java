package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Designation;
import com.techvg.smtrthr.service.dto.DesignationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Designation} and its DTO {@link DesignationDTO}.
 */
@Mapper(componentModel = "spring")
public interface DesignationMapper extends EntityMapper<DesignationDTO, Designation> {}
