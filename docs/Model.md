

# Model

AI модель

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID модели |  |
|**providerId** | **Integer** | ID провайдера модели |  |
|**name** | **String** | Семейство модели |  |
|**publicName** | **String** | Отображаемое название модели |  |
|**modelName** | **String** | Идентификатор модели с префиксом провайдера — передаётся в поле &#x60;model&#x60; запросов к AI Gateway |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип модели: - &#x60;llm&#x60; — языковая модель - &#x60;hosted-llm&#x60; — языковая модель, размещённая в Timeweb Cloud - &#x60;embedding&#x60; — модель эмбеддингов - &#x60;image&#x60; — генерация изображений - &#x60;audio&#x60; — работа с аудио - &#x60;video&#x60; — генерация видео - &#x60;moderation&#x60; — модерация контента - &#x60;system-one&#x60; — служебная модель с собственным API |  |
|**version** | **String** | Версия модели |  |
|**isDeprecated** | **Boolean** | Модель устарела и недоступна для выбора в новых агентах |  |
|**isStopped** | **Boolean** | Поддержка модели остановлена: запросы к ней не выполняются |  |
|**deprecationDate** | **OffsetDateTime** | Дата отключения модели у провайдера. &#x60;null&#x60;, если отключение не запланировано |  |
|**isReasoning** | **Boolean** | Модель поддерживает режим рассуждений |  |
|**paramsInfo** | [**Map&lt;String, ModelParamInfo&gt;**](ModelParamInfo.md) | Параметры генерации, которые можно настроить у агента с этой моделью. Ключ — имя параметра (&#x60;temperature&#x60;, &#x60;top_p&#x60;, &#x60;max_tokens&#x60;, &#x60;reasoning_effort&#x60; и т.д.). &#x60;null&#x60;, если параметры не заданы |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| LLM | &quot;llm&quot; |
| HOSTED_LLM | &quot;hosted-llm&quot; |
| EMBEDDING | &quot;embedding&quot; |
| IMAGE | &quot;image&quot; |
| AUDIO | &quot;audio&quot; |
| VIDEO | &quot;video&quot; |
| MODERATION | &quot;moderation&quot; |
| SYSTEM_ONE | &quot;system-one&quot; |



