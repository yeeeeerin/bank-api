package com.depromeet.bank.controller.data;

import com.depromeet.bank.dto.MessageResponse;
import com.depromeet.bank.exception.BadRequestException;
import com.depromeet.bank.exception.InternalServerErrorException;
import com.depromeet.bank.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/messages")
    public List<MessageResponse> getMessages(@RequestParam(defaultValue = "0") Integer page,
                                             @RequestParam(defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return messageService.getMessages(pageable)
                .stream()
                .map(MessageResponse::from)
                .collect(Collectors.toList());
    }

    @PostMapping("/messages/upload")
    public List<MessageResponse> uploadMessages(@RequestParam("file") MultipartFile multipartFile) {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            if (multipartFile.isEmpty()) {
                throw new BadRequestException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new BadRequestException("Cannot store file with relative path outside current directory " + filename);
            }
            try (InputStream inputStream = multipartFile.getInputStream()) {
                String content = new String(IOUtils.toByteArray(inputStream));
                return messageService.update(content)
                        .stream()
                        .map(MessageResponse::from)
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new InternalServerErrorException("Failed to store file " + filename, e);
        }
    }
}
