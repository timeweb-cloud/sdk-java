

# UpdateServerNATRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**natMode** | [**NatModeEnum**](#NatModeEnum) | Правило для маршрутизации трафика. \\  Досутпные правила: &#x60;dnat_and_snat&#x60; – разрешен входящий и исходящий трафик, &#x60;snat&#x60; – разрешен только исходящий трафик, &#x60;no_nat&#x60; – разрешен трафик только в локальной сети. |  |



## Enum: NatModeEnum

| Name | Value |
|---- | -----|
| DNAT_AND_SNAT | &quot;dnat_and_snat&quot; |
| SNAT | &quot;snat&quot; |
| NO_NAT | &quot;no_nat&quot; |



