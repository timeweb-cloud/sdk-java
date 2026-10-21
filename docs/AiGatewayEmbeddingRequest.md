

# AiGatewayEmbeddingRequest

Запрос эмбеддингов

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**model** | **String** | ID модели эмбеддингов (&#x60;mode: embedding&#x60; в &#x60;GET /v1/models&#x60;) |  |
|**input** | **String** | Текст или список текстов |  |
|**dimensions** | **Integer** | Размерность вектора — для моделей, которые её поддерживают |  [optional] |
|**encodingFormat** | [**EncodingFormatEnum**](#EncodingFormatEnum) | Формат векторов |  [optional] |



## Enum: EncodingFormatEnum

| Name | Value |
|---- | -----|
| FLOAT | &quot;float&quot; |
| BASE64 | &quot;base64&quot; |



