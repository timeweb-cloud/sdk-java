

# DeleteAgent

Причина удаления агента (необязательно)

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**reason** | [**ReasonEnum**](#ReasonEnum) | Причина удаления |  [optional] |
|**comment** | **String** | Комментарий к причине удаления |  [optional] |



## Enum: ReasonEnum

| Name | Value |
|---- | -----|
| LACK_OF_FUNCTIONALITY | &quot;lack_of_functionality&quot; |
| DO_NOT_UNDERSTAND_AGENTS | &quot;do_not_understand_agents&quot; |
| WANT_TO_CHANGE_LANGUAGE_MODEL | &quot;want_to_change_language_model&quot; |
| TOO_EXPENSIVE | &quot;too_expensive&quot; |
| NO_LONGER_NEED | &quot;no_longer_need&quot; |
| SLOW_OR_LOW_QUALITY_SERVICE | &quot;slow_or_low_quality_service&quot; |
| CUSTOM_REASON | &quot;custom_reason&quot; |



