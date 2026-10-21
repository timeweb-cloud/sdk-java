

# ChatListMetrics

Сводка по чатам агента с учётом фильтра по периоду (`from`/`to`)

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**totalReactions** | **Integer** | Количество чатов с учётом фильтра &#x60;response_time_ranges&#x60; (без учёта &#x60;reaction_type&#x60;) |  |
|**totalTime** | **Integer** | Количество чатов с учётом фильтра &#x60;reaction_type&#x60; (без учёта &#x60;response_time_ranges&#x60;) |  |
|**withLikes** | **Integer** | Чатов хотя бы с одной положительной оценкой |  |
|**withDislikes** | **Integer** | Чатов хотя бы с одной отрицательной оценкой |  |
|**withAny** | **Integer** | Чатов хотя бы с одной оценкой |  |
|**responseTimeGroupsReactions** | **Map&lt;String, Integer&gt;** | Количество чатов по среднему времени ответа (в секундах) с учётом фильтра &#x60;response_time_ranges&#x60; |  |
|**responseTimeGroupsTime** | **Map&lt;String, Integer&gt;** | Количество чатов по среднему времени ответа (в секундах) с учётом фильтра &#x60;reaction_type&#x60; |  |



