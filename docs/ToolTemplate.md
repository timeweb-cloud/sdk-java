

# ToolTemplate

Шаблон MCP-инструмента из маркетплейса

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID шаблона. Передаётся в &#x60;template_id&#x60; при установке |  |
|**name** | **String** | Название |  |
|**key** | **String** | Уникальный ключ шаблона |  [optional] |
|**description** | **String** | Описание |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип инструмента |  |
|**version** | **String** | Версия шаблона |  |
|**isApproved** | [**IsApprovedEnum**](#IsApprovedEnum) | Шаблон одобрен для маркетплейса: &#x60;1&#x60; — да, &#x60;0&#x60; — нет |  |
|**authorAccountId** | **String** | Автор шаблона. &#x60;null&#x60; — шаблон Timeweb Cloud |  [optional] |
|**isSystem** | [**IsSystemEnum**](#IsSystemEnum) | Системный шаблон платформы: &#x60;1&#x60; — да, &#x60;0&#x60; — нет |  |
|**installCount** | **Integer** | Сколько раз шаблон установлен |  |
|**category** | [**CategoryEnum**](#CategoryEnum) | Категория шаблона |  [optional] |
|**configSchema** | [**ToolTemplateConfigSchema**](ToolTemplateConfigSchema.md) |  |  [optional] |
|**authType** | **String** | Способ авторизации на MCP-сервере |  |
|**deploymentType** | [**DeploymentTypeEnum**](#DeploymentTypeEnum) | Где работает MCP-сервер: &#x60;managed&#x60; — на стороне Timeweb Cloud, &#x60;external&#x60; — у стороннего сервиса |  |
|**createdAt** | **OffsetDateTime** | Дата и время создания |  |
|**updatedAt** | **OffsetDateTime** | Дата и время последнего изменения |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| MCP | &quot;MCP&quot; |



## Enum: IsApprovedEnum

| Name | Value |
|---- | -----|
| NUMBER_0 | 0 |
| NUMBER_1 | 1 |



## Enum: IsSystemEnum

| Name | Value |
|---- | -----|
| NUMBER_0 | 0 |
| NUMBER_1 | 1 |



## Enum: CategoryEnum

| Name | Value |
|---- | -----|
| SEARCH | &quot;search&quot; |
| NULL | &quot;null&quot; |



## Enum: DeploymentTypeEnum

| Name | Value |
|---- | -----|
| MANAGED | &quot;managed&quot; |
| EXTERNAL | &quot;external&quot; |



