package com.danish.backend.documents;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;

@Service
public class AiService {

    private final RestTemplate restTemplate;

    @Value("${ai.service.url}")
    private String aiServiceURL;

    public AiService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public boolean embedFile(String documentId, File file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("document_id", documentId);
        body.add("file", new FileSystemResource(file));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);


        try{
            EmbedResponse response = restTemplate.postForObject(
                    aiServiceURL + "/embed",
                    requestEntity,
                    EmbedResponse.class
            );

            if(response != null) {
                return response.isSuccess();
            }
        } catch(RestClientException e){
            return false;
        }

        return false;

    }
}
