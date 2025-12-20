package yuuine.ragingestion.domain.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yuuine.ragingestion.dto.response.IngestResponse;
import yuuine.ragingestion.exception.BusinessException;
import yuuine.ragingestion.exception.ErrorCode;
import yuuine.ragingestion.threadLocal.DocumentContextTL;

@RequiredArgsConstructor
@Service
public class ProcessSingleDocument {

    private final ProcessDocument processDocument;
    private final DocumentParserService documentParserService;

    public IngestResponse processSingleDocument(MultipartFile file) {

        //1. 解析文档基本信息
        try {
            processDocument.processDocument(file);
        } catch (BusinessException e) {
            return failResult(file.getOriginalFilename(), e.getMessage()); // 业务错误直接返回
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILED, e); // 系统异常继续抛
        }
        //2. 解析文档内容为纯文本
        String plainText = documentParserService.parse(DocumentContextTL.get());
        System.out.println(plainText);
        //3. 纯文本 chunk 处理

        return null;
    }

    private IngestResponse failResult(@Nullable String originalFilename, String message) {
        return null;
    }


}