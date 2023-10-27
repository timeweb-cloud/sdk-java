

# Vds

Сервер

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор для каждого экземпляра сервера. Автоматически генерируется при создании. |  |
|**name** | **String** | Удобочитаемое имя, установленное для выделенного сервера. |  |
|**comment** | **String** | Комментарий к выделенному серверу. |  |
|**createdAt** | **String** | Дата создания сервера в формате ISO8061. |  |
|**os** | [**VdsOs**](VdsOs.md) |  |  |
|**software** | [**VdsSoftware**](VdsSoftware.md) |  |  |
|**presetId** | **BigDecimal** | Уникальный идентификатор тарифа сервера. |  |
|**location** | [**LocationEnum**](#LocationEnum) | Локация сервера. |  |
|**configuratorId** | **BigDecimal** | Уникальный идентификатор конфигуратора сервера. |  |
|**bootMode** | [**BootModeEnum**](#BootModeEnum) | Режим загрузки ОС сервера. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус сервера. |  |
|**startAt** | **OffsetDateTime** | Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда был запущен сервер. |  |
|**isDdosGuard** | **Boolean** | Это логическое значение, которое показывает, включена ли защита от DDOS у данного сервера. |  |
|**cpu** | **BigDecimal** | Количество ядер процессора сервера. |  |
|**cpuFrequency** | **String** | Частота ядер процессора сервера. |  |
|**ram** | **BigDecimal** | Размер (в Мб) ОЗУ сервера. |  |
|**disks** | [**List&lt;VdsDisksInner&gt;**](VdsDisksInner.md) | Список дисков сервера. |  |
|**avatarId** | **String** | Уникальный идентификатор аватара сервера. Описание методов работы с аватарами появится позднее. |  |
|**vncPass** | **String** | Пароль от VNC. |  |
|**rootPass** | **String** | Пароль root сервера или пароль Администратора для серверов Windows. |  |
|**image** | [**VdsImage**](VdsImage.md) |  |  |
|**networks** | [**List&lt;VdsNetworksInner&gt;**](VdsNetworksInner.md) | Список сетей диска. |  |
|**cloudInit** | **String** | Cloud-init скрипт. |  |
|**isQemuAgent** | **Boolean** | Включен ли QEMU-agent на сервере. |  [optional] |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| RU_2 | &quot;ru-2&quot; |
| PL_1 | &quot;pl-1&quot; |
| KZ_1 | &quot;kz-1&quot; |



## Enum: BootModeEnum

| Name | Value |
|---- | -----|
| STD | &quot;std&quot; |
| SINGLE | &quot;single&quot; |
| CD | &quot;cd&quot; |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| INSTALLING | &quot;installing&quot; |
| SOFTWARE_INSTALL | &quot;software_install&quot; |
| REINSTALLING | &quot;reinstalling&quot; |
| ON | &quot;on&quot; |
| OFF | &quot;off&quot; |
| TURNING_ON | &quot;turning_on&quot; |
| TURNING_OFF | &quot;turning_off&quot; |
| HARD_TURNING_OFF | &quot;hard_turning_off&quot; |
| REBOOTING | &quot;rebooting&quot; |
| HARD_REBOOTING | &quot;hard_rebooting&quot; |
| REMOVING | &quot;removing&quot; |
| REMOVED | &quot;removed&quot; |
| CLONING | &quot;cloning&quot; |
| TRANSFER | &quot;transfer&quot; |
| BLOCKED | &quot;blocked&quot; |
| CONFIGURING | &quot;configuring&quot; |
| NO_PAID | &quot;no_paid&quot; |
| PERMANENT_BLOCKED | &quot;permanent_blocked&quot; |



