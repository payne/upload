package org.mattpayne.learning.upload.upload_info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mattpayne.learning.upload.config.BaseIT;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;


public class UploadInfoResourceTest extends BaseIT {

    @Test
    @Sql("/data/uploadInfoData.sql")
    void getAllUploadInfos_success() throws Exception {
        mockMvc.perform(get("/api/uploadInfos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(((long)1000)));
    }

    @Test
    @Sql("/data/uploadInfoData.sql")
    void getUploadInfo_success() throws Exception {
        mockMvc.perform(get("/api/uploadInfos/1000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Duis autem vel."));
    }

    @Test
    void getUploadInfo_notFound() throws Exception {
        mockMvc.perform(get("/api/uploadInfos/1666")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").value("NotFoundException"));
    }

    @Test
    void createUploadInfo_success() throws Exception {
        mockMvc.perform(post("/api/uploadInfos")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/uploadInfoDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertEquals(1, uploadInfoRepository.count());
    }

    @Test
    void createUploadInfo_missingField() throws Exception {
        mockMvc.perform(post("/api/uploadInfos")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/uploadInfoDTORequest_missingField.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").value("MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("name"));
    }

    @Test
    @Sql("/data/uploadInfoData.sql")
    void updateUploadInfo_success() throws Exception {
        mockMvc.perform(put("/api/uploadInfos/1000")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/uploadInfoDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals("Nam liber tempor.", uploadInfoRepository.findById(((long)1000)).get().getName());
        assertEquals(2, uploadInfoRepository.count());
    }

    @Test
    @Sql("/data/uploadInfoData.sql")
    void deleteUploadInfo_success() throws Exception {
        mockMvc.perform(delete("/api/uploadInfos/1000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertEquals(1, uploadInfoRepository.count());
    }

}
