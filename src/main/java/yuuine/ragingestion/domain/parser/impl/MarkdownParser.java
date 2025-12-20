package yuuine.ragingestion.domain.parser.impl;


import org.springframework.stereotype.Component;
import yuuine.ragingestion.domain.models.DocumentProcessingContext;
import yuuine.ragingestion.domain.parser.DocumentParser;

import java.util.List;

@Component
public class MarkdownParser implements DocumentParser {
    @Override
    public List<String> supportedMimeTypes() {
        return List.of("text/markdown");
    }

    @Override
    public String parse(DocumentProcessingContext context) {
        return "markdown";
    }
}
