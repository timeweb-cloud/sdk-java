

# CertificateTask

Задача на выпуск сертификата Let's Encrypt

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID задачи на выпуск сертификата. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус выпуска сертификата. - &#x60;in_progress&#x60; — сертификат выпускается; - &#x60;success&#x60; — сертификат выпущен и привязан к ресурсу; - &#x60;failed&#x60; — выпустить сертификат не удалось. |  |
|**domains** | **List&lt;String&gt;** | Доменные имена, для которых выпускается сертификат. |  |
|**resourceId** | **Integer** | ID CDN-ресурса, для которого выпускается сертификат. |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| IN_PROGRESS | &quot;in_progress&quot; |
| SUCCESS | &quot;success&quot; |
| FAILED | &quot;failed&quot; |



