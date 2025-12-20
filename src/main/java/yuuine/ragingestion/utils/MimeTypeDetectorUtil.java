package yuuine.ragingestion.utils;

import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;
import org.springframework.stereotype.Component;
import yuuine.ragingestion.exception.BusinessException;
import yuuine.ragingestion.exception.ErrorCode;

import java.util.Locale;

public class MimeTypeDetectorUtil {
    private static final Tika TIKA = new Tika();

    /**
     * 探测文件 MIME 类型（基于内容 + 文件名）
     */
    public static String detectMimeType(String fileName, byte[] fileBytes) {
        if (fileBytes == null || fileBytes.length == 0) {
            return MediaType.OCTET_STREAM.toString();
        }

        try {
            String mimeType = TIKA.detect(fileBytes, normalizeFileName(fileName));
            return normalizeMimeType(mimeType);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.FILE_IO_PROCESS_ERROR, e);
        }
    }

    /**
     * 规范化文件名（防止大小写问题）
     */
    private static String normalizeFileName(String fileName) {
        return fileName == null ? "" : fileName.toLowerCase(Locale.ROOT);
    }

    /**
     * MIME 兜底 & 规范化
     */
    private static String normalizeMimeType(String mimeType) {
        if (mimeType == null || mimeType.isBlank()) {
            return MediaType.OCTET_STREAM.toString();
        }

        // 去除 charset，保持业务统一
        int charsetIndex = mimeType.indexOf(';');
        if (charsetIndex > 0) {
            mimeType = mimeType.substring(0, charsetIndex);
        }

        return mimeType;
    }
}
