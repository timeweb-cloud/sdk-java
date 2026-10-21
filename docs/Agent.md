

# Agent

AI агент

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID агента |  |
|**name** | **String** | Название агента |  |
|**description** | **String** | Описание агента |  |
|**projectId** | **Integer** | ID проекта, в котором находится агент |  [optional] |
|**modelId** | **Integer** | ID основной модели агента |  |
|**providerId** | **Integer** | ID провайдера модели |  |
|**settings** | [**AgentSettings**](AgentSettings.md) |  |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус агента: - &#x60;active&#x60; — работает; - &#x60;suspended&#x60; — приостановлен владельцем; - &#x60;blocked&#x60; — заблокирован (для агентов с пакетом токенов — закончились токены или подписка); - &#x60;deleted&#x60; — удалён. |  |
|**accessType** | [**AccessTypeEnum**](#AccessTypeEnum) | Тип доступа: &#x60;public&#x60; — агент отвечает анонимным посетителям сайта через виджет; &#x60;private&#x60; — только по токену аккаунта. |  |
|**accessId** | **UUID** | Идентификатор доступа агента. Используется для вызова агента из своих приложений через OpenAI-совместимое API (см. https://agent.timeweb.cloud/docs) и для встраивания виджета. |  |
|**tokenLimit** | **Integer** | Устарело. Дневной лимит агента в токенах без привязки к модели, если он задан; &#x60;0&#x60; — такого лимита нет. Лимиты в деньгах, по моделям и за другие периоды здесь не отражаются — используйте лимиты агента (&#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;). |  |
|**totalTokens** | **Integer** | Всего токенов в пакете. Только для агентов с пакетом токенов (устаревшая схема оплаты). |  [optional] |
|**usedTokens** | **Integer** | Использовано токенов пакета. Для агентов с поресурсной оплатой — &#x60;null&#x60;. |  |
|**remainingTokens** | **Integer** | Осталось токенов в пакете. Только для агентов с пакетом токенов. |  [optional] |
|**tokenPackageId** | **Integer** | ID пакета токенов. Только для агентов с пакетом токенов; у агентов с поресурсной оплатой поле отсутствует. |  [optional] |
|**subscriptionRenewalDate** | **OffsetDateTime** | Дата продления подписки на пакет токенов. Только для агентов с пакетом токенов. |  [optional] |
|**autoBuyAdditionalTokens** | **Boolean** | Включена ли автоматическая докупка пакетов токенов |  |
|**tools** | [**List&lt;AgentToolsInner&gt;**](AgentToolsInner.md) | Инструменты, подключённые к агенту |  |
|**additionalModelIds** | **List&lt;Integer&gt;** | ID дополнительных моделей агента |  |
|**additionalModels** | [**List&lt;AgentAdditionalModel&gt;**](AgentAdditionalModel.md) | Дополнительные модели агента с настройками |  |
|**isWebSearchEnabled** | **Boolean** | Включён ли веб-поиск |  |
|**aiGatewayKeyId** | **UUID** | ID ключа AI Gateway, к которому привязан агент. &#x60;null&#x60; — агента можно привязать к ключу (см. обновление агента); поле отсутствует — привязка недоступна. |  [optional] |
|**createdAt** | **OffsetDateTime** | Дата создания агента |  |
|**knowledgeBasesIds** | **List&lt;Integer&gt;** | ID баз знаний, подключённых к агенту |  |



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



