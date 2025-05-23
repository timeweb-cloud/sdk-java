

# Bucket

Хранилище S3

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID для каждого экземпляра хранилища. Автоматически генерируется при создании. |  |
|**name** | **String** | Удобочитаемое имя, установленное для хранилища. |  |
|**description** | **String** | Комментарий к хранилищу. |  [optional] |
|**diskStats** | [**BucketDiskStats**](BucketDiskStats.md) |  |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип хранилища. |  |
|**presetId** | **BigDecimal** | ID тарифа хранилища. |  |
|**configuratorId** | **BigDecimal** | ID конфигуратора хранилища. |  |
|**avatarLink** | **String** | Ссылка на аватар хранилища. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус хранилища. |  |
|**objectAmount** | **BigDecimal** | Количество файлов в хранилище. |  |
|**location** | **String** | Регион хранилища. |  |
|**hostname** | **String** | Адрес хранилища для подключения. |  |
|**accessKey** | **String** | Ключ доступа от хранилища. |  |
|**secretKey** | **String** | Секретный ключ доступа от хранилища. |  |
|**movedInQuarantineAt** | **OffsetDateTime** | Дата перемещения в карантин. |  |
|**storageClass** | [**StorageClassEnum**](#StorageClassEnum) | Класс хранилища. |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| PRIVATE | &quot;private&quot; |
| PUBLIC | &quot;public&quot; |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| NO_PAID | &quot;no_paid&quot; |
| CREATED | &quot;created&quot; |
| TRANSFER | &quot;transfer&quot; |



## Enum: StorageClassEnum

| Name | Value |
|---- | -----|
| COLD | &quot;cold&quot; |
| HOT | &quot;hot&quot; |



