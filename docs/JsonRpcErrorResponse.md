

# JsonRpcErrorResponse

Ответ JSON-RPC 2.0 с ошибкой

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**jsonrpc** | [**JsonrpcEnum**](#JsonrpcEnum) | Версия протокола JSON-RPC, всегда &#x60;2.0&#x60; |  |
|**id** | **String** | ID запроса; &#x60;null&#x60;, если запрос не удалось прочитать |  |
|**error** | [**JsonRpcError**](JsonRpcError.md) |  |  |



## Enum: JsonrpcEnum

| Name | Value |
|---- | -----|
| _2_0 | &quot;2.0&quot; |



