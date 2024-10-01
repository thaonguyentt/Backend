package base.api.system.storage.internal;

import base.api.system.storage.FileInfo;
import base.api.system.storage.FileStorageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class LocalFileStorageService implements FileStorageService {

    private final Path root = Paths.get("uploads");

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public FileInfo save(InputStream fileInputStream, String fileName) {
        init();
        String savedFileName = switch (StringUtils.trimToNull(fileName)) {
            case null -> UUID.randomUUID().toString();
            default -> fileName;
        };
        try {
            Files.copy(fileInputStream, this.root.resolve(savedFileName));
            return new FileInfo(savedFileName);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public InputStream load(String fileName) {

        try {
            Path file = root.resolve(fileName);

            if (Files.exists(file) || Files.isReadable(file)) {
                return Files.newInputStream(file);
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
