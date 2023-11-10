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
    public int store(String identificationString, MultipartFile multipartFile, ImageType imageType) {
        //Podesavanje putanje u zavisnosti od toga da li se slike cuvaju za user-a ili product-e
        Path path = Paths.get(generalPath + generateFolderPath(identificationString, imageType));
        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(!FolderUtility.isFolderEmpty(path))
            FolderUtility.deleteFolderContent(path);

        Path destinationFile = path.resolve("image");
        try {
            Files.copy(multipartFile.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return 1;
    }

    @Override
    public Path getFileLocation(String identificationString, String filename, ImageType imageType) {
        System.out.println("Metoda generateFolderPath(identificationString, imageType): " + generateFolderPath(identificationString, imageType));
        Path path = Paths.get(generalPath + generateFolderPath(identificationString, imageType) + "/" + filename);

        return path.resolve(filename);
    }

    @Override
    public Resource loadImageAsResource(String identificationString, String filename, ImageType imageType) {
        Path path = getFileLocation(identificationString, filename, imageType);
        System.out.println("Putanja: " + path.toString());
        try {
            Resource resource = new UrlResource(path.toUri());

            if(resource.exists() /*|| resource.isReadable()*/){
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

    private String generateFolderPath(String identificationString, ImageType imageType) {
        String folder = "";
        if(imageType == ImageType.USER)
            folder = "/users/" + Base64Coder.encodeString("user" + identificationString);
        else if(imageType == ImageType.PRODUCT)
            folder = "/products/" + Base64Coder.encodeString("product" + identificationString);

        return folder;
    }
}
