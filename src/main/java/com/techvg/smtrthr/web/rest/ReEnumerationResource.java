package com.techvg.smtrthr.web.rest;

import com.techvg.smtrthr.repository.ReEnumerationRepository;
import com.techvg.smtrthr.service.ReEnumerationQueryService;
import com.techvg.smtrthr.service.ReEnumerationService;
import com.techvg.smtrthr.service.criteria.ReEnumerationCriteria;
import com.techvg.smtrthr.service.dto.ReEnumerationDTO;
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
 * REST controller for managing {@link com.techvg.smtrthr.domain.ReEnumeration}.
 */
@RestController
@RequestMapping("/api")
public class ReEnumerationResource {

    private final Logger log = LoggerFactory.getLogger(ReEnumerationResource.class);

    private static final String ENTITY_NAME = "reEnumeration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReEnumerationService reEnumerationService;

    private final ReEnumerationRepository reEnumerationRepository;

    private final ReEnumerationQueryService reEnumerationQueryService;

    public ReEnumerationResource(
        ReEnumerationService reEnumerationService,
        ReEnumerationRepository reEnumerationRepository,
        ReEnumerationQueryService reEnumerationQueryService
    ) {
        this.reEnumerationService = reEnumerationService;
        this.reEnumerationRepository = reEnumerationRepository;
        this.reEnumerationQueryService = reEnumerationQueryService;
    }

    /**
     * {@code POST  /re-enumerations} : Create a new reEnumeration.
     *
     * @param reEnumerationDTO the reEnumerationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reEnumerationDTO, or with status {@code 400 (Bad Request)} if the reEnumeration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/re-enumerations")
    public ResponseEntity<ReEnumerationDTO> createReEnumeration(@RequestBody ReEnumerationDTO reEnumerationDTO) throws URISyntaxException {
        log.debug("REST request to save ReEnumeration : {}", reEnumerationDTO);
        if (reEnumerationDTO.getId() != null) {
            throw new BadRequestAlertException("A new reEnumeration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReEnumerationDTO result = reEnumerationService.save(reEnumerationDTO);
        return ResponseEntity
            .created(new URI("/api/re-enumerations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /re-enumerations/:id} : Updates an existing reEnumeration.
     *
     * @param id the id of the reEnumerationDTO to save.
     * @param reEnumerationDTO the reEnumerationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reEnumerationDTO,
     * or with status {@code 400 (Bad Request)} if the reEnumerationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reEnumerationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/re-enumerations/{id}")
    public ResponseEntity<ReEnumerationDTO> updateReEnumeration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReEnumerationDTO reEnumerationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReEnumeration : {}, {}", id, reEnumerationDTO);
        if (reEnumerationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reEnumerationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reEnumerationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReEnumerationDTO result = reEnumerationService.update(reEnumerationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reEnumerationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /re-enumerations/:id} : Partial updates given fields of an existing reEnumeration, field will ignore if it is null
     *
     * @param id the id of the reEnumerationDTO to save.
     * @param reEnumerationDTO the reEnumerationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reEnumerationDTO,
     * or with status {@code 400 (Bad Request)} if the reEnumerationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reEnumerationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reEnumerationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/re-enumerations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReEnumerationDTO> partialUpdateReEnumeration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReEnumerationDTO reEnumerationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReEnumeration partially : {}, {}", id, reEnumerationDTO);
        if (reEnumerationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reEnumerationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reEnumerationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReEnumerationDTO> result = reEnumerationService.partialUpdate(reEnumerationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reEnumerationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /re-enumerations} : get all the reEnumerations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reEnumerations in body.
     */
    @GetMapping("/re-enumerations")
    public ResponseEntity<List<ReEnumerationDTO>> getAllReEnumerations(
        ReEnumerationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ReEnumerations by criteria: {}", criteria);
        Page<ReEnumerationDTO> page = reEnumerationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /re-enumerations/count} : count all the reEnumerations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/re-enumerations/count")
    public ResponseEntity<Long> countReEnumerations(ReEnumerationCriteria criteria) {
        log.debug("REST request to count ReEnumerations by criteria: {}", criteria);
        return ResponseEntity.ok().body(reEnumerationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /re-enumerations/:id} : get the "id" reEnumeration.
     *
     * @param id the id of the reEnumerationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reEnumerationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/re-enumerations/{id}")
    public ResponseEntity<ReEnumerationDTO> getReEnumeration(@PathVariable Long id) {
        log.debug("REST request to get ReEnumeration : {}", id);
        Optional<ReEnumerationDTO> reEnumerationDTO = reEnumerationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reEnumerationDTO);
    }

    /**
     * {@code DELETE  /re-enumerations/:id} : delete the "id" reEnumeration.
     *
     * @param id the id of the reEnumerationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/re-enumerations/{id}")
    public ResponseEntity<Void> deleteReEnumeration(@PathVariable Long id) {
        log.debug("REST request to delete ReEnumeration : {}", id);
        reEnumerationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
