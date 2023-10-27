package ru.spring.school.online.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.service.FileService;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@ResponseBody
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/{fileKey}")
    public String getFile(@PathVariable String fileKey) throws IOException {
        return fileService.getFileBased64(fileKey);
    }
}
