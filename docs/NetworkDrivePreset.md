

# NetworkDrivePreset


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор тарифа. |  |
|**costPerGb** | **BigDecimal** | Стоимость тарифа сетевого диска. |  |
|**min** | **BigDecimal** | Минимальный размер диска (в Гб). |  |
|**max** | **BigDecimal** | Максимальный размер диска (в Гб). |  |
|**step** | **BigDecimal** | Размер шага диска |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип сетевого диска. |  |
|**read** | [**NetworkDrivePresetRead**](NetworkDrivePresetRead.md) |  |  |
|**write** | [**NetworkDrivePresetWrite**](NetworkDrivePresetWrite.md) |  |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| NVME | &quot;nvme&quot; |
| HDD | &quot;hdd&quot; |



