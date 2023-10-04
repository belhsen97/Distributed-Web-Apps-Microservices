package tn.esprit.userservice.Entitys;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

// id file_name file_type category  file_size    data
@Entity
@Table(name ="attachment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attachment implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String id;
    @Column(name = "file_name" )
    String fileName;
    @Column(name = "file_type" )
    String fileType;
    @Column(name = "file_size" )
    long fileSize;
    @Column(name = "category" )
    @Enumerated(EnumType.STRING)
    Category category;
    @Lob
    @Column(name = "data" )
    byte[] data;
    @Column(name = "add_at" )
    LocalDateTime addAt ;
    @ManyToOne
    Account account;
}
