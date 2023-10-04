package tn.esprit.userservice.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.userservice.Entitys.Account;
import tn.esprit.userservice.Entitys.Attachment;
import tn.esprit.userservice.Entitys.Category;
import tn.esprit.userservice.Exceptions.RessourceNotFoundException;
import tn.esprit.userservice.Libs.GenericCRUDService;
import tn.esprit.userservice.Repositorys.AccountRepository;
import tn.esprit.userservice.Repositorys.AttachmentRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("attachment-service")
public class AttachmentService extends GenericCRUDService<Attachment,String> implements IAttachmentService {
    private AttachmentRepository   attachmentRepository;
    private AccountRepository accountRepository;
    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository,
                             AccountRepository accountRepository  )
    {super(attachmentRepository);
        this.attachmentRepository = attachmentRepository;
        this.accountRepository = accountRepository;}
    @Override
    public Attachment saveAttachment(MultipartFile file) throws Exception {
        String fileName = "";
        try {
            fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                        + fileName);
            }
            Attachment attachment = Attachment.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .data(file.getBytes())
                    .addAt(LocalDateTime.now())
                    .build();
            return attachmentRepository.save(attachment);
        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }
    @Override
    @Transactional
    public Attachment saveFileToAccount(MultipartFile file, long idAccount ) throws Exception  {

        return null;
    }
    @Override
    @Transactional
    public Attachment savePhotoProfileToAccount(MultipartFile file, String username ) throws Exception  {
        Account account = accountRepository.findAccountByUsername(username).
                orElseThrow(()-> new RessourceNotFoundException("Service Attachment ( savePhotoToAccount )  : cannot found account  : "+username)) ;

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                        + fileName);
            }
            Attachment attachment = Attachment.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .data(file.getBytes())
                    .addAt(LocalDateTime.now())
                    .category(Category.PHOTOPROFILE)
                    .build();
            account.addAttachment(attachment);
            account =  accountRepository.save(account);
            List<Attachment> attachmentList = findAttachmentsByCategoryOrderByAddAtAsc( account.getAttachments(), Category.PHOTOPROFILE);
            return attachmentList.get(attachmentList.size() - 1);
        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }
    @Override
    @Transactional
    public Attachment saveCoverProfileToAccount(MultipartFile file, String username ) throws Exception  {
        Account account = accountRepository.findAccountByUsername(username).
                orElseThrow(()-> new RessourceNotFoundException("Service Attachment ( saveCoverProfileToAccount )  : cannot found account  : "+username)) ;

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                        + fileName);
            }
            Attachment attachment = Attachment.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .data(file.getBytes())
                    .addAt(LocalDateTime.now())
                    .category(Category.COVERPICTURE)
                    .build();
            account.addAttachment(attachment);
            account =  accountRepository.save(account);
            List<Attachment> attachmentList = findAttachmentsByCategoryOrderByAddAtAsc( account.getAttachments(), Category.COVERPICTURE);
            return attachmentList.get(attachmentList.size() - 1);
        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }

    @Override
    public Attachment saveCVProfileToAccount(MultipartFile file, String username) throws Exception {
        Account account = accountRepository.findAccountByUsername(username).
                orElseThrow(()-> new RessourceNotFoundException("Service Attachment ( saveCoverProfileToAccount )  : cannot found account  : "+username)) ;

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                        + fileName);
            }
            Attachment attachment = Attachment.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .data(file.getBytes())
                    .addAt(LocalDateTime.now())
                    .category(Category.CV)
                    .build();
            account.addAttachment(attachment);
            account =  accountRepository.save(account);
            List<Attachment> attachmentList = findAttachmentsByCategoryOrderByAddAtAsc( account.getAttachments(), Category.CV);
            return attachmentList.get(attachmentList.size() - 1);
        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }


    @Override
    public List<Attachment> selectByAccountAndCatrgory(long idAccount,Category category)  {
        return attachmentRepository.findAttachmentsByAccountIdAndCategory( idAccount, category);
    }

    @Override
    public Attachment getLastPhoto(long idAccount)  {
        return attachmentRepository.findLastPhotoSaved( idAccount)
                        .orElseThrow(()-> new RessourceNotFoundException("Service Attachement  : getLastPhoto Error : "+idAccount));

    }

    @Override
    public Attachment getAttachment(String fileId) throws Exception {
        return attachmentRepository.findById(fileId).orElseThrow( () -> new Exception("File not found with Id: " + fileId));
    }

    public List<Attachment> findAttachmentsByCategoryOrderByAddAtDesc(List<Attachment> attachments, Category category) {
        return attachments.stream()
                .filter(attachment -> attachment.getCategory() == category)
                .sorted(Comparator.comparing(Attachment::getAddAt).reversed())
                .collect(Collectors.toList());
    }// 2023-08-27T11:37:04  -> 2023-08-27T11:38:04
    public List<Attachment> findAttachmentsByCategoryOrderByAddAtAsc(List<Attachment> attachments, Category category) {
        return attachments.stream()
                .filter(attachment -> attachment.getCategory() == category)
                .sorted(Comparator.comparing(Attachment::getAddAt))
                .collect(Collectors.toList());
    }//2023-08-27T11:38:04  -> 2023-08-27T11:37:04
}
