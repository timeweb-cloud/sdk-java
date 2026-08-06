

# UpdateClusterV2

Изменение кластера базы данных (v2)

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Название кластера базы данных. |  [optional] |
|**presetId** | **Integer** | ID тарифа. Нельзя передавать вместе с &#x60;configuration&#x60; |  [optional] |
|**_configuration** | [**UpdateClusterConfiguration**](UpdateClusterConfiguration.md) |  |  [optional] |
|**configParameters** | [**Mysql**](Mysql.md) |  |  [optional] |
|**hashType** | [**HashTypeEnum**](#HashTypeEnum) | Тип хеширования базы данных (mysql | postgres). |  [optional] |
|**description** | **String** | Описание кластера базы данных |  [optional] |
|**isEnabledPublicNetwork** | **Boolean** | Доступность публичного IP-адреса |  [optional] |
|**isEnabledPublicIpv6** | **Boolean** | Использование публичного IPv6-адреса. |  [optional] |
|**floatingIp** | **String** | Плавающий IP-адрес, который нужно привязать к кластеру базы данных. Передается сам адрес, а не его ID; адрес должен быть свободен (не привязан к другому сервису). |  [optional] |
|**isSecureConnectionEnable** | **Boolean** | Включить защищенное подключение к кластеру базы данных. Обратите внимание: в ответе это же значение возвращается под ключом &#x60;is_secure_connection_enabled&#x60;. |  [optional] |
|**maintenanceSlot** | [**UpdateClusterV2MaintenanceSlot**](UpdateClusterV2MaintenanceSlot.md) |  |  [optional] |
|**diskAutoscaling** | [**UpdateClusterV2DiskAutoscaling**](UpdateClusterV2DiskAutoscaling.md) |  |  [optional] |



## Enum: HashTypeEnum

| Name | Value |
|---- | -----|
| CACHING_SHA2 | &quot;caching_sha2&quot; |
| MYSQL_NATIVE | &quot;mysql_native&quot; |



