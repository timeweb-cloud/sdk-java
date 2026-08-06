

# ConfigOrigin

Настройки источника контента

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**servers** | [**List&lt;OriginServer&gt;**](OriginServer.md) | Origin-серверы, с которых CDN забирает контент. Передача этого поля переключает ресурс с S3-хранилища на origin-сервер. |  [optional] |
|**useHttps** | **Boolean** | Обращаться к источнику контента по HTTPS. |  [optional] |
|**aws** | [**ConfigOriginAws**](ConfigOriginAws.md) |  |  [optional] |



