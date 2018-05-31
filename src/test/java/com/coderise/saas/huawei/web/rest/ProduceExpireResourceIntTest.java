package com.coderise.saas.huawei.web.rest;

import com.coderise.saas.huawei.HuaweiSaasApp;

import com.coderise.saas.huawei.domain.ProduceExpire;
import com.coderise.saas.huawei.repository.ProduceExpireRepository;
import com.coderise.saas.huawei.service.ProduceExpireService;
import com.coderise.saas.huawei.service.dto.ProduceExpireDTO;
import com.coderise.saas.huawei.service.mapper.ProduceExpireMapper;
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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.coderise.saas.huawei.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProduceExpireResource REST controller.
 *
 * @see ProduceExpireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HuaweiSaasApp.class)
public class ProduceExpireResourceIntTest {

    private static final Boolean DEFAULT_TEST_FLAG = false;
    private static final Boolean UPDATED_TEST_FLAG = true;

    private static final Instant DEFAULT_TIME_STAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_STAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProduceExpireRepository produceExpireRepository;

    @Autowired
    private ProduceExpireMapper produceExpireMapper;

    @Autowired
    private ProduceExpireService produceExpireService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProduceExpireMockMvc;

    private ProduceExpire produceExpire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProduceExpireResource produceExpireResource = new ProduceExpireResource(produceExpireService);
        this.restProduceExpireMockMvc = MockMvcBuilders.standaloneSetup(produceExpireResource)
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
    public static ProduceExpire createEntity(EntityManager em) {
        ProduceExpire produceExpire = new ProduceExpire()
            .testFlag(DEFAULT_TEST_FLAG)
            .timeStamp(DEFAULT_TIME_STAMP);
        return produceExpire;
    }

