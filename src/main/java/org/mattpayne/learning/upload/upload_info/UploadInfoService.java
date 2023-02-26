package org.mattpayne.learning.upload.upload_info;

import java.util.List;
import java.util.stream.Collectors;
import org.mattpayne.learning.upload.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UploadInfoService {

    private final UploadInfoRepository uploadInfoRepository;

    public UploadInfoService(final UploadInfoRepository uploadInfoRepository) {
        this.uploadInfoRepository = uploadInfoRepository;
    }

    public List<UploadInfoDTO> findAll() {
        final List<UploadInfo> uploadInfos = uploadInfoRepository.findAll(Sort.by("id"));
        return uploadInfos.stream()
                .map((uploadInfo) -> mapToDTO(uploadInfo, new UploadInfoDTO()))
                .collect(Collectors.toList());
    }

    public UploadInfoDTO get(final Long id) {
        return uploadInfoRepository.findById(id)
                .map(uploadInfo -> mapToDTO(uploadInfo, new UploadInfoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UploadInfoDTO uploadInfoDTO) {
        final UploadInfo uploadInfo = new UploadInfo();
        mapToEntity(uploadInfoDTO, uploadInfo);
        return uploadInfoRepository.save(uploadInfo).getId();
    }

    public void update(final Long id, final UploadInfoDTO uploadInfoDTO) {
        final UploadInfo uploadInfo = uploadInfoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(uploadInfoDTO, uploadInfo);
        uploadInfoRepository.save(uploadInfo);
    }

    public void delete(final Long id) {
        uploadInfoRepository.deleteById(id);
    }

    private UploadInfoDTO mapToDTO(final UploadInfo uploadInfo, final UploadInfoDTO uploadInfoDTO) {
        uploadInfoDTO.setId(uploadInfo.getId());
        uploadInfoDTO.setName(uploadInfo.getName());
        uploadInfoDTO.setType(uploadInfo.getType());
        uploadInfoDTO.setDateUploaded(uploadInfo.getDateUploaded());
        uploadInfoDTO.setStatus(uploadInfo.getStatus());
        return uploadInfoDTO;
    }

    private UploadInfo mapToEntity(final UploadInfoDTO uploadInfoDTO, final UploadInfo uploadInfo) {
        uploadInfo.setName(uploadInfoDTO.getName());
        uploadInfo.setType(uploadInfoDTO.getType());
        uploadInfo.setDateUploaded(uploadInfoDTO.getDateUploaded());
        uploadInfo.setStatus(uploadInfoDTO.getStatus());
        return uploadInfo;
    }

}
