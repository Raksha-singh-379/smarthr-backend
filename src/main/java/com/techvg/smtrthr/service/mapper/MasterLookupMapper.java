package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.MasterLookup;
import com.techvg.smtrthr.service.dto.MasterLookupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MasterLookup} and its DTO {@link MasterLookupDTO}.
 */
@Mapper(componentModel = "spring")
public interface MasterLookupMapper extends EntityMapper<MasterLookupDTO, MasterLookup> {}
