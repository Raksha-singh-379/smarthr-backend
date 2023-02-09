package com.techvg.smtrthr.repository;

import com.techvg.smtrthr.domain.ApprovalSetting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApprovalSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApprovalSettingRepository extends JpaRepository<ApprovalSetting, Long>, JpaSpecificationExecutor<ApprovalSetting> {}
