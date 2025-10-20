

# CreateAgent

Данные для создания AI агента

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Название агента |  |
|**description** | **String** | Описание агента |  [optional] |
|**accessType** | [**AccessTypeEnum**](#AccessTypeEnum) | Тип доступа к агенту |  |
|**modelId** | **BigDecimal** | ID модели |  |
|**tokenPackageId** | **BigDecimal** | ID пакета токенов |  |
|**settings** | [**AgentSettings**](AgentSettings.md) |  |  |
|**projectId** | **BigDecimal** | ID проекта |  [optional] |



## Enum: AccessTypeEnum

| Name | Value |
|---- | -----|
| PUBLIC | &quot;public&quot; |
| PRIVATE | &quot;private&quot; |



