

# UpdateAdmin


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**password** | **String** | Пароль пользователя базы данных |  [optional] |
|**privileges** | [**List&lt;PrivilegesEnum&gt;**](#List&lt;PrivilegesEnum&gt;) | Список привилегий пользователя базы данных |  [optional] |
|**description** | **String** | Описание пользователя базы данных |  [optional] |
|**instanceId** | **BigDecimal** | ID инстанса базы данных для применения привилегий. Если поле не передано, то привилегии будут применены ко всем инстансам |  [optional] |



## Enum: List&lt;PrivilegesEnum&gt;

| Name | Value |
|---- | -----|
| ALTER | &quot;ALTER&quot; |
| ALTER_TABLE | &quot;ALTER_TABLE&quot; |
| ALTER_VIEW | &quot;ALTER_VIEW&quot; |
| CREATE_VIEW | &quot;CREATE_VIEW&quot; |
| CREATE_DICTIONARY | &quot;CREATE_DICTIONARY&quot; |
| CREATE_FUNCTION | &quot;CREATE_FUNCTION&quot; |
| CREATE_TABLE | &quot;CREATE_TABLE&quot; |
| CREATE | &quot;CREATE&quot; |
| DELETE | &quot;DELETE&quot; |
| DROP | &quot;DROP&quot; |
| DROP_TABLE | &quot;DROP_TABLE&quot; |
| DROP_VIEW | &quot;DROP_VIEW&quot; |
| DROP_DICTIONARY | &quot;DROP_DICTIONARY&quot; |
| EVENT | &quot;EVENT&quot; |
| INDEX | &quot;INDEX&quot; |
| INSERT | &quot;INSERT&quot; |
| LOCK_TABLES | &quot;LOCK_TABLES&quot; |
| REFERENCES | &quot;REFERENCES&quot; |
| SELECT | &quot;SELECT&quot; |
| SHOW | &quot;SHOW&quot; |
| SHOW_VIEW | &quot;SHOW_VIEW&quot; |
| TRUNCATE | &quot;TRUNCATE&quot; |
| TRIGGER | &quot;TRIGGER&quot; |
| UPDATE | &quot;UPDATE&quot; |
| READ | &quot;READ&quot; |
| WRITE | &quot;WRITE&quot; |
| READ_WRITE | &quot;READ_WRITE&quot; |
| DB_ADMIN | &quot;DB_ADMIN&quot; |
| ALTER_ROUTINE | &quot;ALTER_ROUTINE&quot; |
| CREATE_ROUTINE | &quot;CREATE_ROUTINE&quot; |
| CREATE_TEMPORARY_TABLES | &quot;CREATE_TEMPORARY_TABLES&quot; |
| TEMPORARY | &quot;TEMPORARY&quot; |
| CONFIGURE | &quot;CONFIGURE&quot; |
| READ_DASHBOARD | &quot;READ_DASHBOARD&quot; |
| WRITE_DASHBOARD | &quot;WRITE_DASHBOARD&quot; |
| DESCRIBE | &quot;DESCRIBE&quot; |
| OPTIMIZE | &quot;OPTIMIZE&quot; |
| EXECUTE | &quot;EXECUTE&quot; |
| CREATEDB | &quot;CREATEDB&quot; |
| CREATEROLE | &quot;CREATEROLE&quot; |
| CREATE_DB | &quot;CREATE_DB&quot; |
| CREATE_USER | &quot;CREATE_USER&quot; |
| PROCESS | &quot;PROCESS&quot; |
| SLOW_LOG | &quot;SLOW_LOG&quot; |
| CREATE_TEMPORARY_TABLE | &quot;CREATE_TEMPORARY_TABLE&quot; |
| ADMIN | &quot;ADMIN&quot; |
| BITMAP | &quot;BITMAP&quot; |
| BLOCKING | &quot;BLOCKING&quot; |
| CONNECTION | &quot;CONNECTION&quot; |
| DANGEROUS | &quot;DANGEROUS&quot; |
| GEO | &quot;GEO&quot; |
| HASH | &quot;HASH&quot; |
| HYPERLOGLOG | &quot;HYPERLOGLOG&quot; |
| FAST | &quot;FAST&quot; |
| KEYSPACE | &quot;KEYSPACE&quot; |
| LIST | &quot;LIST&quot; |
| PUBSUB | &quot;PUBSUB&quot; |
| SCRIPTING | &quot;SCRIPTING&quot; |
| SET | &quot;SET&quot; |
| SORTEDSET | &quot;SORTEDSET&quot; |
| SLOW | &quot;SLOW&quot; |
| STREAM | &quot;STREAM&quot; |
| STRING | &quot;STRING&quot; |
| TRANSACTION | &quot;TRANSACTION&quot; |
| DICTGET | &quot;dictGet&quot; |
| DBADMIN | &quot;dbAdmin&quot; |
| READWRITE | &quot;readWrite&quot; |



