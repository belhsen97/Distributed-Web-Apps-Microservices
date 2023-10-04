package tn.esprit.userservice.Dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.userservice.Entitys.Roles;


@Builder
@AllArgsConstructor
@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    long id;
    String username;
    Roles role;
    boolean enabled;
    String code;
}
