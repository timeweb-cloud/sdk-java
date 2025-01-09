

# DomainRegister

Заявка на регистрацию домена

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**action** | [**ActionEnum**](#ActionEnum) | Тип создаваемой заявки. |  |
|**fqdn** | **String** | Полное имя домена. |  |
|**isAutoprolongEnabled** | **Boolean** | Это логическое значение, которое показывает, включено ли автопродление домена. |  [optional] |
|**isWhoisPrivacyEnabled** | **Boolean** | Это логическое значение, которое показывает, включено ли скрытие данных администратора домена для whois. Опция недоступна для доменов в зонах .ru и .рф. |  [optional] |
|**period** | **DomainPaymentPeriod** |  |  [optional] |
|**personId** | **BigDecimal** | ID администратора, на которого регистрируется домен. |  |



## Enum: ActionEnum

| Name | Value |
|---- | -----|
| REGISTER | &quot;register&quot; |



