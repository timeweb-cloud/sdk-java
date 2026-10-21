

# AiGatewayImageGenerationRequest

Запрос генерации изображений

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**model** | **String** | ID модели генерации изображений (&#x60;mode: image_generation&#x60; в &#x60;GET /v1/models&#x60;) |  |
|**prompt** | **String** | Описание изображения |  |
|**n** | **Integer** | Количество изображений |  [optional] |
|**size** | **String** | Размер изображения |  [optional] |
|**quality** | **String** | Качество изображения |  [optional] |
|**responseFormat** | [**ResponseFormatEnum**](#ResponseFormatEnum) | Формат изображения в ответе. Поддерживается не всеми моделями |  [optional] |
|**outputFormat** | **String** | Формат файла изображения |  [optional] |



## Enum: ResponseFormatEnum

| Name | Value |
|---- | -----|
| URL | &quot;url&quot; |
| B64_JSON | &quot;b64_json&quot; |



