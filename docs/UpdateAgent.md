

# UpdateAgent

Данные для обновления AI агента

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Название агента |  [optional] |
|**description** | **String** | Описание агента |  [optional] |
|**accessType** | [**AccessTypeEnum**](#AccessTypeEnum) | Тип доступа к агенту |  [optional] |
|**status** | [**StatusEnum**](#StatusEnum) | Статус агента |  [optional] |
|**tokenPackageId** | **BigDecimal** | ID пакета токенов |  [optional] |
|**settings** | [**UpdateAgentSettings**](UpdateAgentSettings.md) |  |  [optional] |
|**projectId** | **BigDecimal** | ID проекта |  [optional] |



## Enum: AccessTypeEnum

| Name | Value |
|---- | -----|
| PUBLIC | &quot;public&quot; |
| PRIVATE | &quot;private&quot; |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| ACTIVE | &quot;active&quot; |
| SUSPENDED | &quot;suspended&quot; |



