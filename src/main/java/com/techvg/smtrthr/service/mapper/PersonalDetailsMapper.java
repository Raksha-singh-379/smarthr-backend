package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.PersonalDetails;
import com.techvg.smtrthr.service.dto.PersonalDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonalDetails} and its DTO {@link PersonalDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonalDetailsMapper extends EntityMapper<PersonalDetailsDTO, PersonalDetails> {}
