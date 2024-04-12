

# FloatingIp


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | Идентификатор IP. |  |
|**ip** | **String** | IP-адрес |  |
|**isDdosGuard** | **Boolean** | Это логическое значение, которое показывает, включена ли защита от DDoS. |  |
|**availabilityZone** | **AvailabilityZone** |  |  |
|**resourceType** | [**ResourceTypeEnum**](#ResourceTypeEnum) | Тип ресурса. |  |
|**resourceId** | **BigDecimal** | Id ресурса. |  |
|**comment** | **String** | Комментарий |  |
|**ptr** | **String** | Запись имени узла. |  |



## Enum: ResourceTypeEnum

| Name | Value |
|---- | -----|
| SERVER | &quot;server&quot; |
| BALANCER | &quot;balancer&quot; |
| DATABASE | &quot;database&quot; |
| NETWORK | &quot;network&quot; |



