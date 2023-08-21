

# ServersConfigurator


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор конфигуратора сервера. |  |
|**location** | [**LocationEnum**](#LocationEnum) | Локация сервера. |  |
|**diskType** | [**DiskTypeEnum**](#DiskTypeEnum) | Тип диска. |  |
|**isAllowedLocalNetwork** | **Boolean** | Есть возможность подключения локальной сети |  |
|**cpuFrequency** | **String** | Частота процессора. |  |
|**requirements** | [**ServersConfiguratorRequirements**](ServersConfiguratorRequirements.md) |  |  |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| PL_1 | &quot;pl-1&quot; |
| KZ_1 | &quot;kz-1&quot; |



## Enum: DiskTypeEnum

| Name | Value |
|---- | -----|
| SSD | &quot;ssd&quot; |
| NVME | &quot;nvme&quot; |
| HDD | &quot;hdd&quot; |



