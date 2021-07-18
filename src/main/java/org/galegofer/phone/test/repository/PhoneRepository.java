package org.galegofer.phone.test.repository;

import org.galegofer.phone.test.domain.model.entity.PhoneEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PhoneRepository extends ReactiveCrudRepository<PhoneEntity, Long> {
    Mono<PhoneEntity> findBySerialNumber(String serialNumber);
}
