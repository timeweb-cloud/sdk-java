

# ToolAuthData

Авторизация на MCP-сервере

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) | Способ авторизации |  |
|**data** | [**ToolAuthDataParams**](ToolAuthDataParams.md) |  |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| NONE | &quot;none&quot; |
| BEARER | &quot;bearer&quot; |
| CUSTOM_HEADERS | &quot;custom_headers&quot; |
| OAUTH2 | &quot;oauth2&quot; |



