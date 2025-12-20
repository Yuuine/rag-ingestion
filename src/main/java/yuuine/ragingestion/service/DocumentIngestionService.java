package yuuine.ragingestion.service;


import org.springframework.web.multipart.MultipartFile;
import yuuine.ragingestion.dto.response.IngestResponse;

import java.util.List;

public interface DocumentIngestionService {

    IngestResponse ingest(List<MultipartFile> files);
}
