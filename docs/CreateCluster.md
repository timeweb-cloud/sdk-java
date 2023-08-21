

# CreateCluster


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Название кластера базы данных. |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип базы данных. |  |
|**admin** | [**CreateClusterAdmin**](CreateClusterAdmin.md) |  |  [optional] |
|**instance** | [**CreateClusterInstance**](CreateClusterInstance.md) |  |  [optional] |
|**hashType** | [**HashTypeEnum**](#HashTypeEnum) | Тип хеширования базы данных (mysql5 | mysql | postgres). |  [optional] |
|**presetId** | **Integer** | Идентификатор тарифа. |  |
|**configParameters** | [**ConfigParameters**](ConfigParameters.md) |  |  [optional] |
|**network** | [**Network**](Network.md) |  |  [optional] |
|**description** | **String** | Описание кластера базы данных |  [optional] |



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



