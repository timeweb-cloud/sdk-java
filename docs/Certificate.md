

# Certificate

SSL-сертификат CDN

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID сертификата. Указывается в поле &#x60;config.security.certificate_id&#x60; при привязке сертификата к ресурсу. |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип сертификата. - &#x60;lets_encrypt&#x60; — сертификат, выпущенный через Let&#39;s Encrypt; - &#x60;uploaded&#x60; — сертификат, загруженный вами. |  |
|**cn** | **String** | Основное доменное имя сертификата. |  |
|**domains** | **List&lt;String&gt;** | Все доменные имена сертификата, включая указанные в SAN. |  |
|**issuedAt** | **OffsetDateTime** | Дата выпуска сертификата. |  |
|**expiresAt** | **OffsetDateTime** | Дата окончания действия сертификата. |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| LETS_ENCRYPT | &quot;lets_encrypt&quot; |
| UPLOADED | &quot;uploaded&quot; |



