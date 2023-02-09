package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Tds;
import com.techvg.smtrthr.service.dto.TdsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tds} and its DTO {@link TdsDTO}.
 */
@Mapper(componentModel = "spring")
public interface TdsMapper extends EntityMapper<TdsDTO, Tds> {}
