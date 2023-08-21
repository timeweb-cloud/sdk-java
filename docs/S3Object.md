

# S3Object

An object consists of data and its descriptive metadata.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**key** | **String** | Название файла или папки. |  [optional] |
|**lastModified** | **OffsetDateTime** | Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда была сделана последняя модификация файла или папки. |  [optional] |
|**etag** | **String** | Тег. |  [optional] |
|**size** | **Integer** | Размер (в байтах) файла или папки. |  [optional] |
|**storageClass** | **String** | Класс хранилища. |  [optional] |
|**checksumAlgorithm** | **List&lt;String&gt;** | Алгоритм |  [optional] |
|**owner** | [**S3ObjectOwner**](S3ObjectOwner.md) |  |  [optional] |
|**type** | [**TypeEnum**](#TypeEnum) | Тип (файл или папка). |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| FILE | &quot;file&quot; |
| DIRECTORY | &quot;directory&quot; |



