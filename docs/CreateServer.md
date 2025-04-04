

# CreateServer


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**_configuration** | [**CreateServerConfiguration**](CreateServerConfiguration.md) |  |  [optional] |
|**isDdosGuard** | **Boolean** | Защита от DDoS. Серверу выдается защищенный IP-адрес с защитой уровня L3 / L4. Для включения защиты уровня L7 необходимо создать тикет в техническую поддержку. |  [optional] |
|**osId** | **BigDecimal** | ID операционной системы, которая будет установлена на облачный сервер. Нельзя передавать вместе с &#x60;image_id&#x60;. |  [optional] |
|**imageId** | **UUID** | ID образа, который будет установлен на облачный сервер. Нельзя передавать вместе с &#x60;os_id&#x60;. |  [optional] |
|**softwareId** | **BigDecimal** | ID программного обеспечения сервера. |  [optional] |
|**presetId** | **BigDecimal** | ID тарифа сервера. Нельзя передавать вместе с ключом &#x60;configurator&#x60;. |  [optional] |
|**bandwidth** | **BigDecimal** | Пропускная способность тарифа. Доступные значения от 100 до 1000 с шагом 100. |  [optional] |
|**name** | **String** | Имя облачного сервера. Максимальная длина — 255 символов, имя должно быть уникальным. |  |
|**avatarId** | **String** | ID аватара сервера. |  [optional] |
|**comment** | **String** | Комментарий к облачному серверу. Максимальная длина — 255 символов. |  [optional] |
|**sshKeysIds** | **List&lt;BigDecimal&gt;** | Список SSH-ключей. |  [optional] |
|**isLocalNetwork** | **Boolean** | Локальная сеть. |  [optional] |
|**network** | [**CreateServerNetwork**](CreateServerNetwork.md) |  |  [optional] |
|**cloudInit** | **String** | Cloud-init скрипт |  [optional] |
|**availabilityZone** | **AvailabilityZone** |  |  [optional] |



