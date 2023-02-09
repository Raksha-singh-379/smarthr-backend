package com.techvg.smtrthr.repository;

import com.techvg.smtrthr.domain.PfDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PfDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PfDetailsRepository extends JpaRepository<PfDetails, Long>, JpaSpecificationExecutor<PfDetails> {}
