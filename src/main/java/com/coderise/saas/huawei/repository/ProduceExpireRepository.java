package com.coderise.saas.huawei.repository;

import com.coderise.saas.huawei.domain.ProduceExpire;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProduceExpire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduceExpireRepository extends JpaRepository<ProduceExpire, Long> {

}
