package com.coderise.saas.huawei.service.impl;

import com.coderise.saas.huawei.service.ProduceReleaseService;
import com.coderise.saas.huawei.domain.ProduceRelease;
import com.coderise.saas.huawei.repository.ProduceReleaseRepository;
import com.coderise.saas.huawei.service.dto.ProduceReleaseDTO;
import com.coderise.saas.huawei.service.mapper.ProduceReleaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProduceRelease.
 */
@Service
@Transactional
public class ProduceReleaseServiceImpl implements ProduceReleaseService {

    private final Logger log = LoggerFactory.getLogger(ProduceReleaseServiceImpl.class);

    private final ProduceReleaseRepository produceReleaseRepository;

    private final ProduceReleaseMapper produceReleaseMapper;

    public ProduceReleaseServiceImpl(ProduceReleaseRepository produceReleaseRepository, ProduceReleaseMapper produceReleaseMapper) {
        this.produceReleaseRepository = produceReleaseRepository;
        this.produceReleaseMapper = produceReleaseMapper;
    }

    /**
     * Save a produceRelease.
     *
     * @param produceReleaseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProduceReleaseDTO save(ProduceReleaseDTO produceReleaseDTO) {
        log.debug("Request to save ProduceRelease : {}", produceReleaseDTO);
        ProduceRelease produceRelease = produceReleaseMapper.toEntity(produceReleaseDTO);
        produceRelease = produceReleaseRepository.save(produceRelease);
        return produceReleaseMapper.toDto(produceRelease);
    }

    /**
     * Get all the produceReleases.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProduceReleaseDTO> findAll() {
        log.debug("Request to get all ProduceReleases");
        return produceReleaseRepository.findAll().stream()
            .map(produceReleaseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one produceRelease by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProduceReleaseDTO findOne(Long id) {
        log.debug("Request to get ProduceRelease : {}", id);
        ProduceRelease produceRelease = produceReleaseRepository.findOne(id);
        return produceReleaseMapper.toDto(produceRelease);
    }

    /**
     * Delete the produceRelease by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProduceRelease : {}", id);
        produceReleaseRepository.delete(id);
    }
}
