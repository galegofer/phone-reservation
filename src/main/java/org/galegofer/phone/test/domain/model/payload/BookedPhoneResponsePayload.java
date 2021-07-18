package org.galegofer.phone.test.domain.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Value
@AllArgsConstructor
public class BookedPhoneResponsePayload {
    String personCorpKey;
    String serialNumber;
    LocalDateTime bookDate;
    String manufacturer;
    String model;
    List<String> technologies;
}
