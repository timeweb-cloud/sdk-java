

# AiGatewayChatCompletionChunk

Событие `data:` потокового ответа. Поток завершается строкой `data: [DONE]`

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID ответа |  [optional] |
|**_object** | [**ObjectEnum**](#ObjectEnum) | Тип объекта |  [optional] |
|**created** | **Integer** | Время создания, Unix-время в секундах |  [optional] |
|**model** | **String** | Модель, которая отвечает |  [optional] |
|**choices** | [**List&lt;AiGatewayChatCompletionChunkChoicesInner&gt;**](AiGatewayChatCompletionChunkChoicesInner.md) | Части вариантов ответа |  [optional] |
|**usage** | [**AiGatewayUsage**](AiGatewayUsage.md) |  |  [optional] |



## Enum: ObjectEnum

| Name | Value |
|---- | -----|
| CHAT_COMPLETION_CHUNK | &quot;chat.completion.chunk&quot; |



