

# S3Backup

Резервная копия базы данных, выгруженная в объектное хранилище S3.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **UUID** | ID резервной копии в формате UUID. |  |
|**name** | **String** | Название резервной копии. В текущей реализации всегда совпадает со значением &#x60;id&#x60;. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус резервной копии: &#x60;running&#x60; — копия создаётся, &#x60;success&#x60; — копия создана, &#x60;failed&#x60; — создать копию не удалось. Восстановить кластер можно только из копии со статусом &#x60;success&#x60;. |  |
|**size** | **BigDecimal** | Размер резервной копии в байтах. |  |
|**progress** | **BigDecimal** | Прогресс создания резервной копии, от 0 до 100. |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип резервной копии: &#x60;manual&#x60; — копия создана вручную, &#x60;auto&#x60; — копия создана по расписанию. |  |
|**comment** | **String** | Комментарий к резервной копии. Если комментарий не задавался, возвращается &#x60;null&#x60;. |  [optional] |
|**createdAt** | **OffsetDateTime** | Дата и время создания резервной копии. |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| RUNNING | &quot;running&quot; |
| SUCCESS | &quot;success&quot; |
| FAILED | &quot;failed&quot; |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| MANUAL | &quot;manual&quot; |
| AUTO | &quot;auto&quot; |



