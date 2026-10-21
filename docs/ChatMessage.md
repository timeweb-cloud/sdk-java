

# ChatMessage

Сообщение чата агента

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID сообщения |  |
|**messageId** | **String** | Строковый идентификатор сообщения. Есть не у всех сообщений. |  [optional] |
|**role** | [**RoleEnum**](#RoleEnum) | Автор сообщения |  |
|**createdAt** | **OffsetDateTime** | Дата создания сообщения |  |
|**text** | **String** | Текст сообщения |  |
|**isGood** | **Boolean** | Оценка ответа: &#x60;true&#x60; — понравился, &#x60;false&#x60; — не понравился, &#x60;null&#x60; — без оценки |  |
|**uptimeMs** | **Integer** | Время до начала ответа агента в миллисекундах (для ответов агента) |  |
|**_file** | [**ChatMessageFile**](ChatMessageFile.md) |  |  |



## Enum: RoleEnum

| Name | Value |
|---- | -----|
| USER | &quot;user&quot; |
| ASSISTANT | &quot;assistant&quot; |
| SYSTEM | &quot;system&quot; |



