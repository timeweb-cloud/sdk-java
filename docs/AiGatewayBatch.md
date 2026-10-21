

# AiGatewayBatch

Батч

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID батча |  [optional] |
|**_object** | [**ObjectEnum**](#ObjectEnum) | Тип объекта |  [optional] |
|**endpoint** | **String** | Эндпоинт запросов батча |  [optional] |
|**model** | **String** | Модель батча. На этапе &#x60;validating&#x60; может быть &#x60;null&#x60; |  [optional] |
|**inputFileId** | **String** | ID входного файла |  [optional] |
|**outputFileId** | **String** | ID файла результатов — когда батч выполнен |  [optional] |
|**errorFileId** | **String** | ID файла с ошибками отдельных запросов, если они были |  [optional] |
|**completionWindow** | **String** | Срок выполнения |  [optional] |
|**status** | [**StatusEnum**](#StatusEnum) | Статус батча. &#x60;cancelling&#x60; — отмена выполняется, может занять несколько минут |  [optional] |
|**createdAt** | **Integer** | Время создания, Unix-время в секундах |  [optional] |
|**completedAt** | **Integer** | Время завершения, Unix-время в секундах |  [optional] |
|**expiresAt** | **Integer** | Крайний срок выполнения, Unix-время в секундах |  [optional] |
|**requestCounts** | [**AiGatewayBatchRequestCounts**](AiGatewayBatchRequestCounts.md) |  |  [optional] |
|**metadata** | **Map&lt;String, Object&gt;** | Метки батча из запроса |  [optional] |
|**usage** | **Map&lt;String, Object&gt;** | Расход токенов батча |  [optional] |



## Enum: ObjectEnum

| Name | Value |
|---- | -----|
| BATCH | &quot;batch&quot; |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| VALIDATING | &quot;validating&quot; |
| FAILED | &quot;failed&quot; |
| IN_PROGRESS | &quot;in_progress&quot; |
| FINALIZING | &quot;finalizing&quot; |
| COMPLETED | &quot;completed&quot; |
| EXPIRED | &quot;expired&quot; |
| CANCELLING | &quot;cancelling&quot; |
| CANCELLED | &quot;cancelled&quot; |



