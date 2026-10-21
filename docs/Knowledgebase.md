

# Knowledgebase

База знаний со списком документов

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID базы знаний |  |
|**name** | **String** | Название базы знаний |  |
|**description** | **String** | Описание базы знаний |  |
|**projectId** | **Integer** | ID проекта, в котором находится база знаний. Поля нет, если проект не определён |  [optional] |
|**dbaasId** | **Integer** | ID кластера базы данных OpenSearch, который создан для базы знаний в вашем аккаунте |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус базы знаний: - &#x60;creating&#x60; — создаётся кластер базы данных, документы будут проиндексированы после перехода в &#x60;active&#x60; - &#x60;active&#x60; — база знаний работает - &#x60;blocked&#x60; — заблокирована: закончились токены или не удалось продлить подписку - &#x60;deleted&#x60; — удалена |  |
|**lastSync** | **OffsetDateTime** | Дата и время последней синхронизации. &#x60;null&#x60;, если синхронизации не было |  |
|**totalTokens** | **Integer** | Количество токенов, доступных в текущем периоде подписки (пакет подписки и докупленные пакеты) |  |
|**usedTokens** | **Integer** | Количество израсходованных токенов |  |
|**remainingTokens** | **Integer** | Количество оставшихся токенов |  |
|**tokenPackageId** | **Integer** | ID пакета токенов подписки базы знаний |  |
|**subscriptionRenewalDate** | **OffsetDateTime** | Дата следующего продления подписки (списания стоимости пакета токенов) |  |
|**documents** | [**List&lt;Document&gt;**](Document.md) | Документы базы знаний |  |
|**agentsIds** | **List&lt;Integer&gt;** | ID агентов, к которым привязана база знаний |  |
|**createdAt** | **OffsetDateTime** | Дата и время создания базы знаний |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| ACTIVE | &quot;active&quot; |
| BLOCKED | &quot;blocked&quot; |
| CREATING | &quot;creating&quot; |
| DELETED | &quot;deleted&quot; |



