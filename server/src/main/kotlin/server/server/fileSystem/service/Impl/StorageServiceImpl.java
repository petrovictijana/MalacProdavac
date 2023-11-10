package server.server.fileSystem.service.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.server.fileSystem.service.StorageService;
import server.server.fileSystem.utilities.Base64Coder;
import server.server.fileSystem.utilities.FolderUtility;
import server.server.fileSystem.utilities.ImageType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService {
    @Value("${filesystem.path}")
    private String generalPath;

    @Override
    public int store(String username, MultipartFile multipartFile, ImageType imageType) {
        String folderName = Base64Coder.encodeString("user" + "username");
        //Podesavanje putanje u zavisnosti od toga da li se slike cuvaju za user-a ili product-e
        String folder = imageType == ImageType.USER ? "/users/" : "/products/";
        String currentPath = generalPath + folder + folderName;

        Path path = Paths.get(currentPath);

        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(!FolderUtility.isFolderEmpty(path))
            FolderUtility.deleteFolderContent(path);

        Path destinationFile = path.resolve(multipartFile.getOriginalFilename());
        try {
            Files.copy(multipartFile.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return 1;
    }

    @Override
    public Path getAbsoluteFileLocation(String filename, ImageType imageType) {
        generalPath += imageType == ImageType.USER ? "users/" : "products/";

        Path path = Paths.get(generalPath);
        return path.resolve(filename);
    }

    @Override
    public Resource loadImageAsResource(String filename, ImageType imageType) {
        Path path = getAbsoluteFileLocation(filename, imageType);

        try {
            Resource resource = new UrlResource(path.toUri());

            if(resource.exists() || resource.isReadable()){
                //Ukoliko postoji
                return resource;
            }
            else{
                throw new RuntimeException("Ne mozemo procitati file " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


}
