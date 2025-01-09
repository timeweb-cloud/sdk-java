

# ProjectResource

Ресурс проекта

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID для каждого ресурса проекта. Автоматически генерируется при создании. |  |
|**createdAt** | **String** | Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда был создан ресурс. |  |
|**resourceId** | **BigDecimal** | ID ресурса проекта (сервера, хранилища, кластера, балансировщика, базы данных или выделенного сервера). |  |
|**project** | [**Project**](Project.md) |  |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип ресурса проекта |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| SERVER | &quot;server&quot; |
| BALANCER | &quot;balancer&quot; |
| DATABASE | &quot;database&quot; |
| KUBERNETES | &quot;kubernetes&quot; |
| STORAGE | &quot;storage&quot; |
| DEDICATED | &quot;dedicated&quot; |



