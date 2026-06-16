

# RouterOut


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID роутера |  |
|**accountId** | **String** | ID аккаунта |  |
|**avatarLink** | **String** | Ссылка на аватар роутера |  |
|**name** | **String** | Имя роутера |  |
|**comment** | **String** | Описание роутера |  |
|**status** | **String** | Статус роутера |  |
|**zone** | **String** | Зона доступности |  |
|**ips** | [**List&lt;RouterOutIpsInner&gt;**](RouterOutIpsInner.md) | IP-адреса |  |
|**presetId** | **Integer** | ID тарифа |  |
|**preset** | [**RouterPreset**](RouterPreset.md) |  |  |
|**nodes** | [**List&lt;RouterOutNodesInner&gt;**](RouterOutNodesInner.md) | Ноды |  |
|**networks** | [**List&lt;RouterNetworkMeta&gt;**](RouterNetworkMeta.md) | Сети |  |
|**createdAt** | **OffsetDateTime** | Дата и время создания роутера в формате ISO8601 |  |
|**projectId** | **Integer** | ID проекта |  [optional] |
|**parentServices** | [**List&lt;RouterOutParentServicesInner&gt;**](RouterOutParentServicesInner.md) | Родительские сервисы |  |



