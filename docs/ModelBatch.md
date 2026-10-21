

# ModelBatch

Условия Batch API для модели. Цена в batch = обычная цена × множитель

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**multiplierIn** | **BigDecimal** | Множитель цены входных токенов |  |
|**multiplierOut** | **BigDecimal** | Множитель цены выходных токенов |  |
|**multiplierCacheRead** | **BigDecimal** | Множитель цены токенов, прочитанных из кэша |  |
|**multiplierCacheWrite** | **BigDecimal** | Множитель цены токенов, записанных в кэш |  |
|**endpoints** | **List&lt;String&gt;** | Эндпоинты, допустимые в строках batch-файла |  |
|**supportsImages** | **Boolean** | Принимает ли модель изображения в batch-запросах |  |



