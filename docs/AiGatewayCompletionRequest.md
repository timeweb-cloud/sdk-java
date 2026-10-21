

# AiGatewayCompletionRequest

Запрос Completions (устаревший API OpenAI)

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**model** | **String** | ID модели из &#x60;GET /v1/models&#x60; |  |
|**prompt** | **String** | Текст или список текстов для продолжения |  |
|**maxTokens** | **Integer** | Максимум выходных токенов |  [optional] |
|**temperature** | **BigDecimal** | Температура выборки |  [optional] |
|**topP** | **BigDecimal** | Порог nucleus sampling |  [optional] |
|**stop** | **String** | Строка или список строк, на которых генерация останавливается |  [optional] |
|**stream** | **Boolean** | Отправлять ответ потоком Server-Sent Events (&#x60;text/event-stream&#x60;) |  [optional] |
|**streamOptions** | [**AiGatewayChatCompletionRequestStreamOptions**](AiGatewayChatCompletionRequestStreamOptions.md) |  |  [optional] |



