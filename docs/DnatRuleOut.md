

# DnatRuleOut


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID правила |  |
|**localIp** | **String** | Приватный IP-адрес |  |
|**localPort** | **String** | Внутренний порт или диапазон |  |
|**publicIp** | **String** | Публичный IP-адрес |  |
|**publicPort** | **String** | Внешний порт или диапазон |  |
|**protocol** | [**ProtocolEnum**](#ProtocolEnum) | Протокол |  |



## Enum: ProtocolEnum

| Name | Value |
|---- | -----|
| TCP | &quot;tcp&quot; |
| UDP | &quot;udp&quot; |
| TCP_UDP | &quot;tcp_udp&quot; |



