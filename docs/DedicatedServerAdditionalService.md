

# DedicatedServerAdditionalService

Дополнительная услуга для выделенного сервера

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID дополнительной услуги для выделенного сервера. |  |
|**price** | **BigDecimal** | Стоимость (в рублях) дополнительной услуги для выделенного сервера. |  |
|**period** | [**PeriodEnum**](#PeriodEnum) | Период оплаты. |  |
|**description** | **String** | Описание дополнительной услуги выделенного сервера. |  |
|**shortDescription** | **String** | Краткое описание дополнительной услуги выделенного сервера. |  |
|**name** | **String** | Уникально имя дополнительной услуги выделенного сервера. |  |



## Enum: PeriodEnum

| Name | Value |
|---- | -----|
| P1D | &quot;P1D&quot; |
| P1M | &quot;P1M&quot; |
| P3M | &quot;P3M&quot; |
| P6M | &quot;P6M&quot; |
| P1Y | &quot;P1Y&quot; |
| FOREVER | &quot;forever&quot; |



