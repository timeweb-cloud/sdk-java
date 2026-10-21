

# CreateAgentEntityLimit

Новый лимит агента. Нужно передать `limit` или `money_limit`.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**limitType** | [**LimitTypeEnum**](#LimitTypeEnum) | Период лимита |  |
|**modelId** | **Integer** | ID модели, на запросы к которой действует лимит. Не передан или &#x60;null&#x60; — лимит на все модели агента. |  [optional] |
|**limit** | **Integer** | Лимит в токенах за период. Если не передан — &#x60;0&#x60;, тогда обязателен &#x60;money_limit&#x60;. Не действует, если задан &#x60;money_limit&#x60;. &#x60;0&#x60; при отсутствии &#x60;money_limit&#x60; — запрещены все запросы. |  [optional] |
|**moneyLimit** | **BigDecimal** | Лимит расходов за период в валюте аккаунта, до 8 знаков после запятой. Приоритетнее &#x60;limit&#x60;. &#x60;0&#x60; — запрещены все запросы. |  [optional] |



## Enum: LimitTypeEnum

| Name | Value |
|---- | -----|
| HOUR | &quot;hour&quot; |
| DAY | &quot;day&quot; |
| WEEK | &quot;week&quot; |
| MONTH | &quot;month&quot; |



