package org.galegofer.phone.test.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Value
@AllArgsConstructor
@Table("PHONE")
public class PhoneEntity {

    @Id
    Long id;
    String serialNumber;
    String manufacturer;
    String model;
}
