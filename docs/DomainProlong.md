

# DomainProlong

Заявка на продление домена

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**action** | [**ActionEnum**](#ActionEnum) | Тип создаваемой заявки. |  |
|**fqdn** | **String** | Полное имя домена. |  |
|**isAntispamEnabled** | **Boolean** | Это логическое значение, которое показывает включена ли услуга \&quot;Антиспам\&quot; для домена |  [optional] |
|**isAutoprolongEnabled** | **Boolean** | Это логическое значение, которое показывает, включено ли автопродление домена. |  [optional] |
|**isWhoisPrivacyEnabled** | **Boolean** | Это логическое значение, которое показывает, включено ли скрытие данных администратора домена для whois. Опция недоступна для доменов в зонах .ru и .рф. |  [optional] |
|**period** | **DomainPaymentPeriod** |  |  [optional] |
|**personId** | **BigDecimal** | ID администратора, на которого зарегистрирован домен. |  [optional] |
|**prime** | **DomainPrimeType** |  |  [optional] |



## Enum: ActionEnum

| Name | Value |
|---- | -----|
| PROLONG | &quot;prolong&quot; |



