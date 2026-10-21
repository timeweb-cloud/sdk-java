

# CreateAiSolution

Параметры создания AI-решения

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) | Тип решения. Доступные типы возвращает метод получения типов AI-решений |  |
|**name** | **String** | Название решения. Используется и как имя сервера |  |
|**description** | **String** | Описание решения. Используется и как комментарий к серверу |  [optional] |
|**modelId** | **Integer** | ID модели, с которой будет работать решение. Подходят только действующие текстовые модели (&#x60;type: llm&#x60; в &#x60;GET /api/v3/cloud-ai/models&#x60;) |  |
|**presetId** | **Integer** | ID тарифа сервера из &#x60;GET /api/v1/presets/servers&#x60;. Тариф должен быть не ниже требований типа решения (при создании это не проверяется) и из той же локации, что и зона доступности. Баланса аккаунта должно хватать хотя бы на час работы тарифа |  |
|**projectId** | **Integer** | ID проекта, в который добавляются сервер и ключ AI Gateway решения |  [optional] |
|**availabilityZone** | [**AvailabilityZoneEnum**](#AvailabilityZoneEnum) | Зона доступности сервера. Если не указана, используется зона по умолчанию для региона аккаунта. Зона должна относиться к локации тарифа, а образ решения должен быть доступен в этой зоне |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| OPENCLAW | &quot;openclaw&quot; |



## Enum: AvailabilityZoneEnum

| Name | Value |
|---- | -----|
| SPB_1 | &quot;spb-1&quot; |
| SPB_2 | &quot;spb-2&quot; |
| SPB_3 | &quot;spb-3&quot; |
| SPB_4 | &quot;spb-4&quot; |
| SPB_5 | &quot;spb-5&quot; |
| NSK_1 | &quot;nsk-1&quot; |
| EKB_1 | &quot;ekb-1&quot; |
| KZN_1 | &quot;kzn-1&quot; |
| AMS_1 | &quot;ams-1&quot; |
| GDN_1 | &quot;gdn-1&quot; |
| ALA_1 | &quot;ala-1&quot; |
| NYC_1 | &quot;nyc-1&quot; |
| SJC_1 | &quot;sjc-1&quot; |
| MSK_1 | &quot;msk-1&quot; |
| FRA_1 | &quot;fra-1&quot; |
| HEL_1 | &quot;hel-1&quot; |
| STA_1 | &quot;sta-1&quot; |
| BUF_2 | &quot;buf-2&quot; |
| PAR_1 | &quot;par-1&quot; |
| LON_1 | &quot;lon-1&quot; |



