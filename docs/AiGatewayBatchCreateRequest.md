

# AiGatewayBatchCreateRequest

Запрос создания батча

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**inputFileId** | **String** | ID файла, загруженного с &#x60;purpose&#x3D;batch&#x60; |  |
|**endpoint** | [**EndpointEnum**](#EndpointEnum) | Эндпоинт запросов батча — должен совпадать с &#x60;url&#x60; каждой строки входного файла |  |
|**completionWindow** | [**CompletionWindowEnum**](#CompletionWindowEnum) | Срок выполнения |  |
|**metadata** | **Map&lt;String, String&gt;** | Ваши метки батча: пары «строка — строка» |  [optional] |



## Enum: EndpointEnum

| Name | Value |
|---- | -----|
| CHAT_COMPLETIONS | &quot;/v1/chat/completions&quot; |
| EMBEDDINGS | &quot;/v1/embeddings&quot; |



## Enum: CompletionWindowEnum

| Name | Value |
|---- | -----|
| _24H | &quot;24h&quot; |



