

# CreateTool

Данные для подключения собственного MCP-сервера

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Название инструмента |  |
|**description** | **String** | Описание инструмента |  [optional] |
|**type** | [**TypeEnum**](#TypeEnum) | Тип инструмента. Поддерживается только &#x60;MCP&#x60; |  |
|**protocol** | [**ProtocolEnum**](#ProtocolEnum) | Транспорт MCP: &#x60;http&#x60; — Streamable HTTP, &#x60;sse&#x60; — Server-Sent Events |  |
|**url** | **String** | Адрес MCP-сервера. Сервер должен быть доступен из интернета |  |
|**authData** | [**ToolAuthData**](ToolAuthData.md) |  |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| MCP | &quot;MCP&quot; |



## Enum: ProtocolEnum

| Name | Value |
|---- | -----|
| HTTP | &quot;http&quot; |
| SSE | &quot;sse&quot; |



