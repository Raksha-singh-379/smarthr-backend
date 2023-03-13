package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Remuneration;
import com.techvg.smtrthr.service.dto.RemunerationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Remuneration} and its DTO {@link RemunerationDTO}.
 */
@Mapper(componentModel = "spring")
public interface RemunerationMapper extends EntityMapper<RemunerationDTO, Remuneration> {}
