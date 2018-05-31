package com.coderise.saas.huawei.repository;

import com.coderise.saas.huawei.domain.ProduceExtend;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the ProduceExtend entity. */
@SuppressWarnings("unused")
@Repository
public interface ProduceExtendRepository extends JpaRepository<ProduceExtend, Long> {
  Optional<ProduceExtend> findOneByOrderId(String orderId);
}
