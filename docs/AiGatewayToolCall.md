

# AiGatewayToolCall

Вызов функции моделью

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID вызова. Передайте его в &#x60;tool_call_id&#x60; сообщения с результатом |  [optional] |
|**type** | [**TypeEnum**](#TypeEnum) | Тип вызова |  [optional] |
|**function** | [**AiGatewayToolCallFunction**](AiGatewayToolCallFunction.md) |  |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| FUNCTION | &quot;function&quot; |



