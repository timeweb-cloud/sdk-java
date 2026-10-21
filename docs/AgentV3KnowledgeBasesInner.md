

# AgentV3KnowledgeBasesInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID базы знаний |  |
|**retrievalMode** | [**RetrievalModeEnum**](#RetrievalModeEnum) | Режим поиска по базе знаний: &#x60;classic&#x60; — найденные фрагменты автоматически добавляются к каждому запросу; &#x60;agentic&#x60; — агент сам решает, когда искать в базе знаний. |  |



## Enum: RetrievalModeEnum

| Name | Value |
|---- | -----|
| CLASSIC | &quot;classic&quot; |
| AGENTIC | &quot;agentic&quot; |



