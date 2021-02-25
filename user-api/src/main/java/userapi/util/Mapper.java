package userapi.util;

import org.springframework.stereotype.Component;

import userapi.dtos.UserDTO;
import userapi.models.User;

@Component
public class Mapper {

    public UserDTO convertUserToUserDTO(User user) {
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setIsActive(user.getIsActive());

        return dto;
    }

}
