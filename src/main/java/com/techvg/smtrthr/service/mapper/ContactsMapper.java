package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Contacts;
import com.techvg.smtrthr.service.dto.ContactsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contacts} and its DTO {@link ContactsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactsMapper extends EntityMapper<ContactsDTO, Contacts> {}
