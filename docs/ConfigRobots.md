

# ConfigRobots

Настройки robots.txt для доменов ресурса

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) | Режим отдачи &#x60;robots.txt&#x60;. - &#x60;deny&#x60; — CDN отдает &#x60;robots.txt&#x60;, запрещающий индексацию; - &#x60;cached&#x60; — &#x60;robots.txt&#x60; берется с источника контента; - &#x60;custom&#x60; — CDN отдает содержимое из поля &#x60;content&#x60;. |  |
|**content** | **String** | Содержимое &#x60;robots.txt&#x60;. Обязательно и учитывается только при &#x60;type&#x60; &#x3D; &#x60;custom&#x60;. |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| DENY | &quot;deny&quot; |
| CACHED | &quot;cached&quot; |
| CUSTOM | &quot;custom&quot; |



