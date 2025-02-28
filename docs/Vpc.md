

# Vpc


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID сети. |  |
|**name** | **String** | Имя сети. |  |
|**subnetV4** | **String** | Маска подсети. |  |
|**location** | [**LocationEnum**](#LocationEnum) | Локация сети. |  |
|**createdAt** | **OffsetDateTime** | Дата создания сети. |  |
|**description** | **String** | Описание. |  |
|**availabilityZone** | **AvailabilityZone** |  |  |
|**publicIp** | **String** | Публичный IP-адрес сети. |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип сети. |  |
|**busyAddress** | **List&lt;String&gt;** | Занятые адреса в сети |  |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| RU_2 | &quot;ru-2&quot; |
| PL_1 | &quot;pl-1&quot; |
| NL_1 | &quot;nl-1&quot; |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| BGP | &quot;bgp&quot; |
| OVN | &quot;ovn&quot; |



