package com.techvg.smtrthr.repository;

import com.techvg.smtrthr.domain.WorkDaysSetting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkDaysSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkDaysSettingRepository extends JpaRepository<WorkDaysSetting, Long>, JpaSpecificationExecutor<WorkDaysSetting> {}
