

# AiSolutionTelegramBot

Telegram-бот решения

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**accountKey** | **String** | ID бота в решении: &#x60;b&lt;ID бота в Telegram&gt;&#x60; |  |
|**botUsername** | **String** | Имя пользователя бота без &#x60;@&#x60;. &#x60;null&#x60;, если Telegram его не вернул |  |
|**state** | [**StateEnum**](#StateEnum) | Состояние бота: &#x60;pending_pairing&#x60; — бот подключён, но владелец ещё не подтверждён (ожидается код подтверждения); &#x60;connected&#x60; — владелец подтверждён, агент отвечает в этом боте |  |



## Enum: StateEnum

| Name | Value |
|---- | -----|
| PENDING_PAIRING | &quot;pending_pairing&quot; |
| CONNECTED | &quot;connected&quot; |



