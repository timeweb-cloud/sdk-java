

# TokenPackage

Пакет токенов

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID пакета |  |
|**modelId** | **Integer** | ID модели, к которой относится пакет |  |
|**serviceTypeId** | **Integer** | ID тарифа пакета в биллинге |  |
|**name** | **String** | Название пакета |  |
|**packageType** | [**PackageTypeEnum**](#PackageTypeEnum) | Тип пакета: &#x60;base&#x60; — пакет подписки, &#x60;additional&#x60; — дополнительный пакет, &#x60;promo&#x60; — промо-пакет |  |
|**type** | [**TypeEnum**](#TypeEnum) | Для чего пакет: &#x60;agent&#x60; — AI агент, &#x60;knowledgebase&#x60; — база знаний |  |
|**tokenAmount** | **Integer** | Количество токенов в пакете |  |
|**price** | **BigDecimal** | Цена пакета в рублях |  |
|**durationDays** | **Integer** | Период подписки в днях. &#x60;null&#x60; для дополнительных пакетов |  |
|**isAvailable** | **Boolean** | Пакет доступен для покупки |  |



## Enum: PackageTypeEnum

| Name | Value |
|---- | -----|
| BASE | &quot;base&quot; |
| ADDITIONAL | &quot;additional&quot; |
| PROMO | &quot;promo&quot; |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| AGENT | &quot;agent&quot; |
| KNOWLEDGEBASE | &quot;knowledgebase&quot; |



