

# S3Subdomain

Поддомен.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID поддомена. |  |
|**subdomain** | **String** | Поддомен. |  |
|**certReleased** | **OffsetDateTime** | Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда был выдан SSL сертификат. |  |
|**tries** | **BigDecimal** | Количество попыток перевыпустить SSL сертификат. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Поддомен. |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| RELEASED | &quot;ssl_released&quot; |
| NOT_REQUESTED | &quot;ssl_not_requested&quot; |
| RE_RELEASE_ERROR | &quot;ssl_re_release_error&quot; |



