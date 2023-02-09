package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.State;
import com.techvg.smtrthr.service.dto.StateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link State} and its DTO {@link StateDTO}.
 */
@Mapper(componentModel = "spring")
public interface StateMapper extends EntityMapper<StateDTO, State> {}
