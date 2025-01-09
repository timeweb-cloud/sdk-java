

# ServersPreset


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID тарифа сервера. |  |
|**location** | [**LocationEnum**](#LocationEnum) | Локация сервера. |  |
|**price** | **BigDecimal** | Стоимость в рублях. |  |
|**cpu** | **BigDecimal** | Количество ядер процессора. |  |
|**cpuFrequency** | **String** | Частота процессора. |  |
|**ram** | **BigDecimal** | Количество (в Мб) оперативной памяти. |  |
|**disk** | **BigDecimal** | Размер диска (в Мб). |  |
|**diskType** | [**DiskTypeEnum**](#DiskTypeEnum) | Тип диска. |  |
|**bandwidth** | **BigDecimal** | Пропускная способность тарифа. |  |
|**description** | **String** | Описание тарифа. |  |
|**descriptionShort** | **String** | Короткое описание тарифа. |  |
|**isAllowedLocalNetwork** | **Boolean** | Есть возможность подключения локальной сети |  |
|**tags** | **List&lt;String&gt;** | Список тегов тарифа. |  |



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



