

# AiSolutionChatStatus

Готовность чата решения

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**ready** | **Boolean** | Готов ли чат принимать сообщения |  |
|**reason** | [**ReasonEnum**](#ReasonEnum) | Почему чат ещё не готов. Отсутствует, если чат готов. &#x60;unreachable&#x60; — до сервера не достучаться (разворачивается, выключен или нет публичного IP); &#x60;software_installing&#x60; — устанавливается ПО агента; &#x60;configuring&#x60; — агент настраивается; &#x60;install_failed&#x60; — установка ПО не удалась, пересоздайте решение; &#x60;machine_error&#x60; — сервер в состоянии ошибки, пересоздайте решение; &#x60;deleted&#x60; — решение удалено; &#x60;outdated&#x60; — сервер создан старой версией решения, пересоздайте решение |  [optional] |



## Enum: ReasonEnum

| Name | Value |
|---- | -----|
| UNREACHABLE | &quot;unreachable&quot; |
| SOFTWARE_INSTALLING | &quot;software_installing&quot; |
| CONFIGURING | &quot;configuring&quot; |
| INSTALL_FAILED | &quot;install_failed&quot; |
| MACHINE_ERROR | &quot;machine_error&quot; |
| DELETED | &quot;deleted&quot; |
| OUTDATED | &quot;outdated&quot; |



