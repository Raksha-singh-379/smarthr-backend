package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Address;
import com.techvg.smtrthr.service.dto.AddressDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {}
