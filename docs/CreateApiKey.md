

# CreateApiKey


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Имя, установленное для токена. |  |
|**expire** | **OffsetDateTime** | Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда истекает токен. |  [optional] |
|**isAbleToDelete** | **Boolean** | Это логическое значение, которое показывает, можно ли удалять управляемые сервисы при помощи данного токена без подтверждения через Телеграм, когда это подтверждение включено. |  [optional] |
|**roles** | [**List&lt;RolesEnum&gt;**](#List&lt;RolesEnum&gt;) | Роли, которые могут быть назначены токену. |  [optional] |
|**projects** | **List&lt;BigDecimal&gt;** | Список идентификаторов проектов, к которым привязан токен. Если передан null - доступ к проектам не ограничен. |  [optional] |



## Enum: List&lt;RolesEnum&gt;

| Name | Value |
|---- | -----|
| SERVERS_READ | &quot;servers:read&quot; |
| SERVERS_WRITE | &quot;servers:write&quot; |
| DATABASES_READ | &quot;databases:read&quot; |
| DATABASES_WRITE | &quot;databases:write&quot; |
| BALANCERS_READ | &quot;balancers:read&quot; |
| BALANCERS_WRITE | &quot;balancers:write&quot; |
| STORAGES_READ | &quot;storages:read&quot; |
| STORAGES_WRITE | &quot;storages:write&quot; |
| DEDICATED_READ | &quot;dedicated:read&quot; |
| DEDICATED_WRITE | &quot;dedicated:write&quot; |
| CLUSTERS_READ | &quot;clusters:read&quot; |
| CLUSTERS_WRITE | &quot;clusters:write&quot; |
| VPC_READ | &quot;vpc:read&quot; |
| VPC_WRITE | &quot;vpc:write&quot; |
| FLOATING_IPS_READ | &quot;floating-ips:read&quot; |
| FLOATING_IPS_WRITE | &quot;floating-ips:write&quot; |
| DOMAINS_READ | &quot;domains:read&quot; |
| DOMAINS_WRITE | &quot;domains:write&quot; |
| ADMINISTRATORS_WRITE | &quot;administrators:write&quot; |
| FIREWALL_READ | &quot;firewall:read&quot; |
| FIREWALL_READ | &quot;firewall:read&quot; |
| FINANCES_WRITE | &quot;finances:write&quot; |
| SUPPORT_READ | &quot;support:read&quot; |
| SUPPORT_WRITE | &quot;support:write&quot; |
| VPN_READ | &quot;vpn:read&quot; |
| VPN_WRITE | &quot;vpn:write&quot; |
| MAIL_READ | &quot;mail:read&quot; |
| MAIL_WRITE | &quot;mail:write&quot; |
| APPS_READ | &quot;apps:read&quot; |
| APPS_WRITE | &quot;apps:write&quot; |
| NETWORK_DRIVES_READ | &quot;network-drives:read&quot; |
| NETWORK_DRIVES_WRITE | &quot;network-drives:write&quot; |



