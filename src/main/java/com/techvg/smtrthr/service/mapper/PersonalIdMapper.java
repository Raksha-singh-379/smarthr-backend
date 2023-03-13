package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.PersonalId;
import com.techvg.smtrthr.service.dto.PersonalIdDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonalId} and its DTO {@link PersonalIdDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonalIdMapper extends EntityMapper<PersonalIdDTO, PersonalId> {}
