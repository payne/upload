package org.mattpayne.learning.upload.upload_info;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UploadInfoDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String type;

    private LocalDateTime dateUploaded;

    @Size(max = 255)
    private String status;

}
