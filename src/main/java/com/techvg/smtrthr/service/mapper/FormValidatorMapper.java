package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.FormValidator;
import com.techvg.smtrthr.service.dto.FormValidatorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FormValidator} and its DTO {@link FormValidatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface FormValidatorMapper extends EntityMapper<FormValidatorDTO, FormValidator> {}
