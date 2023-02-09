package com.techvg.smtrthr.service;

import com.techvg.smtrthr.domain.ReportingStructure;
import com.techvg.smtrthr.repository.ReportingStructureRepository;
import com.techvg.smtrthr.service.dto.ReportingStructureDTO;
import com.techvg.smtrthr.service.mapper.ReportingStructureMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReportingStructure}.
 */
@Service
@Transactional
public class ReportingStructureService {

    private final Logger log = LoggerFactory.getLogger(ReportingStructureService.class);

    private final ReportingStructureRepository reportingStructureRepository;

    private final ReportingStructureMapper reportingStructureMapper;

    public ReportingStructureService(
        ReportingStructureRepository reportingStructureRepository,
        ReportingStructureMapper reportingStructureMapper
    ) {
        this.reportingStructureRepository = reportingStructureRepository;
        this.reportingStructureMapper = reportingStructureMapper;
    }

    /**
     * Save a reportingStructure.
     *
     * @param reportingStructureDTO the entity to save.
     * @return the persisted entity.
     */
    public ReportingStructureDTO save(ReportingStructureDTO reportingStructureDTO) {
        log.debug("Request to save ReportingStructure : {}", reportingStructureDTO);
        ReportingStructure reportingStructure = reportingStructureMapper.toEntity(reportingStructureDTO);
        reportingStructure = reportingStructureRepository.save(reportingStructure);
        return reportingStructureMapper.toDto(reportingStructure);
    }

    /**
     * Update a reportingStructure.
     *
     * @param reportingStructureDTO the entity to save.
     * @return the persisted entity.
     */
    public ReportingStructureDTO update(ReportingStructureDTO reportingStructureDTO) {
        log.debug("Request to update ReportingStructure : {}", reportingStructureDTO);
        ReportingStructure reportingStructure = reportingStructureMapper.toEntity(reportingStructureDTO);
        reportingStructure = reportingStructureRepository.save(reportingStructure);
        return reportingStructureMapper.toDto(reportingStructure);
    }

    /**
     * Partially update a reportingStructure.
     *
     * @param reportingStructureDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ReportingStructureDTO> partialUpdate(ReportingStructureDTO reportingStructureDTO) {
        log.debug("Request to partially update ReportingStructure : {}", reportingStructureDTO);

        return reportingStructureRepository
            .findById(reportingStructureDTO.getId())
            .map(existingReportingStructure -> {
                reportingStructureMapper.partialUpdate(existingReportingStructure, reportingStructureDTO);

                return existingReportingStructure;
            })
            .map(reportingStructureRepository::save)
            .map(reportingStructureMapper::toDto);
    }

    /**
     * Get all the reportingStructures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportingStructureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReportingStructures");
        return reportingStructureRepository.findAll(pageable).map(reportingStructureMapper::toDto);
    }

    /**
     * Get one reportingStructure by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReportingStructureDTO> findOne(Long id) {
        log.debug("Request to get ReportingStructure : {}", id);
        return reportingStructureRepository.findById(id).map(reportingStructureMapper::toDto);
    }

    /**
     * Delete the reportingStructure by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReportingStructure : {}", id);
        reportingStructureRepository.deleteById(id);
    }
}
