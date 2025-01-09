

# ResourceTransfer


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**toProject** | **BigDecimal** | ID проекта, куда переносится ресурс. |  |
|**resourceId** | **BigDecimal** | ID перемещаемого ресурса (сервера, хранилища, кластера, балансировщика, базы данных или выделенного сервера). |  |
|**resourceType** | [**ResourceTypeEnum**](#ResourceTypeEnum) | Тип перемещаемого ресурса. |  |



## Enum: ResourceTypeEnum

| Name | Value |
|---- | -----|
| SERVER | &quot;server&quot; |
| BALANCER | &quot;balancer&quot; |
| DATABASE | &quot;database&quot; |
| KUBERNETES | &quot;kubernetes&quot; |
| STORAGE | &quot;storage&quot; |
| DEDICATED | &quot;dedicated&quot; |



