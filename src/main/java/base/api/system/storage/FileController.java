package base.api.system.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileController {
    @Autowired @Qualifier("documentStorage")
    FileStorageService storageService;

    @PostMapping("/api/test/upload")
    public ResponseEntity<String> upload(@RequestParam("file")MultipartFile file) {
        String message = "";
        try {
            storageService.save(file.getInputStream(), file.getOriginalFilename());

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @PostMapping("/api/test/uploadimgbb")
    public ResponseEntity<List<String>> upload(@RequestParam("file")MultipartFile[] files) {
        try {
            List<InputStream> inputStreams = new ArrayList<>();
            List<String> fileNames = new ArrayList<>();


            // Lặp qua từng file để thêm InputStream và tên file vào danh sách
            for (MultipartFile file : files) {
                inputStreams.add(file.getInputStream());
                fileNames.add(file.getOriginalFilename());
            }
            // Gọi phương thức uploadFiles từ FileUploader service
            List<String> displayUrls = storageService.saveImgbb(inputStreams, fileNames);
            return ResponseEntity.ok(displayUrls);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Xử lý các lỗi khác
        }
    }


}
