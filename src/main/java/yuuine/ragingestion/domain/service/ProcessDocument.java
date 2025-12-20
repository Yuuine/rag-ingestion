package yuuine.ragingestion.domain.service;

import lombok.RequiredArgsConstructor;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yuuine.ragingestion.domain.models.DocumentProcessingContext;
import yuuine.ragingestion.exception.BusinessException;
import yuuine.ragingestion.exception.ErrorCode;
import yuuine.ragingestion.utils.MD5Util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class ProcessDocument {

    private final MD5Util md5Util;

    public DocumentProcessingContext processDocument(MultipartFile file) {

        String fileName = file.getOriginalFilename();

        //1. 验证文件是否合法(文件不能为空文件；文件上传大小限制为最大 100MB)
        validateFile(file);

        //2. 读取文件到内存，进行下一步处理
        byte[] fileBytes;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_GET_BYTES_ERROR, e);
        }

        //3.计算文件的MD5值，作为文件唯一标识
        String md5 = md5Util.md5(fileBytes);

        //4. 解析文件元信息
        String mimeType = detectMimeType(fileName, fileBytes);

        //5. 构建文件处理上下文对象
        DocumentProcessingContext context = new DocumentProcessingContext();
        context.setFileMd5(md5);
        context.setFileName(fileName);
        context.setMimeType(mimeType);
        context.setFileBytes(fileBytes);

        //6. 将上下文对象context存入ThreadLocal里面

        //7. 返回文件处理上下文对象
        return context;
    }

    private String detectMimeType(String fileName, byte[] fileBytes) {
        // 使用 Tika 的 AutoDetectParser
        Detector detector = new DefaultDetector();
        Metadata metadata = new Metadata();
        try (InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
            MediaType mediaType = detector.detect(inputStream, metadata);
            String mimeType = mediaType.toString();

            // 处理 MIME 类型为空的情况
            if (mimeType == null || mimeType.trim().isEmpty()) {
                mimeType = "application/octet-stream";
            }
            return mimeType;
            // 针对text/plain类型的特殊处理
            if ("text/plain".equals(mimeType)) {
                mimeType = textTypeDetect(fileName);
            }
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_IO_PROCESS_ERROR, e);
        }
    }

    private String textTypeDetect(String filename) {

        if (filename.endsWith(".md")) {
            return "text/markdown";
        }

        if (filename.endsWith(".json")) {
            return "application/json";
        }

        return "text/plain"; // 默认仍然是文本
    }


    private void validateFile(MultipartFile file) {

        // 禁止上传空文件
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.FILE_UPLOAD_EMPTY);
        }
        // 禁止上传文件大小超过 100MB
        if (file.getSize() > 100 * 1024 * 1024) {
            throw new BusinessException(ErrorCode.FILE_SIZE_TOO_LARGE);
        }
    }

}
