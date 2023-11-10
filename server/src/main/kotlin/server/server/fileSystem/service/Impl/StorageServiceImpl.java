package server.server.fileSystem.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.server.fileSystem.service.StorageService;
import server.server.fileSystem.utilities.ImageType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService {
    private String generalPath = "fileSystem/";

    @Override
    public int store(MultipartFile multipartFile, ImageType imageType) {
        //Podesavanje putanje u zavisnosti od toga da li se slike cuvaju za user-a ili product-e
        generalPath += imageType == ImageType.USER ? "users/" : "products/";

        Path path = Paths.get(generalPath);

        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Path destinationFile = path.resolve(multipartFile.getOriginalFilename());
        try {
            Files.copy(multipartFile.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return 1;
    }
}
