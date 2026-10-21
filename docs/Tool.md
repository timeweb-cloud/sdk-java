

# Tool

MCP-инструмент аккаунта

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID инструмента |  |
|**accountId** | **String** | Логин аккаунта-владельца |  |
|**templateId** | **Integer** | ID шаблона маркетплейса, из которого установлен инструмент. &#x60;null&#x60; — собственный MCP-сервер |  |
|**key** | **String** | Ключ шаблона маркетплейса. Сейчас в ответах не возвращается |  [optional] |
|**name** | **String** | Название инструмента |  |
|**description** | **String** | Описание инструмента |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип инструмента. У инструментов из шаблонов может быть &#x60;null&#x60; |  |
|**protocol** | [**ProtocolEnum**](#ProtocolEnum) | Транспорт MCP: &#x60;http&#x60; — Streamable HTTP, &#x60;sse&#x60; — Server-Sent Events. У инструментов из шаблонов всегда &#x60;null&#x60; |  |
|**url** | **String** | Адрес MCP-сервера. У инструментов из шаблонов всегда &#x60;null&#x60; |  |
|**methods** | [**List&lt;ToolMethod&gt;**](ToolMethod.md) | Методы MCP-сервера. &#x60;null&#x60; или пустой массив — методы ещё не получены, например до завершения OAuth2-авторизации |  |
|**authData** | [**ToolAuthData**](ToolAuthData.md) |  |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус инструмента: &#x60;active&#x60; — работает, &#x60;inactive&#x60; — выключен вручную, &#x60;pending_auth&#x60; — ожидает OAuth2-авторизации, &#x60;error&#x60; — последнее обновление методов завершилось ошибкой (причина в &#x60;status_info&#x60;). Агент вызывает только инструменты в статусе &#x60;active&#x60; |  |
|**statusInfo** | **String** | Причина статуса &#x60;error&#x60; |  |
|**createdAt** | **OffsetDateTime** | Дата и время создания |  |
|**updatedAt** | **OffsetDateTime** | Дата и время последнего изменения |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| MCP | &quot;MCP&quot; |
| NULL | &quot;null&quot; |



## Enum: ProtocolEnum

| Name | Value |
|---- | -----|
| HTTP | &quot;http&quot; |
| SSE | &quot;sse&quot; |
| NULL | &quot;null&quot; |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| ACTIVE | &quot;active&quot; |
| INACTIVE | &quot;inactive&quot; |
| PENDING_AUTH | &quot;pending_auth&quot; |
| ERROR | &quot;error&quot; |



