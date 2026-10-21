

# DocumentStatusInfoDetails

Подробности, набор полей зависит от состояния. Могут присутствовать и другие поля

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**startedAt** | **OffsetDateTime** | &#x60;indexing&#x60;: время начала индексации |  [optional] |
|**currentStage** | **String** | &#x60;indexing&#x60;: текущий этап |  [optional] |
|**progress** | **BigDecimal** | &#x60;indexing&#x60;: прогресс в процентах |  [optional] |
|**indexingDurationMs** | **BigDecimal** | &#x60;indexed&#x60;: длительность индексации в миллисекундах |  [optional] |
|**tokensUsed** | **BigDecimal** | &#x60;indexed&#x60;: израсходовано токенов |  [optional] |
|**tokensEstimated** | **BigDecimal** | &#x60;indexed&#x60;: оценка количества токенов перед индексацией |  [optional] |
|**chunksCount** | **BigDecimal** | &#x60;indexed&#x60;: количество фрагментов, на которые разбит документ |  [optional] |
|**needed** | **BigDecimal** | &#x60;INSUFFICIENT_TOKENS&#x60;: сколько токенов нужно |  [optional] |
|**available** | **BigDecimal** | &#x60;INSUFFICIENT_TOKENS&#x60;: сколько токенов доступно |  [optional] |
|**estimated** | **BigDecimal** | &#x60;INSUFFICIENT_TOKENS&#x60;: оценка без запаса |  [optional] |
|**balance** | **BigDecimal** | &#x60;CRITICAL_BALANCE&#x60;, &#x60;NEGATIVE_BALANCE&#x60;: текущий остаток токенов |  [optional] |
|**minRequired** | **BigDecimal** | &#x60;CRITICAL_BALANCE&#x60;: минимально необходимый остаток |  [optional] |



