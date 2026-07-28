

# ModelV3

Модель AI (v3). Идентификация и управляющие поля находятся на верхнем уровне, все сравнимые характеристики (цены, лимиты, возможности) — в словаре `parameter_values`

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор модели |  |
|**providerId** | **BigDecimal** | ID провайдера, который предоставляет модель |  |
|**name** | **String** | Название модели |  |
|**publicName** | **String** | Публичное имя модели |  |
|**modelName** | **String** | Идентификатор модели, который передаётся в поле &#x60;model&#x60; тела запроса |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип модели (llm — языковая модель, hosted-llm — self-hosted языковая модель, embedding — модель для эмбеддингов, image — генерация изображений, audio — работа с аудио, moderation — модерация контента) |  |
|**version** | **String** | Версия модели |  |
|**isDeprecated** | **Boolean** | Признак, что модель устарела |  |
|**isStopped** | **Boolean** | Признак, что поддержка модели остановлена |  |
|**deprecationDate** | **OffsetDateTime** | Дата отключения модели у провайдера. &#x60;null&#x60;, если отключение не запланировано |  [optional] |
|**createdAt** | **OffsetDateTime** | Дата и время добавления модели |  |
|**updatedAt** | **OffsetDateTime** | Дата и время последнего обновления модели |  |
|**paramsInfo** | **Object** | Информация о доступных параметрах модели с их ограничениями. Ключ — название параметра, значение — его тип, ограничения и значение по умолчанию |  [optional] |
|**parameterValues** | **Map&lt;String, Object&gt;** | Характеристики модели. Ключ — код характеристики, значение — число, строка, булево значение или массив строк.  Основные коды: - &#x60;cost_in&#x60; — цена за 1 входной токен в рублях - &#x60;cost_out&#x60; — цена за 1 выходной токен в рублях - &#x60;context_window&#x60; — размер контекстного окна в токенах - &#x60;max_output_tokens&#x60; — максимальное количество токенов в ответе - &#x60;is_reasoning&#x60; — поддержка режима рассуждений - &#x60;supports_function_calling&#x60; — поддержка function calling - &#x60;supported_input_modalities&#x60; — поддерживаемые входные модальности (например, &#x60;text&#x60;, &#x60;image&#x60;) - &#x60;supported_output_modalities&#x60; — поддерживаемые выходные модальности - &#x60;avg_speed&#x60; — средняя скорость генерации в токенах в секунду - &#x60;aa_intelligence_index&#x60;, &#x60;aa_coding_index&#x60;, &#x60;aa_agentic_index&#x60; — индексы качества Artificial Analysis - &#x60;release_date&#x60; — дата релиза модели |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| LLM | &quot;llm&quot; |
| HOSTED_LLM | &quot;hosted-llm&quot; |
| EMBEDDING | &quot;embedding&quot; |
| IMAGE | &quot;image&quot; |
| AUDIO | &quot;audio&quot; |
| MODERATION | &quot;moderation&quot; |



