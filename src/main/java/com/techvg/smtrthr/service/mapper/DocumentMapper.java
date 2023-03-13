package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Document;
import com.techvg.smtrthr.service.dto.DocumentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {}
