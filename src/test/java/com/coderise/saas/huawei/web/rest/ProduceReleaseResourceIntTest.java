package com.coderise.saas.huawei.web.rest;

import com.coderise.saas.huawei.HuaweiSaasApp;

import com.coderise.saas.huawei.domain.ProduceRelease;
import com.coderise.saas.huawei.repository.ProduceReleaseRepository;
import com.coderise.saas.huawei.service.ProduceReleaseService;
import com.coderise.saas.huawei.service.dto.ProduceReleaseDTO;
import com.coderise.saas.huawei.service.mapper.ProduceReleaseMapper;
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
 * Test class for the ProduceReleaseResource REST controller.
 *
 * @see ProduceReleaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HuaweiSaasApp.class)
public class ProduceReleaseResourceIntTest {

    private static final Boolean DEFAULT_TEST_FLAG = false;
    private static final Boolean UPDATED_TEST_FLAG = true;

    private static final Instant DEFAULT_TIME_STAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_STAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProduceReleaseRepository produceReleaseRepository;

    @Autowired
    private ProduceReleaseMapper produceReleaseMapper;

    @Autowired
    private ProduceReleaseService produceReleaseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProduceReleaseMockMvc;

    private ProduceRelease produceRelease;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProduceReleaseResource produceReleaseResource = new ProduceReleaseResource(produceReleaseService);
        this.restProduceReleaseMockMvc = MockMvcBuilders.standaloneSetup(produceReleaseResource)
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
    public static ProduceRelease createEntity(EntityManager em) {
        ProduceRelease produceRelease = new ProduceRelease()
            .testFlag(DEFAULT_TEST_FLAG)
            .timeStamp(DEFAULT_TIME_STAMP);
        return produceRelease;
    }

