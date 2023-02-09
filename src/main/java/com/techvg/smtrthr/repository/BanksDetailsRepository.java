package com.techvg.smtrthr.repository;

import com.techvg.smtrthr.domain.BanksDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BanksDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BanksDetailsRepository extends JpaRepository<BanksDetails, Long>, JpaSpecificationExecutor<BanksDetails> {}
