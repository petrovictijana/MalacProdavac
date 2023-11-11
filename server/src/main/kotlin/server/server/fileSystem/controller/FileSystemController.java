package server.server.fileSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import server.server.fileSystem.CustomMultipartFile;
import server.server.fileSystem.MyRequest;
import server.server.fileSystem.MyResponse;
import server.server.fileSystem.service.StorageService;
import server.server.fileSystem.utilities.FolderUtility;
import server.server.fileSystem.utilities.ImageType;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping()
public class FileSystemController {
    private final StorageService storageService;
    @Autowired
    public FileSystemController(StorageService storageService){
        this.storageService = storageService;
    }

//    @PostMapping("/profile-picture-upload")
//    public int profilePictureUpload(@RequestParam("username") String username,
//                                    @RequestParam("file") MultipartFile multipartFile,
//                                    RedirectAttributes redirectAttributes){
//        return storageService.store(username, multipartFile, ImageType.USER);
//    }

    @PostMapping("/profile-picture-upload")
    public int profilePictureUpload(@RequestBody MyRequest myRequest,
                                    RedirectAttributes redirectAttributes){
        return storageService.store(myRequest.getUsername(),
                new CustomMultipartFile(myRequest.getImage()),
                ImageType.USER);
    }

    @PostMapping("/product-picture-upload")
    public int productPictureUpload(@RequestParam("productId") Long productId,
                                    @RequestParam("file") MultipartFile multipartFile,
                                    RedirectAttributes redirectAttributes){
        return storageService.store(String.valueOf(productId), multipartFile, ImageType.PRODUCT);
    }

    @GetMapping("user/profile-picture")
    public ResponseEntity<?> getImage(@RequestParam("username") String username) throws IOException {
        Resource file = storageService.loadImageAsResource(username, ImageType.USER);
        System.out.println("Filename: " + file.getFilename().toString());

        if(file == null)
            return ResponseEntity.notFound().build();

        String contentType = determineImageContentType(file.getFilename());

        MyResponse myResponse = MyResponse.builder()
                .ime(username)
                .image(FolderUtility.convertResourceToByteArray(file))
                .build();

        return ResponseEntity.ok()
                .body(myResponse);
    }

    // Pomoćna metoda za određivanje Content-Type na osnovu ekstenzije fajla
    private String determineImageContentType(String filename) {
        Path path = Paths.get(filename);
        String extension = getExtension(path);

        if ("png".equalsIgnoreCase(extension)) {
            return "image/png";
        } else if ("jpg".equalsIgnoreCase(extension) || "jpeg".equalsIgnoreCase(extension)) {
            return "image/jpeg";
        }
        // Dodajte druge tipove slika prema potrebi
        return "application/octet-stream"; // Fallback vrednost ako tip slike nije prepoznat
    }

    // Metoda za dobijanje ekstenzije fajla
    private String getExtension(Path path) {
        String filename = path.getFileName().toString();
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }
}
