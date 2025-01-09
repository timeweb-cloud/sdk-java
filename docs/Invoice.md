

# Invoice

Оплата заявки на продление/регистрацию домена при помощи платежной системы

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**moneySource** | [**MoneySourceEnum**](#MoneySourceEnum) | Тип создаваемой заявки. |  |
|**personId** | **BigDecimal** | ID администратора, на которого зарегистрирован домен. |  [optional] |
|**paymentType** | [**PaymentTypeEnum**](#PaymentTypeEnum) | Тип платежной системы. |  |
|**payerId** | **BigDecimal** | Идентификационный номер плательщика |  |



## Enum: MoneySourceEnum

| Name | Value |
|---- | -----|
| INVOICE | &quot;invoice&quot; |



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



