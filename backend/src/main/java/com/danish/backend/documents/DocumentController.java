package com.danish.backend.documents;

import com.danish.backend.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {


    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<DocumentResponse> uploadDocument(
            @AuthenticationPrincipal User user,
            @RequestParam("file") MultipartFile file){

        Document document = documentService.uploadNewDocument(user, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(new DocumentResponse(document));
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponse>> getDocuments(
            @AuthenticationPrincipal User user){
        List<Document> documents = documentService.getDocumentsByUser(user);
        List<DocumentResponse> documentResponses = documents.stream()
                .map(DocumentResponse::new)
                .toList();

        return ResponseEntity.ok(documentResponses);
    }

}
