

# Db

База данных

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор для каждого экземпляра базы данных. Автоматически генерируется при создании. |  |
|**createdAt** | **String** | Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда была создана база данных. |  |
|**accountId** | **String** | Идентификатор пользователя |  |
|**login** | **String** | Логин для подключения к базе данных. |  |
|**location** | [**LocationEnum**](#LocationEnum) | Локация сервера. |  [optional] |
|**password** | **String** | Пароль для подключения к базе данных. |  |
|**name** | **String** | Название базы данных. |  |
|**host** | **String** | Хост. |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип базы данных. |  |
|**hashType** | [**HashTypeEnum**](#HashTypeEnum) | Тип хеширования базы данных (mysql5 | mysql | postgres). |  |
|**port** | **Integer** | Порт |  |
|**ip** | **String** | IP-адрес сетевого интерфейса IPv4. |  |
|**localIp** | **String** | IP-адрес локального сетевого интерфейса IPv4. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Текущий статус базы данных. |  |
|**presetId** | **Integer** | Идентификатор тарифа. |  |
|**diskStats** | [**DbDiskStats**](DbDiskStats.md) |  |  |
|**configParameters** | [**ConfigParameters**](ConfigParameters.md) |  |  |
|**isOnlyLocalIpAccess** | **Boolean** | Это логическое значение, которое показывает, доступна ли база данных только по локальному IP адресу. |  |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| RU_2 | &quot;ru-2&quot; |
| PL_1 | &quot;pl-1&quot; |
| KZ_1 | &quot;kz-1&quot; |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| MYSQL | &quot;mysql&quot; |
| MYSQL5 | &quot;mysql5&quot; |
| POSTGRES | &quot;postgres&quot; |
| REDIS | &quot;redis&quot; |
| MONGODB | &quot;mongodb&quot; |



## Enum: HashTypeEnum

| Name | Value |
|---- | -----|
| CACHING_SHA2 | &quot;caching_sha2&quot; |
| MYSQL_NATIVE | &quot;mysql_native&quot; |
| NULL | &quot;null&quot; |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| STARTED | &quot;started&quot; |
| STARTING | &quot;starting&quot; |
| STOPED | &quot;stoped&quot; |
| NO_PAID | &quot;no_paid&quot; |



