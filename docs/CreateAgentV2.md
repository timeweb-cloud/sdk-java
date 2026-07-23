

# CreateAgentV2

Данные для создания AI агента через API v2

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Название агента |  |
|**description** | **String** | Описание агента |  [optional] |
|**accessType** | [**AccessTypeEnum**](#AccessTypeEnum) | Тип доступа к агенту |  |
|**modelId** | **BigDecimal** | ID основной модели |  |
|**tokenLimit** | **BigDecimal** | Дневной лимит токенов для агента (0 — без лимита) |  [optional] |
|**settings** | [**AgentSettings**](AgentSettings.md) |  |  |
|**projectId** | **BigDecimal** | ID проекта |  [optional] |
|**additionalModelIds** | **List&lt;BigDecimal&gt;** | Список ID дополнительных моделей агента |  [optional] |
|**isWebSearchEnabled** | **Boolean** | Признак использования веб-поиска агентом |  [optional] |



## Enum: AccessTypeEnum

| Name | Value |
|---- | -----|
| PUBLIC | &quot;public&quot; |
| PRIVATE | &quot;private&quot; |



