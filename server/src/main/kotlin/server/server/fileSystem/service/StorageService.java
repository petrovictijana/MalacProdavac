package server.server.fileSystem.service;

import org.springframework.web.multipart.MultipartFile;
import server.server.fileSystem.utilities.ImageType;

public interface StorageService {
    int store(MultipartFile multipartFile, ImageType imageType);
}
