package base.api.system.storage;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {
    void init();
    FileInfo save(InputStream fileInputStream, String fileName);
    public InputStream load(String fileName);
    public void deleteAll();
    public Stream<Path> loadAll();
}
