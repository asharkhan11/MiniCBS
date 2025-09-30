package in.banking.cbs.action_service.service;

import in.banking.cbs.action_service.security.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.TemporalUnit;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinioClientService {

    private final WebClient webClient;
    private final CustomerService customerService;
    private final LoggedInUser loggedInUser;

//    public String sendFileToMinio(MultipartFile file) {
//        Mono<String> stringMono = webClient.post()
//                .uri("http://minio-service/files/upload")
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .body(file, MultipartFile.class)
//                .retrieve()
//                .bodyToMono(String.class)
//                .onErrorResume(ex -> Mono.error());
//
//        return stringMono.block(Duration.ofSeconds(3));
//    }


    public byte[] downloadFile(String fileName) {
        return webClient.get().uri("http://minio-service/files/download/{fileName}", fileName).retrieve().bodyToMono(byte[].class).block();
    }


    private byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + file.getOriginalFilename(), e);
        }
    }
}
