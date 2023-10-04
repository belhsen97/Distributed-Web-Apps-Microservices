package tn.esprit.userservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.userservice.Entitys.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select ac from  Account ac JOIN  ac.user u   where  ((u.username = :username)) ")
    Optional<Account>   findAccountByUsername(@Param("username") String username   );



    @Query("select ac from  Account ac JOIN  ac.user u WHERE u.username IN :usernames")
    List<Account> findAccountDtosByUsernameIn(String[] usernames);
}
