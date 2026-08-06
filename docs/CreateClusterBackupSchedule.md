

# CreateClusterBackupSchedule

Расписание резервного копирования кластера базы данных.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**copyCount** | **Integer** | Количество хранимых резервных копий. |  [optional] |
|**interval** | [**IntervalEnum**](#IntervalEnum) | Периодичность создания резервных копий. |  [optional] |
|**dayOfWeek** | **Integer** | День недели (от 1 до 7) для создания резервной копии. Учитывается только при &#x60;interval: week&#x60;. |  [optional] |
|**dayOfMonth** | **Integer** | День месяца (от 1 до 28) для создания резервной копии. Учитывается только при &#x60;interval: month&#x60;. |  [optional] |



## Enum: IntervalEnum

| Name | Value |
|---- | -----|
| DAY | &quot;day&quot; |
| WEEK | &quot;week&quot; |
| MONTH | &quot;month&quot; |



