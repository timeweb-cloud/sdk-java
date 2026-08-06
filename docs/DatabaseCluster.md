

# DatabaseCluster

Кластер базы данных

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID для каждого экземпляра базы данных. Автоматически генерируется при создании. |  |
|**createdAt** | **String** | Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда была создана база данных. |  |
|**location** | [**LocationEnum**](#LocationEnum) | Локация сервера. |  |
|**name** | **String** | Название кластера базы данных. |  |
|**description** | **String** | Описание кластера базы данных. |  |
|**networks** | [**List&lt;DatabaseClusterNetworksInner&gt;**](DatabaseClusterNetworksInner.md) | Список сетей кластера базы данных. |  |
|**isEnabledPublicIpv6** | **Boolean** | Использование публичного IPv6-адреса. |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип базы данных. Список возможных значений шире, чем список типов, доступных при создании нового кластера. |  |
|**hashType** | [**HashTypeEnum**](#HashTypeEnum) | Тип хеширования кластера базы данных (mysql5 | mysql | postgres). |  |
|**avatarLink** | **String** | Ссылка на аватар для базы данных. |  |
|**port** | **Integer** | Порт |  |
|**status** | [**StatusEnum**](#StatusEnum) | Текущий статус кластера базы данных. Значение &#x60;read_only&#x60; означает, что запись в кластер заблокирована из-за переполнения диска — чтобы снять блокировку, освободите место или увеличьте размер диска. |  |
|**presetId** | **Integer** | ID тарифа. Равен &#x60;null&#x60; у кластеров, созданных через конфигуратор — в этом случае заполнен &#x60;configurator_id&#x60;. |  |
|**configuratorId** | **Integer** | ID конфигуратора. Равен &#x60;null&#x60; у кластеров, созданных по тарифу. |  |
|**cpu** | **Integer** | Количество ядер процессора. |  |
|**cpuFrequency** | **String** | Частота процессора. |  |
|**isDedicatedCpu** | **Boolean** | Используются ли выделенные ядра процессора. |  |
|**ram** | **Integer** | Объем оперативной памяти (в Мб). |  |
|**disk** | [**DatabaseClusterDisk**](DatabaseClusterDisk.md) |  |  |
|**hasAdditionalDisk** | **Boolean** | Подключен ли к кластеру дополнительный диск. |  |
|**diskAutoscaling** | [**DatabaseClusterDiskAutoscaling**](DatabaseClusterDiskAutoscaling.md) |  |  |
|**configParameters** | [**Mysql**](Mysql.md) |  |  |
|**isEnabledPublicNetwork** | **Boolean** | Доступность публичного IP-адреса |  |
|**isSecureConnectionEnabled** | **Boolean** | Включено ли защищенное подключение к кластеру базы данных. |  |
|**isAutobackupsEnabled** | **Boolean** | Включены ли автоматические резервные копии кластера базы данных. |  |
|**isBackupScheduleEnabled** | **Boolean** | Включено ли расписание резервного копирования кластера базы данных. |  |
|**availabilityZone** | **AvailabilityZone** |  |  |
|**projectId** | **Integer** | ID проекта, в котором находится кластер базы данных. |  [optional] |
|**replicaList** | [**List&lt;DatabaseClusterReplicaListInner&gt;**](DatabaseClusterReplicaListInner.md) | Список реплик кластера базы данных. |  |
|**domains** | [**List&lt;DatabaseClusterDomainsInner&gt;**](DatabaseClusterDomainsInner.md) | Список доменов кластера базы данных. Если публичная сеть отключена (&#x60;is_enabled_public_network: false&#x60;), список всегда пустой. |  |
|**childServices** | [**List&lt;DatabaseClusterChildServicesInner&gt;**](DatabaseClusterChildServicesInner.md) | Список дочерних сервисов кластера базы данных. |  |
|**parentServices** | [**List&lt;DatabaseClusterParentServicesInner&gt;**](DatabaseClusterParentServicesInner.md) | Список родительских сервисов кластера базы данных. |  |
|**maintenanceSlot** | [**DatabaseClusterMaintenanceSlot**](DatabaseClusterMaintenanceSlot.md) |  |  |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| RU_3 | &quot;ru-3&quot; |
| PL_1 | &quot;pl-1&quot; |
| NL_1 | &quot;nl-1&quot; |
| DE_1 | &quot;de-1&quot; |
| US_2 | &quot;us-2&quot; |
| US_3 | &quot;us-3&quot; |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| MYSQL | &quot;mysql&quot; |
| MYSQL5 | &quot;mysql5&quot; |
| MYSQL8_4 | &quot;mysql8_4&quot; |
| POSTGRES | &quot;postgres&quot; |
| POSTGRES14 | &quot;postgres14&quot; |
| POSTGRES15 | &quot;postgres15&quot; |
| POSTGRES16 | &quot;postgres16&quot; |
| POSTGRES17 | &quot;postgres17&quot; |
| POSTGRES18 | &quot;postgres18&quot; |
| REDIS | &quot;redis&quot; |
| REDIS7 | &quot;redis7&quot; |
| REDIS8_1 | &quot;redis8_1&quot; |
| VALKEY | &quot;valkey&quot; |
| VALKEY7 | &quot;valkey7&quot; |
| VALKEY8_1 | &quot;valkey8_1&quot; |
| VALKEY9_1 | &quot;valkey9_1&quot; |
| MONGODB | &quot;mongodb&quot; |
| MONGODB4 | &quot;mongodb4&quot; |
| MONGODB6 | &quot;mongodb6&quot; |
| MONGODB7 | &quot;mongodb7&quot; |
| MONGODB8_0 | &quot;mongodb8_0&quot; |
| OPENSEARCH | &quot;opensearch&quot; |
| OPENSEARCH2_19 | &quot;opensearch2_19&quot; |
| CLICKHOUSE | &quot;clickhouse&quot; |
| CLICKHOUSE24 | &quot;clickhouse24&quot; |
| CLICKHOUSE25 | &quot;clickhouse25&quot; |
| KAFKA | &quot;kafka&quot; |
| RABBITMQ | &quot;rabbitmq&quot; |
| RABBITMQ4_0 | &quot;rabbitmq4_0&quot; |



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
| TRANSFER | &quot;transfer&quot; |
| REBOOTING | &quot;rebooting&quot; |
| TURNING_OFF | &quot;turning_off&quot; |
| TURNING_ON | &quot;turning_on&quot; |
| READ_ONLY | &quot;read_only&quot; |
| USER_TRANSFER | &quot;user_transfer&quot; |



