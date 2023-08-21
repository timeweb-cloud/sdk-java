

# SshKey


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор SSH-ключа |  |
|**name** | **String** | Название SSH-ключа |  |
|**body** | **String** | Тело SSH-ключа |  |
|**createdAt** | **OffsetDateTime** | Дата создания ключа |  |
|**usedBy** | [**List&lt;SshKeyUsedByInner&gt;**](SshKeyUsedByInner.md) | Список серверов, которые используют SSH-ключ |  |
|**isDefault** | **Boolean** | Будет ли выбираться SSh-ключ по умолчанию при создании сервера |  [optional] |



