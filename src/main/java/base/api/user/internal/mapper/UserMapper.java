package base.api.user.internal.mapper;

import base.api.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import base.api.user.internal.entity.User;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

//  @Mapping(target = "password", ignore = true)
  UserDto userDTOFromUser(User user);

  User userFromUserDTO(UserDto userDTO);
}
