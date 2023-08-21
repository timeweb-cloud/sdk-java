

# MasterPresetOutApi


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | Уникальный идентификатор тарифа |  |
|**description** | **String** | Описание тарифа |  |
|**descriptionShort** | **String** | Краткое описание тарифа |  |
|**price** | **BigDecimal** | Стоимость |  |
|**cpu** | **Integer** | Количество ядер ноды |  |
|**ram** | **Integer** | Количество памяти ноды |  |
|**disk** | **Integer** | Количество пространства на ноде |  |
|**network** | **Integer** | Пропускная способность ноды |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип тарифа |  [optional] |
|**limit** | **Integer** | Ограничение по количеству воркер-нод в кластере |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| MASTER | &quot;master&quot; |



