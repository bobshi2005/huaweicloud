package com.coderise.saas.huawei.repository;

import com.coderise.saas.huawei.domain.ProduceNew;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the ProduceNew entity. */
@SuppressWarnings("unused")
@Repository
public interface ProduceNewRepository extends JpaRepository<ProduceNew, Long> {
  Optional<ProduceNew> findOneByOrderId(String orderId);
}
