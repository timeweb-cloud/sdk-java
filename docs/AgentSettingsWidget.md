

# AgentSettingsWidget

Настройки виджета

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**whitelistDomains** | **List&lt;String&gt;** | Массив разрешенных доменов для виджета |  |
|**name** | **String** | Отображаемое имя агента в виджете |  |
|**signature** | **String** | Подпись/подзаголовок, отображаемый под именем агента в виджете |  [optional] |
|**welcomeMessage** | **String** | Приветственное сообщение, показываемое при открытии виджета |  |
|**primaryColor** | **String** | Основной цвет виджета (hex-код цвета в формате #RRGGBB) |  |
|**font** | **String** | Семейство шрифтов для виджета |  |
|**iconUrl** | **String** | URL иконки виджета |  [optional] |
|**chatPosition** | [**ChatPositionEnum**](#ChatPositionEnum) | Позиция виджета чата на странице |  |



## Enum: ChatPositionEnum

| Name | Value |
|---- | -----|
| BOTTOM_RIGHT | &quot;bottom_right&quot; |
| BOTTOM_LEFT | &quot;bottom_left&quot; |
| TOP_RIGHT | &quot;top_right&quot; |
| TOP_LEFT | &quot;top_left&quot; |



