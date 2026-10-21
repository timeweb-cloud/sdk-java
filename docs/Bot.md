

# Bot

Бот, подключенный к AI агенту (Telegram или MAX)

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **UUID** | ID бота в Timeweb Cloud |  |
|**accountId** | **String** | Логин аккаунта, которому принадлежит бот |  |
|**agentId** | **Integer** | ID агента, который отвечает от имени бота |  |
|**username** | **String** | Username бота в мессенджере (без &#x60;@&#x60;) |  |
|**firstName** | **String** | Имя бота в мессенджере |  |
|**description** | **String** | Комментарий к боту. Пустая строка, если не задан |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус подключения: &#x60;pending&#x60; — ожидает подключения, &#x60;active&#x60; — работает, &#x60;error&#x60; — ошибка подключения |  |
|**whitelistEnabled** | **Boolean** | Включено ли ограничение доступа по списку username. У MAX-ботов всегда &#x60;false&#x60; |  |
|**whitelistUsernames** | **List&lt;String&gt;** | Username пользователей, которым разрешено писать боту: в нижнем регистре, без &#x60;@&#x60;. У MAX-ботов всегда пустой |  |
|**createdAt** | **OffsetDateTime** | Дата и время создания |  |
|**updatedAt** | **OffsetDateTime** | Дата и время последнего обновления |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| PENDING | &quot;pending&quot; |
| ACTIVE | &quot;active&quot; |
| ERROR | &quot;error&quot; |



