

# UpdateClusterV2DiskAutoscaling

Настройки автоматического расширения диска.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**isEnabled** | **Boolean** | Включить автоматическое расширение диска. |  |
|**stepSize** | **Integer** | Шаг расширения диска (в Мб). Значение должно быть кратно 5120, минимум — 5120, максимум — 102400. |  [optional] |
|**thresholdPercent** | [**ThresholdPercentEnum**](#ThresholdPercentEnum) | Порог заполнения диска (в процентах), при достижении которого диск расширяется. |  [optional] |



## Enum: ThresholdPercentEnum

| Name | Value |
|---- | -----|
| NUMBER_80 | 80 |
| NUMBER_90 | 90 |
| NUMBER_95 | 95 |



