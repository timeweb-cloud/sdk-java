

# AgentSettings

Настройки агента

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**model** | **AgentModelSettings** |  |  |
|**systemPrompt** | **String** | Системный промпт (инструкция) агента |  |
|**refineQuery** | **Boolean** | Уточнять (переформулировать) запрос пользователя перед поиском по базам знаний |  |
|**memoryEnabled** | **Boolean** | Запоминать факты о каждом авторизованном пользователе и учитывать их в ответах. В виджете не работает. |  [optional] |
|**widget** | [**AgentWidgetSettings**](AgentWidgetSettings.md) |  |  [optional] |
|**webSearchAllowedSites** | **List&lt;String&gt;** | Домены, которыми ограничивается веб-поиск агента (не более 5). Каждый элемент — домен (&#x60;timeweb.ru&#x60;) или домен с путём (&#x60;timeweb.ru/docs&#x60;). Если поле не передано при обновлении, сохранённый список не меняется. |  [optional] |
|**additionalModels** | [**List&lt;AgentAdditionalModel&gt;**](AgentAdditionalModel.md) | Настройки дополнительных моделей. Заполняется автоматически из поля &#x60;additional_models&#x60; запроса создания или обновления агента, передавать здесь не нужно. |  [optional] |



