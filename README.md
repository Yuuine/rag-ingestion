# RAG Ingestion Service

RAG Ingestion Service 是一个文档处理和嵌入生成服务，用于将各种格式的文档转换为适合检索增强生成（RAG）系统使用的文本块。

## 功能特性

- 支持多种文档格式解析（PDF、TXT、Markdown、DOCX等）
- 智能文本分块处理(多种分块策略)
- 基于LangChain4j的递归文本分块器
- 返回结构化 Chunk 列表（JSON）

## 配置

```yaml
chunker:
  chunk-size: 500   # 每个文本块的最大字符数 
  overlap: 50       # 相邻文本块之间的重叠字符数
```

## 接口文档

完整 API 说明请参阅：[API 文档](./docs/api.md)

## 致谢

- 通用文档检测与提取库 [Apache Tika](https://github.com/apache/tika)
- PDF文档处理库 [Apache PDFBox](https://github.com/apache/pdfbox)
- Microsoft 文档处理库 [Apache POI](https://github.com/apache/poi)
- OCR 光学字符识别库 [Tesseract](https://github.com/tesseract-ocr/tesseract)