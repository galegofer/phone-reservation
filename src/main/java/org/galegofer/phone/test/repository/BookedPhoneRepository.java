package org.galegofer.phone.test.repository;

import org.galegofer.phone.test.domain.model.entity.BookedPhoneEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookedPhoneRepository extends ReactiveCrudRepository<BookedPhoneEntity, Long> {
    Flux<BookedPhoneEntity> findAll();
    Mono<BookedPhoneEntity> findBySerialNumber(String serialNumber);
    @Modifying
    Mono<Integer> deleteBySerialNumber(String serialNumber);
}
