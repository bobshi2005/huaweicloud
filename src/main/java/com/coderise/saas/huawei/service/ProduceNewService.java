package com.coderise.saas.huawei.service;

import com.coderise.saas.huawei.service.dto.ProduceNewDTO;
import java.util.List;

/**
 * Service Interface for managing ProduceNew.
 */
public interface ProduceNewService {

    /**
     * Save a produceNew.
     *
     * @param produceNewDTO the entity to save
     * @return the persisted entity
     */
    ProduceNewDTO save(ProduceNewDTO produceNewDTO);

    /**
     * Get all the produceNews.
     *
     * @return the list of entities
     */
    List<ProduceNewDTO> findAll();

    /**
     * Get the "id" produceNew.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProduceNewDTO findOne(Long id);

    /**
     * Delete the "id" produceNew.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
