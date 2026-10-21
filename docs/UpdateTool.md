

# UpdateTool

Изменяемые поля инструмента. Незаданные поля не меняются

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Название инструмента |  [optional] |
|**description** | **String** | Описание инструмента |  [optional] |
|**type** | [**TypeEnum**](#TypeEnum) | Тип инструмента |  [optional] |
|**protocol** | [**ProtocolEnum**](#ProtocolEnum) | Транспорт MCP |  [optional] |
|**url** | **String** | Адрес MCP-сервера. Список методов при смене адреса не обновляется — вызовите обновление методов |  [optional] |
|**authData** | [**ToolAuthData**](ToolAuthData.md) |  |  [optional] |
|**status** | [**StatusEnum**](#StatusEnum) | Статус инструмента. Чтобы временно выключить инструмент у всех агентов, передайте &#x60;inactive&#x60;, чтобы включить — &#x60;active&#x60; |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| MCP | &quot;MCP&quot; |



## Enum: ProtocolEnum

| Name | Value |
|---- | -----|
| HTTP | &quot;http&quot; |
| SSE | &quot;sse&quot; |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| ACTIVE | &quot;active&quot; |
| INACTIVE | &quot;inactive&quot; |
| PENDING_AUTH | &quot;pending_auth&quot; |



