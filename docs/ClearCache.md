

# ClearCache

Параметры очистки кэша

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**purgeType** | [**PurgeTypeEnum**](#PurgeTypeEnum) | Тип очистки. - &#x60;full&#x60; — очистить весь кэш ресурса; - &#x60;partial&#x60; — очистить кэш только по путям из &#x60;paths&#x60;. |  |
|**paths** | **List&lt;String&gt;** | Список путей к файлам, кэш которых нужно очистить. Обязателен при &#x60;purge_type&#x60; &#x3D; &#x60;partial&#x60;. |  [optional] |



## Enum: PurgeTypeEnum

| Name | Value |
|---- | -----|
| FULL | &quot;full&quot; |
| PARTIAL | &quot;partial&quot; |



