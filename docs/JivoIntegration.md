

# JivoIntegration

Интеграция AI агента с Jivo

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **UUID** | ID интеграции |  |
|**accountId** | **String** | Логин аккаунта, которому принадлежит интеграция |  |
|**agentId** | **Integer** | ID агента |  |
|**accessId** | **UUID** | Access ID агента. Это токен бота для Jivo и последний сегмент адреса, на который Jivo отправляет события |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус интеграции: &#x60;pending&#x60; — ожидает подключения, &#x60;active&#x60; — работает, &#x60;error&#x60; — ошибка подключения |  |
|**createdAt** | **OffsetDateTime** | Дата и время создания |  |
|**updatedAt** | **OffsetDateTime** | Дата и время последнего обновления |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| PENDING | &quot;pending&quot; |
| ACTIVE | &quot;active&quot; |
| ERROR | &quot;error&quot; |



