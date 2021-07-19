package org.galegofer.phone.test.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class PhoneApplicationException extends RuntimeException {

    private final int statusCode;

    @Builder
    public PhoneApplicationException(final String message, final int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
