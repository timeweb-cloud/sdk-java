

# DocumentStatusInfo

Подробности о состоянии индексации документа. `null`, если информации нет

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) | Тип состояния, совпадает со статусом документа |  |
|**timestamp** | **OffsetDateTime** | Время, когда записано состояние |  |
|**errorCode** | [**ErrorCodeEnum**](#ErrorCodeEnum) | Код ошибки, только для &#x60;type: error&#x60;: - &#x60;INSUFFICIENT_TOKENS&#x60; — недостаточно токенов для индексации - &#x60;CRITICAL_BALANCE&#x60; — критически низкий остаток токенов - &#x60;NEGATIVE_BALANCE&#x60; — отрицательный остаток токенов - &#x60;INDEXING_ERROR&#x60; — не удалось обработать документ |  [optional] |
|**stage** | **String** | Этап индексации, на котором произошла ошибка |  [optional] |
|**traceId** | **String** | Идентификатор операции индексации — укажите его при обращении в поддержку |  [optional] |
|**details** | **DocumentStatusInfoDetails** |  |  [optional] |



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



