package com.coderise.saas.huawei.web.rest;

import com.coderise.saas.huawei.HuaweiSaasApp;

import com.coderise.saas.huawei.domain.ProduceNew;
import com.coderise.saas.huawei.repository.ProduceNewRepository;
import com.coderise.saas.huawei.service.ProduceNewService;
import com.coderise.saas.huawei.service.dto.ProduceNewDTO;
import com.coderise.saas.huawei.service.mapper.ProduceNewMapper;
import com.coderise.saas.huawei.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.coderise.saas.huawei.web.rest.TestUtil.sameInstant;
import static com.coderise.saas.huawei.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProduceNewResource REST controller.
 *
 * @see ProduceNewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HuaweiSaasApp.class)
public class ProduceNewResourceIntTest {

    private static final String DEFAULT_AUTH_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_AUTH_TOKEN = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIME_STAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_STAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SKU_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SKU_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TEST_FLAG = false;
    private static final Boolean UPDATED_TEST_FLAG = true;

    private static final Boolean DEFAULT_TRIAL_FLAG = false;
    private static final Boolean UPDATED_TRIAL_FLAG = true;

    private static final ZonedDateTime DEFAULT_EXPIRE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ProduceNewRepository produceNewRepository;

    @Autowired
    private ProduceNewMapper produceNewMapper;

    @Autowired
    private ProduceNewService produceNewService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProduceNewMockMvc;

