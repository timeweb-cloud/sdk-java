

# DnsRecord

DNS-запись.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) | Тип DNS-записи. |  |
|**id** | **BigDecimal** | ID DNS-записи. |  [optional] |
|**data** | [**DnsRecordData**](DnsRecordData.md) |  |  |
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



