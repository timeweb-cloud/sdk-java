

# ChatFirstMessage

Первое сообщение пользователя в чате

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID сообщения |  |
|**role** | [**RoleEnum**](#RoleEnum) | Автор сообщения |  |
|**createdAt** | **OffsetDateTime** | Дата создания сообщения |  |
|**text** | **String** | Текст сообщения |  |



## Enum: RoleEnum

| Name | Value |
|---- | -----|
| USER | &quot;user&quot; |
| ASSISTANT | &quot;assistant&quot; |
| SYSTEM | &quot;system&quot; |



