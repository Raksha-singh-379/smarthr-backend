package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.BanksDetails;
import com.techvg.smtrthr.service.dto.BanksDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BanksDetails} and its DTO {@link BanksDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface BanksDetailsMapper extends EntityMapper<BanksDetailsDTO, BanksDetails> {}
