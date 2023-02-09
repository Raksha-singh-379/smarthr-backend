package com.techvg.smtrthr.service;

import com.techvg.smtrthr.domain.*; // for static metamodels
import com.techvg.smtrthr.domain.WorkinDaysSetting;
import com.techvg.smtrthr.repository.WorkinDaysSettingRepository;
import com.techvg.smtrthr.service.criteria.WorkinDaysSettingCriteria;
import com.techvg.smtrthr.service.dto.WorkinDaysSettingDTO;
import com.techvg.smtrthr.service.mapper.WorkinDaysSettingMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link WorkinDaysSetting} entities in the database.
 * The main input is a {@link WorkinDaysSettingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkinDaysSettingDTO} or a {@link Page} of {@link WorkinDaysSettingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkinDaysSettingQueryService extends QueryService<WorkinDaysSetting> {

    private final Logger log = LoggerFactory.getLogger(WorkinDaysSettingQueryService.class);

    private final WorkinDaysSettingRepository workinDaysSettingRepository;

    private final WorkinDaysSettingMapper workinDaysSettingMapper;

    public WorkinDaysSettingQueryService(
        WorkinDaysSettingRepository workinDaysSettingRepository,
        WorkinDaysSettingMapper workinDaysSettingMapper
    ) {
        this.workinDaysSettingRepository = workinDaysSettingRepository;
        this.workinDaysSettingMapper = workinDaysSettingMapper;
    }

    /**
     * Return a {@link List} of {@link WorkinDaysSettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkinDaysSettingDTO> findByCriteria(WorkinDaysSettingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkinDaysSetting> specification = createSpecification(criteria);
        return workinDaysSettingMapper.toDto(workinDaysSettingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WorkinDaysSettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkinDaysSettingDTO> findByCriteria(WorkinDaysSettingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkinDaysSetting> specification = createSpecification(criteria);
        return workinDaysSettingRepository.findAll(specification, page).map(workinDaysSettingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkinDaysSettingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkinDaysSetting> specification = createSpecification(criteria);
        return workinDaysSettingRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkinDaysSettingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkinDaysSetting> createSpecification(WorkinDaysSettingCriteria criteria) {
        Specification<WorkinDaysSetting> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkinDaysSetting_.id));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), WorkinDaysSetting_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), WorkinDaysSetting_.lastModifiedBy));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), WorkinDaysSetting_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), WorkinDaysSetting_.companyId));
            }
        }
        return specification;
    }
}
