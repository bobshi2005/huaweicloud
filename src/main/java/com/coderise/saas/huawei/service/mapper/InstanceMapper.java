package com.coderise.saas.huawei.service.mapper;

import com.coderise.saas.huawei.domain.*;
import com.coderise.saas.huawei.service.dto.InstanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Instance and its DTO InstanceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InstanceMapper extends EntityMapper<InstanceDTO, Instance> {



    default Instance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Instance instance = new Instance();
        instance.setId(id);
        return instance;
    }
}
