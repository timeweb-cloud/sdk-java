

# BalancerNetworksInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID сети. Есть только у приватных сетей. |  [optional] |
|**type** | [**TypeEnum**](#TypeEnum) | Тип сети. |  |
|**natMode** | [**NatModeEnum**](#NatModeEnum) | Тип преобразования сетевых адресов. |  [optional] |
|**portId** | **String** | ID порта. |  [optional] |
|**ips** | [**List&lt;BalancerNetworksInnerIpsInner&gt;**](BalancerNetworksInnerIpsInner.md) | Список IP-адресов сети. |  |



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



