

# DomainNameServer

Name-сервер

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**isDelegationAllowed** | **Boolean** | Это логическое значение, которое показывает включена ли услуга разрешено ли делегирование домена. |  |
|**items** | [**List&lt;DomainNameServerItemsInner&gt;**](DomainNameServerItemsInner.md) | Список name-серверов |  |
|**taskStatus** | [**TaskStatusEnum**](#TaskStatusEnum) | Статус добавления name-сервера. |  |



## Enum: TaskStatusEnum

| Name | Value |
|---- | -----|
| DONE | &quot;done&quot; |
| ACTIVE | &quot;active&quot; |
| FAILED | &quot;failed&quot; |



