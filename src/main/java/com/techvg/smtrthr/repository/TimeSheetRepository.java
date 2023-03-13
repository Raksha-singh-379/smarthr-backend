package com.techvg.smtrthr.repository;

import com.techvg.smtrthr.domain.TimeSheet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TimeSheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long>, JpaSpecificationExecutor<TimeSheet> {}
