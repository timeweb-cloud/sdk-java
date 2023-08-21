

# CreateDns


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**priority** | **BigDecimal** | Приоритет DNS-записи. |  [optional] |
|**subdomain** | **String** | Полное имя поддомена. |  [optional] |
|**type** | [**TypeEnum**](#TypeEnum) | Тип DNS-записи. |  |
|**value** | **String** | Значение DNS-записи. |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| TXT | &quot;TXT&quot; |
| SRV | &quot;SRV&quot; |
| CNAME | &quot;CNAME&quot; |
| AAAA | &quot;AAAA&quot; |
| MX | &quot;MX&quot; |
| A | &quot;A&quot; |



