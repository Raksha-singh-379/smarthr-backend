package com.techvg.smtrthr.repository;

import com.techvg.smtrthr.domain.WorkinDaysSetting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkinDaysSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkinDaysSettingRepository extends JpaRepository<WorkinDaysSetting, Long>, JpaSpecificationExecutor<WorkinDaysSetting> {}
