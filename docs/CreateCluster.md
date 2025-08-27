

# CreateCluster


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Название кластера базы данных. |  |
|**type** | **DbType** |  |  |
|**admin** | [**CreateClusterAdmin**](CreateClusterAdmin.md) |  |  [optional] |
|**instance** | [**CreateClusterInstance**](CreateClusterInstance.md) |  |  [optional] |
|**hashType** | [**HashTypeEnum**](#HashTypeEnum) | Тип хеширования базы данных (mysql | postgres). |  [optional] |
|**presetId** | **Integer** | ID тарифа. Нельзя передавать вместе с &#x60;configurator_id&#x60; |  [optional] |
|**configuratorId** | **Integer** | ID конфигуратора. Нельзя передавать вместе с &#x60;preset_id&#x60; |  [optional] |
|**projectId** | **Integer** | ID проекта. |  [optional] |
|**configParameters** | [**ConfigParameters**](ConfigParameters.md) |  |  [optional] |
|**replication** | [**DbReplication**](DbReplication.md) |  |  [optional] |
|**network** | [**Network**](Network.md) |  |  [optional] |
|**description** | **String** | Описание кластера базы данных |  [optional] |
|**availabilityZone** | **AvailabilityZone** |  |  [optional] |
|**autoBackups** | [**CreateDbAutoBackups**](CreateDbAutoBackups.md) |  |  [optional] |



## Enum: HashTypeEnum

| Name | Value |
|---- | -----|
| CACHING_SHA2 | &quot;caching_sha2&quot; |
| MYSQL_NATIVE | &quot;mysql_native&quot; |



