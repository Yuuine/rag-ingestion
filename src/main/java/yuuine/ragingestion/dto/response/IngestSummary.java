package yuuine.ragingestion.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class IngestSummary {

    private int totalFiles;                     // 本次请求一共上传了多少个文件
    private List<String> successfulFiles;       // 成功解析的文件名列表（如 ["a.pdf", "b.docx"]）
    private List<FailedFile> failedFiles;       // 解析失败的文件详情列表

    public static class FailedFile {
        private String filename;   // 失败的原始文件名（如 "加密文档.pdf"）
        private String reason;     // 失败的具体原因（如 "PDF is encrypted"）
    }

}
