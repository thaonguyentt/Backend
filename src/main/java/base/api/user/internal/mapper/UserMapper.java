package base.api.user.internal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import base.api.user.UserDTO;
import base.api.user.internal.entity.User;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

//  @Mapping(target = "password", ignore = true)
  UserDTO userDTOFromUser(User user);

  User userFromUserDTO(UserDTO userDTO);
}
