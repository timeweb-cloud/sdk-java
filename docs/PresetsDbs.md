

# PresetsDbs


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID для каждого экземпляра тарифа базы данных. |  [optional] |
|**description** | **String** | Описание тарифа. |  [optional] |
|**descriptionShort** | **String** | Краткое описание тарифа. |  [optional] |
|**cpu** | **BigDecimal** | Количество ядер процессора тарифа. |  [optional] |
|**cpuFrequency** | **String** | Частота процессора (в ГГц). |  [optional] |
|**ram** | **BigDecimal** | Объём оперативной памяти тарифа (в Мб). |  [optional] |
|**disk** | **BigDecimal** | Размер диска тарифа (в Мб). |  [optional] |
|**type** | [**TypeEnum**](#TypeEnum) | Семейство СУБД тарифа. Значение не совпадает с типом кластера, который передаётся в поле &#x60;type&#x60; при создании кластера (&#x60;POST /api/v1/databases&#x60;): там используется версионированный тип, например &#x60;postgres17&#x60;. Тарифы для Valkey возвращаются со значением &#x60;redis&#x60; — отдельного значения &#x60;valkey&#x60; в этом поле не бывает. |  [optional] |
|**price** | **BigDecimal** | Стоимость тарифа базы данных |  [optional] |
|**location** | [**LocationEnum**](#LocationEnum) | Географическое расположение тарифа. |  [optional] |
|**tags** | **List&lt;String&gt;** | Теги тарифа, в том числе тег группы тарифов, в пределах которой доступна смена тарифа. |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| MYSQL | &quot;mysql&quot; |
| MYSQL5 | &quot;mysql5&quot; |
| POSTGRES | &quot;postgres&quot; |
| REDIS | &quot;redis&quot; |
| MONGODB | &quot;mongodb&quot; |
| OPENSEARCH | &quot;opensearch&quot; |
| CLICKHOUSE | &quot;clickhouse&quot; |
| KAFKA | &quot;kafka&quot; |
| RABBITMQ | &quot;rabbitmq&quot; |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| RU_3 | &quot;ru-3&quot; |
| PL_1 | &quot;pl-1&quot; |
| NL_1 | &quot;nl-1&quot; |
| DE_1 | &quot;de-1&quot; |
| US_2 | &quot;us-2&quot; |
| US_3 | &quot;us-3&quot; |



