package base.api.book.mapper;

import base.api.book.dto.VoucherSessionDto;
import base.api.book.entity.VoucherSession;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VoucherSessionMapper {

    // Mapping from DTO to entity
    VoucherSession toEntity(VoucherSessionDto VoucherSessionDto);

    // Mapping from entity to DTO
    VoucherSessionDto toDto(VoucherSession VoucherSession);

    // update from DTO to entity, ignore null value
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VoucherSession partialUpdate(VoucherSessionDto VoucherSessionDto, @MappingTarget VoucherSession VoucherSession);

}
