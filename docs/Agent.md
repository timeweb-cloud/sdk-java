

# Agent

AI Agent

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор агента |  |
|**name** | **String** | Название агента |  |
|**description** | **String** | Описание агента |  |
|**modelId** | **BigDecimal** | ID модели |  |
|**providerId** | **BigDecimal** | ID провайдера |  |
|**settings** | [**AgentSettings**](AgentSettings.md) |  |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус агента |  |
|**accessType** | [**AccessTypeEnum**](#AccessTypeEnum) | Тип доступа к агенту |  |
|**totalTokens** | **BigDecimal** | Всего токенов выделено агенту |  |
|**usedTokens** | **BigDecimal** | Использовано токенов |  |
|**remainingTokens** | **BigDecimal** | Осталось токенов |  |
|**tokenPackageId** | **BigDecimal** | ID пакета токенов |  |
|**subscriptionRenewalDate** | **OffsetDateTime** | Дата обновления подписки |  |
|**knowledgeBasesIds** | **List&lt;BigDecimal&gt;** | ID баз знаний, связанных с агентом |  |
|**accessId** | **BigDecimal** | ID доступа |  |
|**createdAt** | **OffsetDateTime** | Дата создания агента |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| ACTIVE | &quot;active&quot; |
| BLOCKED | &quot;blocked&quot; |
| DELETED | &quot;deleted&quot; |
| SUSPENDED | &quot;suspended&quot; |



## Enum: AccessTypeEnum

| Name | Value |
|---- | -----|
| PUBLIC | &quot;public&quot; |
| PRIVATE | &quot;private&quot; |



