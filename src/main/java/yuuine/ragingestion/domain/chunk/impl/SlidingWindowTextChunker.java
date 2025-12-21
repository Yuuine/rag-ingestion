package yuuine.ragingestion.domain.chunk.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yuuine.ragingestion.domain.chunk.Chunk;
import yuuine.ragingestion.domain.chunk.TextChunker;
import yuuine.ragingestion.utils.UUIDUtil; // 假设你有此工具类

import java.util.function.Consumer;

/**
 * 滑动窗口文本分块器实现。
 */
@Slf4j
@Service
public class SlidingWindowTextChunker implements TextChunker {

    /**
     * 单个 chunk 目标字符数（最大）
     */
    private static final int CHUNK_SIZE = 300;

    /**
     * 相邻 chunk 的固定重叠字符数
     */
    private static final int OVERLAP = 50;

    /**
     * 流式生成 chunk，通过 consumer 处理。
     * 此方法不返回List，而是通过回调函数将每个chunk传出。
     *
     * @param text          待处理的原始文本
     * @param chunkConsumer 处理每个生成的chunk的消费者
     */
    @Override
    public void chunkStream(String text, Consumer<Chunk> chunkConsumer) {
        if (text == null || text.isBlank()) {
            log.debug("Input text is null or blank, skipping chunking.");
            return;
        }

        int textLength = text.length();
        int start = 0;
        int index = 0;

        while (start < textLength) {
            int idealEnd = Math.min(start + CHUNK_SIZE, textLength);

            // 寻找自然边界
            int adjustedEnd = adjustToNaturalBoundary(text, start, idealEnd);

            // 如果调整后端点太靠近 start，则使用 idealEnd
            if (adjustedEnd - start < CHUNK_SIZE - OVERLAP) {
                adjustedEnd = idealEnd;
            }

            String chunkText = text.substring(start, adjustedEnd);

            if (!chunkText.isBlank()) {
                // 创建领域模型 Chunk 对象
                Chunk chunk = new Chunk();
                chunk.setChunkId(UUIDUtil.UUIDGenerate()); // 生成唯一ID
                chunk.setChunkIndex(index++); // 设置索引
                chunk.setChunkText(chunkText); // 设置文本
                chunk.setCharCount(chunkText.codePointCount(0, chunkText.length())); // 计算字符数

                // 通过回调函数传出
                chunkConsumer.accept(chunk);
            } else {
                log.debug("Generated empty chunk at index {}, skipping.", index);
            }

            // 固定步长前进
            start += (CHUNK_SIZE - OVERLAP);
            if (start >= textLength) {
                break;
            }
        }
        log.debug("Finished chunking text of length {} into {} chunks.", textLength, index);
    }

    /**
     * 在指定范围内寻找最后一个自然句子边界。
     * 搜索范围：从 idealEnd 往前，直到 start + (CHUNK_SIZE - OVERLAP)
     */
    private int adjustToNaturalBoundary(String text, int start, int idealEnd) {
        int minEnd = start + (CHUNK_SIZE - OVERLAP);
        if (minEnd >= idealEnd) {
            return idealEnd; // 如果范围无效，则不调整
        }

        // 扩展边界字符集
        String boundaries = "\n。.!?！？";

        for (int i = idealEnd; i > minEnd; i--) {
            char c = text.charAt(i - 1);
            if (boundaries.indexOf(c) != -1) {
                return i; // 在边界字符之后截断
            }
        }
        return idealEnd; // 未找到合适边界
    }
}