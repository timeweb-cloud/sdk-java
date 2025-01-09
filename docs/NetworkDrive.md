

# NetworkDrive


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID сетевого диска. |  |
|**name** | **String** | Название сетевого диска. |  |
|**comment** | **String** | Комментарий |  |
|**size** | **BigDecimal** | Размер диска в Гб |  |
|**serviceList** | [**List&lt;NetworkDriveServiceListInner&gt;**](NetworkDriveServiceListInner.md) | Список сервисов к которым подключен диск. |  |
|**location** | [**LocationEnum**](#LocationEnum) | Локация сетевого диска. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус сетевого диска. |  |
|**availabilityZone** | **AvailabilityZone** |  |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип сетевого диска. |  |
|**presetId** | **Integer** | ID тарифа. |  |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| NEW | &quot;new&quot; |
| CREATED | &quot;created&quot; |
| FAILED | &quot;failed&quot; |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| NVME | &quot;nvme&quot; |
| HDD | &quot;hdd&quot; |



