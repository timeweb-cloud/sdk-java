

# DatabaseConfigurator

Конфигуратор кластера базы данных — произвольная конфигурация ресурсов вместо готового тарифа

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID конфигуратора. Передаётся при создании кластера в поле &#x60;configurator_id&#x60;. |  |
|**diskType** | [**DiskTypeEnum**](#DiskTypeEnum) | Тип диска. |  |
|**cpuFrequency** | **String** | Частота процессора (в ГГц). |  |
|**isAllowedLocalNetwork** | **Boolean** | Есть возможность подключения локальной сети. |  |
|**location** | [**LocationEnum**](#LocationEnum) | Географическое расположение конфигуратора. |  |
|**requirements** | [**DatabaseConfiguratorRequirements**](DatabaseConfiguratorRequirements.md) |  |  |
|**prices** | [**DatabaseConfiguratorPrices**](DatabaseConfiguratorPrices.md) |  |  [optional] |
|**tags** | **List&lt;String&gt;** | Теги конфигуратора, в том числе тег группы, в пределах которой доступна смена конфигурации кластера. |  |



## Enum: DiskTypeEnum

| Name | Value |
|---- | -----|
| SSD | &quot;ssd&quot; |
| NVME | &quot;nvme&quot; |
| HDD | &quot;hdd&quot; |



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



