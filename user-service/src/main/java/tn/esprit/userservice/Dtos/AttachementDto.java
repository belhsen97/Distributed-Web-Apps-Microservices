package tn.esprit.userservice.Dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.userservice.Entitys.Category;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttachementDto {
     String id;
     String fileName;
     String downloadURL;
     String fileType;
     long fileSize;
     Category category;
     LocalDateTime addAt ;
}
