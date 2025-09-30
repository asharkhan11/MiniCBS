package in.banking.cbs.minio_service.Minio.Service.controller;

import in.banking.cbs.minio_service.Minio.Service.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService storageService;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws Exception {
        return storageService.uploadFile(file);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable String fileName) throws Exception {
        try (InputStream is = storageService.downloadFile(fileName)) {
            byte[] content = is.readAllBytes();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(content);
        }
    }
}
