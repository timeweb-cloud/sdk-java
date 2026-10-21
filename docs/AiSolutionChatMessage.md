

# AiSolutionChatMessage

Сообщение из истории чата решения

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**entryId** | **String** | ID сообщения |  |
|**conversationId** | **String** | ID разговора |  |
|**role** | [**RoleEnum**](#RoleEnum) | Автор сообщения: &#x60;user&#x60;, &#x60;assistant&#x60; или &#x60;context-reset&#x60; — служебная отметка с пустым &#x60;content&#x60;, обозначающая место сброса контекста разговора |  |
|**content** | **String** | Текст сообщения |  |
|**createdAt** | **OffsetDateTime** | Дата и время сообщения |  |



## Enum: RoleEnum

| Name | Value |
|---- | -----|
| USER | &quot;user&quot; |
| ASSISTANT | &quot;assistant&quot; |
| CONTEXT_RESET | &quot;context-reset&quot; |



