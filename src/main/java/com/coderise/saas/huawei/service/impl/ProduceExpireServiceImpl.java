package com.coderise.saas.huawei.service.impl;

import com.coderise.saas.huawei.service.ProduceExpireService;
import com.coderise.saas.huawei.domain.ProduceExpire;
import com.coderise.saas.huawei.repository.ProduceExpireRepository;
import com.coderise.saas.huawei.service.dto.ProduceExpireDTO;
import com.coderise.saas.huawei.service.mapper.ProduceExpireMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProduceExpire.
 */
@Service
@Transactional
public class ProduceExpireServiceImpl implements ProduceExpireService {

    private final Logger log = LoggerFactory.getLogger(ProduceExpireServiceImpl.class);

    private final ProduceExpireRepository produceExpireRepository;

    private final ProduceExpireMapper produceExpireMapper;

    public ProduceExpireServiceImpl(ProduceExpireRepository produceExpireRepository, ProduceExpireMapper produceExpireMapper) {
        this.produceExpireRepository = produceExpireRepository;
        this.produceExpireMapper = produceExpireMapper;
    }

    /**
     * Save a produceExpire.
     *
     * @param produceExpireDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProduceExpireDTO save(ProduceExpireDTO produceExpireDTO) {
        log.debug("Request to save ProduceExpire : {}", produceExpireDTO);
        ProduceExpire produceExpire = produceExpireMapper.toEntity(produceExpireDTO);
        produceExpire = produceExpireRepository.save(produceExpire);
        return produceExpireMapper.toDto(produceExpire);
    }

    /**
     * Get all the produceExpires.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProduceExpireDTO> findAll() {
        log.debug("Request to get all ProduceExpires");
        return produceExpireRepository.findAll().stream()
            .map(produceExpireMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one produceExpire by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProduceExpireDTO findOne(Long id) {
        log.debug("Request to get ProduceExpire : {}", id);
        ProduceExpire produceExpire = produceExpireRepository.findOne(id);
        return produceExpireMapper.toDto(produceExpire);
    }

    /**
     * Delete the produceExpire by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProduceExpire : {}", id);
        produceExpireRepository.delete(id);
    }
}
