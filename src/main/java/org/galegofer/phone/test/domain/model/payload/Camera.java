package org.galegofer.phone.test.domain.model.payload;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class Camera {
    String description;
    String name;
}
