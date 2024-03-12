

# CreateDbAutoBackups

База данных

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**copyCount** | **BigDecimal** | Количество копий для хранения. Минимальное количество &#x60;1&#x60;, максимальное &#x60;99&#x60; |  |
|**creationStartAt** | **OffsetDateTime** | Дата начала создания первого автобэкапа. Значение в формате &#x60;ISO8601&#x60;. Время не учитывается. |  |
|**interval** | [**IntervalEnum**](#IntervalEnum) | Периодичность создания автобэкапов |  |
|**dayOfWeek** | **BigDecimal** | День недели, в который будут создаваться автобэкапы. Работает только со значением &#x60;interval&#x60;: &#x60;week&#x60;. Доступные значение от &#x60;1 &#x60;до &#x60;7&#x60;. |  |



## Enum: IntervalEnum

| Name | Value |
|---- | -----|
| DAY | &quot;day&quot; |
| WEEK | &quot;week&quot; |
| MONTH | &quot;month&quot; |



