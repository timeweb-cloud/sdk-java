

# AiGatewayChatCompletionRequest

Запрос Chat Completions в формате OpenAI. Перечислены основные поля, остальные параметры OpenAI передаются модели как есть. Параметры, которые модель не поддерживает, могут быть отклонены с ошибкой 400

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**model** | **String** | ID модели из &#x60;GET /v1/models&#x60; |  |
|**messages** | **List&lt;AiGatewayChatMessage&gt;** | История диалога целиком |  |
|**stream** | **Boolean** | Отправлять ответ потоком Server-Sent Events (&#x60;text/event-stream&#x60;) |  [optional] |
|**streamOptions** | [**AiGatewayChatCompletionRequestStreamOptions**](AiGatewayChatCompletionRequestStreamOptions.md) |  |  [optional] |
|**maxCompletionTokens** | **Integer** | Максимум выходных токенов, включая рассуждения |  [optional] |
|**maxTokens** | **Integer** | Устаревший синоним &#x60;max_completion_tokens&#x60; |  [optional] |
|**temperature** | **BigDecimal** | Температура выборки |  [optional] |
|**topP** | **BigDecimal** | Порог nucleus sampling |  [optional] |
|**n** | **Integer** | Количество вариантов ответа |  [optional] |
|**stop** | **String** | Строка или список строк, на которых генерация останавливается |  [optional] |
|**tools** | **List&lt;Map&lt;String, Object&gt;&gt;** | Функции, которые может вызвать модель |  [optional] |
|**toolChoice** | [**ToolChoiceEnum**](#ToolChoiceEnum) | Режим вызова функций: &#x60;none&#x60;, &#x60;auto&#x60;, &#x60;required&#x60; или объект с конкретной функцией |  [optional] |
|**responseFormat** | **Map&lt;String, Object&gt;** | Формат ответа: &#x60;{\&quot;type\&quot;: \&quot;json_object\&quot;}&#x60; или &#x60;{\&quot;type\&quot;: \&quot;json_schema\&quot;, \&quot;json_schema\&quot;: {...}}&#x60; |  [optional] |
|**reasoningEffort** | **String** | Уровень рассуждений для моделей с рассуждениями, например &#x60;low&#x60;, &#x60;high&#x60; |  [optional] |
|**user** | **String** | Идентификатор вашего конечного пользователя |  [optional] |



## Enum: ToolChoiceEnum

| Name | Value |
|---- | -----|
| NONE | &quot;none&quot; |
| AUTO | &quot;auto&quot; |
| REQUIRED | &quot;required&quot; |



