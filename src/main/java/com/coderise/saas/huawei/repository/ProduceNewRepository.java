package com.coderise.saas.huawei.repository;

import com.coderise.saas.huawei.domain.ProduceNew;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProduceNew entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduceNewRepository extends JpaRepository<ProduceNew, Long> {

}
