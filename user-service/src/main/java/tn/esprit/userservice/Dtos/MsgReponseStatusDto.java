package tn.esprit.userservice.Dtos;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MsgReponseStatusDto {
    String title = "Message";
    LocalDate datestamp;
    LocalTime timestamp;
    ReponseStatus status;
    String message;
}
