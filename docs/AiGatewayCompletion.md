

# AiGatewayCompletion

Ответ Completions

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID ответа |  [optional] |
|**_object** | [**ObjectEnum**](#ObjectEnum) | Тип объекта |  [optional] |
|**created** | **Integer** | Время создания, Unix-время в секундах |  [optional] |
|**model** | **String** | Модель, которая ответила |  [optional] |
|**choices** | [**List&lt;AiGatewayCompletionChoicesInner&gt;**](AiGatewayCompletionChoicesInner.md) | Варианты продолжения |  [optional] |
|**usage** | [**AiGatewayUsage**](AiGatewayUsage.md) |  |  [optional] |



## Enum: ObjectEnum

| Name | Value |
|---- | -----|
| TEXT_COMPLETION | &quot;text_completion&quot; |



