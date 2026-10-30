

# AppV2

Приложение v2. Создание и первый деплой выполняются асинхронно. Поля сервера и инфраструктуры могут отсутствовать, пока соответствующие ресурсы не созданы.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID для каждого экземпляра приложения. Автоматически генерируется при создании. |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип приложения. |  |
|**name** | **String** | Удобочитаемое имя, установленное для приложения. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Текущее состояние приложения. После принятия запроса на создание — new. Ответ 201 не означает завершение развёртывания. |  |
|**provider** | [**AppV2Provider**](AppV2Provider.md) |  |  |
|**ip** | **String** | Публичный IPv4-адрес. Отсутствует, пока сервер и IP-адрес не созданы. |  [optional] |
|**domains** | [**List&lt;AppV2DomainsInner&gt;**](AppV2DomainsInner.md) | Домены приложения. В начальном ответе на создание массив пуст. |  |
|**framework** | [**FrameworkEnum**](#FrameworkEnum) | Фреймворк приложения. Поле может отсутствовать до создания инфраструктуры. |  [optional] |
|**location** | [**LocationEnum**](#LocationEnum) | Локация сервера. Может отсутствовать до создания сервера. |  [optional] |
|**repository** | [**Repository**](Repository.md) |  |  [optional] |
|**envVersion** | **String** | Версия окружения. Поле может отсутствовать до создания инфраструктуры. |  [optional] |
|**envs** | **Map&lt;String, String&gt;** | Переменные окружения приложения. Объект с ключами и значениями типа string. Поле может отсутствовать до создания инфраструктуры. |  [optional] |
|**isAutoDeploy** | **Boolean** | Включен ли автоматический деплой. |  |
|**commitSha** | **String** | Хэш коммита, из которого собрано приложение. |  [optional] |
|**comment** | **String** | Комментарий к приложению. |  [optional] |
|**presetId** | **BigDecimal** | ID тарифа приложения; 0 при использовании конфигуратора. |  |
|**indexDir** | **String** | Каталог со статическими файлами frontend-приложения. До создания инфраструктуры поле отсутствует. Для backend — null. |  [optional] |
|**buildCmd** | **String** | Команда сборки приложения. Поле может отсутствовать до создания инфраструктуры. |  [optional] |
|**avatarLink** | **String** | Ссылка на аватар приложения. |  |
|**runCmd** | **String** | Команда запуска backend-приложения. До создания инфраструктуры поле отсутствует. Для frontend — null. |  [optional] |
|**_configuration** | [**AppV2Configuration**](AppV2Configuration.md) |  |  [optional] |
|**diskStatus** | [**AppV2DiskStatus**](AppV2DiskStatus.md) |  |  [optional] |
|**isQemuAgent** | **Boolean** | Включён ли агент QEMU. Для frontend — null. Для backend поле отсутствует до создания сервера. |  [optional] |
|**language** | [**LanguageEnum**](#LanguageEnum) | Язык или способ развёртывания. Может отсутствовать до создания инфраструктуры. Поле может отсутствовать до создания инфраструктуры. |  [optional] |
|**startTime** | **OffsetDateTime** | Время последнего изменения записи приложения. |  |
|**branch** | **String** | Ветка Git-репозитория. В ответе v2 поле называется branch. |  [optional] |
|**configuratorId** | **Integer** | ID конфигуратора приложения; null при использовании готового тарифа. |  |
|**projectId** | **Integer** | ID проекта приложения. Может отсутствовать, если проект не задан. |  [optional] |
|**systemDependencies** | **List&lt;String&gt;** | Системные пакеты приложения. Поле отсутствует, если зависимости не заданы. |  [optional] |
|**createdAt** | **OffsetDateTime** | Время создания записи приложения. |  |
|**availabilityZone** | [**AvailabilityZoneEnum**](#AvailabilityZoneEnum) | Зона доступности сервера. Может быть null или отсутствовать до создания сервера. |  [optional] |
|**requestsLimit** | **Integer** | Ограничение количества запросов; null, если индивидуальный лимит не задан. |  |
|**buildEntryPath** | **String** | Каталог приложения в Git-репозитории, относительно которого выполняется сборка. Может отсутствовать до создания инфраструктуры. |  [optional] |
|**healthCheck** | **String** | HTTP-путь для проверки работоспособности backend-приложения. Может отсутствовать до создания инфраструктуры. |  [optional] |
|**dockerImage** | **String** | Имя готового Docker-образа без тега. Присутствует для backend из готового Docker-образа. |  [optional] |
|**dockerImageTag** | **String** | Тег готового Docker-образа. Присутствует для backend из готового Docker-образа. |  [optional] |
|**isRussianTrustedCa** | **Boolean** | Добавлены ли доверенные корневые сертификаты Минцифры в окружение backend-приложения. Поле возвращается в российском регионе после создания инфраструктуры. |  [optional] |
|**isTciTrustedCa** | **Boolean** | Добавлены ли доверенные корневые сертификаты ТЦИ в окружение backend-приложения. Поле возвращается в российском регионе после создания инфраструктуры. |  [optional] |
|**blockedPorts** | **List&lt;Integer&gt;** | Заблокированные порты backend-приложения. Поле может отсутствовать до создания сервера. |  [optional] |
|**reverseProxy** | **Object** | Настройки обратного прокси. В начальном ответе на создание — null. |  |



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
| BLOCKED | &quot;blocked&quot; |
| REQUEST_LIMIT_EXCEEDED | &quot;request_limit_exceeded&quot; |



## Enum: FrameworkEnum

| Name | Value |
|---- | -----|
| DJANGO | &quot;django&quot; |
| EXPRESS | &quot;express&quot; |
| PHOENIX | &quot;phoenix&quot; |
| SPRING | &quot;Spring&quot; |
| LARAVEL | &quot;laravel&quot; |
| BEEGO | &quot;beego&quot; |
| FASTAPI | &quot;fastapi&quot; |
| ASP_NET_CORE | &quot;ASP.NET Core&quot; |
| HAPI | &quot;hapi&quot; |
| CELERY | &quot;celery&quot; |
| FLASK | &quot;flask&quot; |
| GIN | &quot;gin&quot; |
| DOCKER | &quot;docker&quot; |
| DOCKER_COMPOSE | &quot;docker-compose&quot; |
| FASTIFY | &quot;fastify&quot; |
| NEST | &quot;nest&quot; |
| SYMFONY | &quot;symfony&quot; |
| YII | &quot;yii&quot; |
| NEXT_JS | &quot;next.js&quot; |
| NUXT | &quot;nuxt&quot; |
| ASTRO | &quot;astro&quot; |
| TANSTACK_START | &quot;tanstack-start&quot; |
| SOLID_START | &quot;solid-start&quot; |
| KTOR | &quot;ktor&quot; |
| AXUM | &quot;axum&quot; |
| ACTIX | &quot;actix&quot; |
| TORNADO | &quot;tornado&quot; |
| AIOHTTP | &quot;aiohttp&quot; |
| FIBER | &quot;fiber&quot; |
| ECHO | &quot;echo&quot; |
| MICRONAUT | &quot;Micronaut&quot; |
| QUARKUS | &quot;Quarkus&quot; |
| REMIX | &quot;remix&quot; |
| SVELTEKIT | &quot;sveltekit&quot; |
| ANGULAR | &quot;angular&quot; |
| EMBER | &quot;ember&quot; |
| PREACT | &quot;preact&quot; |
| REACT | &quot;react&quot; |
| SOLID | &quot;solid&quot; |
| SVELTE | &quot;svelte&quot; |
| VUE | &quot;vue&quot; |
| STATIC_NOBUILD | &quot;static-nobuild&quot; |
| ANOTHER | &quot;another&quot; |



## Enum: LocationEnum

| Name | Value |
|---- | -----|
| RU_1 | &quot;ru-1&quot; |
| RU_2 | &quot;ru-2&quot; |
| RU_3 | &quot;ru-3&quot; |
| PL_1 | &quot;pl-1&quot; |
| KZ_1 | &quot;kz-1&quot; |
| NL_1 | &quot;nl-1&quot; |
| US_1 | &quot;us-1&quot; |
| US_2 | &quot;us-2&quot; |
| DE_1 | &quot;de-1&quot; |



## Enum: LanguageEnum

| Name | Value |
|---- | -----|
| JAVASCRIPT | &quot;javascript&quot; |
| NODE | &quot;node&quot; |
| ELIXIR | &quot;elixir&quot; |
| GO | &quot;go&quot; |
| PHP | &quot;php&quot; |
| PYTHON | &quot;python&quot; |
| RUBY | &quot;ruby&quot; |
| DOCKER | &quot;docker&quot; |
| DOCKER_COMPOSE | &quot;docker-compose&quot; |
| DOTNET | &quot;dotnet&quot; |
| JAVA | &quot;java&quot; |
| KOTLIN | &quot;kotlin&quot; |
| RUST | &quot;rust&quot; |
| BUN | &quot;bun&quot; |
| DENO | &quot;deno&quot; |
| CUSTOM_IMAGE | &quot;custom-image&quot; |



## Enum: AvailabilityZoneEnum

| Name | Value |
|---- | -----|
| SPB_1 | &quot;spb-1&quot; |
| SPB_2 | &quot;spb-2&quot; |
| SPB_3 | &quot;spb-3&quot; |
| SPB_4 | &quot;spb-4&quot; |
| NSK_1 | &quot;nsk-1&quot; |
| AMS_1 | &quot;ams-1&quot; |
| GDN_1 | &quot;gdn-1&quot; |
| ALA_1 | &quot;ala-1&quot; |
| NYC_1 | &quot;nyc-1&quot; |
| MSK_1 | &quot;msk-1&quot; |



