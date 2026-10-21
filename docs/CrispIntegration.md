

# CrispIntegration

Интеграция AI агента с Crisp

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **UUID** | ID интеграции |  |
|**accountId** | **String** | Логин аккаунта, которому принадлежит интеграция |  |
|**agentId** | **Integer** | ID агента |  |
|**websiteId** | **UUID** | Website ID сайта в Crisp |  |
|**webhookSecret** | **String** | Секрет вебхука — последний сегмент адреса вебхука, который нужно добавить в Crisp: &#x60;https://api.timeweb.cloud/api/v1/crisp/{website_id}/webhook/{webhook_secret}&#x60; |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус интеграции: &#x60;pending&#x60; — ожидает подключения, &#x60;active&#x60; — работает, &#x60;error&#x60; — ошибка подключения |  |
|**createdAt** | **OffsetDateTime** | Дата и время создания |  |
|**updatedAt** | **OffsetDateTime** | Дата и время последнего обновления |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| PENDING | &quot;pending&quot; |
| ACTIVE | &quot;active&quot; |
| ERROR | &quot;error&quot; |



