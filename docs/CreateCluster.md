

# CreateCluster


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Название кластера базы данных. |  |
|**type** | **DbType** |  |  |
|**admin** | [**CreateClusterAdmin**](CreateClusterAdmin.md) |  |  [optional] |
|**instance** | [**CreateClusterInstance**](CreateClusterInstance.md) |  |  [optional] |
|**hashType** | [**HashTypeEnum**](#HashTypeEnum) | Тип хеширования базы данных (mysql | postgres). |  [optional] |
|**presetId** | **Integer** | ID тарифа. Нельзя передавать вместе с &#x60;configuration&#x60; |  [optional] |
|**_configuration** | [**CreateClusterConfiguration**](CreateClusterConfiguration.md) |  |  [optional] |
|**projectId** | **Integer** | ID проекта. |  [optional] |
|**configParameters** | [**Mysql**](Mysql.md) |  |  [optional] |
|**replication** | [**DbReplication**](DbReplication.md) |  |  [optional] |
|**network** | [**Network**](Network.md) |  |  [optional] |
|**isPublicIpv6** | **Boolean** | Использование IPv6 адреса. |  [optional] |
|**description** | **String** | Описание кластера базы данных |  [optional] |
|**availabilityZone** | **AvailabilityZone** |  |  [optional] |
|**autoBackups** | [**CreateDbAutoBackups**](CreateDbAutoBackups.md) |  |  [optional] |
|**backupSchedule** | [**CreateClusterBackupSchedule**](CreateClusterBackupSchedule.md) |  |  [optional] |
|**maintenanceSlot** | [**CreateClusterMaintenanceSlot**](CreateClusterMaintenanceSlot.md) |  |  [optional] |
|**diskAutoscaling** | [**CreateClusterDiskAutoscaling**](CreateClusterDiskAutoscaling.md) |  |  [optional] |



## Enum: HashTypeEnum

| Name | Value |
|---- | -----|
| CACHING_SHA2 | &quot;caching_sha2&quot; |
| MYSQL_NATIVE | &quot;mysql_native&quot; |



