# API 接口文档

## 接口说明

### POST /ingest

上传文件并进行文档处理和文本分块。

#### 请求参数

| 参数名   | 类型                  | 必填 | 说明                |
|-------|---------------------|----|-------------------|
| files | List<MultipartFile> | 是  | 要处理的文件列表，支持多种文档格式 |

#### 请求示例

```http request
POST /ingest Content-Type: multipart/form-data
files: [file1.pdf, file2.docx, file3.txt]
```

#### 响应参数

| 参数名     | 类型     | 说明          |
|---------|--------|-------------|
| code    | int    | 响应状态码，0表示成功 |
| message | string | 响应消息        |
| data    | object | 响应数据        |

##### data.chunks

| 参数名        | 类型     | 说明      |
|------------|--------|---------|
| source     | string | 源文件名    |
| fileMd5    | string | 文件MD5值  |
| chunkId    | string | 文本块唯一ID |
| chunkIndex | int    | 文本块索引   |
| chunkText  | string | 文本块内容   |
| charCount  | int    | 文本块字符数  |

##### data.summary

| 参数名        | 类型     | 说明     |
|------------|--------|--------|
| totalFiles | int    | 总文件数   |
| fileResult | object | 文件处理结果 |

###### data.summary.fileResult

| 参数名             | 类型           | 说明        |
|-----------------|--------------|-----------|
| successfulFiles | List<string> | 成功处理的文件列表 |
| failedFiles     | List<string> | 处理失败的文件列表 |

#### 响应示例

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
        "fileMd5": "a1b2c3d4d5f25s8f88sd8f8ed4f5d4fd",
        "chunkId": "0b39cbaa6cb94c839972aefc9a6099ae",
        "chunkIndex": 1,
        "chunkText": "that they need...我的第一号作品。",
        "charCount": 292
      }
    ],
    "summary": {
      "totalFiles": 3,
      "fileResult": {
        "successfulFiles": [
          "小王子.pdf",
          "小公主.pdf"
        ],
        "failedFiles": [
          "百年孤独.pdf"
        ]
      }
    }
  }
}
```