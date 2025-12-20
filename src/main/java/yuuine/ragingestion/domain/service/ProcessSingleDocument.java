package yuuine.ragingestion.domain.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yuuine.ragingestion.domain.models.DocumentProcessingContext;
import yuuine.ragingestion.dto.response.IngestResponse;
import yuuine.ragingestion.exception.BusinessException;
import yuuine.ragingestion.exception.ErrorCode;

@RequiredArgsConstructor
@Service
public class ProcessSingleDocument {

    private final ProcessDocument processDocument;

    public IngestResponse processSingleDocument(MultipartFile file) {

        DocumentProcessingContext context;
        //1. 解析文档基本信息
        try {
            context = processDocument.processDocument(file);
        } catch (BusinessException e) {
            return failResult(file.getOriginalFilename(), e.getMessage()); // 业务错误直接返回
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILED, e); // 系统异常继续抛
        }
        //2. 解析文档内容为纯文本
        String plainText = documentParser.parse();

        //3. 纯文本 chunk 处理

        //4. 构建返回结果对象

        return null;
    }

    private IngestResponse failResult(@Nullable String originalFilename, String message) {
        return null;
    }


}