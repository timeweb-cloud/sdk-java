

# HttpResourceConfig

Конфигурация CDN-ресурса. Все секции необязательны: при изменении конфигурации переданные поля накладываются на текущие значения, а непереданные остаются без изменений.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**delivery** | [**ConfigDelivery**](ConfigDelivery.md) |  |  [optional] |
|**cache** | [**ConfigCache**](ConfigCache.md) |  |  [optional] |
|**origin** | [**ConfigOrigin**](ConfigOrigin.md) |  |  [optional] |
|**security** | [**ConfigSecurity**](ConfigSecurity.md) |  |  [optional] |
|**httpHeaders** | [**ConfigHttpHeaders**](ConfigHttpHeaders.md) |  |  [optional] |
|**access** | [**ConfigAccess**](ConfigAccess.md) |  |  [optional] |
|**domains** | [**ConfigDomains**](ConfigDomains.md) |  |  [optional] |
|**robots** | [**ConfigRobots**](ConfigRobots.md) |  |  [optional] |



