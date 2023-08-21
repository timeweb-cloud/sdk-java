

# VpcService


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Идентификатор сервисв. |  |
|**name** | **String** | Имя сервиса. |  |
|**publicIp** | **String** | Публичный IP-адрес сервиса |  [optional] |
|**localIp** | **String** | Приватный IP-адрес сервиса |  [optional] |
|**type** | [**TypeEnum**](#TypeEnum) | Тип сервиса. |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| SERVER | &quot;server&quot; |
| BALANCER | &quot;balancer&quot; |
| DBAAS | &quot;dbaas&quot; |



