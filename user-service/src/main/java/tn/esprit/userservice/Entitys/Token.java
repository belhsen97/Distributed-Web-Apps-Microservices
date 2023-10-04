package tn.esprit.userservice.Entitys;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Entity
@Table(name ="token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Token implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
    Long id;
    @Column(unique = true)
    public String token;
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;
    public boolean revoked;
    public boolean expired;
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}
