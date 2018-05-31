package com.coderise.saas.huawei.repository;

import com.coderise.saas.huawei.domain.ProduceExtend;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProduceExtend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduceExtendRepository extends JpaRepository<ProduceExtend, Long> {

}
