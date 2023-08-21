

# DedicatedServerPreset

Выделенный сервер

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор тарифа выделенного сервера. |  |
|**description** | **String** | Описание характеристик тарифа выделенного сервера. |  |
|**isIpmiEnabled** | **Boolean** | Это логическое значение, которое показывает, доступен ли IPMI у данного тарифа. |  |
|**cpu** | [**DedicatedServerPresetCpu**](DedicatedServerPresetCpu.md) |  |  |
|**disk** | [**DedicatedServerPresetDisk**](DedicatedServerPresetDisk.md) |  |  |
|**price** | **BigDecimal** | Стоимость тарифа выделенного сервера |  [optional] |
|**memory** | [**DedicatedServerPresetMemory**](DedicatedServerPresetMemory.md) |  |  |
|**location** | [**LocationEnum**](#LocationEnum) | Локация. |  |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| RU_2 | &quot;ru-2&quot; |
| KZ_1 | &quot;kz-1&quot; |
| PL_1 | &quot;pl-1&quot; |



