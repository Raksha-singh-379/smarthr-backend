package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.PfDetails;
import com.techvg.smtrthr.service.dto.PfDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PfDetails} and its DTO {@link PfDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PfDetailsMapper extends EntityMapper<PfDetailsDTO, PfDetails> {}
