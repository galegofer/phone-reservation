package org.galegofer.phone.test.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
public class Phone {
    String serialNumber;
    String manufacturer;
    String model;
}
