package ru.school.fileservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.school.exception.InvalidTokenException;
import ru.school.fileservice.dto.FileRequest;
import ru.school.fileservice.exception.InvalidFileException;
import ru.school.fileservice.service.FileService;
import ru.school.response.MessageResponse;

import java.io.IOException;

@RestController
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "User file controller")
public class FileController {
    private final FileService fileService;

    @Operation(summary = "Get file", description = "Returns file bytearray by id (String). Size should be in (0; 4000]" +
            "Throws 404 (NotFound), 400 (InvalidId)")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getFile(@PathVariable String id, @RequestParam(required = false) Integer size) throws IOException {
        if (size == null)
            return fileService.getFile(id);
        else if (size > 0 && size <= 4000)
            return fileService.getScaledImage(id, size);
        else
            throw new BadRequestException("Invalid image size");
    }

    @Operation(summary = "Save new file", description = "Creates new file and info about it in DB " +
            "If client has valid access token, file will has this client as an owner (or null). " +
            "Throws 400 (InvalidFile)")
    @PostMapping
    public MessageResponse upload(@RequestBody FileRequest file, HttpServletRequest request)
            throws InvalidFileException {
        return new MessageResponse(fileService.upload(file, request));
    }

    @Operation(summary = "Removes file", description = "Access only for admins or owner. " +
            "Throws 403 (NoAccess, InvalidToken)")
    @DeleteMapping("/{id}")
    public void removeFile(@PathVariable String id, HttpServletRequest request)
            throws InvalidTokenException {
        fileService.removeFile(id, request);
    }
}
