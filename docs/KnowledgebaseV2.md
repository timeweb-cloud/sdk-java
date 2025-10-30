

# KnowledgebaseV2

База знаний (версия API v2)

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор базы знаний |  |
|**name** | **String** | Название базы знаний |  |
|**description** | **String** | Описание базы знаний |  [optional] |
|**dbaasId** | **BigDecimal** | ID базы данных opensearch |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус базы знаний |  |
|**lastSync** | **OffsetDateTime** | Дата последней синхронизации |  [optional] |
|**totalTokens** | **BigDecimal** | Всего токенов выделено |  |
|**usedTokens** | **BigDecimal** | Использовано токенов |  |
|**remainingTokens** | **BigDecimal** | Осталось токенов |  |
|**tokenPackageId** | **BigDecimal** | ID пакета токенов |  |
|**subscriptionRenewalDate** | **OffsetDateTime** | Дата обновления подписки |  |
|**documentsCount** | **BigDecimal** | Общее количество документов в базе знаний |  |
|**agentsIds** | **List&lt;BigDecimal&gt;** | ID агентов, связанных с базой знаний |  |
|**createdAt** | **OffsetDateTime** | Дата создания базы знаний |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| ACTIVE | &quot;active&quot; |
| BLOCKED | &quot;blocked&quot; |
| CREATING | &quot;creating&quot; |
| DELETED | &quot;deleted&quot; |



