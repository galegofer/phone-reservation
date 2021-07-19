package org.galegofer.phone.test.domain.model.entity;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Value
@Table
public class PersonEntity {

    @Id
    Long id;
    String firstName;
    String lastname;
    String corpKey;
    String email;
}
