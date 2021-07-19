package org.galegofer.phone.test.mapper;

import org.galegofer.phone.test.domain.model.BookedPhone;
import org.galegofer.phone.test.domain.model.entity.BookedPhoneEntity;
import org.galegofer.phone.test.domain.model.payload.BookedPhoneResponsePayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookedPhoneMapper {

    BookedPhoneEntity modelToEntity(BookedPhone source);

    BookedPhone entityToModel(BookedPhoneEntity destination);

    BookedPhoneResponsePayload modelToPayload(BookedPhone source);

    BookedPhone payloadToModel(BookedPhoneResponsePayload destination);
}
