

# CreateDedicatedServer


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**planId** | **BigDecimal** | ID списка дополнительных услуг выделенного сервера. |  [optional] |
|**presetId** | **BigDecimal** | ID тарифа выделенного сервера. |  |
|**osId** | **BigDecimal** | ID операционной системы, которая будет установлена на выделенный сервер. |  [optional] |
|**cpId** | **BigDecimal** | ID панели управления, которая будет установлена на выделенный сервер. |  [optional] |
|**bandwidthId** | **BigDecimal** | ID интернет-канала, который будет установлен на выделенный сервер. |  [optional] |
|**networkDriveId** | **BigDecimal** | ID сетевого диска, который будет установлен на выделенный сервер. |  [optional] |
|**additionalIpAddrId** | **BigDecimal** | ID дополнительного IP-адреса, который будет установлен на выделенный сервер. |  [optional] |
|**paymentPeriod** | [**PaymentPeriodEnum**](#PaymentPeriodEnum) | Период оплаты. |  |
|**name** | **String** | Удобочитаемое имя выделенного сервера. Максимальная длина — 255 символов, имя должно быть уникальным. |  |
|**comment** | **String** | Комментарий к выделенному серверу. Максимальная длина — 255 символов. |  [optional] |



## Enum: PaymentPeriodEnum

| Name | Value |
|---- | -----|
| P1M | &quot;P1M&quot; |
| P3M | &quot;P3M&quot; |
| P6M | &quot;P6M&quot; |
| P1Y | &quot;P1Y&quot; |



