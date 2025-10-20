

# DocumentStatusInfo

Детальная информация о текущем состоянии документа в базе знаний

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) | Тип статуса документа |  |
|**timestamp** | **OffsetDateTime** | Время записи информации (ISO string) |  |
|**errorCode** | [**ErrorCodeEnum**](#ErrorCodeEnum) | Код ошибки (только для type: &#39;error&#39;) |  [optional] |
|**details** | [**DocumentStatusInfoDetails**](DocumentStatusInfoDetails.md) |  |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| NEW | &quot;new&quot; |
| INDEXING | &quot;indexing&quot; |
| INDEXED | &quot;indexed&quot; |
| ERROR | &quot;error&quot; |



## Enum: ErrorCodeEnum

| Name | Value |
|---- | -----|
| INSUFFICIENT_TOKENS | &quot;INSUFFICIENT_TOKENS&quot; |
| CRITICAL_BALANCE | &quot;CRITICAL_BALANCE&quot; |
| NEGATIVE_BALANCE | &quot;NEGATIVE_BALANCE&quot; |
| INDEXING_ERROR | &quot;INDEXING_ERROR&quot; |



