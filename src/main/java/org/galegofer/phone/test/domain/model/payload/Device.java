package org.galegofer.phone.test.domain.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class Device {
    @JsonProperty("device_url")
    String deviceURL;
    int id;
    @JsonProperty("image_url")
    String imageURL;
    String manufacturer;
    String name;
    int rating;
    @JsonProperty("release_date")
    Object releaseDate;
    Specs specs;
}
