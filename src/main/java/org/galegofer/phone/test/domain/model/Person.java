package org.galegofer.phone.test.domain.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Person {
    String firstName;
    String lastname;
    String corpKey;
    String email;
}
