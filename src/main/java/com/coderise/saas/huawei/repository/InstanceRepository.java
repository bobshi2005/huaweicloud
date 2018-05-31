package com.coderise.saas.huawei.repository;

import com.coderise.saas.huawei.domain.Instance;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Instance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstanceRepository extends JpaRepository<Instance, Long> {

}
