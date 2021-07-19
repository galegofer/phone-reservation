package org.galegofer.phone.test.domain.model.payload;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@AllArgsConstructor
@Jacksonized
public class BookedPhoneResponsePayload {

    String personCorpKey;
    String serialNumber;
    LocalDateTime bookDate;
    String manufacturer;
    String model;
    List<String> technologies;
}
