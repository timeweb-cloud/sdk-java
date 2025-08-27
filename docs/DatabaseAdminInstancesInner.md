

# DatabaseAdminInstancesInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**instanceId** | **BigDecimal** | ID базы данных |  |
|**privileges** | [**List&lt;PrivilegesEnum&gt;**](#List&lt;PrivilegesEnum&gt;) | Список привилегий пользователя базы данных |  |



## Enum: List&lt;PrivilegesEnum&gt;

| Name | Value |
|---- | -----|
| ALTER | &quot;ALTER&quot; |
| CREATE_VIEW | &quot;CREATE_VIEW&quot; |
| CREATE | &quot;CREATE&quot; |
| DELETE | &quot;DELETE&quot; |
| DROP | &quot;DROP&quot; |
| EVENT | &quot;EVENT&quot; |
| INDEX | &quot;INDEX&quot; |
| INSERT | &quot;INSERT&quot; |
| LOCK_TABLES | &quot;LOCK_TABLES&quot; |
| REFERENCES | &quot;REFERENCES&quot; |
| SELECT | &quot;SELECT&quot; |
| SHOW_VIEW | &quot;SHOW_VIEW&quot; |
| TRUNCATE | &quot;TRUNCATE&quot; |
| UPDATE | &quot;UPDATE&quot; |
| READ | &quot;READ&quot; |
| WRITE | &quot;WRITE&quot; |
| CONNECTION | &quot;CONNECTION&quot; |
| FAST | &quot;FAST&quot; |
| READWRITE | &quot;readWrite&quot; |
| ALTER_ROUTINE | &quot;ALTER_ROUTINE&quot; |
| CREATE_ROUTINE | &quot;CREATE_ROUTINE&quot; |
| TRANSACTION | &quot;TRANSACTION&quot; |
| SLOW_LOG | &quot;SLOW_LOG&quot; |
| TRIGGER | &quot;TRIGGER&quot; |
| CREATE_TEMPORARY_TABLES | &quot;CREATE_TEMPORARY_TABLES&quot; |



