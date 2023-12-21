

# CreateBalancer


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Удобочитаемое имя, установленное для балансировщика. |  |
|**algo** | [**AlgoEnum**](#AlgoEnum) | Алгоритм переключений балансировщика. |  |
|**isSticky** | **Boolean** | Это логическое значение, которое показывает, сохраняется ли сессия. |  |
|**isUseProxy** | **Boolean** | Это логическое значение, которое показывает, выступает ли балансировщик в качестве прокси. |  |
|**isSsl** | **Boolean** | Это логическое значение, которое показывает, требуется ли перенаправление на SSL. |  |
|**isKeepalive** | **Boolean** | Это логическое значение, которое показывает, выдает ли балансировщик сигнал о проверке жизнеспособности. |  |
|**proto** | [**ProtoEnum**](#ProtoEnum) | Протокол. |  |
|**port** | **BigDecimal** | Порт балансировщика. |  |
|**path** | **String** | Адрес балансировщика. |  |
|**inter** | **BigDecimal** | Интервал проверки. |  |
|**timeout** | **BigDecimal** | Таймаут ответа балансировщика. |  |
|**fall** | **BigDecimal** | Порог количества ошибок. |  |
|**rise** | **BigDecimal** | Порог количества успешных ответов. |  |
|**presetId** | **BigDecimal** | Идентификатор тарифа. |  |
|**network** | [**Network**](Network.md) |  |  [optional] |
|**availabilityZone** | **AvailabilityZone** |  |  [optional] |



## Enum: AlgoEnum

| Name | Value |
|---- | -----|
| ROUNDROBIN | &quot;roundrobin&quot; |
| LEASTCONN | &quot;leastconn&quot; |



## Enum: ProtoEnum

| Name | Value |
|---- | -----|
| HTTP | &quot;http&quot; |
| HTTP2 | &quot;http2&quot; |
| HTTPS | &quot;https&quot; |
| TCP | &quot;tcp&quot; |



