package server.server.fileSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import server.server.fileSystem.service.StorageService;

@RestController
@RequestMapping("/fileSystem")
public class FileSystemController {
    @Autowired
    private final StorageService storageService;

    public FileSystemController(StorageService storageService){
        this.storageService = storageService;
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file")MultipartFile multipartFile,
                                   RedirectAttributes redirectAttributes){
        storageService.store(multipartFile);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " +
                multipartFile.getOriginalFilename() + "!");

        return "redirect:/";
    }
}
