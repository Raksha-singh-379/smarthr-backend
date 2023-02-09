package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.WorkExperience;
import com.techvg.smtrthr.service.dto.WorkExperienceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkExperience} and its DTO {@link WorkExperienceDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkExperienceMapper extends EntityMapper<WorkExperienceDTO, WorkExperience> {}
