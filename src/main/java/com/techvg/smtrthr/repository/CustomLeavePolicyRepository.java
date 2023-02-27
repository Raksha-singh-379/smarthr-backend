package com.techvg.smtrthr.repository;

import com.techvg.smtrthr.domain.CustomLeavePolicy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CustomLeavePolicy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomLeavePolicyRepository extends JpaRepository<CustomLeavePolicy, Long>, JpaSpecificationExecutor<CustomLeavePolicy> {}
