package com.coderise.saas.huawei.repository;

import com.coderise.saas.huawei.domain.ProduceRelease;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProduceRelease entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduceReleaseRepository extends JpaRepository<ProduceRelease, Long> {

}
