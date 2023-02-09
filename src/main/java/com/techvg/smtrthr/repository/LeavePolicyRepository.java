package com.techvg.smtrthr.repository;

import com.techvg.smtrthr.domain.LeavePolicy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeavePolicy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeavePolicyRepository extends JpaRepository<LeavePolicy, Long>, JpaSpecificationExecutor<LeavePolicy> {}
