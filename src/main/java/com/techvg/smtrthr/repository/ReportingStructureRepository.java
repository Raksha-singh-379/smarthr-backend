package com.techvg.smtrthr.repository;

import com.techvg.smtrthr.domain.ReportingStructure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReportingStructure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportingStructureRepository
    extends JpaRepository<ReportingStructure, Long>, JpaSpecificationExecutor<ReportingStructure> {}
