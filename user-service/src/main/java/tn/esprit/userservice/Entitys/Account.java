package tn.esprit.userservice.Entitys;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//id created_at firstname lastname cin phone date_of_birth email LinkedIn gender state city zip_code address

@Entity
@Table(name ="account")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
    long id;
    @Column(name = "created_at" )
    LocalDateTime createdAt ;
    @Column(name = "firstname" )
    String firstname;
    @Column(name = "lastname" )
    String lastname;
    @Column(name = "cin" )
    int cin;
    @Column(name = "phone" )
    int phone;
    @Column(name = "date_of_birth" )
    LocalDate dateOfBirth;
    @Column(name = "email" )
    String email;
    @Column(name = "linked_in" )
    String linkedIn;
    @Column(name = "github" )
    String github;
    @Column(name = "gender" )
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Column(name = "state_region" )
    @Enumerated(EnumType.STRING)
    StateRegion state;
    @Column(name = "city" )
    String city;
    @Column(name = "zip_code" )
    int zipCode;
    @Column(name = "address" )
    String address;
    @OneToOne(cascade = CascadeType.ALL)
    User user;
    @OneToMany(cascade = CascadeType.ALL , mappedBy="account")
    List<Attachment> attachments = new ArrayList<Attachment>();
    public void addAttachment(Attachment attachment) {
        if ( this.getAttachments() == null ){this.setAttachments(new ArrayList<Attachment>()) ; }
        attachments.add(attachment);  //this.getAttachments().add(attachment);
        attachment.setAccount(this);

    }
    public void removeAttachment(Attachment attachment) {
        if ( this.getAttachments() == null ){ return;}
        attachments.remove(attachment);
        attachment.setAccount(null);
    }
    public void addUser(User user) {
        this.setUser(user);
        user.setAccount(this);
    }
    public void removeUser(User user) {
        this.setUser(null);
        user.setAccount(null);
    }
}



