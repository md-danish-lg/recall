package com.danish.backend.documents;

import com.danish.backend.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    @Value("${file.upload-dir}") private String fullPath;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document uploadNewDocument(User user, MultipartFile file) {
        Document document = new Document();
        if(file.getOriginalFilename() == null){
            throw new InvalidExtensionException("File Name cannot be empty");
        }

        String originalFilename = file.getOriginalFilename();
        List<String> extension = List.of(originalFilename.split("\\."));
        String lastExtension = extension.getLast();

        List<String> allowedExtensions = List.of("pdf", "md", "txt");

        if(!(allowedExtensions.contains(lastExtension))){
            throw new InvalidExtensionException("Only pdf, md, txt files Allowed!");
        }

        String uniqueFileName = UUID.randomUUID() + "_" + originalFilename;
        String fullFilePath = fullPath + "/" + uniqueFileName;


        try{
            file.transferTo(new File(fullFilePath));
            document.setFilename(uniqueFileName);
            document.setFilePath(fullFilePath);
            document.setUser(user);
            documentRepository.save(document);
            return document;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Document> getDocumentsByUser(User user) {
        return documentRepository.findDocumentsByUser(user);
    }
}
