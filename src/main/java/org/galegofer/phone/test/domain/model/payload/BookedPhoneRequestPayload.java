package org.galegofer.phone.test.domain.model.payload;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;

@Builder
@Value
@AllArgsConstructor
@Jacksonized
public class BookedPhoneRequestPayload {

    @NotBlank
    @Length(max = 20, message = "personCorpKey length must be less than 20")
    @Pattern(regexp = "^[0-9a-zA-Z\\s]*$")
    String personCorpKey;

    @NotBlank
    @Length(max = 20, message = "serialNumber length must be less than 20")
    @Pattern(regexp = "^[0-9a-zA-Z\\s]*$")
    String serialNumber;
}
