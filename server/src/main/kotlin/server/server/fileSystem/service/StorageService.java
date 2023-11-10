package server.server.fileSystem.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import server.server.fileSystem.utilities.ImageType;

import java.nio.file.Path;

public interface StorageService {
    int store(String username, MultipartFile multipartFile, ImageType imageType);

    Path getAbsoluteFileLocation(String filename, ImageType imageType);

    Resource loadImageAsResource(String filename, ImageType imageType);
}
