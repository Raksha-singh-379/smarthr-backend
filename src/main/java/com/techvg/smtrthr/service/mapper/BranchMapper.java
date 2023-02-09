package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Branch;
import com.techvg.smtrthr.service.dto.BranchDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Branch} and its DTO {@link BranchDTO}.
 */
@Mapper(componentModel = "spring")
public interface BranchMapper extends EntityMapper<BranchDTO, Branch> {}
