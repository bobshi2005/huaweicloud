package com.coderise.saas.huawei.web.rest;

import com.coderise.saas.huawei.HuaweiSaasApp;

import com.coderise.saas.huawei.domain.ProduceExtend;
import com.coderise.saas.huawei.repository.ProduceExtendRepository;
import com.coderise.saas.huawei.service.ProduceExtendService;
import com.coderise.saas.huawei.service.dto.ProduceExtendDTO;
import com.coderise.saas.huawei.service.mapper.ProduceExtendMapper;
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
 * Test class for the ProduceExtendResource REST controller.
 *
 * @see ProduceExtendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HuaweiSaasApp.class)
public class ProduceExtendResourceIntTest {

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EXPIRE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_TEST_FLAG = false;
    private static final Boolean UPDATED_TEST_FLAG = true;

    private static final Boolean DEFAULT_TRIAL_TO_FORMAL = false;
    private static final Boolean UPDATED_TRIAL_TO_FORMAL = true;

    private static final Instant DEFAULT_TIME_STAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_STAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProduceExtendRepository produceExtendRepository;

    @Autowired
    private ProduceExtendMapper produceExtendMapper;

    @Autowired
    private ProduceExtendService produceExtendService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProduceExtendMockMvc;

    private ProduceExtend produceExtend;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProduceExtendResource produceExtendResource = new ProduceExtendResource(produceExtendService);
        this.restProduceExtendMockMvc = MockMvcBuilders.standaloneSetup(produceExtendResource)
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
    public static ProduceExtend createEntity(EntityManager em) {
        ProduceExtend produceExtend = new ProduceExtend()
            .orderId(DEFAULT_ORDER_ID)
            .productId(DEFAULT_PRODUCT_ID)
            .expireTime(DEFAULT_EXPIRE_TIME)
            .testFlag(DEFAULT_TEST_FLAG)
            .trialToFormal(DEFAULT_TRIAL_TO_FORMAL)
            .timeStamp(DEFAULT_TIME_STAMP);
        return produceExtend;
    }

