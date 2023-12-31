

# DomainRegister

Заявка на регистрацию домена

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**action** | [**ActionEnum**](#ActionEnum) | Тип создаваемой заявки. |  |
|**fqdn** | **String** | Полное имя домена. |  |
|**isAutoprolongEnabled** | **Boolean** | Это логическое значение, которое показывает, включено ли автопродление домена. |  [optional] |
|**isWhoisPrivacyEnabled** | **Boolean** | Это логическое значение, которое показывает, включено ли скрытие данных администратора домена для whois. Опция недоступна для доменов в зонах .ru и .рф. |  [optional] |
|**ns** | [**List&lt;DomainRegisterNsInner&gt;**](DomainRegisterNsInner.md) | Name-серверы для регистрации домена. Если не передавать этот параметр, будут использованы наши стандартные name-серверы. Нужно указать как минимум 2 name-сервера. |  [optional] |
|**period** | **DomainPaymentPeriod** |  |  [optional] |
|**personId** | **BigDecimal** | Идентификатор администратора, на которого регистрируется домен. |  |



## Enum: ActionEnum

| Name | Value |
|---- | -----|
| REGISTER | &quot;register&quot; |



