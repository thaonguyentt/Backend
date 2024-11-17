package base.api.system.storage.internal;

import base.api.system.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfiguration {
    @Autowired FileRepository fileRepository;
    @Bean
    public FileStorageService imageStorage() {
        return new LocalFileStorageService("files/images", fileRepository);
    }

    @Bean
    public FileStorageService documentStorage() {
        return new LocalFileStorageService("files/documents", fileRepository);
    }
}
