

# CreateClusterMaintenanceSlot

Период технического обслуживания кластера базы данных.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) | Режим обслуживания. |  |
|**from** | **String** | Начало периода обслуживания в формате &#x60;HH:mmZ&#x60; (UTC). Учитывается только при &#x60;type: fixed_time&#x60;. |  [optional] |
|**to** | **String** | Конец периода обслуживания в формате &#x60;HH:mmZ&#x60; (UTC). Учитывается только при &#x60;type: fixed_time&#x60;. |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| ANY_TIME | &quot;any_time&quot; |
| FIXED_TIME | &quot;fixed_time&quot; |



