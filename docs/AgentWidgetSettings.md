

# AgentWidgetSettings

Настройки виджета чата агента для встраивания на сайт

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**whitelistDomains** | **Set&lt;String&gt;** | Домены, на которых разрешено показывать виджет. Пустой массив — без ограничений. |  |
|**name** | **String** | Отображаемое имя агента в виджете |  |
|**signature** | **String** | Подпись под именем агента в виджете |  [optional] |
|**welcomeMessage** | **String** | Приветственное сообщение при открытии виджета |  |
|**primaryColor** | **String** | Основной цвет виджета в формате &#x60;#RRGGBB&#x60; |  |
|**textColor** | **String** | Цвет текста в формате &#x60;#RRGGBB&#x60; |  [optional] |
|**backgroundColor** | **String** | Цвет фона в формате &#x60;#RRGGBB&#x60; |  [optional] |
|**headerFooterColor** | **String** | Цвет шапки и подвала виджета в формате &#x60;#RRGGBB&#x60; |  [optional] |
|**font** | **String** | Шрифт виджета. Пустая строка — шрифт сайта. |  |
|**iconUrl** | **URI** | URL иконки агента в виджете |  [optional] |
|**useAvatar** | **Boolean** | Использовать загруженный в панели аватар как иконку агента |  [optional] |
|**showButton** | **Boolean** | Показывать кнопку открытия виджета |  [optional] |
|**showToolProcessing** | **Boolean** | Показывать в виджете статус выполнения инструментов агента |  [optional] |
|**showConfirmationButtons** | **Boolean** | Показывать кнопки подтверждения и отмены операций, которые агент выполняет через инструменты |  [optional] |
|**chatPosition** | [**ChatPositionEnum**](#ChatPositionEnum) | Положение виджета на странице |  |
|**chatPositionAdjustmentX** | **BigDecimal** | Смещение виджета по горизонтали в пикселях |  [optional] |
|**chatPositionAdjustmentY** | **BigDecimal** | Смещение виджета по вертикали в пикселях |  [optional] |



## Enum: ChatPositionEnum

| Name | Value |
|---- | -----|
| BOTTOM_RIGHT | &quot;bottom_right&quot; |
| BOTTOM_LEFT | &quot;bottom_left&quot; |
| TOP_RIGHT | &quot;top_right&quot; |
| TOP_LEFT | &quot;top_left&quot; |



