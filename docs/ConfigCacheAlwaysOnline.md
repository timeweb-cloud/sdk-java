

# ConfigCacheAlwaysOnline

Отдавать устаревший контент из кэша, если источник недоступен. `null` — отключить.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**staleConditions** | [**List&lt;StaleConditionsEnum&gt;**](#List&lt;StaleConditionsEnum&gt;) | Условия, при которых из кэша отдается устаревший контент. |  |



## Enum: List&lt;StaleConditionsEnum&gt;

| Name | Value |
|---- | -----|
| ERROR | &quot;error&quot; |
| TIMEOUT | &quot;timeout&quot; |
| INVALID_HEADER | &quot;invalid_header&quot; |
| UPDATING | &quot;updating&quot; |
| HTTP_403 | &quot;http_403&quot; |
| HTTP_404 | &quot;http_404&quot; |
| HTTP_429 | &quot;http_429&quot; |
| HTTP_500 | &quot;http_500&quot; |
| HTTP_502 | &quot;http_502&quot; |
| HTTP_503 | &quot;http_503&quot; |
| HTTP_504 | &quot;http_504&quot; |



