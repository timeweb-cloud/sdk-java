

# DedicatedServer

Выделенный сервер

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | ID для каждого экземпляра выделенного сервера. Автоматически генерируется при создании. |  |
|**cpuDescription** | **String** | Описание параметров процессора выделенного сервера. |  |
|**hddDescription** | **String** | Описание параметров жёсткого диска выделенного сервера. |  |
|**ramDescription** | **String** | Описание ОЗУ выделенного сервера. |  |
|**createdAt** | **OffsetDateTime** | Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда был создан выделенный сервер. |  |
|**ip** | **String** | IP-адрес сетевого интерфейса IPv4. |  |
|**ipmiIp** | **String** | IP-адрес сетевого интерфейса IPMI. |  |
|**ipmiLogin** | **String** | Логин, используемый для входа в IPMI-консоль. |  |
|**ipmiPassword** | **String** | Пароль, используемый для входа в IPMI-консоль. |  |
|**ipv6** | **String** | IP-адрес сетевого интерфейса IPv6. |  |
|**nodeId** | **BigDecimal** | Внутренний дополнительный ID сервера. |  |
|**name** | **String** | Удобочитаемое имя, установленное для выделенного сервера. |  |
|**comment** | **String** | Комментарий к выделенному серверу. |  |
|**vncPass** | **String** | Пароль для подключения к VNC-консоли выделенного сервера. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Строка состояния, указывающая состояние выделенного сервера. Может быть «installing», «installed», «on» или «off». |  |
|**osId** | **BigDecimal** | ID операционной системы, установленной на выделенный сервер. |  |
|**cpId** | **BigDecimal** | ID панели управления, установленной на выделенный сервер. |  |
|**bandwidthId** | **BigDecimal** | ID интернет-канала, установленного на выделенный сервер. |  |
|**networkDriveId** | **List&lt;BigDecimal&gt;** | Массив уникальных ID сетевых дисков, подключенных к выделенному серверу. |  |
|**additionalIpAddrId** | **List&lt;BigDecimal&gt;** | Массив уникальных ID дополнительных IP-адресов, подключенных к выделенному серверу. |  |
|**planId** | **BigDecimal** | ID списка дополнительных услуг выделенного сервера. |  |
|**price** | **BigDecimal** | Стоимость выделенного сервера. |  |
|**location** | [**LocationEnum**](#LocationEnum) | Локация сервера. |  |
|**autoinstallReady** | **BigDecimal** | Количество готовых к автоматической выдаче серверов. Если значение равно 0, сервер будет установлен через инженеров. |  |
|**password** | **String** | Пароль root сервера или пароль Администратора для серверов Windows. |  |
|**avatarLink** | **String** | Ссылка на аватар сервера. |  |
|**isPreInstalled** | **Boolean** | Это логическое значение, которое показывает, готов ли выделенный сервер к моментальной выдаче. |  |
|**presetId** | **Integer** | ID тарифа сервера. |  |
|**projectId** | **Integer** | ID проекта |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| INSTALLING | &quot;installing&quot; |
| INSTALLED | &quot;installed&quot; |
| ON | &quot;on&quot; |
| OFF | &quot;off&quot; |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| PL_1 | &quot;pl-1&quot; |
| KZ_1 | &quot;kz-1&quot; |
| NL_1 | &quot;nl-1&quot; |
| TR_1 | &quot;tr-1&quot; |
| US_2 | &quot;us-2&quot; |
| DE_1 | &quot;de-1&quot; |
| FI_1 | &quot;fi-1&quot; |



