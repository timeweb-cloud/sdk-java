

# JsonRpcNotification

Уведомление JSON-RPC 2.0: без `id`, ответа на него нет. Принимается с HTTP 202

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**jsonrpc** | [**JsonrpcEnum**](#JsonrpcEnum) | Версия протокола JSON-RPC, всегда &#x60;2.0&#x60; |  |
|**method** | **String** | Метод уведомления |  |
|**params** | **Map&lt;String, Object&gt;** | Параметры уведомления |  [optional] |



## Enum: JsonrpcEnum

| Name | Value |
|---- | -----|
| _2_0 | &quot;2.0&quot; |



