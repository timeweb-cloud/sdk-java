

# ModelParamInfo

Настраиваемый параметр генерации

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) | Тип значения параметра |  |
|**description** | **String** | Описание параметра (на английском) |  |
|**min** | **BigDecimal** | Минимальное значение, для &#x60;type: number&#x60; |  [optional] |
|**max** | **BigDecimal** | Максимальное значение, для &#x60;type: number&#x60; |  [optional] |
|**_enum** | **List&lt;String&gt;** | Допустимые значения, для &#x60;type: enum&#x60; |  [optional] |
|**_default** | **BigDecimal** | Значение по умолчанию |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| NUMBER | &quot;number&quot; |
| ENUM | &quot;enum&quot; |
| BOOLEAN | &quot;boolean&quot; |



