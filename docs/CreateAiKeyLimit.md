

# CreateAiKeyLimit

Параметры создания лимита ключа

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**entityType** | **String** | Тип сущности, на которую действует лимит. Для лимита на сам ключ передайте его тип — поле &#x60;entity_type&#x60; ключа (&#x60;aikey&#x60; или &#x60;k8s&#x60;) |  |
|**entityId** | **String** | ID сущности. Для лимита на сам ключ не нужен: подставляется ID ключа |  [optional] |
|**limitType** | [**LimitTypeEnum**](#LimitTypeEnum) | Период лимита: &#x60;hour&#x60; — час, &#x60;day&#x60; — сутки, &#x60;week&#x60; — 7 дней, &#x60;month&#x60; — 30 дней |  |
|**modelId** | **Integer** | ID модели из каталога (&#x60;GET /api/v3/cloud-ai/models&#x60;), на которую действует лимит. Не передан — лимит на все модели |  [optional] |
|**limit** | **Integer** | Лимит в токенах за период. Нужен &#x60;limit&#x60; или &#x60;money_limit&#x60;; если передан только &#x60;money_limit&#x60;, &#x60;limit&#x60; будет &#x60;0&#x60;. &#x60;0&#x60; без &#x60;money_limit&#x60; — запросы запрещены |  [optional] |
|**moneyLimit** | **BigDecimal** | Лимит в деньгах за период, в валюте аккаунта, не больше 8 знаков после точки. Приоритетнее &#x60;limit&#x60;. &#x60;0&#x60; — запросы запрещены |  [optional] |



## Enum: LimitTypeEnum

| Name | Value |
|---- | -----|
| HOUR | &quot;hour&quot; |
| DAY | &quot;day&quot; |
| WEEK | &quot;week&quot; |
| MONTH | &quot;month&quot; |



