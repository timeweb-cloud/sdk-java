

# ServicePrice

Информация о стоимости сервиса

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**cost** | **BigDecimal** | Стоимость сервиса |  |
|**totalCost** | **BigDecimal** | Общая стоимость сервиса с учетом всех дополнительных услуг |  |
|**type** | **ServicePriceType** |  |  |
|**serviceId** | **BigDecimal** | Идентификатор сервиса |  [optional] |
|**projectId** | **BigDecimal** | Идентификатор проекта |  [optional] |
|**services** | [**List&lt;ServiceServicePrice&gt;**](ServiceServicePrice.md) | Список вложенных сервисов |  [optional] |
|**info** | [**InfoServicePrice**](InfoServicePrice.md) |  |  [optional] |
|**_configuration** | [**ServicePriceConfiguration**](ServicePriceConfiguration.md) |  |  [optional] |



