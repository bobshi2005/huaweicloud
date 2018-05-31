package com.coderise.saas.huawei.web.rest;

import com.coderise.saas.huawei.HuaweiSaasApp;

import com.coderise.saas.huawei.domain.Instance;
import com.coderise.saas.huawei.repository.InstanceRepository;
import com.coderise.saas.huawei.service.InstanceService;
import com.coderise.saas.huawei.service.dto.InstanceDTO;
import com.coderise.saas.huawei.service.mapper.InstanceMapper;
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
 * Test class for the InstanceResource REST controller.
 *
 * @see InstanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HuaweiSaasApp.class)
public class InstanceResourceIntTest {

    private static final String DEFAULT_APP_FRONT_END_URL = "AAAAAAAAAA";
    private static final String UPDATED_APP_FRONT_END_URL = "BBBBBBBBBB";

    private static final String DEFAULT_APP_ADMIN_URL = "AAAAAAAAAA";
    private static final String UPDATED_APP_ADMIN_URL = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EXPIRE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_IS_RELEASED = false;
    private static final Boolean UPDATED_IS_RELEASED = true;

    private static final Boolean DEFAULT_IS_TRIAL = false;
    private static final Boolean UPDATED_IS_TRIAL = true;

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RESULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RESULT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT_MSG = "AAAAAAAAAA";
    private static final String UPDATED_RESULT_MSG = "BBBBBBBBBB";

    @Autowired
    private InstanceRepository instanceRepository;

    @Autowired
    private InstanceMapper instanceMapper;

    @Autowired
    private InstanceService instanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInstanceMockMvc;

