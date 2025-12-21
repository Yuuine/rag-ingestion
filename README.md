# RAG Ingestion Service

RAG Ingestion Service 是一个文档处理和嵌入生成服务，用于将各种格式的文档转换为适合检索增强生成（RAG）系统使用的文本块。

## 功能特性

- 支持多种文档格式解析（PDF、TXT、Markdown、DOCX等）
- 智能文本分块处理(多种分块策略)
- 返回结构化 Chunk 列表（JSON）

## API接口

### 输入 POST /ingest

```http
POST /ingest
Content-Type: multipart/form-data

@RequestParam("files") List<MultipartFile>
```

### 响应

示例：
```json
{
  "code": 0,
  "message": "success",
  "data": {
    "chunks": [
      {
        "source": "小王子.pdf",
        "fileMd5": "cee977041098e8ec37160e49ec6ffe7c",
        "chunkId": "010301cb37f64b19bf5cee93cea00b81",
        "chunkIndex": 0,
        "chunkText": "小王子的星球是哪颗？... far away",
        "charCount": 275
      },
      {
        "source": "小公主.pdf",
        "fileMd5": "a1b2c3d4...",
        "chunkId": "0b39cbaa6cb94c839972aefc9a6099ae",
        "chunkIndex": 1,
        "chunkText": "that they need...我的第一号作品。",
        "charCount": 292
      }
    ],
    "summary": {
      "totalFiles": 3,
        "fileResult": {
          "successfulFiles": ["小王子.pdf", "小公主.pdf"],
          "failedFiles": ["百年孤独.pdf"]
      }
    }
  }
}
```

## 致谢

- 通用文档检测与提取库 [Apache Tika](https://github.com/apache/tika)
- PDF文档处理库 [Apache PDFBox](https://github.com/apache/pdfbox)
- Microsoft 文档处理库 [Apache POI](https://github.com/apache/poi)
- OCR 光学字符识别库 [Tesseract](https://github.com/tesseract-ocr/tesseract)