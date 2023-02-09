package com.techvg.smtrthr.repository;

import com.techvg.smtrthr.domain.ReEnumeration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReEnumeration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReEnumerationRepository extends JpaRepository<ReEnumeration, Long>, JpaSpecificationExecutor<ReEnumeration> {}
