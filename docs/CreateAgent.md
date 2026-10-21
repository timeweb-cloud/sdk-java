

# CreateAgent

Данные для создания AI агента (устаревший метод API v1)

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Название агента |  |
|**description** | **String** | Описание агента |  [optional] |
|**accessType** | [**AccessTypeEnum**](#AccessTypeEnum) | Тип доступа: &#x60;public&#x60; — агент отвечает анонимным посетителям сайта через виджет; &#x60;private&#x60; — только по токену аккаунта. |  |
|**modelId** | **Integer** | ID основной модели агента |  |
|**settings** | [**AgentSettings**](AgentSettings.md) |  |  |
|**projectId** | **Integer** | ID проекта, в который нужно добавить агента. По умолчанию — проект по умолчанию. |  [optional] |
|**isWebSearchEnabled** | **Boolean** | Включить веб-поиск |  [optional] |
|**tokenPackageId** | **Integer** | ID пакета токенов. Обязателен для совместимости, но не учитывается: агент всегда создаётся с поресурсной оплатой. |  |



## Enum: AccessTypeEnum

| Name | Value |
|---- | -----|
| PUBLIC | &quot;public&quot; |
| PRIVATE | &quot;private&quot; |