    @Before
    public void initTest() {
        produceRelease = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduceRelease() throws Exception {
        int databaseSizeBeforeCreate = produceReleaseRepository.findAll().size();

        // Create the ProduceRelease
        ProduceReleaseDTO produceReleaseDTO = produceReleaseMapper.toDto(produceRelease);
        restProduceReleaseMockMvc.perform(post("/api/produce-releases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceReleaseDTO)))
            .andExpect(status().isCreated());

        // Validate the ProduceRelease in the database
        List<ProduceRelease> produceReleaseList = produceReleaseRepository.findAll();
        assertThat(produceReleaseList).hasSize(databaseSizeBeforeCreate + 1);
        ProduceRelease testProduceRelease = produceReleaseList.get(produceReleaseList.size() - 1);
        assertThat(testProduceRelease.isTestFlag()).isEqualTo(DEFAULT_TEST_FLAG);
        assertThat(testProduceRelease.getTimeStamp()).isEqualTo(DEFAULT_TIME_STAMP);
    }

    @Test
    @Transactional
    public void createProduceReleaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produceReleaseRepository.findAll().size();

        // Create the ProduceRelease with an existing ID
        produceRelease.setId(1L);
        ProduceReleaseDTO produceReleaseDTO = produceReleaseMapper.toDto(produceRelease);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduceReleaseMockMvc.perform(post("/api/produce-releases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceReleaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProduceRelease in the database
        List<ProduceRelease> produceReleaseList = produceReleaseRepository.findAll();
        assertThat(produceReleaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProduceReleases() throws Exception {
        // Initialize the database
        produceReleaseRepository.saveAndFlush(produceRelease);

        // Get all the produceReleaseList
        restProduceReleaseMockMvc.perform(get("/api/produce-releases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produceRelease.getId().intValue())))
            .andExpect(jsonPath("$.[*].testFlag").value(hasItem(DEFAULT_TEST_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(DEFAULT_TIME_STAMP.toString())));
    }

    @Test
    @Transactional
    public void getProduceRelease() throws Exception {
        // Initialize the database
        produceReleaseRepository.saveAndFlush(produceRelease);

        // Get the produceRelease
        restProduceReleaseMockMvc.perform(get("/api/produce-releases/{id}", produceRelease.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produceRelease.getId().intValue()))
            .andExpect(jsonPath("$.testFlag").value(DEFAULT_TEST_FLAG.booleanValue()))
            .andExpect(jsonPath("$.timeStamp").value(DEFAULT_TIME_STAMP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduceRelease() throws Exception {
        // Get the produceRelease
        restProduceReleaseMockMvc.perform(get("/api/produce-releases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduceRelease() throws Exception {
        // Initialize the database
        produceReleaseRepository.saveAndFlush(produceRelease);
        int databaseSizeBeforeUpdate = produceReleaseRepository.findAll().size();

        // Update the produceRelease
        ProduceRelease updatedProduceRelease = produceReleaseRepository.findOne(produceRelease.getId());
        // Disconnect from session so that the updates on updatedProduceRelease are not directly saved in db
        em.detach(updatedProduceRelease);
        updatedProduceRelease
            .testFlag(UPDATED_TEST_FLAG)
            .timeStamp(UPDATED_TIME_STAMP);
        ProduceReleaseDTO produceReleaseDTO = produceReleaseMapper.toDto(updatedProduceRelease);

        restProduceReleaseMockMvc.perform(put("/api/produce-releases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceReleaseDTO)))
            .andExpect(status().isOk());

        // Validate the ProduceRelease in the database
        List<ProduceRelease> produceReleaseList = produceReleaseRepository.findAll();
        assertThat(produceReleaseList).hasSize(databaseSizeBeforeUpdate);
        ProduceRelease testProduceRelease = produceReleaseList.get(produceReleaseList.size() - 1);
        assertThat(testProduceRelease.isTestFlag()).isEqualTo(UPDATED_TEST_FLAG);
        assertThat(testProduceRelease.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingProduceRelease() throws Exception {
        int databaseSizeBeforeUpdate = produceReleaseRepository.findAll().size();

        // Create the ProduceRelease
        ProduceReleaseDTO produceReleaseDTO = produceReleaseMapper.toDto(produceRelease);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProduceReleaseMockMvc.perform(put("/api/produce-releases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produceReleaseDTO)))
            .andExpect(status().isCreated());

        // Validate the ProduceRelease in the database
        List<ProduceRelease> produceReleaseList = produceReleaseRepository.findAll();
        assertThat(produceReleaseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduceRelease() throws Exception {
        // Initialize the database
        produceReleaseRepository.saveAndFlush(produceRelease);
        int databaseSizeBeforeDelete = produceReleaseRepository.findAll().size();

        // Get the produceRelease
        restProduceReleaseMockMvc.perform(delete("/api/produce-releases/{id}", produceRelease.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProduceRelease> produceReleaseList = produceReleaseRepository.findAll();
        assertThat(produceReleaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProduceRelease.class);
        ProduceRelease produceRelease1 = new ProduceRelease();
        produceRelease1.setId(1L);
        ProduceRelease produceRelease2 = new ProduceRelease();
        produceRelease2.setId(produceRelease1.getId());
        assertThat(produceRelease1).isEqualTo(produceRelease2);
        produceRelease2.setId(2L);
        assertThat(produceRelease1).isNotEqualTo(produceRelease2);
        produceRelease1.setId(null);
        assertThat(produceRelease1).isNotEqualTo(produceRelease2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProduceReleaseDTO.class);
        ProduceReleaseDTO produceReleaseDTO1 = new ProduceReleaseDTO();
        produceReleaseDTO1.setId(1L);
        ProduceReleaseDTO produceReleaseDTO2 = new ProduceReleaseDTO();
        assertThat(produceReleaseDTO1).isNotEqualTo(produceReleaseDTO2);
        produceReleaseDTO2.setId(produceReleaseDTO1.getId());
        assertThat(produceReleaseDTO1).isEqualTo(produceReleaseDTO2);
        produceReleaseDTO2.setId(2L);
        assertThat(produceReleaseDTO1).isNotEqualTo(produceReleaseDTO2);
        produceReleaseDTO1.setId(null);
        assertThat(produceReleaseDTO1).isNotEqualTo(produceReleaseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(produceReleaseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(produceReleaseMapper.fromId(null)).isNull();
    }
}
