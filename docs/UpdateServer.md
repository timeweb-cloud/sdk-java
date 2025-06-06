

# UpdateServer


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**configurator** | [**UpdateServerConfigurator**](UpdateServerConfigurator.md) |  |  [optional] |
|**osId** | **BigDecimal** | ID операционной системы, которая будет установлена на облачный сервер. |  [optional] |
|**softwareId** | **BigDecimal** | ID программного обеспечения сервера. |  [optional] |
|**presetId** | **BigDecimal** | ID тарифа сервера. Нельзя передавать вместе с ключом &#x60;configurator&#x60;. |  [optional] |
|**bandwidth** | **BigDecimal** | Пропускная способность тарифа. Доступные значения от 100 до 1000 с шагом 100. |  [optional] |
|**name** | **String** | Имя облачного сервера. Максимальная длина — 255 символов, имя должно быть уникальным. |  [optional] |
|**avatarId** | **String** | ID аватара сервера. Описание методов работы с аватарами появится позднее. |  [optional] |
|**comment** | **String** | Комментарий к облачному серверу. Максимальная длина — 255 символов. |  [optional] |
|**imageId** | **UUID** | ID образа, который будет установлен на облачный сервер. Нельзя передавать вместе с &#x60;os_id&#x60;. |  [optional] |
|**cloudInit** | **String** | Cloud-init скрипт |  [optional] |



