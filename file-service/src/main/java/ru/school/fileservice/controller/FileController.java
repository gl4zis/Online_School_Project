package ru.school.fileservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.school.exception.InvalidTokenException;
import ru.school.fileservice.exception.InvalidFileException;
import ru.school.fileservice.service.FileService;

import java.io.FileNotFoundException;

@RestController
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "User file controller")
public class FileController {
    private final FileService fileService;

    @Operation(summary = "Get file", description = "Returns file base64 by id (long). " +
            "Throws 404 (NotFound), 400 (InvalidId)")
    @GetMapping("/{id}")
    public String getFile(@PathVariable("id") Long id) throws FileNotFoundException {
        return fileService.getFileBase64(id);
    }

    @Operation(summary = "Save new file", description = "Creates new file in DB. " +
            "If client has valid access token, file will has this client as an owner (can be null). " +
            "Throws 400 (InvalidFile)")
    @PostMapping
    public Long createFile(@RequestBody MultipartFile file, HttpServletRequest request)
            throws InvalidFileException {
        return fileService.saveNewFile(file, request);
    }

    @Operation(summary = "Removes file from DB", description = "Access only for admins or owner. " +
            "Throws 403 (NoAccess, InvalidToken)")
    @DeleteMapping("/{id}")
    public void removeFile(@PathVariable("id") Long id, HttpServletRequest request)
            throws InvalidTokenException {
        fileService.removeFile(id, request);
    }
}
