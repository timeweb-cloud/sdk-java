

# VpcPort


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID порта. |  |
|**natMode** | [**NatModeEnum**](#NatModeEnum) | Тип преобразования сетевых адресов. |  |
|**mac** | **String** | MAC адрес. |  |
|**ipv4** | **String** | Внутренний адрес. |  |
|**service** | [**VpcPortService**](VpcPortService.md) |  |  |



## Enum: NatModeEnum

| Name | Value |
|---- | -----|
| DNAT_AND_SNAT | &quot;dnat_and_snat&quot; |
| SNAT | &quot;snat&quot; |
| NO_NAT | &quot;no_nat&quot; |



