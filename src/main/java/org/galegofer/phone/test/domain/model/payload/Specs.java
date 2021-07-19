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
public class Specs implements Serializable {

    @JsonProperty("Battery")
    List<Battery> battery;
    @JsonProperty("Buyers information")
    List<BuyersInformation> buyersInformation;
    @JsonProperty("Camera")
    List<Camera> camera;
    @JsonProperty("Connectivity & Features")
    List<ConnectivityFeature> connectivityFeatures;
    @JsonProperty("Design")
    List<Design> design;
    @JsonProperty("Display")
    List<Display> display;
    @JsonProperty("Hardware")
    List<Hardware> hardware;
    @JsonProperty("Multimedia")
    List<Multimedia> multimedia;
}
