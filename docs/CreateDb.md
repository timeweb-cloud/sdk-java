

# CreateDb


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**login** | **String** | Логин для подключения к базе данных. |  [optional] |
|**password** | **String** | Пароль для подключения к базе данных. |  |
|**name** | **String** | Название базы данных. |  |
|**type** | **DbType** |  |  |
|**hashType** | [**HashTypeEnum**](#HashTypeEnum) | Тип хеширования базы данных (mysql5 | mysql | postgres). |  [optional] |
|**presetId** | **Integer** | Идентификатор тарифа. |  |
|**configParameters** | [**ConfigParameters**](ConfigParameters.md) |  |  [optional] |
|**network** | [**Network**](Network.md) |  |  [optional] |



## Enum: HashTypeEnum

| Name | Value |
|---- | -----|
| CACHING_SHA2 | &quot;caching_sha2&quot; |
| MYSQL_NATIVE | &quot;mysql_native&quot; |



