package base.api.system.storage;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import java.io.IOException;

public interface FileStorageService {
    void init();
    FileInfo save(InputStream fileInputStream, String fileName);
    public InputStream load(String fileName);
    public void deleteAll();
    public Stream<Path> loadAll();
    List<String> saveImgbb(List<InputStream> fileInputStreams, List<String> fileNames) throws IOException;
}
