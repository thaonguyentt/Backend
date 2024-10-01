package base.api.book.controller;

import base.api.book.dto.CopyDto;
import base.api.book.service.CopyService;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://the-flying-bookstore.vercel.app"})
@RequestMapping("api/copy")
public class CopyController {
    private final CopyService copyService;

    public CopyController(CopyService copyService) {
        this.copyService = copyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CopyDto> getCopyById (@PathVariable Long id) {
        CopyDto copyDto = copyService.getCopyById(id);
        if(copyDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(copyDto);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<List<CopyDto>> getCopyByOwnerId (@PathVariable Long id) {
        return ResponseEntity.ok(copyService.getCopyByOwnerId(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CopyDto>> findAllCopies(Pageable pageable) {
        return ResponseEntity.ok().body(copyService.findAllCopies(pageable));
    }

    @GetMapping
    public ResponseEntity<List<CopyDto>> getAllCopy () {
        return ResponseEntity.ok(copyService.getAllCopy());
    }

    @PostMapping
    public ResponseEntity<CopyDto> createCopy (@RequestBody CopyDto copyDto) {
        return ResponseEntity.ok(copyService.createCopy(copyDto));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<CopyDto> updateCopy (@PathVariable Long id, @RequestBody CopyDto copyDto) {
        return ResponseEntity.ok(copyService.updateCopy(copyDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCopy (@PathVariable Long id) {
        copyService.deleteCopy(id);
        return ResponseEntity.noContent().build();
    }

//    @Configuration
//    public class FileUploadConfig {
//
//        @Bean
//        public MultipartConfigElement multipartConfigElement() {
//            MultipartConfigFactory factory = new MultipartConfigFactory();
//            factory.setMaxFileSize("10MB");
//            factory.setMaxRequestSize("10MB");
//            return factory.createMultipartConfig();
//        }
//    }

    @PostMapping (path="/upload")
    public ResponseEntity<String> upload (@RequestParam("file") MultipartFile[] file) {
        // Kiểm tra nếu file không trống

        if (file.length == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload.");
        }

        return ResponseEntity.ok("ok");
//
//        try {
//            // Gọi API của server lưu file
//            String response = uploadFileToImgbb(file);
//            return ResponseEntity.ok("File uploaded successfully: " + response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed.");
//        }
    }

    private String uploadFileToImgbb (MultipartFile file) throws IOException {
        String fileServerUrl = "https://api.imgbb.com/1/upload?key=f97f8ed9513b219d146c34700f66a5da&expiration=600";

        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        // Tạo header cho request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Tạo body cho request
        HttpEntity<MultipartFile> requestEntity = new HttpEntity<>(file, headers);

        // Gửi request POST lên API lưu file
        ResponseEntity<String> response = restTemplate.postForEntity(fileServerUrl, requestEntity, String.class);

        // Trả về phản hồi từ API lưu file
        return response.getBody();
    }


}
