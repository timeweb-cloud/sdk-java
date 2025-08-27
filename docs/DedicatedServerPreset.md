

# DedicatedServerPreset

Выделенный сервер

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID тарифа выделенного сервера. |  |
|**description** | **String** | Описание характеристик тарифа выделенного сервера. |  |
|**isIpmiEnabled** | **Boolean** | Это логическое значение, которое показывает, доступен ли IPMI у данного тарифа. |  |
|**isPreInstalled** | **Boolean** | Это логическое значение, которое показывает, готов ли выделенный сервер к моментальной выдаче. |  |
|**cpu** | [**DedicatedServerPresetCpu**](DedicatedServerPresetCpu.md) |  |  |
|**disk** | [**DedicatedServerPresetDisk**](DedicatedServerPresetDisk.md) |  |  |
|**price** | **BigDecimal** | Стоимость тарифа выделенного сервера |  |
|**memory** | [**DedicatedServerPresetMemory**](DedicatedServerPresetMemory.md) |  |  |
|**location** | [**LocationEnum**](#LocationEnum) | Локация. |  |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| RU_2 | &quot;ru-2&quot; |
| KZ_1 | &quot;kz-1&quot; |
| PL_1 | &quot;pl-1&quot; |
| NL_1 | &quot;nl-1&quot; |
| US_2 | &quot;us-2&quot; |
| TR_1 | &quot;tr-1&quot; |
| DE_1 | &quot;de-1&quot; |
| FI_1 | &quot;fi-1&quot; |



