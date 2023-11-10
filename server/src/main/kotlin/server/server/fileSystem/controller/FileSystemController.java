package server.server.fileSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import server.server.fileSystem.service.StorageService;
import server.server.fileSystem.utilities.ImageType;

@RestController
@RequestMapping()
public class FileSystemController {
    private final StorageService storageService;
    @Autowired
    public FileSystemController(StorageService storageService){
        this.storageService = storageService;
    }

    @PostMapping("/profile-picture-upload")
    public int profilePictureUpload(@RequestParam("username") String username,
                                    @RequestParam("file") MultipartFile multipartFile,
                                    RedirectAttributes redirectAttributes){
        return storageService.store(username, multipartFile, ImageType.USER);
    }

    @PostMapping("/product-picture-upload")
    public int productPictureUpload(@RequestParam("username") String username,
                                    @RequestParam("file") MultipartFile multipartFile,
                                    RedirectAttributes redirectAttributes){
        return storageService.store(username, multipartFile, ImageType.PRODUCT);
    }

    @GetMapping
    public ResponseEntity<Resource> getImage(@RequestParam("filename") String filename){
        Resource file = storageService.loadImageAsResource(filename, ImageType.USER);

        if(file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
