package com.techvg.smtrthr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.smtrthr.IntegrationTest;
import com.techvg.smtrthr.domain.ReportingStructure;
import com.techvg.smtrthr.repository.ReportingStructureRepository;
import com.techvg.smtrthr.service.criteria.ReportingStructureCriteria;
import com.techvg.smtrthr.service.dto.ReportingStructureDTO;
import com.techvg.smtrthr.service.mapper.ReportingStructureMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReportingStructureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReportingStructureResourceIT {

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final Long DEFAULT_REPORTING_EMP_ID = 1L;
    private static final Long UPDATED_REPORTING_EMP_ID = 2L;
    private static final Long SMALLER_REPORTING_EMP_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_REPORTING_STR_ID = 1L;
    private static final Long UPDATED_REPORTING_STR_ID = 2L;
    private static final Long SMALLER_REPORTING_STR_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/reporting-structures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReportingStructureRepository reportingStructureRepository;

    @Autowired
    private ReportingStructureMapper reportingStructureMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportingStructureMockMvc;

    private ReportingStructure reportingStructure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportingStructure createEntity(EntityManager em) {
        ReportingStructure reportingStructure = new ReportingStructure()
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .reportingEmpId(DEFAULT_REPORTING_EMP_ID)
            .status(DEFAULT_STATUS)
            .reportingStrId(DEFAULT_REPORTING_STR_ID)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .companyId(DEFAULT_COMPANY_ID);
        return reportingStructure;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportingStructure createUpdatedEntity(EntityManager em) {
        ReportingStructure reportingStructure = new ReportingStructure()
            .employeeId(UPDATED_EMPLOYEE_ID)
            .reportingEmpId(UPDATED_REPORTING_EMP_ID)
            .status(UPDATED_STATUS)
            .reportingStrId(UPDATED_REPORTING_STR_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .companyId(UPDATED_COMPANY_ID);
        return reportingStructure;
    }

    @BeforeEach
    public void initTest() {
        reportingStructure = createEntity(em);
    }

    @Test
    @Transactional
    void createReportingStructure() throws Exception {
        int databaseSizeBeforeCreate = reportingStructureRepository.findAll().size();
        // Create the ReportingStructure
        ReportingStructureDTO reportingStructureDTO = reportingStructureMapper.toDto(reportingStructure);
        restReportingStructureMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportingStructureDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReportingStructure in the database
        List<ReportingStructure> reportingStructureList = reportingStructureRepository.findAll();
        assertThat(reportingStructureList).hasSize(databaseSizeBeforeCreate + 1);
        ReportingStructure testReportingStructure = reportingStructureList.get(reportingStructureList.size() - 1);
        assertThat(testReportingStructure.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testReportingStructure.getReportingEmpId()).isEqualTo(DEFAULT_REPORTING_EMP_ID);
        assertThat(testReportingStructure.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReportingStructure.getReportingStrId()).isEqualTo(DEFAULT_REPORTING_STR_ID);
        assertThat(testReportingStructure.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testReportingStructure.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testReportingStructure.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    void createReportingStructureWithExistingId() throws Exception {
        // Create the ReportingStructure with an existing ID
        reportingStructure.setId(1L);
        ReportingStructureDTO reportingStructureDTO = reportingStructureMapper.toDto(reportingStructure);

        int databaseSizeBeforeCreate = reportingStructureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportingStructureMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportingStructureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportingStructure in the database
        List<ReportingStructure> reportingStructureList = reportingStructureRepository.findAll();
        assertThat(reportingStructureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReportingStructures() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList
        restReportingStructureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportingStructure.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].reportingEmpId").value(hasItem(DEFAULT_REPORTING_EMP_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].reportingStrId").value(hasItem(DEFAULT_REPORTING_STR_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())));
    }

    @Test
    @Transactional
    void getReportingStructure() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get the reportingStructure
        restReportingStructureMockMvc
            .perform(get(ENTITY_API_URL_ID, reportingStructure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reportingStructure.getId().intValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.reportingEmpId").value(DEFAULT_REPORTING_EMP_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.reportingStrId").value(DEFAULT_REPORTING_STR_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()));
    }

    @Test
    @Transactional
    void getReportingStructuresByIdFiltering() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        Long id = reportingStructure.getId();

        defaultReportingStructureShouldBeFound("id.equals=" + id);
        defaultReportingStructureShouldNotBeFound("id.notEquals=" + id);

        defaultReportingStructureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReportingStructureShouldNotBeFound("id.greaterThan=" + id);

        defaultReportingStructureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReportingStructureShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultReportingStructureShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the reportingStructureList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultReportingStructureShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultReportingStructureShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the reportingStructureList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultReportingStructureShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where employeeId is not null
        defaultReportingStructureShouldBeFound("employeeId.specified=true");

        // Get all the reportingStructureList where employeeId is null
        defaultReportingStructureShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllReportingStructuresByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultReportingStructureShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the reportingStructureList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultReportingStructureShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultReportingStructureShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the reportingStructureList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultReportingStructureShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultReportingStructureShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the reportingStructureList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultReportingStructureShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultReportingStructureShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the reportingStructureList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultReportingStructureShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByReportingEmpIdIsEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where reportingEmpId equals to DEFAULT_REPORTING_EMP_ID
        defaultReportingStructureShouldBeFound("reportingEmpId.equals=" + DEFAULT_REPORTING_EMP_ID);

        // Get all the reportingStructureList where reportingEmpId equals to UPDATED_REPORTING_EMP_ID
        defaultReportingStructureShouldNotBeFound("reportingEmpId.equals=" + UPDATED_REPORTING_EMP_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByReportingEmpIdIsInShouldWork() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where reportingEmpId in DEFAULT_REPORTING_EMP_ID or UPDATED_REPORTING_EMP_ID
        defaultReportingStructureShouldBeFound("reportingEmpId.in=" + DEFAULT_REPORTING_EMP_ID + "," + UPDATED_REPORTING_EMP_ID);

        // Get all the reportingStructureList where reportingEmpId equals to UPDATED_REPORTING_EMP_ID
        defaultReportingStructureShouldNotBeFound("reportingEmpId.in=" + UPDATED_REPORTING_EMP_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByReportingEmpIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where reportingEmpId is not null
        defaultReportingStructureShouldBeFound("reportingEmpId.specified=true");

        // Get all the reportingStructureList where reportingEmpId is null
        defaultReportingStructureShouldNotBeFound("reportingEmpId.specified=false");
    }

    @Test
    @Transactional
    void getAllReportingStructuresByReportingEmpIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where reportingEmpId is greater than or equal to DEFAULT_REPORTING_EMP_ID
        defaultReportingStructureShouldBeFound("reportingEmpId.greaterThanOrEqual=" + DEFAULT_REPORTING_EMP_ID);

        // Get all the reportingStructureList where reportingEmpId is greater than or equal to UPDATED_REPORTING_EMP_ID
        defaultReportingStructureShouldNotBeFound("reportingEmpId.greaterThanOrEqual=" + UPDATED_REPORTING_EMP_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByReportingEmpIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where reportingEmpId is less than or equal to DEFAULT_REPORTING_EMP_ID
        defaultReportingStructureShouldBeFound("reportingEmpId.lessThanOrEqual=" + DEFAULT_REPORTING_EMP_ID);

        // Get all the reportingStructureList where reportingEmpId is less than or equal to SMALLER_REPORTING_EMP_ID
        defaultReportingStructureShouldNotBeFound("reportingEmpId.lessThanOrEqual=" + SMALLER_REPORTING_EMP_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByReportingEmpIdIsLessThanSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where reportingEmpId is less than DEFAULT_REPORTING_EMP_ID
        defaultReportingStructureShouldNotBeFound("reportingEmpId.lessThan=" + DEFAULT_REPORTING_EMP_ID);

        // Get all the reportingStructureList where reportingEmpId is less than UPDATED_REPORTING_EMP_ID
        defaultReportingStructureShouldBeFound("reportingEmpId.lessThan=" + UPDATED_REPORTING_EMP_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByReportingEmpIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where reportingEmpId is greater than DEFAULT_REPORTING_EMP_ID
        defaultReportingStructureShouldNotBeFound("reportingEmpId.greaterThan=" + DEFAULT_REPORTING_EMP_ID);

        // Get all the reportingStructureList where reportingEmpId is greater than SMALLER_REPORTING_EMP_ID
        defaultReportingStructureShouldBeFound("reportingEmpId.greaterThan=" + SMALLER_REPORTING_EMP_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where status equals to DEFAULT_STATUS
        defaultReportingStructureShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the reportingStructureList where status equals to UPDATED_STATUS
        defaultReportingStructureShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultReportingStructureShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the reportingStructureList where status equals to UPDATED_STATUS
        defaultReportingStructureShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where status is not null
        defaultReportingStructureShouldBeFound("status.specified=true");

        // Get all the reportingStructureList where status is null
        defaultReportingStructureShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllReportingStructuresByStatusContainsSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where status contains DEFAULT_STATUS
        defaultReportingStructureShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the reportingStructureList where status contains UPDATED_STATUS
        defaultReportingStructureShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where status does not contain DEFAULT_STATUS
        defaultReportingStructureShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the reportingStructureList where status does not contain UPDATED_STATUS
        defaultReportingStructureShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByReportingStrIdIsEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where reportingStrId equals to DEFAULT_REPORTING_STR_ID
        defaultReportingStructureShouldBeFound("reportingStrId.equals=" + DEFAULT_REPORTING_STR_ID);

        // Get all the reportingStructureList where reportingStrId equals to UPDATED_REPORTING_STR_ID
        defaultReportingStructureShouldNotBeFound("reportingStrId.equals=" + UPDATED_REPORTING_STR_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByReportingStrIdIsInShouldWork() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where reportingStrId in DEFAULT_REPORTING_STR_ID or UPDATED_REPORTING_STR_ID
        defaultReportingStructureShouldBeFound("reportingStrId.in=" + DEFAULT_REPORTING_STR_ID + "," + UPDATED_REPORTING_STR_ID);

        // Get all the reportingStructureList where reportingStrId equals to UPDATED_REPORTING_STR_ID
        defaultReportingStructureShouldNotBeFound("reportingStrId.in=" + UPDATED_REPORTING_STR_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByReportingStrIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where reportingStrId is not null
        defaultReportingStructureShouldBeFound("reportingStrId.specified=true");

        // Get all the reportingStructureList where reportingStrId is null
        defaultReportingStructureShouldNotBeFound("reportingStrId.specified=false");
    }

    @Test
    @Transactional
    void getAllReportingStructuresByReportingStrIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where reportingStrId is greater than or equal to DEFAULT_REPORTING_STR_ID
        defaultReportingStructureShouldBeFound("reportingStrId.greaterThanOrEqual=" + DEFAULT_REPORTING_STR_ID);

        // Get all the reportingStructureList where reportingStrId is greater than or equal to UPDATED_REPORTING_STR_ID
        defaultReportingStructureShouldNotBeFound("reportingStrId.greaterThanOrEqual=" + UPDATED_REPORTING_STR_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByReportingStrIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where reportingStrId is less than or equal to DEFAULT_REPORTING_STR_ID
        defaultReportingStructureShouldBeFound("reportingStrId.lessThanOrEqual=" + DEFAULT_REPORTING_STR_ID);

        // Get all the reportingStructureList where reportingStrId is less than or equal to SMALLER_REPORTING_STR_ID
        defaultReportingStructureShouldNotBeFound("reportingStrId.lessThanOrEqual=" + SMALLER_REPORTING_STR_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByReportingStrIdIsLessThanSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where reportingStrId is less than DEFAULT_REPORTING_STR_ID
        defaultReportingStructureShouldNotBeFound("reportingStrId.lessThan=" + DEFAULT_REPORTING_STR_ID);

        // Get all the reportingStructureList where reportingStrId is less than UPDATED_REPORTING_STR_ID
        defaultReportingStructureShouldBeFound("reportingStrId.lessThan=" + UPDATED_REPORTING_STR_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByReportingStrIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where reportingStrId is greater than DEFAULT_REPORTING_STR_ID
        defaultReportingStructureShouldNotBeFound("reportingStrId.greaterThan=" + DEFAULT_REPORTING_STR_ID);

        // Get all the reportingStructureList where reportingStrId is greater than SMALLER_REPORTING_STR_ID
        defaultReportingStructureShouldBeFound("reportingStrId.greaterThan=" + SMALLER_REPORTING_STR_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultReportingStructureShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the reportingStructureList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultReportingStructureShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultReportingStructureShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the reportingStructureList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultReportingStructureShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where lastModified is not null
        defaultReportingStructureShouldBeFound("lastModified.specified=true");

        // Get all the reportingStructureList where lastModified is null
        defaultReportingStructureShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllReportingStructuresByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultReportingStructureShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the reportingStructureList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultReportingStructureShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultReportingStructureShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the reportingStructureList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultReportingStructureShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where lastModifiedBy is not null
        defaultReportingStructureShouldBeFound("lastModifiedBy.specified=true");

        // Get all the reportingStructureList where lastModifiedBy is null
        defaultReportingStructureShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllReportingStructuresByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultReportingStructureShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the reportingStructureList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultReportingStructureShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultReportingStructureShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the reportingStructureList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultReportingStructureShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where companyId equals to DEFAULT_COMPANY_ID
        defaultReportingStructureShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the reportingStructureList where companyId equals to UPDATED_COMPANY_ID
        defaultReportingStructureShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultReportingStructureShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the reportingStructureList where companyId equals to UPDATED_COMPANY_ID
        defaultReportingStructureShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where companyId is not null
        defaultReportingStructureShouldBeFound("companyId.specified=true");

        // Get all the reportingStructureList where companyId is null
        defaultReportingStructureShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllReportingStructuresByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultReportingStructureShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the reportingStructureList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultReportingStructureShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultReportingStructureShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the reportingStructureList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultReportingStructureShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where companyId is less than DEFAULT_COMPANY_ID
        defaultReportingStructureShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the reportingStructureList where companyId is less than UPDATED_COMPANY_ID
        defaultReportingStructureShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllReportingStructuresByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        // Get all the reportingStructureList where companyId is greater than DEFAULT_COMPANY_ID
        defaultReportingStructureShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the reportingStructureList where companyId is greater than SMALLER_COMPANY_ID
        defaultReportingStructureShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReportingStructureShouldBeFound(String filter) throws Exception {
        restReportingStructureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportingStructure.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].reportingEmpId").value(hasItem(DEFAULT_REPORTING_EMP_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].reportingStrId").value(hasItem(DEFAULT_REPORTING_STR_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())));

        // Check, that the count call also returns 1
        restReportingStructureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReportingStructureShouldNotBeFound(String filter) throws Exception {
        restReportingStructureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReportingStructureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingReportingStructure() throws Exception {
        // Get the reportingStructure
        restReportingStructureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReportingStructure() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        int databaseSizeBeforeUpdate = reportingStructureRepository.findAll().size();

        // Update the reportingStructure
        ReportingStructure updatedReportingStructure = reportingStructureRepository.findById(reportingStructure.getId()).get();
        // Disconnect from session so that the updates on updatedReportingStructure are not directly saved in db
        em.detach(updatedReportingStructure);
        updatedReportingStructure
            .employeeId(UPDATED_EMPLOYEE_ID)
            .reportingEmpId(UPDATED_REPORTING_EMP_ID)
            .status(UPDATED_STATUS)
            .reportingStrId(UPDATED_REPORTING_STR_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .companyId(UPDATED_COMPANY_ID);
        ReportingStructureDTO reportingStructureDTO = reportingStructureMapper.toDto(updatedReportingStructure);

        restReportingStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reportingStructureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportingStructureDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReportingStructure in the database
        List<ReportingStructure> reportingStructureList = reportingStructureRepository.findAll();
        assertThat(reportingStructureList).hasSize(databaseSizeBeforeUpdate);
        ReportingStructure testReportingStructure = reportingStructureList.get(reportingStructureList.size() - 1);
        assertThat(testReportingStructure.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testReportingStructure.getReportingEmpId()).isEqualTo(UPDATED_REPORTING_EMP_ID);
        assertThat(testReportingStructure.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReportingStructure.getReportingStrId()).isEqualTo(UPDATED_REPORTING_STR_ID);
        assertThat(testReportingStructure.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testReportingStructure.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testReportingStructure.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void putNonExistingReportingStructure() throws Exception {
        int databaseSizeBeforeUpdate = reportingStructureRepository.findAll().size();
        reportingStructure.setId(count.incrementAndGet());

        // Create the ReportingStructure
        ReportingStructureDTO reportingStructureDTO = reportingStructureMapper.toDto(reportingStructure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportingStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reportingStructureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportingStructureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportingStructure in the database
        List<ReportingStructure> reportingStructureList = reportingStructureRepository.findAll();
        assertThat(reportingStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReportingStructure() throws Exception {
        int databaseSizeBeforeUpdate = reportingStructureRepository.findAll().size();
        reportingStructure.setId(count.incrementAndGet());

        // Create the ReportingStructure
        ReportingStructureDTO reportingStructureDTO = reportingStructureMapper.toDto(reportingStructure);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportingStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportingStructureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportingStructure in the database
        List<ReportingStructure> reportingStructureList = reportingStructureRepository.findAll();
        assertThat(reportingStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReportingStructure() throws Exception {
        int databaseSizeBeforeUpdate = reportingStructureRepository.findAll().size();
        reportingStructure.setId(count.incrementAndGet());

        // Create the ReportingStructure
        ReportingStructureDTO reportingStructureDTO = reportingStructureMapper.toDto(reportingStructure);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportingStructureMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportingStructureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReportingStructure in the database
        List<ReportingStructure> reportingStructureList = reportingStructureRepository.findAll();
        assertThat(reportingStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReportingStructureWithPatch() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        int databaseSizeBeforeUpdate = reportingStructureRepository.findAll().size();

        // Update the reportingStructure using partial update
        ReportingStructure partialUpdatedReportingStructure = new ReportingStructure();
        partialUpdatedReportingStructure.setId(reportingStructure.getId());

        partialUpdatedReportingStructure
            .employeeId(UPDATED_EMPLOYEE_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .companyId(UPDATED_COMPANY_ID);

        restReportingStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReportingStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReportingStructure))
            )
            .andExpect(status().isOk());

        // Validate the ReportingStructure in the database
        List<ReportingStructure> reportingStructureList = reportingStructureRepository.findAll();
        assertThat(reportingStructureList).hasSize(databaseSizeBeforeUpdate);
        ReportingStructure testReportingStructure = reportingStructureList.get(reportingStructureList.size() - 1);
        assertThat(testReportingStructure.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testReportingStructure.getReportingEmpId()).isEqualTo(DEFAULT_REPORTING_EMP_ID);
        assertThat(testReportingStructure.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReportingStructure.getReportingStrId()).isEqualTo(DEFAULT_REPORTING_STR_ID);
        assertThat(testReportingStructure.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testReportingStructure.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testReportingStructure.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void fullUpdateReportingStructureWithPatch() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        int databaseSizeBeforeUpdate = reportingStructureRepository.findAll().size();

        // Update the reportingStructure using partial update
        ReportingStructure partialUpdatedReportingStructure = new ReportingStructure();
        partialUpdatedReportingStructure.setId(reportingStructure.getId());

        partialUpdatedReportingStructure
            .employeeId(UPDATED_EMPLOYEE_ID)
            .reportingEmpId(UPDATED_REPORTING_EMP_ID)
            .status(UPDATED_STATUS)
            .reportingStrId(UPDATED_REPORTING_STR_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .companyId(UPDATED_COMPANY_ID);

        restReportingStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReportingStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReportingStructure))
            )
            .andExpect(status().isOk());

        // Validate the ReportingStructure in the database
        List<ReportingStructure> reportingStructureList = reportingStructureRepository.findAll();
        assertThat(reportingStructureList).hasSize(databaseSizeBeforeUpdate);
        ReportingStructure testReportingStructure = reportingStructureList.get(reportingStructureList.size() - 1);
        assertThat(testReportingStructure.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testReportingStructure.getReportingEmpId()).isEqualTo(UPDATED_REPORTING_EMP_ID);
        assertThat(testReportingStructure.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReportingStructure.getReportingStrId()).isEqualTo(UPDATED_REPORTING_STR_ID);
        assertThat(testReportingStructure.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testReportingStructure.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testReportingStructure.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingReportingStructure() throws Exception {
        int databaseSizeBeforeUpdate = reportingStructureRepository.findAll().size();
        reportingStructure.setId(count.incrementAndGet());

        // Create the ReportingStructure
        ReportingStructureDTO reportingStructureDTO = reportingStructureMapper.toDto(reportingStructure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportingStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reportingStructureDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportingStructureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportingStructure in the database
        List<ReportingStructure> reportingStructureList = reportingStructureRepository.findAll();
        assertThat(reportingStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReportingStructure() throws Exception {
        int databaseSizeBeforeUpdate = reportingStructureRepository.findAll().size();
        reportingStructure.setId(count.incrementAndGet());

        // Create the ReportingStructure
        ReportingStructureDTO reportingStructureDTO = reportingStructureMapper.toDto(reportingStructure);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportingStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportingStructureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportingStructure in the database
        List<ReportingStructure> reportingStructureList = reportingStructureRepository.findAll();
        assertThat(reportingStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReportingStructure() throws Exception {
        int databaseSizeBeforeUpdate = reportingStructureRepository.findAll().size();
        reportingStructure.setId(count.incrementAndGet());

        // Create the ReportingStructure
        ReportingStructureDTO reportingStructureDTO = reportingStructureMapper.toDto(reportingStructure);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportingStructureMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportingStructureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReportingStructure in the database
        List<ReportingStructure> reportingStructureList = reportingStructureRepository.findAll();
        assertThat(reportingStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReportingStructure() throws Exception {
        // Initialize the database
        reportingStructureRepository.saveAndFlush(reportingStructure);

        int databaseSizeBeforeDelete = reportingStructureRepository.findAll().size();

        // Delete the reportingStructure
        restReportingStructureMockMvc
            .perform(delete(ENTITY_API_URL_ID, reportingStructure.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportingStructure> reportingStructureList = reportingStructureRepository.findAll();
        assertThat(reportingStructureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
