

# Document

Документ в базе знаний

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор документа |  |
|**name** | **String** | Название документа |  |
|**size** | **BigDecimal** | Размер документа в байтах |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус индексации документа |  |
|**indexingVersion** | **String** | Версия индексации |  [optional] |
|**statusInfo** | [**DocumentStatusInfo**](DocumentStatusInfo.md) |  |  [optional] |
|**createdAt** | **OffsetDateTime** | Дата создания документа |  |
|**updatedAt** | **OffsetDateTime** | Дата обновления документа |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| NEW | &quot;new&quot; |
| INDEXED | &quot;indexed&quot; |
| INDEXING | &quot;indexing&quot; |
| ERROR | &quot;error&quot; |