    @Before
    public void initTest() {
        produceExpire = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduceExpire() throws Exception {
        int databaseSizeBeforeCreate = produceExpireRepository.findAll().size();

        // Create the ProduceExpire
        ProduceExpireDTO produceExpireDTO = produceExpireMapper.toDto(produceExpire);
        restProduceExpireMockMvc.perform(post("/api/produce-expires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceExpireDTO)))
            .andExpect(status().isCreated());

        // Validate the ProduceExpire in the database
        List<ProduceExpire> produceExpireList = produceExpireRepository.findAll();
        assertThat(produceExpireList).hasSize(databaseSizeBeforeCreate + 1);
        ProduceExpire testProduceExpire = produceExpireList.get(produceExpireList.size() - 1);
        assertThat(testProduceExpire.isTestFlag()).isEqualTo(DEFAULT_TEST_FLAG);
        assertThat(testProduceExpire.getTimeStamp()).isEqualTo(DEFAULT_TIME_STAMP);
    }

    @Test
    @Transactional
    public void createProduceExpireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produceExpireRepository.findAll().size();

        // Create the ProduceExpire with an existing ID
        produceExpire.setId(1L);
        ProduceExpireDTO produceExpireDTO = produceExpireMapper.toDto(produceExpire);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduceExpireMockMvc.perform(post("/api/produce-expires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceExpireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProduceExpire in the database
        List<ProduceExpire> produceExpireList = produceExpireRepository.findAll();
        assertThat(produceExpireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProduceExpires() throws Exception {
        // Initialize the database
        produceExpireRepository.saveAndFlush(produceExpire);

        // Get all the produceExpireList
        restProduceExpireMockMvc.perform(get("/api/produce-expires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produceExpire.getId().intValue())))
            .andExpect(jsonPath("$.[*].testFlag").value(hasItem(DEFAULT_TEST_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(DEFAULT_TIME_STAMP.toString())));
    }

    @Test
    @Transactional
    public void getProduceExpire() throws Exception {
        // Initialize the database
        produceExpireRepository.saveAndFlush(produceExpire);

        // Get the produceExpire
        restProduceExpireMockMvc.perform(get("/api/produce-expires/{id}", produceExpire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produceExpire.getId().intValue()))
            .andExpect(jsonPath("$.testFlag").value(DEFAULT_TEST_FLAG.booleanValue()))
            .andExpect(jsonPath("$.timeStamp").value(DEFAULT_TIME_STAMP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduceExpire() throws Exception {
        // Get the produceExpire
        restProduceExpireMockMvc.perform(get("/api/produce-expires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduceExpire() throws Exception {
        // Initialize the database
        produceExpireRepository.saveAndFlush(produceExpire);
        int databaseSizeBeforeUpdate = produceExpireRepository.findAll().size();

        // Update the produceExpire
        ProduceExpire updatedProduceExpire = produceExpireRepository.findOne(produceExpire.getId());
        // Disconnect from session so that the updates on updatedProduceExpire are not directly saved in db
        em.detach(updatedProduceExpire);
        updatedProduceExpire
            .testFlag(UPDATED_TEST_FLAG)
            .timeStamp(UPDATED_TIME_STAMP);
        ProduceExpireDTO produceExpireDTO = produceExpireMapper.toDto(updatedProduceExpire);

        restProduceExpireMockMvc.perform(put("/api/produce-expires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceExpireDTO)))
            .andExpect(status().isOk());

        // Validate the ProduceExpire in the database
        List<ProduceExpire> produceExpireList = produceExpireRepository.findAll();
        assertThat(produceExpireList).hasSize(databaseSizeBeforeUpdate);
        ProduceExpire testProduceExpire = produceExpireList.get(produceExpireList.size() - 1);
        assertThat(testProduceExpire.isTestFlag()).isEqualTo(UPDATED_TEST_FLAG);
        assertThat(testProduceExpire.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingProduceExpire() throws Exception {
        int databaseSizeBeforeUpdate = produceExpireRepository.findAll().size();

        // Create the ProduceExpire
        ProduceExpireDTO produceExpireDTO = produceExpireMapper.toDto(produceExpire);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProduceExpireMockMvc.perform(put("/api/produce-expires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceExpireDTO)))
            .andExpect(status().isCreated());

        // Validate the ProduceExpire in the database
        List<ProduceExpire> produceExpireList = produceExpireRepository.findAll();
        assertThat(produceExpireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduceExpire() throws Exception {
        // Initialize the database
        produceExpireRepository.saveAndFlush(produceExpire);
        int databaseSizeBeforeDelete = produceExpireRepository.findAll().size();

        // Get the produceExpire
        restProduceExpireMockMvc.perform(delete("/api/produce-expires/{id}", produceExpire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProduceExpire> produceExpireList = produceExpireRepository.findAll();
        assertThat(produceExpireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProduceExpire.class);
        ProduceExpire produceExpire1 = new ProduceExpire();
        produceExpire1.setId(1L);
        ProduceExpire produceExpire2 = new ProduceExpire();
        produceExpire2.setId(produceExpire1.getId());
        assertThat(produceExpire1).isEqualTo(produceExpire2);
        produceExpire2.setId(2L);
        assertThat(produceExpire1).isNotEqualTo(produceExpire2);
        produceExpire1.setId(null);
        assertThat(produceExpire1).isNotEqualTo(produceExpire2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProduceExpireDTO.class);
        ProduceExpireDTO produceExpireDTO1 = new ProduceExpireDTO();
        produceExpireDTO1.setId(1L);
        ProduceExpireDTO produceExpireDTO2 = new ProduceExpireDTO();
        assertThat(produceExpireDTO1).isNotEqualTo(produceExpireDTO2);
        produceExpireDTO2.setId(produceExpireDTO1.getId());
        assertThat(produceExpireDTO1).isEqualTo(produceExpireDTO2);
        produceExpireDTO2.setId(2L);
        assertThat(produceExpireDTO1).isNotEqualTo(produceExpireDTO2);
        produceExpireDTO1.setId(null);
        assertThat(produceExpireDTO1).isNotEqualTo(produceExpireDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(produceExpireMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(produceExpireMapper.fromId(null)).isNull();
    }
}
