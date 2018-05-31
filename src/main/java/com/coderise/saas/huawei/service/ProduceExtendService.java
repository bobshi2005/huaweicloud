package com.coderise.saas.huawei.service;

import com.coderise.saas.huawei.service.dto.ProduceExtendDTO;
import java.util.List;

/**
 * Service Interface for managing ProduceExtend.
 */
public interface ProduceExtendService {

    /**
     * Save a produceExtend.
     *
     * @param produceExtendDTO the entity to save
     * @return the persisted entity
     */
    ProduceExtendDTO save(ProduceExtendDTO produceExtendDTO);

    /**
     * Get all the produceExtends.
     *
     * @return the list of entities
     */
    List<ProduceExtendDTO> findAll();

    /**
     * Get the "id" produceExtend.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProduceExtendDTO findOne(Long id);

    /**
     * Delete the "id" produceExtend.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
