

# PresetsDbs


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор для каждого экземпляра тарифа базы данных. |  [optional] |
|**description** | **String** | Описание тарифа. |  [optional] |
|**descriptionShort** | **String** | Краткое описание тарифа. |  [optional] |
|**cpu** | **BigDecimal** | Описание процессора тарифа. |  [optional] |
|**ram** | **BigDecimal** | Описание ОЗУ тарифа. |  [optional] |
|**disk** | **BigDecimal** | Описание диска тарифа. |  [optional] |
|**type** | [**TypeEnum**](#TypeEnum) | Тип тарифа базы данных |  [optional] |
|**price** | **BigDecimal** | Стоимость тарифа базы данных |  [optional] |
|**location** | [**LocationEnum**](#LocationEnum) | Географическое расположение тарифа. |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| MYSQL | &quot;mysql&quot; |
| MYSQL5 | &quot;mysql5&quot; |
| POSTGRES | &quot;postgres&quot; |
| REDIS | &quot;redis&quot; |
| MONGODB | &quot;mongodb&quot; |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| PL_1 | &quot;pl-1&quot; |
| KZ_1 | &quot;kz-1&quot; |



