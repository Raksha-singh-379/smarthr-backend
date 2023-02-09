package com.techvg.smtrthr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.smtrthr.IntegrationTest;
import com.techvg.smtrthr.domain.Approver;
import com.techvg.smtrthr.repository.ApproverRepository;
import com.techvg.smtrthr.service.criteria.ApproverCriteria;
import com.techvg.smtrthr.service.dto.ApproverDTO;
import com.techvg.smtrthr.service.mapper.ApproverMapper;
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
 * Integration tests for the {@link ApproverResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApproverResourceIT {

    private static final String DEFAULT_APPROVER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPROVER_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_APPROVAL_SETTING_ID = 1L;
    private static final Long UPDATED_APPROVAL_SETTING_ID = 2L;
    private static final Long SMALLER_APPROVAL_SETTING_ID = 1L - 1L;

    private static final Long DEFAULT_DEPARTMENT_ID = 1L;
    private static final Long UPDATED_DEPARTMENT_ID = 2L;
    private static final Long SMALLER_DEPARTMENT_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/approvers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApproverRepository approverRepository;

    @Autowired
    private ApproverMapper approverMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApproverMockMvc;

    private Approver approver;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Approver createEntity(EntityManager em) {
        Approver approver = new Approver()
            .approverName(DEFAULT_APPROVER_NAME)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .status(DEFAULT_STATUS)
            .approvalSettingId(DEFAULT_APPROVAL_SETTING_ID)
            .departmentId(DEFAULT_DEPARTMENT_ID)
            .companyId(DEFAULT_COMPANY_ID);
        return approver;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Approver createUpdatedEntity(EntityManager em) {
        Approver approver = new Approver()
            .approverName(UPDATED_APPROVER_NAME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .status(UPDATED_STATUS)
            .approvalSettingId(UPDATED_APPROVAL_SETTING_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .companyId(UPDATED_COMPANY_ID);
        return approver;
    }

    @BeforeEach
    public void initTest() {
        approver = createEntity(em);
    }

    @Test
    @Transactional
    void createApprover() throws Exception {
        int databaseSizeBeforeCreate = approverRepository.findAll().size();
        // Create the Approver
        ApproverDTO approverDTO = approverMapper.toDto(approver);
        restApproverMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approverDTO)))
            .andExpect(status().isCreated());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeCreate + 1);
        Approver testApprover = approverList.get(approverList.size() - 1);
        assertThat(testApprover.getApproverName()).isEqualTo(DEFAULT_APPROVER_NAME);
        assertThat(testApprover.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testApprover.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testApprover.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApprover.getApprovalSettingId()).isEqualTo(DEFAULT_APPROVAL_SETTING_ID);
        assertThat(testApprover.getDepartmentId()).isEqualTo(DEFAULT_DEPARTMENT_ID);
        assertThat(testApprover.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    void createApproverWithExistingId() throws Exception {
        // Create the Approver with an existing ID
        approver.setId(1L);
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        int databaseSizeBeforeCreate = approverRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApproverMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApprovers() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList
        restApproverMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approver.getId().intValue())))
            .andExpect(jsonPath("$.[*].approverName").value(hasItem(DEFAULT_APPROVER_NAME)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].approvalSettingId").value(hasItem(DEFAULT_APPROVAL_SETTING_ID.intValue())))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())));
    }

    @Test
    @Transactional
    void getApprover() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get the approver
        restApproverMockMvc
            .perform(get(ENTITY_API_URL_ID, approver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(approver.getId().intValue()))
            .andExpect(jsonPath("$.approverName").value(DEFAULT_APPROVER_NAME))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.approvalSettingId").value(DEFAULT_APPROVAL_SETTING_ID.intValue()))
            .andExpect(jsonPath("$.departmentId").value(DEFAULT_DEPARTMENT_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()));
    }

    @Test
    @Transactional
    void getApproversByIdFiltering() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        Long id = approver.getId();

        defaultApproverShouldBeFound("id.equals=" + id);
        defaultApproverShouldNotBeFound("id.notEquals=" + id);

        defaultApproverShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApproverShouldNotBeFound("id.greaterThan=" + id);

        defaultApproverShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApproverShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApproversByApproverNameIsEqualToSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where approverName equals to DEFAULT_APPROVER_NAME
        defaultApproverShouldBeFound("approverName.equals=" + DEFAULT_APPROVER_NAME);

        // Get all the approverList where approverName equals to UPDATED_APPROVER_NAME
        defaultApproverShouldNotBeFound("approverName.equals=" + UPDATED_APPROVER_NAME);
    }

    @Test
    @Transactional
    void getAllApproversByApproverNameIsInShouldWork() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where approverName in DEFAULT_APPROVER_NAME or UPDATED_APPROVER_NAME
        defaultApproverShouldBeFound("approverName.in=" + DEFAULT_APPROVER_NAME + "," + UPDATED_APPROVER_NAME);

        // Get all the approverList where approverName equals to UPDATED_APPROVER_NAME
        defaultApproverShouldNotBeFound("approverName.in=" + UPDATED_APPROVER_NAME);
    }

    @Test
    @Transactional
    void getAllApproversByApproverNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where approverName is not null
        defaultApproverShouldBeFound("approverName.specified=true");

        // Get all the approverList where approverName is null
        defaultApproverShouldNotBeFound("approverName.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByApproverNameContainsSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where approverName contains DEFAULT_APPROVER_NAME
        defaultApproverShouldBeFound("approverName.contains=" + DEFAULT_APPROVER_NAME);

        // Get all the approverList where approverName contains UPDATED_APPROVER_NAME
        defaultApproverShouldNotBeFound("approverName.contains=" + UPDATED_APPROVER_NAME);
    }

    @Test
    @Transactional
    void getAllApproversByApproverNameNotContainsSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where approverName does not contain DEFAULT_APPROVER_NAME
        defaultApproverShouldNotBeFound("approverName.doesNotContain=" + DEFAULT_APPROVER_NAME);

        // Get all the approverList where approverName does not contain UPDATED_APPROVER_NAME
        defaultApproverShouldBeFound("approverName.doesNotContain=" + UPDATED_APPROVER_NAME);
    }

    @Test
    @Transactional
    void getAllApproversByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultApproverShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the approverList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultApproverShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllApproversByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultApproverShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the approverList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultApproverShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllApproversByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where lastModified is not null
        defaultApproverShouldBeFound("lastModified.specified=true");

        // Get all the approverList where lastModified is null
        defaultApproverShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultApproverShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the approverList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApproverShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllApproversByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultApproverShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the approverList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApproverShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllApproversByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where lastModifiedBy is not null
        defaultApproverShouldBeFound("lastModifiedBy.specified=true");

        // Get all the approverList where lastModifiedBy is null
        defaultApproverShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultApproverShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the approverList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultApproverShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllApproversByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultApproverShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the approverList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultApproverShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllApproversByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where status equals to DEFAULT_STATUS
        defaultApproverShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the approverList where status equals to UPDATED_STATUS
        defaultApproverShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApproversByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultApproverShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the approverList where status equals to UPDATED_STATUS
        defaultApproverShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApproversByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where status is not null
        defaultApproverShouldBeFound("status.specified=true");

        // Get all the approverList where status is null
        defaultApproverShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByStatusContainsSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where status contains DEFAULT_STATUS
        defaultApproverShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the approverList where status contains UPDATED_STATUS
        defaultApproverShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApproversByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where status does not contain DEFAULT_STATUS
        defaultApproverShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the approverList where status does not contain UPDATED_STATUS
        defaultApproverShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApproversByApprovalSettingIdIsEqualToSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where approvalSettingId equals to DEFAULT_APPROVAL_SETTING_ID
        defaultApproverShouldBeFound("approvalSettingId.equals=" + DEFAULT_APPROVAL_SETTING_ID);

        // Get all the approverList where approvalSettingId equals to UPDATED_APPROVAL_SETTING_ID
        defaultApproverShouldNotBeFound("approvalSettingId.equals=" + UPDATED_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllApproversByApprovalSettingIdIsInShouldWork() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where approvalSettingId in DEFAULT_APPROVAL_SETTING_ID or UPDATED_APPROVAL_SETTING_ID
        defaultApproverShouldBeFound("approvalSettingId.in=" + DEFAULT_APPROVAL_SETTING_ID + "," + UPDATED_APPROVAL_SETTING_ID);

        // Get all the approverList where approvalSettingId equals to UPDATED_APPROVAL_SETTING_ID
        defaultApproverShouldNotBeFound("approvalSettingId.in=" + UPDATED_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllApproversByApprovalSettingIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where approvalSettingId is not null
        defaultApproverShouldBeFound("approvalSettingId.specified=true");

        // Get all the approverList where approvalSettingId is null
        defaultApproverShouldNotBeFound("approvalSettingId.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByApprovalSettingIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where approvalSettingId is greater than or equal to DEFAULT_APPROVAL_SETTING_ID
        defaultApproverShouldBeFound("approvalSettingId.greaterThanOrEqual=" + DEFAULT_APPROVAL_SETTING_ID);

        // Get all the approverList where approvalSettingId is greater than or equal to UPDATED_APPROVAL_SETTING_ID
        defaultApproverShouldNotBeFound("approvalSettingId.greaterThanOrEqual=" + UPDATED_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllApproversByApprovalSettingIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where approvalSettingId is less than or equal to DEFAULT_APPROVAL_SETTING_ID
        defaultApproverShouldBeFound("approvalSettingId.lessThanOrEqual=" + DEFAULT_APPROVAL_SETTING_ID);

        // Get all the approverList where approvalSettingId is less than or equal to SMALLER_APPROVAL_SETTING_ID
        defaultApproverShouldNotBeFound("approvalSettingId.lessThanOrEqual=" + SMALLER_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllApproversByApprovalSettingIdIsLessThanSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where approvalSettingId is less than DEFAULT_APPROVAL_SETTING_ID
        defaultApproverShouldNotBeFound("approvalSettingId.lessThan=" + DEFAULT_APPROVAL_SETTING_ID);

        // Get all the approverList where approvalSettingId is less than UPDATED_APPROVAL_SETTING_ID
        defaultApproverShouldBeFound("approvalSettingId.lessThan=" + UPDATED_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllApproversByApprovalSettingIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where approvalSettingId is greater than DEFAULT_APPROVAL_SETTING_ID
        defaultApproverShouldNotBeFound("approvalSettingId.greaterThan=" + DEFAULT_APPROVAL_SETTING_ID);

        // Get all the approverList where approvalSettingId is greater than SMALLER_APPROVAL_SETTING_ID
        defaultApproverShouldBeFound("approvalSettingId.greaterThan=" + SMALLER_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllApproversByDepartmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where departmentId equals to DEFAULT_DEPARTMENT_ID
        defaultApproverShouldBeFound("departmentId.equals=" + DEFAULT_DEPARTMENT_ID);

        // Get all the approverList where departmentId equals to UPDATED_DEPARTMENT_ID
        defaultApproverShouldNotBeFound("departmentId.equals=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllApproversByDepartmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where departmentId in DEFAULT_DEPARTMENT_ID or UPDATED_DEPARTMENT_ID
        defaultApproverShouldBeFound("departmentId.in=" + DEFAULT_DEPARTMENT_ID + "," + UPDATED_DEPARTMENT_ID);

        // Get all the approverList where departmentId equals to UPDATED_DEPARTMENT_ID
        defaultApproverShouldNotBeFound("departmentId.in=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllApproversByDepartmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where departmentId is not null
        defaultApproverShouldBeFound("departmentId.specified=true");

        // Get all the approverList where departmentId is null
        defaultApproverShouldNotBeFound("departmentId.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByDepartmentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where departmentId is greater than or equal to DEFAULT_DEPARTMENT_ID
        defaultApproverShouldBeFound("departmentId.greaterThanOrEqual=" + DEFAULT_DEPARTMENT_ID);

        // Get all the approverList where departmentId is greater than or equal to UPDATED_DEPARTMENT_ID
        defaultApproverShouldNotBeFound("departmentId.greaterThanOrEqual=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllApproversByDepartmentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where departmentId is less than or equal to DEFAULT_DEPARTMENT_ID
        defaultApproverShouldBeFound("departmentId.lessThanOrEqual=" + DEFAULT_DEPARTMENT_ID);

        // Get all the approverList where departmentId is less than or equal to SMALLER_DEPARTMENT_ID
        defaultApproverShouldNotBeFound("departmentId.lessThanOrEqual=" + SMALLER_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllApproversByDepartmentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where departmentId is less than DEFAULT_DEPARTMENT_ID
        defaultApproverShouldNotBeFound("departmentId.lessThan=" + DEFAULT_DEPARTMENT_ID);

        // Get all the approverList where departmentId is less than UPDATED_DEPARTMENT_ID
        defaultApproverShouldBeFound("departmentId.lessThan=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllApproversByDepartmentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where departmentId is greater than DEFAULT_DEPARTMENT_ID
        defaultApproverShouldNotBeFound("departmentId.greaterThan=" + DEFAULT_DEPARTMENT_ID);

        // Get all the approverList where departmentId is greater than SMALLER_DEPARTMENT_ID
        defaultApproverShouldBeFound("departmentId.greaterThan=" + SMALLER_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllApproversByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where companyId equals to DEFAULT_COMPANY_ID
        defaultApproverShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the approverList where companyId equals to UPDATED_COMPANY_ID
        defaultApproverShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApproversByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultApproverShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the approverList where companyId equals to UPDATED_COMPANY_ID
        defaultApproverShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApproversByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where companyId is not null
        defaultApproverShouldBeFound("companyId.specified=true");

        // Get all the approverList where companyId is null
        defaultApproverShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultApproverShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the approverList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultApproverShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApproversByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultApproverShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the approverList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultApproverShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApproversByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where companyId is less than DEFAULT_COMPANY_ID
        defaultApproverShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the approverList where companyId is less than UPDATED_COMPANY_ID
        defaultApproverShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApproversByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList where companyId is greater than DEFAULT_COMPANY_ID
        defaultApproverShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the approverList where companyId is greater than SMALLER_COMPANY_ID
        defaultApproverShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApproverShouldBeFound(String filter) throws Exception {
        restApproverMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approver.getId().intValue())))
            .andExpect(jsonPath("$.[*].approverName").value(hasItem(DEFAULT_APPROVER_NAME)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].approvalSettingId").value(hasItem(DEFAULT_APPROVAL_SETTING_ID.intValue())))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())));

        // Check, that the count call also returns 1
        restApproverMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApproverShouldNotBeFound(String filter) throws Exception {
        restApproverMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApproverMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApprover() throws Exception {
        // Get the approver
        restApproverMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApprover() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        int databaseSizeBeforeUpdate = approverRepository.findAll().size();

        // Update the approver
        Approver updatedApprover = approverRepository.findById(approver.getId()).get();
        // Disconnect from session so that the updates on updatedApprover are not directly saved in db
        em.detach(updatedApprover);
        updatedApprover
            .approverName(UPDATED_APPROVER_NAME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .status(UPDATED_STATUS)
            .approvalSettingId(UPDATED_APPROVAL_SETTING_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .companyId(UPDATED_COMPANY_ID);
        ApproverDTO approverDTO = approverMapper.toDto(updatedApprover);

        restApproverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approverDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverDTO))
            )
            .andExpect(status().isOk());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
        Approver testApprover = approverList.get(approverList.size() - 1);
        assertThat(testApprover.getApproverName()).isEqualTo(UPDATED_APPROVER_NAME);
        assertThat(testApprover.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testApprover.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testApprover.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApprover.getApprovalSettingId()).isEqualTo(UPDATED_APPROVAL_SETTING_ID);
        assertThat(testApprover.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testApprover.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void putNonExistingApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(count.incrementAndGet());

        // Create the Approver
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approverDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(count.incrementAndGet());

        // Create the Approver
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(count.incrementAndGet());

        // Create the Approver
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approverDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApproverWithPatch() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        int databaseSizeBeforeUpdate = approverRepository.findAll().size();

        // Update the approver using partial update
        Approver partialUpdatedApprover = new Approver();
        partialUpdatedApprover.setId(approver.getId());

        partialUpdatedApprover
            .lastModified(UPDATED_LAST_MODIFIED)
            .approvalSettingId(UPDATED_APPROVAL_SETTING_ID)
            .companyId(UPDATED_COMPANY_ID);

        restApproverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprover.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprover))
            )
            .andExpect(status().isOk());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
        Approver testApprover = approverList.get(approverList.size() - 1);
        assertThat(testApprover.getApproverName()).isEqualTo(DEFAULT_APPROVER_NAME);
        assertThat(testApprover.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testApprover.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testApprover.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApprover.getApprovalSettingId()).isEqualTo(UPDATED_APPROVAL_SETTING_ID);
        assertThat(testApprover.getDepartmentId()).isEqualTo(DEFAULT_DEPARTMENT_ID);
        assertThat(testApprover.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void fullUpdateApproverWithPatch() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        int databaseSizeBeforeUpdate = approverRepository.findAll().size();

        // Update the approver using partial update
        Approver partialUpdatedApprover = new Approver();
        partialUpdatedApprover.setId(approver.getId());

        partialUpdatedApprover
            .approverName(UPDATED_APPROVER_NAME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .status(UPDATED_STATUS)
            .approvalSettingId(UPDATED_APPROVAL_SETTING_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .companyId(UPDATED_COMPANY_ID);

        restApproverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprover.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprover))
            )
            .andExpect(status().isOk());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
        Approver testApprover = approverList.get(approverList.size() - 1);
        assertThat(testApprover.getApproverName()).isEqualTo(UPDATED_APPROVER_NAME);
        assertThat(testApprover.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testApprover.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testApprover.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApprover.getApprovalSettingId()).isEqualTo(UPDATED_APPROVAL_SETTING_ID);
        assertThat(testApprover.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testApprover.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(count.incrementAndGet());

        // Create the Approver
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, approverDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approverDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(count.incrementAndGet());

        // Create the Approver
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approverDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(count.incrementAndGet());

        // Create the Approver
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(approverDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApprover() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        int databaseSizeBeforeDelete = approverRepository.findAll().size();

        // Delete the approver
        restApproverMockMvc
            .perform(delete(ENTITY_API_URL_ID, approver.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
