package tn.esprit.userservice.Dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequestDto {
  @NotBlank(message = "Authentication Request : username NotBlank")
  @Size(min = 2, max = 50 , message = "Authentication Request : username must between 2 - 50")
  String username;
  @Size(min = 8, max = 50 , message = "Authentication Request: password must between 8 - 50")
  String password;
  @Email(message = "Authentication Request : email must be correct")
  String email;
}
