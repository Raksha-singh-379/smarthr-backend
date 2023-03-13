package com.techvg.smtrthr.repository;

import com.techvg.smtrthr.domain.ApprovalLevel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApprovalLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApprovalLevelRepository extends JpaRepository<ApprovalLevel, Long>, JpaSpecificationExecutor<ApprovalLevel> {}
