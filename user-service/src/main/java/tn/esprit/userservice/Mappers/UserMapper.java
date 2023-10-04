package tn.esprit.userservice.Mappers;

import tn.esprit.userservice.Dtos.UserDto;
import tn.esprit.userservice.Entitys.User;

public class UserMapper {
    public static User mapToEntity(UserDto UserDto){
        return User.builder()
                .id(UserDto.getId())
                .username(UserDto.getUsername())
                .role(UserDto.getRole())
                .enabled(UserDto.isEnabled())
                .build();
    }
    public static UserDto mapToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .build();
    }
}