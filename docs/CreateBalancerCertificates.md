

# CreateBalancerCertificates

Сертификат SSL.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) | Тип сертификата. |  [optional] |
|**fqdn** | **String** | Полное имя домена. |  [optional] |
|**certData** | **String** | Данные сертификата. Нужны только для типа custom. |  [optional] |
|**keyData** | **String** | Данные ключа. Нужны только для типа custom. |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| LETS_ENCRYPT | &quot;lets_encrypt&quot; |
| CUSTOM | &quot;custom&quot; |



