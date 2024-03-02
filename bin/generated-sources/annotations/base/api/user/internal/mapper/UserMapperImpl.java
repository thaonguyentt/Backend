package base.api.user.internal.mapper;

import base.api.user.UserDTO;
import base.api.user.internal.entity.User;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-23T16:41:56+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240103-0614, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO userDTOFromUser(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String username = null;
        String firstName = null;
        String lastName = null;
        String email = null;
        String phoneNumber = null;

        id = user.getId();
        username = user.getUsername();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        phoneNumber = user.getPhoneNumber();

        String password = null;
        LocalDate birthDate = null;

        UserDTO userDTO = new UserDTO( id, username, firstName, lastName, email, phoneNumber, password, birthDate );

        return userDTO;
    }

    @Override
    public User userFromUserDTO(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( userDTO.email() );
        user.setFirstName( userDTO.firstName() );
        user.setId( userDTO.id() );
        user.setLastName( userDTO.lastName() );
        user.setPhoneNumber( userDTO.phoneNumber() );
        user.setUsername( userDTO.username() );

        return user;
    }
}
