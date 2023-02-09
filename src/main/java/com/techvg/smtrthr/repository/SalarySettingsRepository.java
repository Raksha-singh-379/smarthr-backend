package com.techvg.smtrthr.repository;

import com.techvg.smtrthr.domain.SalarySettings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SalarySettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalarySettingsRepository extends JpaRepository<SalarySettings, Long>, JpaSpecificationExecutor<SalarySettings> {}
