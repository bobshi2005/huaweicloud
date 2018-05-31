package com.coderise.saas.huawei.service.impl;

import com.coderise.saas.huawei.service.InstanceService;
import com.coderise.saas.huawei.domain.Instance;
import com.coderise.saas.huawei.repository.InstanceRepository;
import com.coderise.saas.huawei.service.dto.InstanceDTO;
import com.coderise.saas.huawei.service.mapper.InstanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Instance.
 */
@Service
@Transactional
public class InstanceServiceImpl implements InstanceService {

    private final Logger log = LoggerFactory.getLogger(InstanceServiceImpl.class);

    private final InstanceRepository instanceRepository;

    private final InstanceMapper instanceMapper;

    public InstanceServiceImpl(InstanceRepository instanceRepository, InstanceMapper instanceMapper) {
        this.instanceRepository = instanceRepository;
        this.instanceMapper = instanceMapper;
    }

    /**
     * Save a instance.
     *
     * @param instanceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InstanceDTO save(InstanceDTO instanceDTO) {
        log.debug("Request to save Instance : {}", instanceDTO);
        Instance instance = instanceMapper.toEntity(instanceDTO);
        instance = instanceRepository.save(instance);
        return instanceMapper.toDto(instance);
    }

    /**
     * Get all the instances.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<InstanceDTO> findAll() {
        log.debug("Request to get all Instances");
        return instanceRepository.findAll().stream()
            .map(instanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one instance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public InstanceDTO findOne(Long id) {
        log.debug("Request to get Instance : {}", id);
        Instance instance = instanceRepository.findOne(id);
        return instanceMapper.toDto(instance);
    }

    /**
     * Delete the instance by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Instance : {}", id);
        instanceRepository.delete(id);
    }
}
