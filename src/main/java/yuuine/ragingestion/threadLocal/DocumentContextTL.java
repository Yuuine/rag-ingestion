package yuuine.ragingestion.threadLocal;

import yuuine.ragingestion.domain.models.DocumentProcessingContext;

public class DocumentContextTL {

    private static final ThreadLocal<DocumentProcessingContext> CONTEXT = new ThreadLocal<>();

    public static void set(DocumentProcessingContext context) {
        CONTEXT.set(context);
    }

    public static DocumentProcessingContext get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

}