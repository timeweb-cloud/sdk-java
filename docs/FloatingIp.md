

# FloatingIp


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | Идентификатор IP. |  |
|**ip** | **String** | IP-адрес |  |
|**isDdosGuard** | **Boolean** | Это логическое значение, которое показывает, включена ли защита от DDoS. |  |
|**availabilityZone** | **AvailabilityZone** |  |  |
|**resourceType** | [**ResourceTypeEnum**](#ResourceTypeEnum) | Тип ресурса. |  [optional] |
|**resourceId** | **BigDecimal** | Id ресурса. |  [optional] |
|**comment** | **String** | Комментарий |  [optional] |
|**ptr** | **String** | Запись имени узла. |  [optional] |



## Enum: ResourceTypeEnum

| Name | Value |
|---- | -----|
| SERVER | &quot;server&quot; |
| BALANCER | &quot;balancer&quot; |
| DATABASE | &quot;database&quot; |



