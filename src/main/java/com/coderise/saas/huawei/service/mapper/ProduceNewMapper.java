package com.coderise.saas.huawei.service.mapper;

import com.coderise.saas.huawei.domain.*;
import com.coderise.saas.huawei.service.dto.ProduceNewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProduceNew and its DTO ProduceNewDTO.
 */
@Mapper(componentModel = "spring", uses = {InstanceMapper.class})
public interface ProduceNewMapper extends EntityMapper<ProduceNewDTO, ProduceNew> {

    @Mapping(source = "instance.id", target = "instanceId")
    ProduceNewDTO toDto(ProduceNew produceNew);

    @Mapping(source = "instanceId", target = "instance")
    ProduceNew toEntity(ProduceNewDTO produceNewDTO);

    default ProduceNew fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProduceNew produceNew = new ProduceNew();
        produceNew.setId(id);
        return produceNew;
    }
}
