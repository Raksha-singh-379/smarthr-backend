package com.techvg.smtrthr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.smtrthr.IntegrationTest;
import com.techvg.smtrthr.domain.ReEnumeration;
import com.techvg.smtrthr.repository.ReEnumerationRepository;
import com.techvg.smtrthr.service.criteria.ReEnumerationCriteria;
import com.techvg.smtrthr.service.dto.ReEnumerationDTO;
import com.techvg.smtrthr.service.mapper.ReEnumerationMapper;
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
 * Integration tests for the {@link ReEnumerationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReEnumerationResourceIT {

    private static final String DEFAULT_SALARY_BASIS = "AAAAAAAAAA";
    private static final String UPDATED_SALARY_BASIS = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final String DEFAULT_PAYMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_EMPLOYE_ID = 1L;
    private static final Long UPDATED_EMPLOYE_ID = 2L;
    private static final Long SMALLER_EMPLOYE_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/re-enumerations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReEnumerationRepository reEnumerationRepository;

    @Autowired
    private ReEnumerationMapper reEnumerationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReEnumerationMockMvc;

    private ReEnumeration reEnumeration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReEnumeration createEntity(EntityManager em) {
        ReEnumeration reEnumeration = new ReEnumeration()
            .salaryBasis(DEFAULT_SALARY_BASIS)
            .amount(DEFAULT_AMOUNT)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .status(DEFAULT_STATUS)
            .employeId(DEFAULT_EMPLOYE_ID)
            .companyId(DEFAULT_COMPANY_ID);
        return reEnumeration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReEnumeration createUpdatedEntity(EntityManager em) {
        ReEnumeration reEnumeration = new ReEnumeration()
            .salaryBasis(UPDATED_SALARY_BASIS)
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .status(UPDATED_STATUS)
            .employeId(UPDATED_EMPLOYE_ID)
            .companyId(UPDATED_COMPANY_ID);
        return reEnumeration;
    }

    @BeforeEach
    public void initTest() {
        reEnumeration = createEntity(em);
    }

    @Test
    @Transactional
    void createReEnumeration() throws Exception {
        int databaseSizeBeforeCreate = reEnumerationRepository.findAll().size();
        // Create the ReEnumeration
        ReEnumerationDTO reEnumerationDTO = reEnumerationMapper.toDto(reEnumeration);
        restReEnumerationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reEnumerationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReEnumeration in the database
        List<ReEnumeration> reEnumerationList = reEnumerationRepository.findAll();
        assertThat(reEnumerationList).hasSize(databaseSizeBeforeCreate + 1);
        ReEnumeration testReEnumeration = reEnumerationList.get(reEnumerationList.size() - 1);
        assertThat(testReEnumeration.getSalaryBasis()).isEqualTo(DEFAULT_SALARY_BASIS);
        assertThat(testReEnumeration.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testReEnumeration.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testReEnumeration.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testReEnumeration.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testReEnumeration.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReEnumeration.getEmployeId()).isEqualTo(DEFAULT_EMPLOYE_ID);
        assertThat(testReEnumeration.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    void createReEnumerationWithExistingId() throws Exception {
        // Create the ReEnumeration with an existing ID
        reEnumeration.setId(1L);
        ReEnumerationDTO reEnumerationDTO = reEnumerationMapper.toDto(reEnumeration);

        int databaseSizeBeforeCreate = reEnumerationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReEnumerationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reEnumerationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReEnumeration in the database
        List<ReEnumeration> reEnumerationList = reEnumerationRepository.findAll();
        assertThat(reEnumerationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReEnumerations() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList
        restReEnumerationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reEnumeration.getId().intValue())))
            .andExpect(jsonPath("$.[*].salaryBasis").value(hasItem(DEFAULT_SALARY_BASIS)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())));
    }

    @Test
    @Transactional
    void getReEnumeration() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get the reEnumeration
        restReEnumerationMockMvc
            .perform(get(ENTITY_API_URL_ID, reEnumeration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reEnumeration.getId().intValue()))
            .andExpect(jsonPath("$.salaryBasis").value(DEFAULT_SALARY_BASIS))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.employeId").value(DEFAULT_EMPLOYE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()));
    }

    @Test
    @Transactional
    void getReEnumerationsByIdFiltering() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        Long id = reEnumeration.getId();

        defaultReEnumerationShouldBeFound("id.equals=" + id);
        defaultReEnumerationShouldNotBeFound("id.notEquals=" + id);

        defaultReEnumerationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReEnumerationShouldNotBeFound("id.greaterThan=" + id);

        defaultReEnumerationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReEnumerationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllReEnumerationsBySalaryBasisIsEqualToSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where salaryBasis equals to DEFAULT_SALARY_BASIS
        defaultReEnumerationShouldBeFound("salaryBasis.equals=" + DEFAULT_SALARY_BASIS);

        // Get all the reEnumerationList where salaryBasis equals to UPDATED_SALARY_BASIS
        defaultReEnumerationShouldNotBeFound("salaryBasis.equals=" + UPDATED_SALARY_BASIS);
    }

    @Test
    @Transactional
    void getAllReEnumerationsBySalaryBasisIsInShouldWork() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where salaryBasis in DEFAULT_SALARY_BASIS or UPDATED_SALARY_BASIS
        defaultReEnumerationShouldBeFound("salaryBasis.in=" + DEFAULT_SALARY_BASIS + "," + UPDATED_SALARY_BASIS);

        // Get all the reEnumerationList where salaryBasis equals to UPDATED_SALARY_BASIS
        defaultReEnumerationShouldNotBeFound("salaryBasis.in=" + UPDATED_SALARY_BASIS);
    }

    @Test
    @Transactional
    void getAllReEnumerationsBySalaryBasisIsNullOrNotNull() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where salaryBasis is not null
        defaultReEnumerationShouldBeFound("salaryBasis.specified=true");

        // Get all the reEnumerationList where salaryBasis is null
        defaultReEnumerationShouldNotBeFound("salaryBasis.specified=false");
    }

    @Test
    @Transactional
    void getAllReEnumerationsBySalaryBasisContainsSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where salaryBasis contains DEFAULT_SALARY_BASIS
        defaultReEnumerationShouldBeFound("salaryBasis.contains=" + DEFAULT_SALARY_BASIS);

        // Get all the reEnumerationList where salaryBasis contains UPDATED_SALARY_BASIS
        defaultReEnumerationShouldNotBeFound("salaryBasis.contains=" + UPDATED_SALARY_BASIS);
    }

    @Test
    @Transactional
    void getAllReEnumerationsBySalaryBasisNotContainsSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where salaryBasis does not contain DEFAULT_SALARY_BASIS
        defaultReEnumerationShouldNotBeFound("salaryBasis.doesNotContain=" + DEFAULT_SALARY_BASIS);

        // Get all the reEnumerationList where salaryBasis does not contain UPDATED_SALARY_BASIS
        defaultReEnumerationShouldBeFound("salaryBasis.doesNotContain=" + UPDATED_SALARY_BASIS);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where amount equals to DEFAULT_AMOUNT
        defaultReEnumerationShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the reEnumerationList where amount equals to UPDATED_AMOUNT
        defaultReEnumerationShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultReEnumerationShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the reEnumerationList where amount equals to UPDATED_AMOUNT
        defaultReEnumerationShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where amount is not null
        defaultReEnumerationShouldBeFound("amount.specified=true");

        // Get all the reEnumerationList where amount is null
        defaultReEnumerationShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllReEnumerationsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultReEnumerationShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the reEnumerationList where amount is greater than or equal to UPDATED_AMOUNT
        defaultReEnumerationShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where amount is less than or equal to DEFAULT_AMOUNT
        defaultReEnumerationShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the reEnumerationList where amount is less than or equal to SMALLER_AMOUNT
        defaultReEnumerationShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where amount is less than DEFAULT_AMOUNT
        defaultReEnumerationShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the reEnumerationList where amount is less than UPDATED_AMOUNT
        defaultReEnumerationShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where amount is greater than DEFAULT_AMOUNT
        defaultReEnumerationShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the reEnumerationList where amount is greater than SMALLER_AMOUNT
        defaultReEnumerationShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByPaymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where paymentType equals to DEFAULT_PAYMENT_TYPE
        defaultReEnumerationShouldBeFound("paymentType.equals=" + DEFAULT_PAYMENT_TYPE);

        // Get all the reEnumerationList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultReEnumerationShouldNotBeFound("paymentType.equals=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByPaymentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where paymentType in DEFAULT_PAYMENT_TYPE or UPDATED_PAYMENT_TYPE
        defaultReEnumerationShouldBeFound("paymentType.in=" + DEFAULT_PAYMENT_TYPE + "," + UPDATED_PAYMENT_TYPE);

        // Get all the reEnumerationList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultReEnumerationShouldNotBeFound("paymentType.in=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByPaymentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where paymentType is not null
        defaultReEnumerationShouldBeFound("paymentType.specified=true");

        // Get all the reEnumerationList where paymentType is null
        defaultReEnumerationShouldNotBeFound("paymentType.specified=false");
    }

    @Test
    @Transactional
    void getAllReEnumerationsByPaymentTypeContainsSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where paymentType contains DEFAULT_PAYMENT_TYPE
        defaultReEnumerationShouldBeFound("paymentType.contains=" + DEFAULT_PAYMENT_TYPE);

        // Get all the reEnumerationList where paymentType contains UPDATED_PAYMENT_TYPE
        defaultReEnumerationShouldNotBeFound("paymentType.contains=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByPaymentTypeNotContainsSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where paymentType does not contain DEFAULT_PAYMENT_TYPE
        defaultReEnumerationShouldNotBeFound("paymentType.doesNotContain=" + DEFAULT_PAYMENT_TYPE);

        // Get all the reEnumerationList where paymentType does not contain UPDATED_PAYMENT_TYPE
        defaultReEnumerationShouldBeFound("paymentType.doesNotContain=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultReEnumerationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the reEnumerationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultReEnumerationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultReEnumerationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the reEnumerationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultReEnumerationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where lastModified is not null
        defaultReEnumerationShouldBeFound("lastModified.specified=true");

        // Get all the reEnumerationList where lastModified is null
        defaultReEnumerationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllReEnumerationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultReEnumerationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the reEnumerationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultReEnumerationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultReEnumerationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the reEnumerationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultReEnumerationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where lastModifiedBy is not null
        defaultReEnumerationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the reEnumerationList where lastModifiedBy is null
        defaultReEnumerationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllReEnumerationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultReEnumerationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the reEnumerationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultReEnumerationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultReEnumerationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the reEnumerationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultReEnumerationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where status equals to DEFAULT_STATUS
        defaultReEnumerationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the reEnumerationList where status equals to UPDATED_STATUS
        defaultReEnumerationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultReEnumerationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the reEnumerationList where status equals to UPDATED_STATUS
        defaultReEnumerationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where status is not null
        defaultReEnumerationShouldBeFound("status.specified=true");

        // Get all the reEnumerationList where status is null
        defaultReEnumerationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllReEnumerationsByStatusContainsSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where status contains DEFAULT_STATUS
        defaultReEnumerationShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the reEnumerationList where status contains UPDATED_STATUS
        defaultReEnumerationShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where status does not contain DEFAULT_STATUS
        defaultReEnumerationShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the reEnumerationList where status does not contain UPDATED_STATUS
        defaultReEnumerationShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByEmployeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where employeId equals to DEFAULT_EMPLOYE_ID
        defaultReEnumerationShouldBeFound("employeId.equals=" + DEFAULT_EMPLOYE_ID);

        // Get all the reEnumerationList where employeId equals to UPDATED_EMPLOYE_ID
        defaultReEnumerationShouldNotBeFound("employeId.equals=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByEmployeIdIsInShouldWork() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where employeId in DEFAULT_EMPLOYE_ID or UPDATED_EMPLOYE_ID
        defaultReEnumerationShouldBeFound("employeId.in=" + DEFAULT_EMPLOYE_ID + "," + UPDATED_EMPLOYE_ID);

        // Get all the reEnumerationList where employeId equals to UPDATED_EMPLOYE_ID
        defaultReEnumerationShouldNotBeFound("employeId.in=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByEmployeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where employeId is not null
        defaultReEnumerationShouldBeFound("employeId.specified=true");

        // Get all the reEnumerationList where employeId is null
        defaultReEnumerationShouldNotBeFound("employeId.specified=false");
    }

    @Test
    @Transactional
    void getAllReEnumerationsByEmployeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where employeId is greater than or equal to DEFAULT_EMPLOYE_ID
        defaultReEnumerationShouldBeFound("employeId.greaterThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the reEnumerationList where employeId is greater than or equal to UPDATED_EMPLOYE_ID
        defaultReEnumerationShouldNotBeFound("employeId.greaterThanOrEqual=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByEmployeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where employeId is less than or equal to DEFAULT_EMPLOYE_ID
        defaultReEnumerationShouldBeFound("employeId.lessThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the reEnumerationList where employeId is less than or equal to SMALLER_EMPLOYE_ID
        defaultReEnumerationShouldNotBeFound("employeId.lessThanOrEqual=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByEmployeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where employeId is less than DEFAULT_EMPLOYE_ID
        defaultReEnumerationShouldNotBeFound("employeId.lessThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the reEnumerationList where employeId is less than UPDATED_EMPLOYE_ID
        defaultReEnumerationShouldBeFound("employeId.lessThan=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByEmployeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where employeId is greater than DEFAULT_EMPLOYE_ID
        defaultReEnumerationShouldNotBeFound("employeId.greaterThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the reEnumerationList where employeId is greater than SMALLER_EMPLOYE_ID
        defaultReEnumerationShouldBeFound("employeId.greaterThan=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where companyId equals to DEFAULT_COMPANY_ID
        defaultReEnumerationShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the reEnumerationList where companyId equals to UPDATED_COMPANY_ID
        defaultReEnumerationShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultReEnumerationShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the reEnumerationList where companyId equals to UPDATED_COMPANY_ID
        defaultReEnumerationShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where companyId is not null
        defaultReEnumerationShouldBeFound("companyId.specified=true");

        // Get all the reEnumerationList where companyId is null
        defaultReEnumerationShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllReEnumerationsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultReEnumerationShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the reEnumerationList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultReEnumerationShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultReEnumerationShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the reEnumerationList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultReEnumerationShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where companyId is less than DEFAULT_COMPANY_ID
        defaultReEnumerationShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the reEnumerationList where companyId is less than UPDATED_COMPANY_ID
        defaultReEnumerationShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllReEnumerationsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        // Get all the reEnumerationList where companyId is greater than DEFAULT_COMPANY_ID
        defaultReEnumerationShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the reEnumerationList where companyId is greater than SMALLER_COMPANY_ID
        defaultReEnumerationShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReEnumerationShouldBeFound(String filter) throws Exception {
        restReEnumerationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reEnumeration.getId().intValue())))
            .andExpect(jsonPath("$.[*].salaryBasis").value(hasItem(DEFAULT_SALARY_BASIS)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())));

        // Check, that the count call also returns 1
        restReEnumerationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReEnumerationShouldNotBeFound(String filter) throws Exception {
        restReEnumerationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReEnumerationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingReEnumeration() throws Exception {
        // Get the reEnumeration
        restReEnumerationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReEnumeration() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        int databaseSizeBeforeUpdate = reEnumerationRepository.findAll().size();

        // Update the reEnumeration
        ReEnumeration updatedReEnumeration = reEnumerationRepository.findById(reEnumeration.getId()).get();
        // Disconnect from session so that the updates on updatedReEnumeration are not directly saved in db
        em.detach(updatedReEnumeration);
        updatedReEnumeration
            .salaryBasis(UPDATED_SALARY_BASIS)
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .status(UPDATED_STATUS)
            .employeId(UPDATED_EMPLOYE_ID)
            .companyId(UPDATED_COMPANY_ID);
        ReEnumerationDTO reEnumerationDTO = reEnumerationMapper.toDto(updatedReEnumeration);

        restReEnumerationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reEnumerationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reEnumerationDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReEnumeration in the database
        List<ReEnumeration> reEnumerationList = reEnumerationRepository.findAll();
        assertThat(reEnumerationList).hasSize(databaseSizeBeforeUpdate);
        ReEnumeration testReEnumeration = reEnumerationList.get(reEnumerationList.size() - 1);
        assertThat(testReEnumeration.getSalaryBasis()).isEqualTo(UPDATED_SALARY_BASIS);
        assertThat(testReEnumeration.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testReEnumeration.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testReEnumeration.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testReEnumeration.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testReEnumeration.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReEnumeration.getEmployeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testReEnumeration.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void putNonExistingReEnumeration() throws Exception {
        int databaseSizeBeforeUpdate = reEnumerationRepository.findAll().size();
        reEnumeration.setId(count.incrementAndGet());

        // Create the ReEnumeration
        ReEnumerationDTO reEnumerationDTO = reEnumerationMapper.toDto(reEnumeration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReEnumerationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reEnumerationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reEnumerationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReEnumeration in the database
        List<ReEnumeration> reEnumerationList = reEnumerationRepository.findAll();
        assertThat(reEnumerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReEnumeration() throws Exception {
        int databaseSizeBeforeUpdate = reEnumerationRepository.findAll().size();
        reEnumeration.setId(count.incrementAndGet());

        // Create the ReEnumeration
        ReEnumerationDTO reEnumerationDTO = reEnumerationMapper.toDto(reEnumeration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReEnumerationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reEnumerationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReEnumeration in the database
        List<ReEnumeration> reEnumerationList = reEnumerationRepository.findAll();
        assertThat(reEnumerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReEnumeration() throws Exception {
        int databaseSizeBeforeUpdate = reEnumerationRepository.findAll().size();
        reEnumeration.setId(count.incrementAndGet());

        // Create the ReEnumeration
        ReEnumerationDTO reEnumerationDTO = reEnumerationMapper.toDto(reEnumeration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReEnumerationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reEnumerationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReEnumeration in the database
        List<ReEnumeration> reEnumerationList = reEnumerationRepository.findAll();
        assertThat(reEnumerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReEnumerationWithPatch() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        int databaseSizeBeforeUpdate = reEnumerationRepository.findAll().size();

        // Update the reEnumeration using partial update
        ReEnumeration partialUpdatedReEnumeration = new ReEnumeration();
        partialUpdatedReEnumeration.setId(reEnumeration.getId());

        partialUpdatedReEnumeration
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .status(UPDATED_STATUS);

        restReEnumerationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReEnumeration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReEnumeration))
            )
            .andExpect(status().isOk());

        // Validate the ReEnumeration in the database
        List<ReEnumeration> reEnumerationList = reEnumerationRepository.findAll();
        assertThat(reEnumerationList).hasSize(databaseSizeBeforeUpdate);
        ReEnumeration testReEnumeration = reEnumerationList.get(reEnumerationList.size() - 1);
        assertThat(testReEnumeration.getSalaryBasis()).isEqualTo(DEFAULT_SALARY_BASIS);
        assertThat(testReEnumeration.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testReEnumeration.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testReEnumeration.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testReEnumeration.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testReEnumeration.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReEnumeration.getEmployeId()).isEqualTo(DEFAULT_EMPLOYE_ID);
        assertThat(testReEnumeration.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    void fullUpdateReEnumerationWithPatch() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        int databaseSizeBeforeUpdate = reEnumerationRepository.findAll().size();

        // Update the reEnumeration using partial update
        ReEnumeration partialUpdatedReEnumeration = new ReEnumeration();
        partialUpdatedReEnumeration.setId(reEnumeration.getId());

        partialUpdatedReEnumeration
            .salaryBasis(UPDATED_SALARY_BASIS)
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .status(UPDATED_STATUS)
            .employeId(UPDATED_EMPLOYE_ID)
            .companyId(UPDATED_COMPANY_ID);

        restReEnumerationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReEnumeration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReEnumeration))
            )
            .andExpect(status().isOk());

        // Validate the ReEnumeration in the database
        List<ReEnumeration> reEnumerationList = reEnumerationRepository.findAll();
        assertThat(reEnumerationList).hasSize(databaseSizeBeforeUpdate);
        ReEnumeration testReEnumeration = reEnumerationList.get(reEnumerationList.size() - 1);
        assertThat(testReEnumeration.getSalaryBasis()).isEqualTo(UPDATED_SALARY_BASIS);
        assertThat(testReEnumeration.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testReEnumeration.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testReEnumeration.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testReEnumeration.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testReEnumeration.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReEnumeration.getEmployeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testReEnumeration.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingReEnumeration() throws Exception {
        int databaseSizeBeforeUpdate = reEnumerationRepository.findAll().size();
        reEnumeration.setId(count.incrementAndGet());

        // Create the ReEnumeration
        ReEnumerationDTO reEnumerationDTO = reEnumerationMapper.toDto(reEnumeration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReEnumerationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reEnumerationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reEnumerationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReEnumeration in the database
        List<ReEnumeration> reEnumerationList = reEnumerationRepository.findAll();
        assertThat(reEnumerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReEnumeration() throws Exception {
        int databaseSizeBeforeUpdate = reEnumerationRepository.findAll().size();
        reEnumeration.setId(count.incrementAndGet());

        // Create the ReEnumeration
        ReEnumerationDTO reEnumerationDTO = reEnumerationMapper.toDto(reEnumeration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReEnumerationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reEnumerationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReEnumeration in the database
        List<ReEnumeration> reEnumerationList = reEnumerationRepository.findAll();
        assertThat(reEnumerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReEnumeration() throws Exception {
        int databaseSizeBeforeUpdate = reEnumerationRepository.findAll().size();
        reEnumeration.setId(count.incrementAndGet());

        // Create the ReEnumeration
        ReEnumerationDTO reEnumerationDTO = reEnumerationMapper.toDto(reEnumeration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReEnumerationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reEnumerationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReEnumeration in the database
        List<ReEnumeration> reEnumerationList = reEnumerationRepository.findAll();
        assertThat(reEnumerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReEnumeration() throws Exception {
        // Initialize the database
        reEnumerationRepository.saveAndFlush(reEnumeration);

        int databaseSizeBeforeDelete = reEnumerationRepository.findAll().size();

        // Delete the reEnumeration
        restReEnumerationMockMvc
            .perform(delete(ENTITY_API_URL_ID, reEnumeration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReEnumeration> reEnumerationList = reEnumerationRepository.findAll();
        assertThat(reEnumerationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