    private Instance instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstanceResource instanceResource = new InstanceResource(instanceService);
        this.restInstanceMockMvc = MockMvcBuilders.standaloneSetup(instanceResource)
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
    public static Instance createEntity(EntityManager em) {
        Instance instance = new Instance()
            .appFrontEndUrl(DEFAULT_APP_FRONT_END_URL)
            .appAdminUrl(DEFAULT_APP_ADMIN_URL)
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD)
            .expireTime(DEFAULT_EXPIRE_TIME)
            .isReleased(DEFAULT_IS_RELEASED)
            .isTrial(DEFAULT_IS_TRIAL)
            .updateTime(DEFAULT_UPDATE_TIME)
            .resultCode(DEFAULT_RESULT_CODE)
            .resultMsg(DEFAULT_RESULT_MSG);
        return instance;
    }

    @Before
    public void initTest() {
        instance = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstance() throws Exception {
        int databaseSizeBeforeCreate = instanceRepository.findAll().size();

        // Create the Instance
        InstanceDTO instanceDTO = instanceMapper.toDto(instance);
        restInstanceMockMvc.perform(post("/api/instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Instance in the database
        List<Instance> instanceList = instanceRepository.findAll();
        assertThat(instanceList).hasSize(databaseSizeBeforeCreate + 1);
        Instance testInstance = instanceList.get(instanceList.size() - 1);
        assertThat(testInstance.getAppFrontEndUrl()).isEqualTo(DEFAULT_APP_FRONT_END_URL);
        assertThat(testInstance.getAppAdminUrl()).isEqualTo(DEFAULT_APP_ADMIN_URL);
        assertThat(testInstance.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testInstance.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testInstance.getExpireTime()).isEqualTo(DEFAULT_EXPIRE_TIME);
        assertThat(testInstance.isIsReleased()).isEqualTo(DEFAULT_IS_RELEASED);
        assertThat(testInstance.isIsTrial()).isEqualTo(DEFAULT_IS_TRIAL);
        assertThat(testInstance.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testInstance.getResultCode()).isEqualTo(DEFAULT_RESULT_CODE);
        assertThat(testInstance.getResultMsg()).isEqualTo(DEFAULT_RESULT_MSG);
    }

    @Test
    @Transactional
    public void createInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instanceRepository.findAll().size();

        // Create the Instance with an existing ID
        instance.setId(1L);
        InstanceDTO instanceDTO = instanceMapper.toDto(instance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstanceMockMvc.perform(post("/api/instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Instance in the database
        List<Instance> instanceList = instanceRepository.findAll();
        assertThat(instanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInstances() throws Exception {
        // Initialize the database
        instanceRepository.saveAndFlush(instance);

        // Get all the instanceList
        restInstanceMockMvc.perform(get("/api/instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instance.getId().intValue())))
            .andExpect(jsonPath("$.[*].appFrontEndUrl").value(hasItem(DEFAULT_APP_FRONT_END_URL.toString())))
            .andExpect(jsonPath("$.[*].appAdminUrl").value(hasItem(DEFAULT_APP_ADMIN_URL.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].expireTime").value(hasItem(sameInstant(DEFAULT_EXPIRE_TIME))))
            .andExpect(jsonPath("$.[*].isReleased").value(hasItem(DEFAULT_IS_RELEASED.booleanValue())))
            .andExpect(jsonPath("$.[*].isTrial").value(hasItem(DEFAULT_IS_TRIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].resultCode").value(hasItem(DEFAULT_RESULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].resultMsg").value(hasItem(DEFAULT_RESULT_MSG.toString())));
    }

    @Test
    @Transactional
    public void getInstance() throws Exception {
        // Initialize the database
        instanceRepository.saveAndFlush(instance);

        // Get the instance
        restInstanceMockMvc.perform(get("/api/instances/{id}", instance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(instance.getId().intValue()))
            .andExpect(jsonPath("$.appFrontEndUrl").value(DEFAULT_APP_FRONT_END_URL.toString()))
            .andExpect(jsonPath("$.appAdminUrl").value(DEFAULT_APP_ADMIN_URL.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.expireTime").value(sameInstant(DEFAULT_EXPIRE_TIME)))
            .andExpect(jsonPath("$.isReleased").value(DEFAULT_IS_RELEASED.booleanValue()))
            .andExpect(jsonPath("$.isTrial").value(DEFAULT_IS_TRIAL.booleanValue()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.resultCode").value(DEFAULT_RESULT_CODE.toString()))
            .andExpect(jsonPath("$.resultMsg").value(DEFAULT_RESULT_MSG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstance() throws Exception {
        // Get the instance
        restInstanceMockMvc.perform(get("/api/instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstance() throws Exception {
        // Initialize the database
        instanceRepository.saveAndFlush(instance);
        int databaseSizeBeforeUpdate = instanceRepository.findAll().size();

        // Update the instance
        Instance updatedInstance = instanceRepository.findOne(instance.getId());
        // Disconnect from session so that the updates on updatedInstance are not directly saved in db
        em.detach(updatedInstance);
        updatedInstance
            .appFrontEndUrl(UPDATED_APP_FRONT_END_URL)
            .appAdminUrl(UPDATED_APP_ADMIN_URL)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .expireTime(UPDATED_EXPIRE_TIME)
            .isReleased(UPDATED_IS_RELEASED)
            .isTrial(UPDATED_IS_TRIAL)
            .updateTime(UPDATED_UPDATE_TIME)
            .resultCode(UPDATED_RESULT_CODE)
            .resultMsg(UPDATED_RESULT_MSG);
        InstanceDTO instanceDTO = instanceMapper.toDto(updatedInstance);

        restInstanceMockMvc.perform(put("/api/instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instanceDTO)))
            .andExpect(status().isOk());

        // Validate the Instance in the database
        List<Instance> instanceList = instanceRepository.findAll();
        assertThat(instanceList).hasSize(databaseSizeBeforeUpdate);
        Instance testInstance = instanceList.get(instanceList.size() - 1);
        assertThat(testInstance.getAppFrontEndUrl()).isEqualTo(UPDATED_APP_FRONT_END_URL);
        assertThat(testInstance.getAppAdminUrl()).isEqualTo(UPDATED_APP_ADMIN_URL);
        assertThat(testInstance.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testInstance.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testInstance.getExpireTime()).isEqualTo(UPDATED_EXPIRE_TIME);
        assertThat(testInstance.isIsReleased()).isEqualTo(UPDATED_IS_RELEASED);
        assertThat(testInstance.isIsTrial()).isEqualTo(UPDATED_IS_TRIAL);
        assertThat(testInstance.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testInstance.getResultCode()).isEqualTo(UPDATED_RESULT_CODE);
        assertThat(testInstance.getResultMsg()).isEqualTo(UPDATED_RESULT_MSG);
    }

    @Test
    @Transactional
    public void updateNonExistingInstance() throws Exception {
        int databaseSizeBeforeUpdate = instanceRepository.findAll().size();

        // Create the Instance
        InstanceDTO instanceDTO = instanceMapper.toDto(instance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInstanceMockMvc.perform(put("/api/instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Instance in the database
        List<Instance> instanceList = instanceRepository.findAll();
        assertThat(instanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInstance() throws Exception {
        // Initialize the database
        instanceRepository.saveAndFlush(instance);
        int databaseSizeBeforeDelete = instanceRepository.findAll().size();

        // Get the instance
        restInstanceMockMvc.perform(delete("/api/instances/{id}", instance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Instance> instanceList = instanceRepository.findAll();
        assertThat(instanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Instance.class);
        Instance instance1 = new Instance();
        instance1.setId(1L);
        Instance instance2 = new Instance();
        instance2.setId(instance1.getId());
        assertThat(instance1).isEqualTo(instance2);
        instance2.setId(2L);
        assertThat(instance1).isNotEqualTo(instance2);
        instance1.setId(null);
        assertThat(instance1).isNotEqualTo(instance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstanceDTO.class);
        InstanceDTO instanceDTO1 = new InstanceDTO();
        instanceDTO1.setId(1L);
        InstanceDTO instanceDTO2 = new InstanceDTO();
        assertThat(instanceDTO1).isNotEqualTo(instanceDTO2);
        instanceDTO2.setId(instanceDTO1.getId());
        assertThat(instanceDTO1).isEqualTo(instanceDTO2);
        instanceDTO2.setId(2L);
        assertThat(instanceDTO1).isNotEqualTo(instanceDTO2);
        instanceDTO1.setId(null);
        assertThat(instanceDTO1).isNotEqualTo(instanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(instanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(instanceMapper.fromId(null)).isNull();
    }
}
