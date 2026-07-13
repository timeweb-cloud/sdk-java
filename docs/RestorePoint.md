

# RestorePoint


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID снапшота. |  |
|**createdAt** | **OffsetDateTime** | Дата и время создания снапшота в формате ISO 8601. |  |
|**expiredAt** | **OffsetDateTime** | Дата и время истечения снапшота в формате ISO 8601. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус снапшота.  - &#x60;creating&#x60; — создаётся; - &#x60;created&#x60; — создан; - &#x60;committed&#x60; — зафиксирован; - &#x60;rolled_back&#x60; — откачен; - &#x60;error&#x60; — ошибка; - &#x60;deleted&#x60; — удалён. |  |
|**vdsId** | **BigDecimal** | ID облачного сервера (VDS), к которому относится снапшот. |  |
|**accountId** | **String** | ID аккаунта-владельца снапшота. |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| CREATING | &quot;creating&quot; |
| CREATED | &quot;created&quot; |
| COMMITTED | &quot;committed&quot; |
| ROLLED_BACK | &quot;rolled_back&quot; |
| ERROR | &quot;error&quot; |
| DELETED | &quot;deleted&quot; |



