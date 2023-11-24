package ru.school.online.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.school.online.service.FileService;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@ResponseBody
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/{fileKey}")
    public String getFile(@PathVariable("fileKey") String fileKey) {
        return fileService.getFileBase64(Long.parseLong(fileKey));   // TODO Check types
    }

    @PostMapping
    public String createFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.saveNewFile(file).toString();
    }

    // TODO return some message ?
    @DeleteMapping("/{fileKey}")
    public void removeFile(@PathVariable("fileKey") String fileKey) {
        fileService.removeFile(Long.parseLong(fileKey));
    }
}
