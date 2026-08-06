

# ConfigAccess

Настройки доступа к ресурсу. Секция доступна только для чтения — изменить ее через API нельзя.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**allowedMethods** | [**List&lt;AllowedMethodsEnum&gt;**](#List&lt;AllowedMethodsEnum&gt;) | Дополнительные HTTP-методы, разрешенные при обращении к ресурсу. &#x60;null&#x60; — используется набор методов по умолчанию. |  [optional] [readonly] |



## Enum: List&lt;AllowedMethodsEnum&gt;

| Name | Value |
|---- | -----|
| POST | &quot;POST&quot; |
| PUT | &quot;PUT&quot; |
| PATCH | &quot;PATCH&quot; |
| DELETE | &quot;DELETE&quot; |
| MKCOL | &quot;MKCOL&quot; |
| COPY | &quot;COPY&quot; |
| MOVE | &quot;MOVE&quot; |
| PROPFIND | &quot;PROPFIND&quot; |
| PROPPATCH | &quot;PROPPATCH&quot; |
| LOCK | &quot;LOCK&quot; |
| UNLOCK | &quot;UNLOCK&quot; |



