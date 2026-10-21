

# SyncAiKeyLimits

Полный набор лимитов ключа за один период

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**limits** | [**List&lt;SyncAiKeyLimitsLimitsInner&gt;**](SyncAiKeyLimitsLimitsInner.md) | Лимиты по моделям. &#x60;model_id&#x60; в списке не должны повторяться. Пустой список удаляет все лимиты периода |  |
|**limitType** | [**LimitTypeEnum**](#LimitTypeEnum) | Период лимита: &#x60;hour&#x60; — час, &#x60;day&#x60; — сутки, &#x60;week&#x60; — 7 дней, &#x60;month&#x60; — 30 дней |  |
|**entityType** | **String** | Тип сущности. По умолчанию — тип ключа (лимиты на сам ключ) |  [optional] |
|**entityId** | **String** | ID сущности. Для лимитов на сам ключ не нужен |  [optional] |



## Enum: LimitTypeEnum

| Name | Value |
|---- | -----|
| HOUR | &quot;hour&quot; |
| DAY | &quot;day&quot; |
| WEEK | &quot;week&quot; |
| MONTH | &quot;month&quot; |



