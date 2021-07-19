package org.galegofer.phone.test.domain.model.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Builder
@Value
@AllArgsConstructor
@Table("BOOKED_PHONE")
public class BookedPhoneEntity {

    @Id
    Long id;
    String personCorpKey;
    String serialNumber;
    LocalDateTime bookDate;
}
