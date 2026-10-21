

# JsonRpcResultResponse

Успешный ответ JSON-RPC 2.0. Приходит в поле `data` события `message` потока `text/event-stream`

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**jsonrpc** | [**JsonrpcEnum**](#JsonrpcEnum) | Версия протокола JSON-RPC, всегда &#x60;2.0&#x60; |  |
|**id** | **String** | ID запроса, выбранный клиентом; ответ приходит с тем же ID |  |
|**result** | [**InitializeResult**](InitializeResult.md) |  |  |



## Enum: JsonrpcEnum

| Name | Value |
|---- | -----|
| _2_0 | &quot;2.0&quot; |



