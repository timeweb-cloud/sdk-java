

# UpdateDocument

Данные для изменения документа. Передайте только поля, которые нужно изменить

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Новое название документа |  [optional] |
|**url** | **URI** | Новый HTTP(S)-адрес. Только для документов типа &#x60;link&#x60;; после смены адреса документ переиндексируется |  [optional] |
|**indexingSchedule** | [**IndexingSchedule**](IndexingSchedule.md) |  |  [optional] |



