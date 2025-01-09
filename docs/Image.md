

# Image


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID образа. |  |
|**status** | **ImageStatus** |  |  |
|**createdAt** | **String** | Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда был создан образ. |  |
|**deletedAt** | **String** | Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда был удален образ. |  |
|**size** | **Integer** | Размер физического диска в мегабайтах. |  |
|**virtualSize** | **Integer** | Размер виртуального диска в мегабайтах. |  |
|**name** | **String** | Имя образа. |  |
|**description** | **String** | Описание образа. |  |
|**diskId** | **Integer** | ID связанного с образом диска. |  |
|**location** | [**LocationEnum**](#LocationEnum) | Локация образа. |  |
|**os** | **OS** |  |  |
|**progress** | **Integer** | Процент создания образа. |  |
|**isCustom** | **Boolean** | Логическое значение, которое показывает, является ли образ кастомным. |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип образа. |  |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| RU_2 | &quot;ru-2&quot; |
| PL_1 | &quot;pl-1&quot; |
| KZ_1 | &quot;kz-1&quot; |
| NL_1 | &quot;nl-1&quot; |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| QCOW2 | &quot;qcow2&quot; |
| ISO | &quot;iso&quot; |



