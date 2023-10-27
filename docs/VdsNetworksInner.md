

# VdsNetworksInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) | Тип сети. |  |
|**natMode** | [**NatModeEnum**](#NatModeEnum) | Тип преобразования сетевых адресов. |  [optional] |
|**bandwidth** | **BigDecimal** | Пропускная способность сети. |  [optional] |
|**ips** | [**List&lt;VdsNetworksInnerIpsInner&gt;**](VdsNetworksInnerIpsInner.md) | Список IP-адресов сети. |  |
|**isDdosGuard** | **Boolean** | Подключена ли DDoS-защита. Только для публичных сетей. |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| PUBLIC | &quot;public&quot; |
| LOCAL | &quot;local&quot; |



## Enum: NatModeEnum

| Name | Value |
|---- | -----|
| DNAT_AND_SNAT | &quot;dnat_and_snat&quot; |
| SNAT | &quot;snat&quot; |
| NO_NAT | &quot;no_nat&quot; |



