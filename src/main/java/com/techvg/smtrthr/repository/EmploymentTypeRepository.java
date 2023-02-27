package com.techvg.smtrthr.repository;

import com.techvg.smtrthr.domain.EmploymentType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmploymentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmploymentTypeRepository extends JpaRepository<EmploymentType, Long>, JpaSpecificationExecutor<EmploymentType> {}
