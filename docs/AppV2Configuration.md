

# AppV2Configuration

Конфигурация сервера backend-приложения. До создания сервера поле отсутствует. Для frontend — null.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**cpu** | **BigDecimal** | Количество ядер процессора. |  [optional] |
|**ram** | **BigDecimal** | Объем оперативной памяти (в МБ). |  [optional] |
|**networkBandwidth** | **BigDecimal** | Скорость сетевого канала (в Мбит/с). |  [optional] |
|**cpuFrequency** | **String** | Частота процессора (в ГГц). |  [optional] |
|**diskType** | [**DiskTypeEnum**](#DiskTypeEnum) | Тип диска. |  [optional] |



## Enum: DiskTypeEnum

| Name | Value |
|---- | -----|
| SSD | &quot;ssd&quot; |
| NVME | &quot;nvme&quot; |
| HDD | &quot;hdd&quot; |



