

# App

Экземпляр приложения.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор для каждого экземпляра приложения. Автоматически генерируется при создании. |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип приложения. |  |
|**name** | **String** | Удобочитаемое имя, установленное для приложения. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус приложения. |  |
|**provider** | [**AppProvider**](AppProvider.md) |  |  |
|**ip** | **String** | IPv4-адрес приложения. |  |
|**domains** | [**List&lt;AppDomainsInner&gt;**](AppDomainsInner.md) |  |  |
|**framework** | **Frameworks** |  |  |
|**location** | [**LocationEnum**](#LocationEnum) | Локация сервера. |  |
|**repository** | [**Repository**](Repository.md) |  |  |
|**envVersion** | **String** | Версия окружения. |  |
|**envs** | **Object** | Переменные окружения приложения. Объект с ключами и значениями типа string. |  |
|**branchName** | **String** | Название ветки репозитория из которой собрано приложение. |  |
|**isAutoDeploy** | **Boolean** | Включен ли автоматический деплой. |  |
|**commitSha** | **String** | Хэш коммита из которого собрано приложеие. |  |
|**comment** | **String** | Комментарий к приложению. |  |
|**presetId** | **BigDecimal** | Идентификатор тарифа. |  |
|**indexDir** | **String** | Путь к директории с индексным файлом. Определен для приложений &#x60;type: frontend&#x60;. Для приложений &#x60;type: backend&#x60; всегда null. |  |
|**buildCmd** | **String** | Команда сборки приложения. |  |
|**runCmd** | **String** | Команда для запуска приложения. Определена для приложений &#x60;type: backend&#x60;. Для приложений &#x60;type: frontend&#x60; всегда null. |  |
|**_configuration** | [**AppConfiguration**](AppConfiguration.md) |  |  |
|**diskStatus** | [**AppDiskStatus**](AppDiskStatus.md) |  |  |
|**isQemuAgent** | **Boolean** | Включен ли агент QEMU. |  |
|**language** | **String** | Язык программирования приложения. |  |
|**startTime** | **OffsetDateTime** | Время запуска приложения. |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| BACKEND | &quot;backend&quot; |
| FRONTEND | &quot;frontend&quot; |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| ACTIVE | &quot;active&quot; |
| PAUSED | &quot;paused&quot; |
| NO_PAID | &quot;no_paid&quot; |
| DEPLOY | &quot;deploy&quot; |
| FAILURE | &quot;failure&quot; |
| STARTUP_ERROR | &quot;startup_error&quot; |
| NEW | &quot;new&quot; |
| REBOOT | &quot;reboot&quot; |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| PL_1 | &quot;pl-1&quot; |
| NL_1 | &quot;nl-1&quot; |



