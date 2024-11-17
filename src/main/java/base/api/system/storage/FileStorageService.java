package base.api.system.storage;

import base.api.system.storage.internal.FileInfo;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import java.io.IOException;

public interface FileStorageService {
    void init();
    FileInfo save(InputStream fileInputStream, String originalFilename);
    public InputStream load(UUID fileId);
    public void deleteAll();
    public Stream<Path> loadAll();

    List<String> saveImgbb(List<InputStream> fileInputStreams, List<String> fileNames) throws IOException;
}
