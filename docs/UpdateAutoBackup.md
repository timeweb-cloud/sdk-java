

# UpdateAutoBackup

Новые настройки автобэкапов базы данных. При значении `is_enabled`: `true` поля `copy_count`, `creation_start_at`, `interval` и `day_of_week` являются обязательными.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**isEnabled** | **Boolean** | Включено ли автобэкапирование |  |
|**copyCount** | **BigDecimal** | Количество копий для хранения. Минимальное количество &#x60;1&#x60;, максимальное &#x60;99&#x60;. Обязательно при &#x60;is_enabled&#x60;: &#x60;true&#x60;. |  [optional] |
|**creationStartAt** | **OffsetDateTime** | Дата начала создания первого автобэкапа. Значение в формате &#x60;ISO8601&#x60;. Время не учитывается. Обязательно при &#x60;is_enabled&#x60;: &#x60;true&#x60;. |  [optional] |
|**interval** | [**IntervalEnum**](#IntervalEnum) | Периодичность создания автобэкапов. Обязательно при &#x60;is_enabled&#x60;: &#x60;true&#x60;. |  [optional] |
|**dayOfWeek** | **BigDecimal** | День недели, в который будут создаваться автобэкапы. Доступные значения от &#x60;1&#x60; до &#x60;7&#x60;. Обязательно при &#x60;is_enabled&#x60;: &#x60;true&#x60; при любой периодичности, но на расписание влияет только при значении &#x60;interval&#x60;: &#x60;week&#x60;. |  [optional] |



## Enum: IntervalEnum

| Name | Value |
|---- | -----|
| DAY | &quot;day&quot; |
| WEEK | &quot;week&quot; |
| MONTH | &quot;month&quot; |



