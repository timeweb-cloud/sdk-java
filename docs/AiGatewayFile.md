

# AiGatewayFile

Файл

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID файла |  [optional] |
|**_object** | [**ObjectEnum**](#ObjectEnum) | Тип объекта |  [optional] |
|**bytes** | **Integer** | Размер файла в байтах |  [optional] |
|**createdAt** | **Integer** | Время загрузки, Unix-время в секундах |  [optional] |
|**filename** | **String** | Имя файла |  [optional] |
|**purpose** | **String** | Назначение файла |  [optional] |
|**status** | **String** | Статус обработки файла, например &#x60;uploaded&#x60; или &#x60;processed&#x60; |  [optional] |
|**expiresAt** | **Integer** | Когда файл будет удалён, Unix-время в секундах. &#x60;null&#x60; — не ограничено |  [optional] |



## Enum: ObjectEnum

| Name | Value |
|---- | -----|
| FILE | &quot;file&quot; |



