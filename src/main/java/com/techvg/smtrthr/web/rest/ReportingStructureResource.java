package com.techvg.smtrthr.web.rest;

import com.techvg.smtrthr.repository.ReportingStructureRepository;
import com.techvg.smtrthr.service.ReportingStructureQueryService;
import com.techvg.smtrthr.service.ReportingStructureService;
import com.techvg.smtrthr.service.criteria.ReportingStructureCriteria;
import com.techvg.smtrthr.service.dto.ReportingStructureDTO;
import com.techvg.smtrthr.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.smtrthr.domain.ReportingStructure}.
 */
@RestController
@RequestMapping("/api")
public class ReportingStructureResource {

    private final Logger log = LoggerFactory.getLogger(ReportingStructureResource.class);

    private static final String ENTITY_NAME = "reportingStructure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportingStructureService reportingStructureService;

    private final ReportingStructureRepository reportingStructureRepository;

    private final ReportingStructureQueryService reportingStructureQueryService;

    public ReportingStructureResource(
        ReportingStructureService reportingStructureService,
        ReportingStructureRepository reportingStructureRepository,
        ReportingStructureQueryService reportingStructureQueryService
    ) {
        this.reportingStructureService = reportingStructureService;
        this.reportingStructureRepository = reportingStructureRepository;
        this.reportingStructureQueryService = reportingStructureQueryService;
    }

    /**
     * {@code POST  /reporting-structures} : Create a new reportingStructure.
     *
     * @param reportingStructureDTO the reportingStructureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportingStructureDTO, or with status {@code 400 (Bad Request)} if the reportingStructure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reporting-structures")
    public ResponseEntity<ReportingStructureDTO> createReportingStructure(@RequestBody ReportingStructureDTO reportingStructureDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReportingStructure : {}", reportingStructureDTO);
        if (reportingStructureDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportingStructure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportingStructureDTO result = reportingStructureService.save(reportingStructureDTO);
        return ResponseEntity
            .created(new URI("/api/reporting-structures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reporting-structures/:id} : Updates an existing reportingStructure.
     *
     * @param id the id of the reportingStructureDTO to save.
     * @param reportingStructureDTO the reportingStructureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportingStructureDTO,
     * or with status {@code 400 (Bad Request)} if the reportingStructureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportingStructureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reporting-structures/{id}")
    public ResponseEntity<ReportingStructureDTO> updateReportingStructure(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReportingStructureDTO reportingStructureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReportingStructure : {}, {}", id, reportingStructureDTO);
        if (reportingStructureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reportingStructureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reportingStructureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReportingStructureDTO result = reportingStructureService.update(reportingStructureDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportingStructureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reporting-structures/:id} : Partial updates given fields of an existing reportingStructure, field will ignore if it is null
     *
     * @param id the id of the reportingStructureDTO to save.
     * @param reportingStructureDTO the reportingStructureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportingStructureDTO,
     * or with status {@code 400 (Bad Request)} if the reportingStructureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reportingStructureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reportingStructureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reporting-structures/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReportingStructureDTO> partialUpdateReportingStructure(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReportingStructureDTO reportingStructureDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReportingStructure partially : {}, {}", id, reportingStructureDTO);
        if (reportingStructureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reportingStructureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reportingStructureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReportingStructureDTO> result = reportingStructureService.partialUpdate(reportingStructureDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportingStructureDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /reporting-structures} : get all the reportingStructures.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportingStructures in body.
     */
    @GetMapping("/reporting-structures")
    public ResponseEntity<List<ReportingStructureDTO>> getAllReportingStructures(
        ReportingStructureCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ReportingStructures by criteria: {}", criteria);
        Page<ReportingStructureDTO> page = reportingStructureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reporting-structures/count} : count all the reportingStructures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/reporting-structures/count")
    public ResponseEntity<Long> countReportingStructures(ReportingStructureCriteria criteria) {
        log.debug("REST request to count ReportingStructures by criteria: {}", criteria);
        return ResponseEntity.ok().body(reportingStructureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /reporting-structures/:id} : get the "id" reportingStructure.
     *
     * @param id the id of the reportingStructureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportingStructureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reporting-structures/{id}")
    public ResponseEntity<ReportingStructureDTO> getReportingStructure(@PathVariable Long id) {
        log.debug("REST request to get ReportingStructure : {}", id);
        Optional<ReportingStructureDTO> reportingStructureDTO = reportingStructureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportingStructureDTO);
    }

    /**
     * {@code DELETE  /reporting-structures/:id} : delete the "id" reportingStructure.
     *
     * @param id the id of the reportingStructureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reporting-structures/{id}")
    public ResponseEntity<Void> deleteReportingStructure(@PathVariable Long id) {
        log.debug("REST request to delete ReportingStructure : {}", id);
        reportingStructureService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
