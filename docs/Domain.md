

# Domain

Домен

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**allowedBuyPeriods** | [**List&lt;DomainAllowedBuyPeriodsInner&gt;**](DomainAllowedBuyPeriodsInner.md) | Допустимые периоды продления домена. |  |
|**daysLeft** | **BigDecimal** | Количество дней, оставшихся до конца срока регистрации домена. |  |
|**domainStatus** | [**DomainStatusEnum**](#DomainStatusEnum) | Статус домена. |  |
|**expiration** | **String** | Дата окончания срока регистрации домена, для доменов без срока окончания регистрации будет приходить 0000-00-00. |  |
|**fqdn** | **String** | Полное имя домена. |  |
|**id** | **BigDecimal** | Уникальный идентификатор домена. |  |
|**isAutoprolongEnabled** | **Boolean** | Это логическое значение, которое показывает, включено ли автопродление домена. |  |
|**isPremium** | **Boolean** | Это логическое значение, которое показывает, является ли домен премиальным. |  |
|**isProlongAllowed** | **Boolean** | Это логическое значение, которое показывает, можно ли сейчас продлить домен. |  |
|**isTechnical** | **Boolean** | Это логическое значение, которое показывает, является ли домен техническим. |  |
|**isWhoisPrivacyEnabled** | **Boolean** | Это логическое значение, которое показывает, включено ли скрытие данных администратора домена для whois. Если приходит null, значит для данной зоны эта услуга не доступна. |  |
|**linkedIp** | **String** | Привязанный к домену IP-адрес. |  |
|**paidTill** | **String** | До какого числа оплачен домен. |  |
|**personId** | **BigDecimal** | Идентификатор администратора, на которого зарегистрирован домен. |  |
|**premiumProlongCost** | **BigDecimal** | Стоимость премиального домена. |  |
|**provider** | **String** | Идентификатор регистратора домена. |  |
|**requestStatus** | [**RequestStatusEnum**](#RequestStatusEnum) | Статус заявки на продление/регистрацию/трансфер домена. |  |
|**subdomains** | [**List&lt;Subdomain&gt;**](Subdomain.md) | Список поддоменов. |  |
|**tldId** | **BigDecimal** | Идентификатор доменной зоны. |  |



## Enum: DomainStatusEnum

| Name | Value |
|---- | -----|
| AWAITING_PAYMENT | &quot;awaiting_payment&quot; |
| EXPIRED | &quot;expired&quot; |
| FINAL_EXPIRED | &quot;final_expired&quot; |
| FREE | &quot;free&quot; |
| NO_PAID | &quot;no_paid&quot; |
| NS_BASED | &quot;ns_based&quot; |
| PAID | &quot;paid&quot; |
| SOON_EXPIRE | &quot;soon_expire&quot; |
| TODAY_EXPIRED | &quot;today_expired&quot; |



## Enum: RequestStatusEnum

| Name | Value |
|---- | -----|
| PROLONGATION_FAIL | &quot;prolongation_fail&quot; |
| PROLONGATION_REQUEST | &quot;prolongation_request&quot; |
| REGISTRATION_FAIL | &quot;registration_fail&quot; |
| REGISTRATION_REQUEST | &quot;registration_request&quot; |
| TRANSFER_FAIL | &quot;transfer_fail&quot; |
| TRANSFER_REQUEST | &quot;transfer_request&quot; |
| AWAITING_PERSON | &quot;awaiting_person&quot; |