    @Before
    public void initTest() {
        produceExtend = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduceExtend() throws Exception {
        int databaseSizeBeforeCreate = produceExtendRepository.findAll().size();

        // Create the ProduceExtend
        ProduceExtendDTO produceExtendDTO = produceExtendMapper.toDto(produceExtend);
        restProduceExtendMockMvc.perform(post("/api/produce-extends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceExtendDTO)))
            .andExpect(status().isCreated());

        // Validate the ProduceExtend in the database
        List<ProduceExtend> produceExtendList = produceExtendRepository.findAll();
        assertThat(produceExtendList).hasSize(databaseSizeBeforeCreate + 1);
        ProduceExtend testProduceExtend = produceExtendList.get(produceExtendList.size() - 1);
        assertThat(testProduceExtend.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testProduceExtend.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testProduceExtend.getExpireTime()).isEqualTo(DEFAULT_EXPIRE_TIME);
        assertThat(testProduceExtend.isTestFlag()).isEqualTo(DEFAULT_TEST_FLAG);
        assertThat(testProduceExtend.isTrialToFormal()).isEqualTo(DEFAULT_TRIAL_TO_FORMAL);
        assertThat(testProduceExtend.getTimeStamp()).isEqualTo(DEFAULT_TIME_STAMP);
    }

    @Test
    @Transactional
    public void createProduceExtendWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produceExtendRepository.findAll().size();

        // Create the ProduceExtend with an existing ID
        produceExtend.setId(1L);
        ProduceExtendDTO produceExtendDTO = produceExtendMapper.toDto(produceExtend);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduceExtendMockMvc.perform(post("/api/produce-extends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceExtendDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProduceExtend in the database
        List<ProduceExtend> produceExtendList = produceExtendRepository.findAll();
        assertThat(produceExtendList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProduceExtends() throws Exception {
        // Initialize the database
        produceExtendRepository.saveAndFlush(produceExtend);

        // Get all the produceExtendList
        restProduceExtendMockMvc.perform(get("/api/produce-extends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produceExtend.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID.toString())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.toString())))
            .andExpect(jsonPath("$.[*].expireTime").value(hasItem(sameInstant(DEFAULT_EXPIRE_TIME))))
            .andExpect(jsonPath("$.[*].testFlag").value(hasItem(DEFAULT_TEST_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].trialToFormal").value(hasItem(DEFAULT_TRIAL_TO_FORMAL.booleanValue())))
            .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(DEFAULT_TIME_STAMP.toString())));
    }

    @Test
    @Transactional
    public void getProduceExtend() throws Exception {
        // Initialize the database
        produceExtendRepository.saveAndFlush(produceExtend);

        // Get the produceExtend
        restProduceExtendMockMvc.perform(get("/api/produce-extends/{id}", produceExtend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produceExtend.getId().intValue()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID.toString()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.toString()))
            .andExpect(jsonPath("$.expireTime").value(sameInstant(DEFAULT_EXPIRE_TIME)))
            .andExpect(jsonPath("$.testFlag").value(DEFAULT_TEST_FLAG.booleanValue()))
            .andExpect(jsonPath("$.trialToFormal").value(DEFAULT_TRIAL_TO_FORMAL.booleanValue()))
            .andExpect(jsonPath("$.timeStamp").value(DEFAULT_TIME_STAMP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduceExtend() throws Exception {
        // Get the produceExtend
        restProduceExtendMockMvc.perform(get("/api/produce-extends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduceExtend() throws Exception {
        // Initialize the database
        produceExtendRepository.saveAndFlush(produceExtend);
        int databaseSizeBeforeUpdate = produceExtendRepository.findAll().size();

        // Update the produceExtend
        ProduceExtend updatedProduceExtend = produceExtendRepository.findOne(produceExtend.getId());
        // Disconnect from session so that the updates on updatedProduceExtend are not directly saved in db
        em.detach(updatedProduceExtend);
        updatedProduceExtend
            .orderId(UPDATED_ORDER_ID)
            .productId(UPDATED_PRODUCT_ID)
            .expireTime(UPDATED_EXPIRE_TIME)
            .testFlag(UPDATED_TEST_FLAG)
            .trialToFormal(UPDATED_TRIAL_TO_FORMAL)
            .timeStamp(UPDATED_TIME_STAMP);
        ProduceExtendDTO produceExtendDTO = produceExtendMapper.toDto(updatedProduceExtend);

        restProduceExtendMockMvc.perform(put("/api/produce-extends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceExtendDTO)))
            .andExpect(status().isOk());

        // Validate the ProduceExtend in the database
        List<ProduceExtend> produceExtendList = produceExtendRepository.findAll();
        assertThat(produceExtendList).hasSize(databaseSizeBeforeUpdate);
        ProduceExtend testProduceExtend = produceExtendList.get(produceExtendList.size() - 1);
        assertThat(testProduceExtend.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testProduceExtend.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testProduceExtend.getExpireTime()).isEqualTo(UPDATED_EXPIRE_TIME);
        assertThat(testProduceExtend.isTestFlag()).isEqualTo(UPDATED_TEST_FLAG);
        assertThat(testProduceExtend.isTrialToFormal()).isEqualTo(UPDATED_TRIAL_TO_FORMAL);
        assertThat(testProduceExtend.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingProduceExtend() throws Exception {
        int databaseSizeBeforeUpdate = produceExtendRepository.findAll().size();

        // Create the ProduceExtend
        ProduceExtendDTO produceExtendDTO = produceExtendMapper.toDto(produceExtend);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProduceExtendMockMvc.perform(put("/api/produce-extends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceExtendDTO)))
            .andExpect(status().isCreated());

        // Validate the ProduceExtend in the database
        List<ProduceExtend> produceExtendList = produceExtendRepository.findAll();
        assertThat(produceExtendList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduceExtend() throws Exception {
        // Initialize the database
        produceExtendRepository.saveAndFlush(produceExtend);
        int databaseSizeBeforeDelete = produceExtendRepository.findAll().size();

        // Get the produceExtend
        restProduceExtendMockMvc.perform(delete("/api/produce-extends/{id}", produceExtend.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProduceExtend> produceExtendList = produceExtendRepository.findAll();
        assertThat(produceExtendList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProduceExtend.class);
        ProduceExtend produceExtend1 = new ProduceExtend();
        produceExtend1.setId(1L);
        ProduceExtend produceExtend2 = new ProduceExtend();
        produceExtend2.setId(produceExtend1.getId());
        assertThat(produceExtend1).isEqualTo(produceExtend2);
        produceExtend2.setId(2L);
        assertThat(produceExtend1).isNotEqualTo(produceExtend2);
        produceExtend1.setId(null);
        assertThat(produceExtend1).isNotEqualTo(produceExtend2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProduceExtendDTO.class);
        ProduceExtendDTO produceExtendDTO1 = new ProduceExtendDTO();
        produceExtendDTO1.setId(1L);
        ProduceExtendDTO produceExtendDTO2 = new ProduceExtendDTO();
        assertThat(produceExtendDTO1).isNotEqualTo(produceExtendDTO2);
        produceExtendDTO2.setId(produceExtendDTO1.getId());
        assertThat(produceExtendDTO1).isEqualTo(produceExtendDTO2);
        produceExtendDTO2.setId(2L);
        assertThat(produceExtendDTO1).isNotEqualTo(produceExtendDTO2);
        produceExtendDTO1.setId(null);
        assertThat(produceExtendDTO1).isNotEqualTo(produceExtendDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(produceExtendMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(produceExtendMapper.fromId(null)).isNull();
    }
}
