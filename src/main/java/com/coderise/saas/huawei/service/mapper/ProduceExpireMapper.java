package com.coderise.saas.huawei.service.mapper;

import com.coderise.saas.huawei.domain.*;
import com.coderise.saas.huawei.service.dto.ProduceExpireDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProduceExpire and its DTO ProduceExpireDTO.
 */
@Mapper(componentModel = "spring", uses = {InstanceMapper.class})
public interface ProduceExpireMapper extends EntityMapper<ProduceExpireDTO, ProduceExpire> {

    @Mapping(source = "instance.id", target = "instanceId")
    ProduceExpireDTO toDto(ProduceExpire produceExpire);

    @Mapping(source = "instanceId", target = "instance")
    ProduceExpire toEntity(ProduceExpireDTO produceExpireDTO);

    default ProduceExpire fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProduceExpire produceExpire = new ProduceExpire();
        produceExpire.setId(id);
        return produceExpire;
    }
}