    private ProduceNew produceNew;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProduceNewResource produceNewResource = new ProduceNewResource(produceNewService);
        this.restProduceNewMockMvc = MockMvcBuilders.standaloneSetup(produceNewResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProduceNew createEntity(EntityManager em) {
        ProduceNew produceNew = new ProduceNew()
            .authToken(DEFAULT_AUTH_TOKEN)
            .timeStamp(DEFAULT_TIME_STAMP)
            .customerId(DEFAULT_CUSTOMER_ID)
            .customerName(DEFAULT_CUSTOMER_NAME)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .email(DEFAULT_EMAIL)
            .businessId(DEFAULT_BUSINESS_ID)
            .orderId(DEFAULT_ORDER_ID)
            .skuCode(DEFAULT_SKU_CODE)
            .productId(DEFAULT_PRODUCT_ID)
            .testFlag(DEFAULT_TEST_FLAG)
            .trialFlag(DEFAULT_TRIAL_FLAG)
            .expireTime(DEFAULT_EXPIRE_TIME);
        return produceNew;
    }

    @Before
    public void initTest() {
        produceNew = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduceNew() throws Exception {
        int databaseSizeBeforeCreate = produceNewRepository.findAll().size();

        // Create the ProduceNew
        ProduceNewDTO produceNewDTO = produceNewMapper.toDto(produceNew);
        restProduceNewMockMvc.perform(post("/api/produce-news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceNewDTO)))
            .andExpect(status().isCreated());

        // Validate the ProduceNew in the database
        List<ProduceNew> produceNewList = produceNewRepository.findAll();
        assertThat(produceNewList).hasSize(databaseSizeBeforeCreate + 1);
        ProduceNew testProduceNew = produceNewList.get(produceNewList.size() - 1);
        assertThat(testProduceNew.getAuthToken()).isEqualTo(DEFAULT_AUTH_TOKEN);
        assertThat(testProduceNew.getTimeStamp()).isEqualTo(DEFAULT_TIME_STAMP);
        assertThat(testProduceNew.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testProduceNew.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testProduceNew.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testProduceNew.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProduceNew.getBusinessId()).isEqualTo(DEFAULT_BUSINESS_ID);
        assertThat(testProduceNew.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testProduceNew.getSkuCode()).isEqualTo(DEFAULT_SKU_CODE);
        assertThat(testProduceNew.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testProduceNew.isTestFlag()).isEqualTo(DEFAULT_TEST_FLAG);
        assertThat(testProduceNew.isTrialFlag()).isEqualTo(DEFAULT_TRIAL_FLAG);
        assertThat(testProduceNew.getExpireTime()).isEqualTo(DEFAULT_EXPIRE_TIME);
    }

    @Test
    @Transactional
    public void createProduceNewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produceNewRepository.findAll().size();

        // Create the ProduceNew with an existing ID
        produceNew.setId(1L);
        ProduceNewDTO produceNewDTO = produceNewMapper.toDto(produceNew);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduceNewMockMvc.perform(post("/api/produce-news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceNewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProduceNew in the database
        List<ProduceNew> produceNewList = produceNewRepository.findAll();
        assertThat(produceNewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProduceNews() throws Exception {
        // Initialize the database
        produceNewRepository.saveAndFlush(produceNew);

        // Get all the produceNewList
        restProduceNewMockMvc.perform(get("/api/produce-news?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produceNew.getId().intValue())))
            .andExpect(jsonPath("$.[*].authToken").value(hasItem(DEFAULT_AUTH_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(DEFAULT_TIME_STAMP.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.toString())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].businessId").value(hasItem(DEFAULT_BUSINESS_ID.toString())))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID.toString())))
            .andExpect(jsonPath("$.[*].skuCode").value(hasItem(DEFAULT_SKU_CODE.toString())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.toString())))
            .andExpect(jsonPath("$.[*].testFlag").value(hasItem(DEFAULT_TEST_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].trialFlag").value(hasItem(DEFAULT_TRIAL_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].expireTime").value(hasItem(sameInstant(DEFAULT_EXPIRE_TIME))));
    }

    @Test
    @Transactional
    public void getProduceNew() throws Exception {
        // Initialize the database
        produceNewRepository.saveAndFlush(produceNew);

        // Get the produceNew
        restProduceNewMockMvc.perform(get("/api/produce-news/{id}", produceNew.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produceNew.getId().intValue()))
            .andExpect(jsonPath("$.authToken").value(DEFAULT_AUTH_TOKEN.toString()))
            .andExpect(jsonPath("$.timeStamp").value(DEFAULT_TIME_STAMP.toString()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.toString()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.businessId").value(DEFAULT_BUSINESS_ID.toString()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID.toString()))
            .andExpect(jsonPath("$.skuCode").value(DEFAULT_SKU_CODE.toString()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.toString()))
            .andExpect(jsonPath("$.testFlag").value(DEFAULT_TEST_FLAG.booleanValue()))
            .andExpect(jsonPath("$.trialFlag").value(DEFAULT_TRIAL_FLAG.booleanValue()))
            .andExpect(jsonPath("$.expireTime").value(sameInstant(DEFAULT_EXPIRE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingProduceNew() throws Exception {
        // Get the produceNew
        restProduceNewMockMvc.perform(get("/api/produce-news/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduceNew() throws Exception {
        // Initialize the database
        produceNewRepository.saveAndFlush(produceNew);
        int databaseSizeBeforeUpdate = produceNewRepository.findAll().size();

        // Update the produceNew
        ProduceNew updatedProduceNew = produceNewRepository.findOne(produceNew.getId());
        // Disconnect from session so that the updates on updatedProduceNew are not directly saved in db
        em.detach(updatedProduceNew);
        updatedProduceNew
            .authToken(UPDATED_AUTH_TOKEN)
            .timeStamp(UPDATED_TIME_STAMP)
            .customerId(UPDATED_CUSTOMER_ID)
            .customerName(UPDATED_CUSTOMER_NAME)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .email(UPDATED_EMAIL)
            .businessId(UPDATED_BUSINESS_ID)
            .orderId(UPDATED_ORDER_ID)
            .skuCode(UPDATED_SKU_CODE)
            .productId(UPDATED_PRODUCT_ID)
            .testFlag(UPDATED_TEST_FLAG)
            .trialFlag(UPDATED_TRIAL_FLAG)
            .expireTime(UPDATED_EXPIRE_TIME);
        ProduceNewDTO produceNewDTO = produceNewMapper.toDto(updatedProduceNew);

        restProduceNewMockMvc.perform(put("/api/produce-news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceNewDTO)))
            .andExpect(status().isOk());

        // Validate the ProduceNew in the database
        List<ProduceNew> produceNewList = produceNewRepository.findAll();
        assertThat(produceNewList).hasSize(databaseSizeBeforeUpdate);
        ProduceNew testProduceNew = produceNewList.get(produceNewList.size() - 1);
        assertThat(testProduceNew.getAuthToken()).isEqualTo(UPDATED_AUTH_TOKEN);
        assertThat(testProduceNew.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
        assertThat(testProduceNew.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testProduceNew.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testProduceNew.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testProduceNew.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProduceNew.getBusinessId()).isEqualTo(UPDATED_BUSINESS_ID);
        assertThat(testProduceNew.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testProduceNew.getSkuCode()).isEqualTo(UPDATED_SKU_CODE);
        assertThat(testProduceNew.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testProduceNew.isTestFlag()).isEqualTo(UPDATED_TEST_FLAG);
        assertThat(testProduceNew.isTrialFlag()).isEqualTo(UPDATED_TRIAL_FLAG);
        assertThat(testProduceNew.getExpireTime()).isEqualTo(UPDATED_EXPIRE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingProduceNew() throws Exception {
        int databaseSizeBeforeUpdate = produceNewRepository.findAll().size();

        // Create the ProduceNew
        ProduceNewDTO produceNewDTO = produceNewMapper.toDto(produceNew);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProduceNewMockMvc.perform(put("/api/produce-news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceNewDTO)))
            .andExpect(status().isCreated());

        // Validate the ProduceNew in the database
        List<ProduceNew> produceNewList = produceNewRepository.findAll();
        assertThat(produceNewList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduceNew() throws Exception {
        // Initialize the database
        produceNewRepository.saveAndFlush(produceNew);
        int databaseSizeBeforeDelete = produceNewRepository.findAll().size();

        // Get the produceNew
        restProduceNewMockMvc.perform(delete("/api/produce-news/{id}", produceNew.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProduceNew> produceNewList = produceNewRepository.findAll();
        assertThat(produceNewList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProduceNew.class);
        ProduceNew produceNew1 = new ProduceNew();
        produceNew1.setId(1L);
        ProduceNew produceNew2 = new ProduceNew();
        produceNew2.setId(produceNew1.getId());
        assertThat(produceNew1).isEqualTo(produceNew2);
        produceNew2.setId(2L);
        assertThat(produceNew1).isNotEqualTo(produceNew2);
        produceNew1.setId(null);
        assertThat(produceNew1).isNotEqualTo(produceNew2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProduceNewDTO.class);
        ProduceNewDTO produceNewDTO1 = new ProduceNewDTO();
        produceNewDTO1.setId(1L);
        ProduceNewDTO produceNewDTO2 = new ProduceNewDTO();
        assertThat(produceNewDTO1).isNotEqualTo(produceNewDTO2);
        produceNewDTO2.setId(produceNewDTO1.getId());
        assertThat(produceNewDTO1).isEqualTo(produceNewDTO2);
        produceNewDTO2.setId(2L);
        assertThat(produceNewDTO1).isNotEqualTo(produceNewDTO2);
        produceNewDTO1.setId(null);
        assertThat(produceNewDTO1).isNotEqualTo(produceNewDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(produceNewMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(produceNewMapper.fromId(null)).isNull();
    }
}
