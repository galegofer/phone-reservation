package org.galegofer.phone.test.domain.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class Root implements Serializable {

    @JsonProperty("Devices")
    List<Device> devices;
}


