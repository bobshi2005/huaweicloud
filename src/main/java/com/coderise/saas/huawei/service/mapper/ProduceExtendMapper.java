package com.coderise.saas.huawei.service.mapper;

import com.coderise.saas.huawei.domain.*;
import com.coderise.saas.huawei.service.dto.ProduceExtendDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProduceExtend and its DTO ProduceExtendDTO.
 */
@Mapper(componentModel = "spring", uses = {InstanceMapper.class})
public interface ProduceExtendMapper extends EntityMapper<ProduceExtendDTO, ProduceExtend> {

    @Mapping(source = "instance.id", target = "instanceId")
    ProduceExtendDTO toDto(ProduceExtend produceExtend);

    @Mapping(source = "instanceId", target = "instance")
    ProduceExtend toEntity(ProduceExtendDTO produceExtendDTO);

    default ProduceExtend fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProduceExtend produceExtend = new ProduceExtend();
        produceExtend.setId(id);
        return produceExtend;
    }
}
