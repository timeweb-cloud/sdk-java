

# ToolConfigField

Поле настройки шаблона: что нужно передать в `config_values` при установке

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**key** | **String** | Ключ поля в &#x60;config_values&#x60; |  |
|**label** | **String** | Название поля для пользователя |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип значения |  |
|**required** | **Boolean** | Поле обязательно |  |
|**description** | **String** | Подсказка к полю |  [optional] |
|**placeholder** | **String** | Пример значения |  [optional] |
|**headerName** | **String** | HTTP-заголовок, в котором значение передаётся MCP-серверу |  [optional] |
|**prefix** | **String** | Префикс, который добавляется перед значением в заголовке |  [optional] |
|**options** | **List&lt;String&gt;** | Допустимые значения (для &#x60;select&#x60;) |  [optional] |
|**_default** | **String** | Значение по умолчанию |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| STRING | &quot;string&quot; |
| SECRET | &quot;secret&quot; |
| NUMBER | &quot;number&quot; |
| BOOLEAN | &quot;boolean&quot; |
| SELECT | &quot;select&quot; |



