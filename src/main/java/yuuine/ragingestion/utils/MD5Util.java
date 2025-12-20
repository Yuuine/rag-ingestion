package yuuine.ragingestion.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
@Component
public class MD5Util {

    public String md5(byte[] fileBytes) {
        InputStream inputStream = new ByteArrayInputStream(fileBytes);
        String md5;
        try {
            md5 = DigestUtils.md5DigestAsHex(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return md5;
    }
}
