

# DatabaseCluster

Кластер базы данных

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID для каждого экземпляра базы данных. Автоматически генерируется при создании. |  |
|**createdAt** | **String** | Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда была создана база данных. |  |
|**location** | [**LocationEnum**](#LocationEnum) | Локация сервера. |  |
|**name** | **String** | Название кластера базы данных. |  |
|**networks** | [**List&lt;DatabaseClusterNetworksInner&gt;**](DatabaseClusterNetworksInner.md) | Список сетей кластера базы данных. |  |
|**type** | **DbType** |  |  |
|**hashType** | [**HashTypeEnum**](#HashTypeEnum) | Тип хеширования кластера базы данных (mysql5 | mysql | postgres). |  |
|**avatarLink** | **String** | Ссылка на аватар для базы данных. |  |
|**port** | **Integer** | Порт |  |
|**status** | [**StatusEnum**](#StatusEnum) | Текущий статус кластера базы данных. |  |
|**presetId** | **Integer** | ID тарифа. |  |
|**disk** | [**DatabaseClusterDisk**](DatabaseClusterDisk.md) |  |  [optional] |
|**configParameters** | [**ConfigParameters**](ConfigParameters.md) |  |  |
|**isEnabledPublicNetwork** | **Boolean** | Доступность публичного IP-адреса |  |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| RU_3 | &quot;ru-3&quot; |
| NL_1 | &quot;nl-1&quot; |
| DE_1 | &quot;de-1&quot; |



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
| STOPPED | &quot;stopped&quot; |
| NO_PAID | &quot;no_paid&quot; |
| LAN_TRANSFER | &quot;lan_transfer&quot; |
| ERROR | &quot;error&quot; |
| BLOCKED | &quot;blocked&quot; |
| BACKUP_RECOVERY | &quot;backup_recovery&quot; |
| REBOOTING | &quot;rebooting&quot; |
| TURNING_OFF | &quot;turning_off&quot; |
| TURNING_ON | &quot;turning_on&quot; |



