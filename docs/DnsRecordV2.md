

# DnsRecordV2

DNS-запись.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) | Тип DNS-записи. |  |
|**id** | **BigDecimal** | ID DNS-записи. |  [optional] |
|**fqdn** | **String** | Полное имя основного домена. |  [optional] |
|**data** | [**DnsRecordV2Data**](DnsRecordV2Data.md) |  |  |
|**ttl** | **BigDecimal** | Время жизни DNS-записи. |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| TXT | &quot;TXT&quot; |
| SRV | &quot;SRV&quot; |
| CNAME | &quot;CNAME&quot; |
| AAAA | &quot;AAAA&quot; |
| MX | &quot;MX&quot; |
| A | &quot;A&quot; |



