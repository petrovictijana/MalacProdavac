package server.server.fileSystem.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.server.fileSystem.StorageProperties;
import server.server.fileSystem.service.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {
    private final Path rootLocation;
    @Autowired
    public StorageServiceImpl(StorageProperties storageProperties){
        if(storageProperties.getLocation().trim().length() == 0){
            throw new RuntimeException("File upload location cannot be null");
        }

        this.rootLocation = Paths.get(storageProperties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void store(MultipartFile multipartFile) {
        if(multipartFile.isEmpty()){
            throw new RuntimeException("Failed to store an empty file.");
        }
        Path destionationFile = this.rootLocation.resolve(
                Paths.get(multipartFile.getOriginalFilename()))
                        .normalize()
                        .toAbsolutePath();
        if(destionationFile.getParent().equals(this.rootLocation.toAbsolutePath())){
            //Cannot store file outside current directory.
        }

        try {
            InputStream inputStream = multipartFile.getInputStream();
            Files.copy(inputStream, destionationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
