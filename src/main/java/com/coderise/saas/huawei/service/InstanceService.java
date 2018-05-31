package com.coderise.saas.huawei.service;

import com.coderise.saas.huawei.service.dto.InstanceDTO;
import java.util.List;

/**
 * Service Interface for managing Instance.
 */
public interface InstanceService {

    /**
     * Save a instance.
     *
     * @param instanceDTO the entity to save
     * @return the persisted entity
     */
    InstanceDTO save(InstanceDTO instanceDTO);

    /**
     * Get all the instances.
     *
     * @return the list of entities
     */
    List<InstanceDTO> findAll();

    /**
     * Get the "id" instance.
     *
     * @param id the id of the entity
     * @return the entity
     */
    InstanceDTO findOne(Long id);

    /**
     * Delete the "id" instance.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
