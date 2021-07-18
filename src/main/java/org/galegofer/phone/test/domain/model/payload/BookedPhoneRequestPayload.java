package org.galegofer.phone.test.domain.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
public class BookedPhoneRequestPayload {
    String personCorpKey;
    String serialNumber;
}
