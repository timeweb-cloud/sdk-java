

# AiGatewayChatCompletion

Ответ Chat Completions

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID ответа |  [optional] |
|**_object** | [**ObjectEnum**](#ObjectEnum) | Тип объекта |  [optional] |
|**created** | **Integer** | Время создания, Unix-время в секундах |  [optional] |
|**model** | **String** | Модель, которая ответила |  [optional] |
|**choices** | [**List&lt;AiGatewayChatCompletionChoicesInner&gt;**](AiGatewayChatCompletionChoicesInner.md) | Варианты ответа |  [optional] |
|**usage** | [**AiGatewayUsage**](AiGatewayUsage.md) |  |  [optional] |



## Enum: ObjectEnum

| Name | Value |
|---- | -----|
| CHAT_COMPLETION | &quot;chat.completion&quot; |



