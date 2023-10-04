package tn.esprit.userservice.Services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.userservice.Entitys.Attachment;
import tn.esprit.userservice.Entitys.Category;
import tn.esprit.userservice.Libs.IGenericCRUD;

import java.util.List;

public interface IAttachmentService extends IGenericCRUD<Attachment,String> {
    Attachment saveAttachment(MultipartFile file)throws Exception ;
    Attachment  saveFileToAccount(MultipartFile file, long idAccount)throws Exception ;
    Attachment getAttachment(String fileId) throws Exception;
    Attachment savePhotoProfileToAccount(MultipartFile file, String username ) throws Exception;
    Attachment saveCoverProfileToAccount(MultipartFile file, String username ) throws Exception;
    Attachment saveCVProfileToAccount(MultipartFile file, String username )throws Exception;
    List<Attachment> selectByAccountAndCatrgory (long idAccount,Category category );
    Attachment getLastPhoto ( long idAccount ) ;
}