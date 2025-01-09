

# PresetsDbs


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID для каждого экземпляра тарифа базы данных. |  [optional] |
|**description** | **String** | Описание тарифа. |  [optional] |
|**descriptionShort** | **String** | Краткое описание тарифа. |  [optional] |
|**cpu** | **BigDecimal** | Описание процессора тарифа. |  [optional] |
|**ram** | **BigDecimal** | Описание ОЗУ тарифа. |  [optional] |
|**disk** | **BigDecimal** | Описание диска тарифа. |  [optional] |
|**type** | **DbType** |  |  [optional] |
|**price** | **BigDecimal** | Стоимость тарифа базы данных |  [optional] |
|**location** | [**LocationEnum**](#LocationEnum) | Географическое расположение тарифа. |  [optional] |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| PL_1 | &quot;pl-1&quot; |
| KZ_1 | &quot;kz-1&quot; |



