package com.coderise.saas.huawei.service;

import com.coderise.saas.huawei.service.dto.ProduceReleaseDTO;
import java.util.List;

/**
 * Service Interface for managing ProduceRelease.
 */
public interface ProduceReleaseService {

    /**
     * Save a produceRelease.
     *
     * @param produceReleaseDTO the entity to save
     * @return the persisted entity
     */
    ProduceReleaseDTO save(ProduceReleaseDTO produceReleaseDTO);

    /**
     * Get all the produceReleases.
     *
     * @return the list of entities
     */
    List<ProduceReleaseDTO> findAll();

    /**
     * Get the "id" produceRelease.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProduceReleaseDTO findOne(Long id);

    /**
     * Delete the "id" produceRelease.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
