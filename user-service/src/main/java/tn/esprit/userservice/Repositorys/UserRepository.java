package tn.esprit.userservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.userservice.Entitys.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String Username);
    Optional<User> findUserByCode(String Code);
    Optional<User> findUserByCodeAndUsername(String Code,String Username);
    @Query("select case when (count(u) > 0)  then true else false end from User u  where  ((u.username = :username))")
    boolean isCorrectUserName(@Param("username") String username   );
    @Query("select case when (count(u) > 0)  then true else false end from User u  where  ((u.code = :code))")
    boolean isCorrectCode(@Param("code") String code   );
    @Query("select u from User u JOIN u.account ac  where  ((ac.email = :email)AND (u.username = :username)) ")
    Optional<User> findUserByUsernameAndEmail(@Param("username") String username ,@Param("email") String email   );
    @Query("select case when (count(u) > 0)  then true else false end from User u JOIN u.account ac  where  ((ac.email = :email)AND (u.username = :username))")
    boolean isCorrectEmailAndUsername(@Param("username") String username  , @Param("email") String email    );
}
