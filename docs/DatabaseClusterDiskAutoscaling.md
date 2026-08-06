

# DatabaseClusterDiskAutoscaling

Настройки автоматического расширения диска.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**isEnabled** | **Boolean** | Включено ли автоматическое расширение диска. |  |
|**stepSize** | **Integer** | Шаг расширения диска (в Мб). |  |
|**thresholdPercent** | [**ThresholdPercentEnum**](#ThresholdPercentEnum) | Порог заполнения диска (в процентах), при достижении которого диск расширяется. |  |



## Enum: ThresholdPercentEnum

| Name | Value |
|---- | -----|
| NUMBER_80 | 80 |
| NUMBER_90 | 90 |
| NUMBER_95 | 95 |



