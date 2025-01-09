

# ServerBackup


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID бэкапа сервера. |  |
|**name** | **String** | Название бэкапа. |  |
|**comment** | **String** | Комментарий к бэкапу. |  |
|**createdAt** | **String** | Дата создания бэкапа. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус бэкапа. |  |
|**size** | **BigDecimal** | Размер бэкапа (в Мб). |  |
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



