

# DomainRequest

Заявка на продление/регистрацию/трансфер домена.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**accountId** | **String** | Идентификатор пользователя |  |
|**authCode** | **String** | Код авторизации для переноса домена. |  |
|**date** | **OffsetDateTime** | Дата создания заявки. |  |
|**domainBundleId** | **String** | Идентификационный номер бандла, в который входит данная заявка (null - если заявка не входит ни в один бандл). |  |
|**errorCodeTransfer** | **String** | Код ошибки трансфера домена. |  |
|**fqdn** | **String** | Полное имя домена. |  |
|**groupId** | **BigDecimal** | Идентификатор группы доменных зон. |  |
|**id** | **BigDecimal** | Идентификатор заявки. |  |
|**isAntispamEnabled** | **Boolean** | Это логическое значение, которое показывает включена ли услуга \&quot;Антиспам\&quot; для домена |  |
|**isAutoprolongEnabled** | **Boolean** | Это логическое значение, которое показывает, включено ли автопродление домена. |  |
|**isWhoisPrivacyEnabled** | **Boolean** | Это логическое значение, которое показывает, включено ли скрытие данных администратора домена для whois. Опция недоступна для доменов в зонах .ru и .рф. |  |
|**message** | **String** | Информационное сообщение о заявке. |  |
|**moneySource** | [**MoneySourceEnum**](#MoneySourceEnum) | Источник (способ) оплаты заявки. |  |
|**period** | **DomainPaymentPeriod** |  |  |
|**personId** | **BigDecimal** | Идентификационный номер персоны для заявки на регистрацию. |  |
|**prime** | **DomainPrimeType** |  |  |
|**soonExpire** | **BigDecimal** | Количество дней до конца регистрации домена, за которые мы уведомим о необходимости продления. |  |
|**sortOrder** | **BigDecimal** | Это значение используется для сортировки доменных зон в панели управления. |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип заявки. |  |



## Enum: MoneySourceEnum

| Name | Value |
|---- | -----|
| USE | &quot;use&quot; |
| BONUS | &quot;bonus&quot; |
| INVOICE | &quot;invoice&quot; |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| REQUEST | &quot;request&quot; |
| PROLONG | &quot;prolong&quot; |
| TRANSFER | &quot;transfer&quot; |



