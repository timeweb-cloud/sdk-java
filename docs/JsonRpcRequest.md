

# JsonRpcRequest

Запрос JSON-RPC 2.0. Форма `params` зависит от `method`

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**jsonrpc** | [**JsonrpcEnum**](#JsonrpcEnum) | Версия протокола JSON-RPC, всегда &#x60;2.0&#x60; |  |
|**id** | **String** | ID запроса, выбранный клиентом; ответ приходит с тем же ID |  |
|**method** | [**MethodEnum**](#MethodEnum) | Метод MCP |  |
|**params** | [**InitializeParams**](InitializeParams.md) |  |  [optional] |



## Enum: JsonrpcEnum

| Name | Value |
|---- | -----|
| _2_0 | &quot;2.0&quot; |



## Enum: MethodEnum

| Name | Value |
|---- | -----|
| INITIALIZE | &quot;initialize&quot; |
| PING | &quot;ping&quot; |
| TOOLS_LIST | &quot;tools/list&quot; |
| TOOLS_CALL | &quot;tools/call&quot; |
| RESOURCES_LIST | &quot;resources/list&quot; |
| RESOURCES_READ | &quot;resources/read&quot; |



