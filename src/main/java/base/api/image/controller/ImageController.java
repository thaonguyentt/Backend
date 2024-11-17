package base.api.image.controller;

import base.api.system.storage.FileStorageService;
import base.api.system.storage.internal.FileInfo;
import org.apache.commons.lang3.Validate;
import org.overviewproject.mime_types.GetBytesException;
import org.overviewproject.mime_types.MimeTypeDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired @Qualifier("imageStorage")
    FileStorageService imageStorageService;

    @GetMapping(value = "/{imageId}")
    @ResponseBody ResponseEntity<Resource> getImage(@PathVariable("imageId") String imageId) {
        Validate.notBlank(imageId);
        InputStream inputStream = imageStorageService.load(UUID.fromString(imageId));

        MimeTypeDetector detector = new MimeTypeDetector();
        String mimeType = null;
        try {
            mimeType = detector.detectMimeType(imageId, inputStream);
        } catch (GetBytesException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok()
          .contentType(MediaType.valueOf(mimeType))
          .body(new InputStreamResource(inputStream));

    }

    @PostMapping("")
    ResponseEntity<List<FileInfo>> uploadImage(@RequestParam("files") List<MultipartFile> files) {
        String message = "";
        List<FileInfo> savedFileInfo = new ArrayList<>();
        for (MultipartFile file : files) {
            try (InputStream inputStream = file.getInputStream()) {
                  savedFileInfo.add(imageStorageService.save(inputStream, file.getOriginalFilename()));
//                message = "Uploaded the file successfully: " + file.getOriginalFilename();
//                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (IOException e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
                throw new ResponseStatusException(HttpStatusCode.valueOf(400), message);

            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFileInfo);
    }


}
