package com.coderise.saas.huawei.service;

import com.coderise.saas.huawei.service.dto.ProduceExpireDTO;
import java.util.List;

/**
 * Service Interface for managing ProduceExpire.
 */
public interface ProduceExpireService {

    /**
     * Save a produceExpire.
     *
     * @param produceExpireDTO the entity to save
     * @return the persisted entity
     */
    ProduceExpireDTO save(ProduceExpireDTO produceExpireDTO);

    /**
     * Get all the produceExpires.
     *
     * @return the list of entities
     */
    List<ProduceExpireDTO> findAll();

    /**
     * Get the "id" produceExpire.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProduceExpireDTO findOne(Long id);

    /**
     * Delete the "id" produceExpire.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
