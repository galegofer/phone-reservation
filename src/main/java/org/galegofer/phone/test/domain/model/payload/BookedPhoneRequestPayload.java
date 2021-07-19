package org.galegofer.phone.test.domain.model.payload;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
public class BookedPhoneRequestPayload {
    @NotBlank
    @Max(20)
    @Pattern(regexp = "^[0-9a-zA-Z\\s]*$")
    String personCorpKey;

    @NotBlank
    @Max(20)
    @Pattern(regexp = "^[0-9a-zA-Z\\s]*$")
    String serialNumber;
}
