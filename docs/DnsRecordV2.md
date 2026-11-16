

# DnsRecordV2

DNS-запись.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID DNS-записи. |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип DNS-записи. |  |
|**data** | [**DnsRecordV2Data**](DnsRecordV2Data.md) |  |  |
|**fqdn** | **String** | Домен, относительно которого указано &#x60;data.subdomain&#x60;. Полное имя записи — &#x60;data.subdomain&#x60; + &#x60;.&#x60; + &#x60;fqdn&#x60;, а если &#x60;data.subdomain&#x60; нет — &#x60;fqdn&#x60;. |  |
|**ttl** | **BigDecimal** | Время жизни DNS-записи в секундах. |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| TXT | &quot;TXT&quot; |
| SRV | &quot;SRV&quot; |
| CNAME | &quot;CNAME&quot; |
| AAAA | &quot;AAAA&quot; |
| MX | &quot;MX&quot; |
| A | &quot;A&quot; |
| PTR | &quot;PTR&quot; |
| HTTPS | &quot;HTTPS&quot; |
| SVCB | &quot;SVCB&quot; |
| CAA | &quot;CAA&quot; |



