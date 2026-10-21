

# SkillSaveMeta

Результат сохранения

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**versionCreated** | **Boolean** | Создана ли новая версия. &#x60;false&#x60; — изменился только заголовок или изменений не было |  |
|**affectedAgentsCount** | **Integer** | У скольких агентов навык включён. Их поведение не меняется, пока их не переведут на новую версию |  |
|**warnings** | [**List&lt;WarningsEnum&gt;**](#List&lt;WarningsEnum&gt;) | Предупреждения, которые не мешают сохранению. &#x60;when_to_use_too_short&#x60; — текст «когда применять» короче 40 символов: модель не сможет отличить подходящий запрос от постороннего |  |
|**prunedVersions** | **List&lt;Integer&gt;** | Номера версий, удалённых из истории из-за лимита в 50 версий |  |



## Enum: List&lt;WarningsEnum&gt;

| Name | Value |
|---- | -----|
| WHEN_TO_USE_TOO_SHORT | &quot;when_to_use_too_short&quot; |



