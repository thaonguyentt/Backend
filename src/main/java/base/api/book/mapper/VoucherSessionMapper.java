package base.api.book.mapper;

import base.api.book.dto.VoucherSessionDto;
import base.api.book.entity.VoucherSession;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VoucherSessionMapper {

    VoucherSession toEntity(VoucherSessionDto VoucherSessionDto);

    VoucherSessionDto toDto(VoucherSession VoucherSession);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VoucherSession partialUpdate(VoucherSessionDto VoucherSessionDto, @MappingTarget VoucherSession VoucherSession);

}
