

# ConfigSecurity

Настройки безопасности

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**redirect** | **Boolean** | Перенаправлять запросы с HTTP на HTTPS. |  [optional] |
|**certificateId** | **Integer** | ID SSL-сертификата, который используется для доменов ресурса. &#x60;null&#x60; — отвязать сертификат. |  [optional] |
|**sslProtocols** | [**Set&lt;SslProtocolsEnum&gt;**](#Set&lt;SslProtocolsEnum&gt;) | Версии SSL/TLS, по которым CDN принимает HTTPS-подключения. &#x60;null&#x60; — принимать все версии. |  [optional] |
|**secureToken** | [**ConfigSecuritySecureToken**](ConfigSecuritySecureToken.md) |  |  [optional] |



## Enum: Set&lt;SslProtocolsEnum&gt;

| Name | Value |
|---- | -----|
| TLSV1 | &quot;TLSv1&quot; |
| TLSV1_1 | &quot;TLSv1.1&quot; |
| TLSV1_2 | &quot;TLSv1.2&quot; |
| TLSV1_3 | &quot;TLSv1.3&quot; |
| SSLV3 | &quot;SSLv3&quot; |



