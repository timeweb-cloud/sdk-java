

# ConfigCacheQueryArgs

Правила учета query-параметров в ключе кэша. `null` — не учитывать query-параметры.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**mode** | [**ModeEnum**](#ModeEnum) | Режим учета query-параметров. - &#x60;all&#x60; — учитывать все параметры; - &#x60;whitelist&#x60; — учитывать только параметры из &#x60;list&#x60;; - &#x60;blacklist&#x60; — учитывать все параметры, кроме перечисленных в &#x60;list&#x60;. |  |
|**_list** | **List&lt;String&gt;** | Список query-параметров для режимов &#x60;whitelist&#x60; и &#x60;blacklist&#x60;. |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| ALL | &quot;all&quot; |
| WHITELIST | &quot;whitelist&quot; |
| BLACKLIST | &quot;blacklist&quot; |



