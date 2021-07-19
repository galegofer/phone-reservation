package org.galegofer.phone.test.domain.model.payload;

import java.io.Serializable;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class Design implements Serializable {

    String description;
    String name;
}
