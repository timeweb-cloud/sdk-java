

# IndexingSchedule

Расписание автоматической переиндексации документа по ссылке

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**days** | [**List&lt;DaysEnum&gt;**](#List&lt;DaysEnum&gt;) | Дни недели, в которые выполняется переиндексация (минимум один) |  |
|**time** | **String** | Время запуска в формате &#x60;HH:mm&#x60; (24 часа) |  |



## Enum: List&lt;DaysEnum&gt;

| Name | Value |
|---- | -----|
| MONDAY | &quot;monday&quot; |
| TUESDAY | &quot;tuesday&quot; |
| WEDNESDAY | &quot;wednesday&quot; |
| THURSDAY | &quot;thursday&quot; |
| FRIDAY | &quot;friday&quot; |
| SATURDAY | &quot;saturday&quot; |
| SUNDAY | &quot;sunday&quot; |



