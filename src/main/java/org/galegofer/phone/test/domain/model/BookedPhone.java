package org.galegofer.phone.test.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Value
@AllArgsConstructor
public class BookedPhone {
    String personCorpKey;
    String serialNumber;
    LocalDateTime bookDate;
    @With
    String manufacturer;
    @With
    String model;
    @With
    List<String> technologies;
}
