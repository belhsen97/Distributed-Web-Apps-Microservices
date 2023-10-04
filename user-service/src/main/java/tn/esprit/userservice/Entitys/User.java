package tn.esprit.userservice.Entitys;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//id  username  password  code  role enabled
@Entity
@Table(name ="user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
    long id;
    @Column(name = "username" )
    String username;
    @Column(name = "password" )
    String password;
    @Column(name = "role" )
    @Enumerated(EnumType.STRING)
    Roles role;
    @Column(name = "is_enabled" )
    boolean enabled;
    @Column(name = "code" )
    String code;
    @JsonIgnore
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Account account;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;





    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority  authority = new SimpleGrantedAuthority( this.role.name());
        authorities.add(authority);
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
