package yuuine.ragingestion.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yuuine.ragingestion.domain.service.ProcessSingleDocument;
import yuuine.ragingestion.dto.response.IngestResponse;
import yuuine.ragingestion.service.DocumentIngestionService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentIngestionServiceImpl implements DocumentIngestionService {

    private final ProcessSingleDocument processSingleDocument;

    @Override
    public IngestResponse ingest(List<MultipartFile> files) {

        //构建返回结果
        IngestResponse ingestResponse = new IngestResponse();

        //遍历上传文件并逐个处理
        for (MultipartFile file : files) {
            //处理单个文件
            IngestResponse chunkResponse = processSingleDocument.processSingleDocument(file);
        }

        return null;
    }
}
