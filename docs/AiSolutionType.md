

# AiSolutionType

Тип AI-решения

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) | Тип решения |  |
|**name** | **String** | Название решения |  |
|**description** | **String** | Описание решения в HTML |  |
|**osIds** | **List&lt;Integer&gt;** | ID операционной системы, на которой работает решение (один элемент). Пустой массив, если этой ОС нет в каталоге ОС аккаунта |  |
|**installations** | **Integer** | Количество установок. Всегда &#x60;0&#x60;, оставлено для обратной совместимости |  |
|**requirements** | [**AiSolutionTypeRequirements**](AiSolutionTypeRequirements.md) |  |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| OPENCLAW | &quot;openclaw&quot; |



