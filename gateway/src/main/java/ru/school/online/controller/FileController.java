package ru.school.online.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.school.online.dto.MessageResponse;
import ru.school.online.service.FileService;

import javax.naming.ServiceUnavailableException;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/file")
@ResponseBody
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/{fileKey}")
    public MessageResponse getFile(@PathVariable("fileKey") String fileKey)
            throws ServiceUnavailableException, FileNotFoundException, BadRequestException
    {
        return new MessageResponse(fileService.getFileBase64(fileKey));
    }

    @PostMapping
    public MessageResponse createFile(@RequestParam("file") MultipartFile file)
            throws BadRequestException, ServiceUnavailableException
    {
        return new MessageResponse(fileService.saveNewFile(file).toString());
    }

    @DeleteMapping("/{fileKey}")
    public MessageResponse removeFile(@PathVariable("fileKey") String fileKey)
            throws NumberFormatException, ServiceUnavailableException, BadRequestException
    {
        fileService.removeFile(fileKey);
        return new MessageResponse("File was removed");
    }
}
