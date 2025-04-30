

# Balancer

Балансировщик

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID для каждого экземпляра балансировщика. Автоматически генерируется при создании. |  |
|**algo** | [**AlgoEnum**](#AlgoEnum) | Алгоритм переключений балансировщика. |  |
|**createdAt** | **OffsetDateTime** | Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда был создан балансировщик. |  |
|**fall** | **BigDecimal** | Порог количества ошибок. |  |
|**inter** | **BigDecimal** | Интервал проверки. |  |
|**ip** | **String** | IP-адрес сетевого интерфейса IPv4. |  |
|**localIp** | **String** | Локальный IP-адрес сетевого интерфейса IPv4. |  |
|**isKeepalive** | **Boolean** | Это логическое значение, которое показывает, выдает ли балансировщик сигнал о проверке жизнеспособности. |  |
|**name** | **String** | Удобочитаемое имя, установленное для балансировщика. |  |
|**path** | **String** | Адрес балансировщика. |  |
|**port** | **BigDecimal** | Порт балансировщика. |  |
|**proto** | [**ProtoEnum**](#ProtoEnum) | Протокол. |  |
|**rise** | **BigDecimal** | Порог количества успешных ответов. |  |
|**maxconn** | **BigDecimal** | Максимальное количество соединений. |  |
|**connectTimeout** | **BigDecimal** | Таймаут подключения. |  |
|**clientTimeout** | **BigDecimal** | Таймаут клиента. |  |
|**serverTimeout** | **BigDecimal** | Таймаут сервера. |  |
|**httprequestTimeout** | **BigDecimal** | Таймаут HTTP запроса. |  |
|**presetId** | **BigDecimal** | ID тарифа. |  |
|**isSsl** | **Boolean** | Это логическое значение, которое показывает, требуется ли перенаправление на SSL. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус балансировщика. |  |
|**isSticky** | **Boolean** | Это логическое значение, которое показывает, сохраняется ли сессия. |  |
|**timeout** | **BigDecimal** | Таймаут ответа балансировщика. |  |
|**avatarLink** | **String** | Ссылка на аватар балансировщика. |  |
|**isUseProxy** | **Boolean** | Это логическое значение, которое показывает, выступает ли балансировщик в качестве прокси. |  |
|**rules** | [**List&lt;Rule&gt;**](Rule.md) |  |  |
|**ips** | **List&lt;String&gt;** | Список IP-адресов, привязанных к балансировщику |  |
|**location** | [**LocationEnum**](#LocationEnum) | Географическое расположение балансировщика |  |
|**availabilityZone** | **AvailabilityZone** |  |  |



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



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| STARTED | &quot;started&quot; |
| STOPED | &quot;stoped&quot; |
| STARTING | &quot;starting&quot; |
| NO_PAID | &quot;no_paid&quot; |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| PL_1 | &quot;pl-1&quot; |



