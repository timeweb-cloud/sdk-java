

# Replica

Реплика кластера базы данных

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID реплики. Автоматически генерируется при создании. |  |
|**dbId** | **Integer** | ID кластера базы данных, которому принадлежит реплика. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Текущий статус реплики. |  |
|**localIp** | **String** | IP-адрес реплики в локальной сети. Возвращается пустая строка, если адрес ещё не назначен. |  |
|**disk** | [**ReplicaDisk**](ReplicaDisk.md) |  |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| STARTED | &quot;started&quot; |
| STOPPED | &quot;stopped&quot; |
| STARTING | &quot;starting&quot; |
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



