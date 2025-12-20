package yuuine.ragingestion.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class IngestResponse {

    private List<ChunkResponse> chunks;
    private IngestSummary summary;

}
