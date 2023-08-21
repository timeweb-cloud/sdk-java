

# TransferStatus

Статус трансфера.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**status** | [**StatusEnum**](#StatusEnum) | Общий статус трансфера. |  |
|**tries** | **BigDecimal** | Количество попыток. |  |
|**totalCount** | **BigDecimal** | Общее количество затронутых объектов. |  |
|**totalSize** | **BigDecimal** | Общий размер затронутых объектов. |  |
|**uploadedCount** | **BigDecimal** | Количество перемещенных объектов. |  |
|**uploadedSize** | **BigDecimal** | Размер перемещенных объектов. |  |
|**errors** | [**List&lt;TransferStatusErrorsInner&gt;**](TransferStatusErrorsInner.md) |  |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| STARTED | &quot;started&quot; |
| SUSPENDED | &quot;suspended&quot; |
| FAILED | &quot;failed&quot; |



