

# DatabaseClusterDomainsInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**fqdn** | **String** | Доменное имя кластера базы данных. |  |
|**certStatus** | [**CertStatusEnum**](#CertStatusEnum) | Статус SSL-сертификата домена. |  |



## Enum: CertStatusEnum

| Name | Value |
|---- | -----|
| NOT_ISSUED | &quot;not_issued&quot; |
| PENDING | &quot;pending&quot; |
| ISSUED | &quot;issued&quot; |
| FAILED | &quot;failed&quot; |



