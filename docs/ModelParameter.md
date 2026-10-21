

# ModelParameter

Характеристика модели из справочника

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**code** | **String** | Код характеристики — ключ в &#x60;parameter_values&#x60; модели |  |
|**name** | **String** | Название характеристики |  [optional] |
|**description** | **String** | Описание характеристики |  [optional] |
|**type** | [**TypeEnum**](#TypeEnum) | Тип значения |  |
|**unit** | **String** | Единица измерения (&#x60;tokens&#x60;, &#x60;tokens_per_second&#x60;, &#x60;currency_per_million&#x60;, ...). &#x60;null&#x60;, если нет |  |
|**group** | **String** | Группа характеристик: &#x60;pricing&#x60;, &#x60;quality&#x60;, &#x60;performance&#x60;, &#x60;capabilities&#x60;, &#x60;limits&#x60;, &#x60;meta&#x60; |  |
|**sortOrder** | **Integer** | Порядок отображения |  |
|**comparisonDirection** | [**ComparisonDirectionEnum**](#ComparisonDirectionEnum) | Какое значение лучше при сравнении моделей |  |
|**format** | [**FormatEnum**](#FormatEnum) | Подсказка для отображения значения. &#x60;currency_per_million_tokens&#x60; — значение в &#x60;parameter_values&#x60; указано за 1 токен, показывать его удобно за 1 млн токенов |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| NUMBER | &quot;number&quot; |
| BOOLEAN | &quot;boolean&quot; |
| STRING | &quot;string&quot; |
| ARRAY | &quot;array&quot; |



## Enum: ComparisonDirectionEnum

| Name | Value |
|---- | -----|
| HIGHER_IS_BETTER | &quot;higher_is_better&quot; |
| LOWER_IS_BETTER | &quot;lower_is_better&quot; |
| NONE | &quot;none&quot; |



## Enum: FormatEnum

| Name | Value |
|---- | -----|
| CURRENCY_PER_MILLION_TOKENS | &quot;currency_per_million_tokens&quot; |
| CURRENCY_PER_IMAGE | &quot;currency_per_image&quot; |
| TOKENS | &quot;tokens&quot; |
| TOKENS_PER_SECOND | &quot;tokens_per_second&quot; |
| SECONDS | &quot;seconds&quot; |
| NUMBER | &quot;number&quot; |
| BOOLEAN | &quot;boolean&quot; |
| STRING | &quot;string&quot; |
| ARRAY | &quot;array&quot; |
| DATE | &quot;date&quot; |



