package org.mattpayne.learning.upload.upload_info;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(value = "/api/uploadInfos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UploadInfoResource {

    private final UploadInfoService uploadInfoService;

    public UploadInfoResource(final UploadInfoService uploadInfoService) {
        this.uploadInfoService = uploadInfoService;
    }


    @GetMapping
    public ResponseEntity<List<UploadInfoDTO>> getAllUploadInfos() {
        return ResponseEntity.ok(uploadInfoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UploadInfoDTO> getUploadInfo(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(uploadInfoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUploadInfo(
            @RequestBody @Valid final UploadInfoDTO uploadInfoDTO) {
        return new ResponseEntity<>(uploadInfoService.create(uploadInfoDTO), HttpStatus.CREATED);
    }

    // Trying to follow https://www.bezkoder.com/spring-boot-file-upload/ after starting with bootify.io
    @PostMapping("/upload")
    public ResponseEntity<UploadInfoDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(uploadInfoService.uploadFile(file), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUploadInfo(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final UploadInfoDTO uploadInfoDTO) {
        uploadInfoService.update(id, uploadInfoDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUploadInfo(@PathVariable(name = "id") final Long id) {
        uploadInfoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
