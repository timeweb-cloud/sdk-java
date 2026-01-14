

# TokenPackage

Пакет токенов

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор пакета |  |
|**modelId** | **BigDecimal** | ID модели, к которой применяется пакет токенов |  |
|**name** | **String** | Название пакета токенов |  |
|**packageType** | [**PackageTypeEnum**](#PackageTypeEnum) | Тип пакета (base - базовый, additional - дополнительный, promo - промо) |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип сущности, к которой относится пакет (agent - агент, knowledgebase - база знаний) |  |
|**tokenAmount** | **BigDecimal** | Количество токенов в пакете |  |
|**price** | **BigDecimal** | Цена пакета в целых единицах |  |
|**durationDays** | **BigDecimal** | Продолжительность пакета в днях (null для дополнительных пакетов) |  [optional] |
|**isAvailable** | **Boolean** | Флаг, указывающий доступность пакета |  |



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



