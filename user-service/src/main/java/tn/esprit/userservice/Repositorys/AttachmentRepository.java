package tn.esprit.userservice.Repositorys;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.userservice.Entitys.Attachment;
import tn.esprit.userservice.Entitys.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, String> {
    @Query("SELECT a FROM Attachment a join  a.account acct WHERE ( a.category = tn.esprit.userservice.Entitys.Category.PHOTOPROFILE and  acct.id = :idAccount)" + "ORDER BY a.addAt DESC")
    Optional<Attachment> findLastPhotoSaved(@Param("idAccount") Long idAccount);
    @Query("SELECT a FROM Attachment a  WHERE ( a.category = :category and  a.account.id = :idAccount)")
    List<Attachment> findAttachmentsByAccountIdAndCategory(@Param("idAccount") Long idAccount,@Param("category") Category category);
}
