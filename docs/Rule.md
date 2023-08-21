

# Rule

Правило для балансировщика

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор для каждого экземпляра правила для балансировщика. Автоматически генерируется при создании. |  |
|**balancerProto** | [**BalancerProtoEnum**](#BalancerProtoEnum) | Протокол балансировщика. |  |
|**balancerPort** | **BigDecimal** | Порт балансировщика. |  |
|**serverProto** | [**ServerProtoEnum**](#ServerProtoEnum) | Протокол сервера. |  |
|**serverPort** | **BigDecimal** | Порт сервера. |  |



## Enum: BalancerProtoEnum

| Name | Value |
|---- | -----|
| HTTP | &quot;http&quot; |
| HTTP2 | &quot;http2&quot; |
| HTTPS | &quot;https&quot; |
| TCP | &quot;tcp&quot; |



## Enum: ServerProtoEnum

| Name | Value |
|---- | -----|
| HTTP | &quot;http&quot; |
| HTTP2 | &quot;http2&quot; |
| HTTPS | &quot;https&quot; |
| TCP | &quot;tcp&quot; |



