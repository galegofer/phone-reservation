package org.galegofer.phone.test.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.With;


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
