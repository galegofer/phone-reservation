package org.galegofer.phone.test.domain.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Value
@Jacksonized
public class Root {
    @JsonProperty("Devices")
    List<Device> devices;
}


