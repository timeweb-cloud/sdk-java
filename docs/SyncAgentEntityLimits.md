

# SyncAgentEntityLimits

Полный набор лимитов агента за период

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**limitType** | [**LimitTypeEnum**](#LimitTypeEnum) | Период лимита |  |
|**limits** | [**List&lt;SyncAgentEntityLimitsLimitsInner&gt;**](SyncAgentEntityLimitsLimitsInner.md) | Лимиты за период — по одному на модель (&#x60;model_id&#x60; не должен повторяться). Лимиты этого периода, которых нет в списке, удаляются; пустой массив удаляет все лимиты периода. |  |



## Enum: LimitTypeEnum

| Name | Value |
|---- | -----|
| HOUR | &quot;hour&quot; |
| DAY | &quot;day&quot; |
| WEEK | &quot;week&quot; |
| MONTH | &quot;month&quot; |



