

# PresetsStorage

Тариф

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID для каждого экземпляра тарифа хранилища. |  |
|**description** | **String** | Описание тарифа. |  |
|**descriptionShort** | **String** | Краткое описание тарифа. |  |
|**disk** | **BigDecimal** | Описание диска хранилища. |  |
|**price** | **BigDecimal** | Стоимость тарифа хранилища. |  |
|**location** | [**LocationEnum**](#LocationEnum) | Географическое расположение тарифа. |  |
|**tags** | **List&lt;String&gt;** | Теги тарифа. |  |
|**storageClass** | [**StorageClassEnum**](#StorageClassEnum) | Класс хранилища. |  |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |



## Enum: StorageClassEnum

| Name | Value |
|---- | -----|
| COLD | &quot;cold&quot; |
| HOT | &quot;hot&quot; |



