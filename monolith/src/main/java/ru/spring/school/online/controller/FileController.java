package ru.spring.school.online.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.service.FileService;

import java.io.IOException;

@Tag(name = "Controller for operations with file resources")
@RestController
@RequestMapping("/file")
@ResponseBody
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Operation(summary = "Returns file by its fileKey",
            description = "Returns string encoded by Base64 of any file if exists")
    @GetMapping("/{fileKey}")
    public String getFile(@PathVariable String fileKey) throws IOException {
        return fileService.getFileBased64(fileKey);
    }
}
