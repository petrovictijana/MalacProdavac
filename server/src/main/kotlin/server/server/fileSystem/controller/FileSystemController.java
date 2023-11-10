package server.server.fileSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import server.server.fileSystem.service.StorageService;
import server.server.fileSystem.utilities.ImageType;

@RestController
@RequestMapping("/fileSystem")
public class FileSystemController {
    private final StorageService storageService;
    @Autowired
    public FileSystemController(StorageService storageService){
        this.storageService = storageService;
    }

    @PostMapping
    public int handleFileUpload(@RequestParam("file") MultipartFile multipartFile,
                                   RedirectAttributes redirectAttributes){
        return storageService.store(multipartFile, ImageType.USER);
    }
}
