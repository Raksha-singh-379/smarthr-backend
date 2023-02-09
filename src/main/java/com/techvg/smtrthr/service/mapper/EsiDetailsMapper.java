package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.EsiDetails;
import com.techvg.smtrthr.service.dto.EsiDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EsiDetails} and its DTO {@link EsiDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EsiDetailsMapper extends EntityMapper<EsiDetailsDTO, EsiDetails> {}
