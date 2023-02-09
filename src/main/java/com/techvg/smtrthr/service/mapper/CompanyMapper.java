package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Company;
import com.techvg.smtrthr.service.dto.CompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {}
