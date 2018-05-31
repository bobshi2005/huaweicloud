package com.coderise.saas.huawei.service.impl;

import com.coderise.saas.huawei.service.ProduceExtendService;
import com.coderise.saas.huawei.domain.ProduceExtend;
import com.coderise.saas.huawei.repository.ProduceExtendRepository;
import com.coderise.saas.huawei.service.dto.ProduceExtendDTO;
import com.coderise.saas.huawei.service.mapper.ProduceExtendMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProduceExtend.
 */
@Service
@Transactional
public class ProduceExtendServiceImpl implements ProduceExtendService {

    private final Logger log = LoggerFactory.getLogger(ProduceExtendServiceImpl.class);

    private final ProduceExtendRepository produceExtendRepository;

    private final ProduceExtendMapper produceExtendMapper;

    public ProduceExtendServiceImpl(ProduceExtendRepository produceExtendRepository, ProduceExtendMapper produceExtendMapper) {
        this.produceExtendRepository = produceExtendRepository;
        this.produceExtendMapper = produceExtendMapper;
    }

    /**
     * Save a produceExtend.
     *
     * @param produceExtendDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProduceExtendDTO save(ProduceExtendDTO produceExtendDTO) {
        log.debug("Request to save ProduceExtend : {}", produceExtendDTO);
        ProduceExtend produceExtend = produceExtendMapper.toEntity(produceExtendDTO);
        produceExtend = produceExtendRepository.save(produceExtend);
        return produceExtendMapper.toDto(produceExtend);
    }

    /**
     * Get all the produceExtends.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProduceExtendDTO> findAll() {
        log.debug("Request to get all ProduceExtends");
        return produceExtendRepository.findAll().stream()
            .map(produceExtendMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one produceExtend by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProduceExtendDTO findOne(Long id) {
        log.debug("Request to get ProduceExtend : {}", id);
        ProduceExtend produceExtend = produceExtendRepository.findOne(id);
        return produceExtendMapper.toDto(produceExtend);
    }

    /**
     * Delete the produceExtend by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProduceExtend : {}", id);
        produceExtendRepository.delete(id);
    }
}
