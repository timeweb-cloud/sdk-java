

# Document

Документ базы знаний

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID документа |  |
|**name** | **String** | Название документа |  |
|**size** | **Integer** | Размер в байтах. Для документа по ссылке — значение заголовка &#x60;Content-Length&#x60; на момент добавления, &#x60;0&#x60;, если сервер его не вернул |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус индексации: - &#x60;new&#x60; — документ добавлен и ждёт индексации - &#x60;indexing&#x60; — индексируется - &#x60;indexed&#x60; — проиндексирован, используется в поиске - &#x60;error&#x60; — ошибка индексации, подробности в &#x60;status_info&#x60; |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип документа: &#x60;file&#x60; — загруженный файл, &#x60;link&#x60; — документ по ссылке |  |
|**indexingSchedule** | [**IndexingSchedule**](IndexingSchedule.md) |  |  |
|**indexingVersion** | **String** | Версия индексации документа |  |
|**statusInfo** | [**DocumentStatusInfo**](DocumentStatusInfo.md) |  |  |
|**link** | **String** | URL документа. Только для документов типа &#x60;link&#x60; |  [optional] |
|**createdAt** | **OffsetDateTime** | Дата и время добавления документа |  |
|**updatedAt** | **OffsetDateTime** | Дата и время последнего изменения документа |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| NEW | &quot;new&quot; |
| INDEXING | &quot;indexing&quot; |
| INDEXED | &quot;indexed&quot; |
| ERROR | &quot;error&quot; |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| FILE | &quot;file&quot; |
| LINK | &quot;link&quot; |



