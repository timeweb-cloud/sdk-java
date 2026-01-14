

# Model

Модель AI

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор модели |  |
|**providerId** | **BigDecimal** | ID провайдера, который предоставляет модель |  |
|**name** | **String** | Название модели |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип модели (llm - языковая модель, embedding - модель для эмбеддингов) |  |
|**version** | **String** | Версия модели |  |
|**paramsInfo** | [**ModelParamsInfo**](ModelParamsInfo.md) |  |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| LLM | &quot;llm&quot; |
| EMBEDDING | &quot;embedding&quot; |



