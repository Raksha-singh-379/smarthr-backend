package com.techvg.smtrthr.repository;

import com.techvg.smtrthr.domain.Approver;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Approver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApproverRepository extends JpaRepository<Approver, Long>, JpaSpecificationExecutor<Approver> {}
