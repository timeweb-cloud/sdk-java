

# TopLevelDomain

Доменная зона.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**allowedBuyPeriods** | [**List&lt;TopLevelDomainAllowedBuyPeriodsInner&gt;**](TopLevelDomainAllowedBuyPeriodsInner.md) | Список доступных периодов для регистрации/продления доменов со стоимостью. |  |
|**earlyRenewPeriod** | **BigDecimal** | Количество дней до истечение срока регистрации, когда можно продлять домен. |  |
|**gracePeriod** | **BigDecimal** | Количество дней, которые действует льготный период когда вы ещё можете продлить домен, после окончания его регистрации |  |
|**id** | **BigDecimal** | ID доменной зоны. |  |
|**isPublished** | **Boolean** | Это логическое значение, которое показывает, опубликована ли доменная зона. |  |
|**isRegistered** | **Boolean** | Это логическое значение, которое показывает, зарегистрирована ли доменная зона. |  |
|**isWhoisPrivacyDefaultEnabled** | **Boolean** | Это логическое значение, которое показывает, включено ли по умолчанию скрытие данных администратора для доменной зоны. |  |
|**isWhoisPrivacyEnabled** | **Boolean** | Это логическое значение, которое показывает, доступно ли управление скрытием данных администратора для доменной зоны. |  |
|**name** | **String** | Имя доменной зоны. |  |
|**price** | **BigDecimal** | Цена регистрации домена |  |
|**prolongPrice** | **BigDecimal** | Цена продления домена. |  |
|**registrar** | [**RegistrarEnum**](#RegistrarEnum) | Регистратор доменной зоны. |  |
|**transfer** | **BigDecimal** | Цена услуги трансфера домена. |  |
|**whoisPrivacyPrice** | **BigDecimal** | Цена услуги скрытия данных администратора для доменной зоны. |  |



## Enum: RegistrarEnum

| Name | Value |
|---- | -----|
| NIC | &quot;NIC&quot; |
| PDR | &quot;PDR&quot; |
| R01 | &quot;R01&quot; |
| TIMEWEB | &quot;timeweb&quot; |
| TIMEWEBVIRTREG | &quot;TimewebVirtreg&quot; |
| WEBNAMES | &quot;Webnames&quot; |
| UNKNOWN | &quot;unknown&quot; |



