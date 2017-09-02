package vkCoverChanger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vkCoverChanger.storage.StorageServiceInstance;
import vkCoverChanger.storage.exception.StorageFileNotFoundException;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by Nikolay V. Petrov on 26.08.2017.
 */

@Controller
public class FileUploadController {

    @Autowired
    private StorageServiceInstance storageServiceInstance;

    private final String NAMESPACE = "storage";

    public FileUploadController(StorageServiceInstance storageServiceInstance) {
        this.storageServiceInstance = storageServiceInstance;
    }

    @GetMapping(NAMESPACE)
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageServiceInstance.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping(NAMESPACE + "/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageServiceInstance.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping(NAMESPACE)
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageServiceInstance.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/" + NAMESPACE;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}