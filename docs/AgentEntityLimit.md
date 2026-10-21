

# AgentEntityLimit

Лимит агента

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **UUID** | ID лимита |  |
|**aiKeyId** | **UUID** | ID служебного ключа агента, на котором учитывается лимит |  |
|**entityType** | **String** | Тип сущности — всегда &#x60;agent&#x60; |  |
|**entityId** | **String** | ID агента |  |
|**limitType** | [**LimitTypeEnum**](#LimitTypeEnum) | Период лимита |  |
|**modelId** | **Integer** | ID модели, на запросы к которой действует лимит. &#x60;null&#x60; — лимит на все модели агента. |  |
|**limit** | **Integer** | Лимит в токенах за период. Не действует, если задан &#x60;money_limit&#x60;. &#x60;0&#x60; — запрещены все запросы. |  |
|**moneyLimit** | **BigDecimal** | Лимит расходов за период в валюте аккаунта; &#x60;null&#x60; — не задан, действует &#x60;limit&#x60; |  |
|**usage** | **Integer** | Израсходовано токенов в текущем периоде |  |
|**moneyUsage** | **BigDecimal** | Израсходовано денег в текущем периоде (полная стоимость запросов до бонусов) |  |
|**createdAt** | **OffsetDateTime** | Дата создания лимита |  |
|**expireAt** | **OffsetDateTime** | Окончание текущего периода — после него расход обнуляется |  |



## Enum: LimitTypeEnum

| Name | Value |
|---- | -----|
| HOUR | &quot;hour&quot; |
| DAY | &quot;day&quot; |
| WEEK | &quot;week&quot; |
| MONTH | &quot;month&quot; |



