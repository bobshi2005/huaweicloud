package com.coderise.saas.huawei.service.impl;

import com.coderise.saas.huawei.service.ProduceNewService;
import com.coderise.saas.huawei.domain.ProduceNew;
import com.coderise.saas.huawei.repository.ProduceNewRepository;
import com.coderise.saas.huawei.service.dto.ProduceNewDTO;
import com.coderise.saas.huawei.service.mapper.ProduceNewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProduceNew.
 */
@Service
@Transactional
public class ProduceNewServiceImpl implements ProduceNewService {

    private final Logger log = LoggerFactory.getLogger(ProduceNewServiceImpl.class);

    private final ProduceNewRepository produceNewRepository;

    private final ProduceNewMapper produceNewMapper;

    public ProduceNewServiceImpl(ProduceNewRepository produceNewRepository, ProduceNewMapper produceNewMapper) {
        this.produceNewRepository = produceNewRepository;
        this.produceNewMapper = produceNewMapper;
    }

    /**
     * Save a produceNew.
     *
     * @param produceNewDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProduceNewDTO save(ProduceNewDTO produceNewDTO) {
        log.debug("Request to save ProduceNew : {}", produceNewDTO);
        ProduceNew produceNew = produceNewMapper.toEntity(produceNewDTO);
        produceNew = produceNewRepository.save(produceNew);
        return produceNewMapper.toDto(produceNew);
    }

    /**
     * Get all the produceNews.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProduceNewDTO> findAll() {
        log.debug("Request to get all ProduceNews");
        return produceNewRepository.findAll().stream()
            .map(produceNewMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one produceNew by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProduceNewDTO findOne(Long id) {
        log.debug("Request to get ProduceNew : {}", id);
        ProduceNew produceNew = produceNewRepository.findOne(id);
        return produceNewMapper.toDto(produceNew);
    }

    /**
     * Delete the produceNew by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProduceNew : {}", id);
        produceNewRepository.delete(id);
    }
}
