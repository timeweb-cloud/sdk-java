

# UpdateDomainRequestRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**moneySource** | [**MoneySourceEnum**](#MoneySourceEnum) | Тип создаваемой заявки. |  |
|**personId** | **BigDecimal** | Идентификатор администратора, на которого зарегистрирован домен. |  [optional] |
|**paymentType** | [**PaymentTypeEnum**](#PaymentTypeEnum) | Тип платежной системы. |  |
|**payerId** | **BigDecimal** | Идентификационный номер плательщика |  |
|**authCode** | **String** | Код авторизации для переноса домена. |  |
|**bonusId** | **BigDecimal** | Идентификатор бонуса. |  |



## Enum: MoneySourceEnum

| Name | Value |
|---- | -----|
| BONUS | &quot;bonus&quot; |



## Enum: PaymentTypeEnum

| Name | Value |
|---- | -----|
| RECEIPT | &quot;receipt&quot; |
| CARD | &quot;card&quot; |
| MOBILE_CARD | &quot;mobile-card&quot; |
| WM | &quot;wm&quot; |
| WEBMONEY | &quot;webmoney&quot; |
| YANDEX | &quot;yandex&quot; |
| YA | &quot;ya&quot; |
| INVOICE | &quot;invoice&quot; |
| SOFORT | &quot;sofort&quot; |
| QIWI_WALLET | &quot;qiwi_wallet&quot; |
| WECHAT | &quot;wechat&quot; |



