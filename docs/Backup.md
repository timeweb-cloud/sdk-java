

# Backup


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID резервной копии. |  |
|**name** | **String** | Название резервной копии. |  |
|**comment** | **String** | Комментарий. |  |
|**createdAt** | **OffsetDateTime** | Дата создания. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус бэкапа. |  |
|**size** | **Integer** | Размер резервной копии (Мб). |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип бэкапа. |  |
|**progress** | **BigDecimal** | Прогресс создания бэкапа. Значение будет меняться в статусе бэкапа &#x60;create&#x60; от 0 до 99, для остальных статусов всегда будет возвращаться 0. |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| PRECREATE | &quot;precreate&quot; |
| DELETE | &quot;delete&quot; |
| SHUTDOWN | &quot;shutdown&quot; |
| RECOVER | &quot;recover&quot; |
| CREATE | &quot;create&quot; |
| FAIL | &quot;fail&quot; |
| DONE | &quot;done&quot; |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| MANUAL | &quot;manual&quot; |
| AUTO | &quot;auto&quot; |



