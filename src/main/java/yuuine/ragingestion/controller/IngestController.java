package yuuine.ragingestion.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import yuuine.ragingestion.common.Result;
import yuuine.ragingestion.dto.response.IngestResponse;
import yuuine.ragingestion.service.DocumentIngestionService;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class IngestController {

    private final DocumentIngestionService documentIngestionService;

    @PostMapping()
    public Result<IngestResponse> ingest(
            @RequestParam("files") List<MultipartFile> files
    ) {

        IngestResponse ingestResponse = documentIngestionService.ingest(files);
        return Result.success(ingestResponse);

    }
}
