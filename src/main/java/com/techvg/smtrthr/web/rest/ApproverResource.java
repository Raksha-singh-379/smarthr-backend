package com.techvg.smtrthr.web.rest;

import com.techvg.smtrthr.repository.ApproverRepository;
import com.techvg.smtrthr.service.ApproverQueryService;
import com.techvg.smtrthr.service.ApproverService;
import com.techvg.smtrthr.service.criteria.ApproverCriteria;
import com.techvg.smtrthr.service.dto.ApproverDTO;
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
 * REST controller for managing {@link com.techvg.smtrthr.domain.Approver}.
 */
@RestController
@RequestMapping("/api")
public class ApproverResource {

    private final Logger log = LoggerFactory.getLogger(ApproverResource.class);

    private static final String ENTITY_NAME = "approver";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApproverService approverService;

    private final ApproverRepository approverRepository;

    private final ApproverQueryService approverQueryService;

    public ApproverResource(
        ApproverService approverService,
        ApproverRepository approverRepository,
        ApproverQueryService approverQueryService
    ) {
        this.approverService = approverService;
        this.approverRepository = approverRepository;
        this.approverQueryService = approverQueryService;
    }

    /**
     * {@code POST  /approvers} : Create a new approver.
     *
     * @param approverDTO the approverDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new approverDTO, or with status {@code 400 (Bad Request)} if the approver has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/approvers")
    public ResponseEntity<ApproverDTO> createApprover(@RequestBody ApproverDTO approverDTO) throws URISyntaxException {
        log.debug("REST request to save Approver : {}", approverDTO);
        if (approverDTO.getId() != null) {
            throw new BadRequestAlertException("A new approver cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApproverDTO result = approverService.save(approverDTO);
        return ResponseEntity
            .created(new URI("/api/approvers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /approvers/:id} : Updates an existing approver.
     *
     * @param id the id of the approverDTO to save.
     * @param approverDTO the approverDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approverDTO,
     * or with status {@code 400 (Bad Request)} if the approverDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the approverDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/approvers/{id}")
    public ResponseEntity<ApproverDTO> updateApprover(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApproverDTO approverDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Approver : {}, {}", id, approverDTO);
        if (approverDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approverDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApproverDTO result = approverService.update(approverDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approverDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /approvers/:id} : Partial updates given fields of an existing approver, field will ignore if it is null
     *
     * @param id the id of the approverDTO to save.
     * @param approverDTO the approverDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approverDTO,
     * or with status {@code 400 (Bad Request)} if the approverDTO is not valid,
     * or with status {@code 404 (Not Found)} if the approverDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the approverDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/approvers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApproverDTO> partialUpdateApprover(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApproverDTO approverDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Approver partially : {}, {}", id, approverDTO);
        if (approverDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approverDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApproverDTO> result = approverService.partialUpdate(approverDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approverDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /approvers} : get all the approvers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of approvers in body.
     */
    @GetMapping("/approvers")
    public ResponseEntity<List<ApproverDTO>> getAllApprovers(
        ApproverCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Approvers by criteria: {}", criteria);
        Page<ApproverDTO> page = approverQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /approvers/count} : count all the approvers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/approvers/count")
    public ResponseEntity<Long> countApprovers(ApproverCriteria criteria) {
        log.debug("REST request to count Approvers by criteria: {}", criteria);
        return ResponseEntity.ok().body(approverQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /approvers/:id} : get the "id" approver.
     *
     * @param id the id of the approverDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the approverDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/approvers/{id}")
    public ResponseEntity<ApproverDTO> getApprover(@PathVariable Long id) {
        log.debug("REST request to get Approver : {}", id);
        Optional<ApproverDTO> approverDTO = approverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(approverDTO);
    }

    /**
     * {@code DELETE  /approvers/:id} : delete the "id" approver.
     *
     * @param id the id of the approverDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/approvers/{id}")
    public ResponseEntity<Void> deleteApprover(@PathVariable Long id) {
        log.debug("REST request to delete Approver : {}", id);
        approverService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
