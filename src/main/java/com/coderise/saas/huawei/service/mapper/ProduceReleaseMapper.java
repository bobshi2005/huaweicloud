package com.coderise.saas.huawei.service.mapper;

import com.coderise.saas.huawei.domain.*;
import com.coderise.saas.huawei.service.dto.ProduceReleaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProduceRelease and its DTO ProduceReleaseDTO.
 */
@Mapper(componentModel = "spring", uses = {InstanceMapper.class})
public interface ProduceReleaseMapper extends EntityMapper<ProduceReleaseDTO, ProduceRelease> {

    @Mapping(source = "instance.id", target = "instanceId")
    ProduceReleaseDTO toDto(ProduceRelease produceRelease);

    @Mapping(source = "instanceId", target = "instance")
    ProduceRelease toEntity(ProduceReleaseDTO produceReleaseDTO);

    default ProduceRelease fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProduceRelease produceRelease = new ProduceRelease();
        produceRelease.setId(id);
        return produceRelease;
    }
}
