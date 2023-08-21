

# UpdateBalancer


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Удобочитаемое имя, установленное для балансировщика. |  [optional] |
|**algo** | [**AlgoEnum**](#AlgoEnum) | Алгоритм переключений балансировщика. |  [optional] |
|**isSticky** | **Boolean** | Это логическое значение, которое показывает, сохраняется ли сессия. |  [optional] |
|**isUseProxy** | **Boolean** | Это логическое значение, которое показывает, выступает ли балансировщик в качестве прокси. |  [optional] |
|**isSsl** | **Boolean** | Это логическое значение, которое показывает, требуется ли перенаправление на SSL. |  [optional] |
|**isKeepalive** | **Boolean** | Это логическое значение, которое показывает, выдает ли балансировщик сигнал о проверке жизнеспособности. |  [optional] |
|**proto** | [**ProtoEnum**](#ProtoEnum) | Протокол. |  [optional] |
|**port** | **BigDecimal** | Порт балансировщика. |  [optional] |
|**path** | **String** | Адрес балансировщика. |  [optional] |
|**inter** | **BigDecimal** | Интервал проверки. |  [optional] |
|**timeout** | **BigDecimal** | Таймаут ответа балансировщика. |  [optional] |
|**fall** | **BigDecimal** | Порог количества ошибок. |  [optional] |
|**rise** | **BigDecimal** | Порог количества успешных ответов. |  [optional] |
|**presetId** | **BigDecimal** | Идентификатор тарифа. |  [optional] |



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



