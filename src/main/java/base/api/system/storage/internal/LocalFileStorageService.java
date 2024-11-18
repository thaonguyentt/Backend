package base.api.system.storage.internal;

import base.api.system.security.Identity;
import base.api.system.security.IdentityUtil;
import base.api.system.storage.FileStorageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


public class LocalFileStorageService implements FileStorageService {

    private final Path root;
    private final String API_URL = "https://api.imgbb.com/1/upload?key=f97f8ed9513b219d146c34700f66a5da&expiration=600";
    private final String API_KEY = "f97f8ed9513b219d146c34700f66a5da";
    private final FileRepository fileRepository;

    LocalFileStorageService(String storageDir, FileRepository fileRepository) {
        super();
        this.fileRepository = fileRepository;
        if (StringUtils.isBlank(storageDir)) {
            throw new IllegalArgumentException("storageDir can't be null or empty");
        }
        this.root = Paths.get(storageDir);
    }


    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    @Transactional
    public FileInfo save(InputStream fileInputStream, String originalFilename) {
        init();
        String savedFileName = switch (StringUtils.trimToNull(originalFilename)) {
            case null -> UUID.randomUUID().toString();
            default -> originalFilename;
        };
        try {
            Identity identity = IdentityUtil.getIdentity();
            Long userId = identity == null ? null : identity.getUserId();
            FileInfo savedFileInfo = fileRepository.saveAndFlush(new FileInfo(null, savedFileName, userId));
            Files.copy(fileInputStream, this.root.resolve(savedFileInfo.getId().toString()));

            return savedFileInfo;
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<String> saveImgbb(List<InputStream> fileInputStreams, List<String> fileNames) throws IOException {

        List<String> responses = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (int i = 0; i < fileInputStreams.size(); i++) {
            InputStream fileInputStream = fileInputStreams.get(i);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            byte[] fileBytes = fileInputStream.readAllBytes();
            String encodedImage = java.util.Base64.getEncoder().encodeToString(fileBytes);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", encodedImage);

            HttpEntity<MultiValueMap<String, Object>> requestEntity
                    = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String jsonResponse = response.getBody();
                JsonNode rootNode = objectMapper.readTree(jsonResponse);
                String displayUrl = rootNode.path("data").path("display_url").asText();
                responses.add(displayUrl);
            } else {
                System.err.println("Request failed with status: " + response.getStatusCode());
            }

//            System.out.println(response.getStatusCode());



        }
        return responses;




//        List<String> responses = new ArrayList<>();
//
//        // Duyệt qua từng file để tải lên
//        for (int i = 0; i < fileInputStreams.size(); i++) {
//
//            InputStream fileInputStream = fileInputStreams.get(i);
////            String fileName = fileName = i < fileNames.size() ? fileNames.get(i) : UUID.randomUUID().toString();
//
//            // Đọc nội dung của file từ InputStream và chuyển sang Base64
//            byte[] fileBytes = fileInputStream.readAllBytes();
//            String encodedImage = java.util.Base64.getEncoder().encodeToString(fileBytes);
//
//
//            // Tạo connection tới API
//            URL url = new URL(API_URL + "?expiration=600&key=" + API_KEY);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Connection", "Keep-Alive");
//            connection.setRequestProperty("Cache-Control", "no-cache");
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            connection.setRequestProperty("Accept", "application/json");
//            connection.setDoOutput(true);
//
//
//            // Xây dựng dữ liệu body
//            String postData = "image=" + encodedImage;
////            StringBuilder postData = new StringBuilder();
////            postData.append("image=").append(encodedImage);
////            postData.append("&name=").append(savedFileName);
//
//
////            if (StringUtils.isNotEmpty(expiration)) {
////                postData.append("&expiration=").append(expiration);
////            }
//
//
//            // Gửi dữ liệu lên API
//            try (OutputStream os = connection.getOutputStream()) {
//                os.write(postData.getBytes());
//                os.flush();
//            }
//
//
//
//                // Kiểm tra và nhận phản hồi từ API
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                StringBuilder response = new StringBuilder();
//                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//                    String inputLine;
//                    while ((inputLine = in.readLine()) != null) {
//                        response.append(inputLine);
//                    }
//                }
//
//                Map<String, Object> jsonMap = parseJson(response.toString());
//                String displayUrl = ((Map<String, Object>) jsonMap.get("data")).get("display_url").toString();
//                responses.add(displayUrl);
//            } else {
//                System.out.println("Error: " + responseCode);
//            }
//
//                try {
//                    StringBuilder response = new StringBuilder();
//                    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//                        String inputLine;
//                        while ((inputLine = in.readLine()) != null) {
//                            response.append(inputLine);
//                        }
//                    }
//
//                    // Phân tích JSON bằng cách sử dụng Map và List
//                    String jsonResponse = response.toString();
//                    Map<String, Object> jsonMap = parseJson(jsonResponse);
//                    String displayUrl = ((Map<String, Object>) jsonMap.get("data")).get("display_url").toString();
//                    responses.add(displayUrl); // Thêm display_url vào danh sách
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//
//                }
//            }
//
//
//        return responses;
    }

    // Hàm phân tích cú pháp JSON đơn giản
    private Map<String, Object> parseJson(String json) {
        Map<String, Object> map = new HashMap<>();
        String[] pairs = json.replaceAll("[{}\"]", "").split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            String key = keyValue[0].trim();
            String value = keyValue.length > 1 ? keyValue[1].trim() : "";
            if (value.startsWith("{")) {
                // Xử lý đối tượng JSON lồng nhau (không sâu hơn một cấp)
                Map<String, Object> nestedMap = new HashMap<>();
                String[] nestedPairs = value.substring(1, value.length() - 1).split(",");
                for (String nestedPair : nestedPairs) {
                    String[] nestedKeyValue = nestedPair.split(":");
                    nestedMap.put(nestedKeyValue[0].trim(), nestedKeyValue[1].trim());
                }
                map.put(key, nestedMap);
            } else {
                map.put(key, value);
            }
        }
        return map;
    }


    @Override
    public InputStream load(UUID fileId) {
        FileInfo fileInfo = fileRepository.findById(fileId).orElse(null);
        try {
            Path file = root.resolve(fileId.toString());

            if (Files.exists(file) || Files.isReadable(file)) {
                return new BufferedInputStream(Files.newInputStream(file));
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
