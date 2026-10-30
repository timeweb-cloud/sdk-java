

# CreateAppV2

Параметры создания приложения. Для Git-репозитория обязательны provider_id, repository_id, branch_name, commit_sha, build_cmd, is_auto_deploy и framework. Для frontend также обязательны index_dir и preset_id. Для backend из Git обязателен run_cmd. Для backend из готового Docker-образа передайте docker_image и docker_image_tag; Git-параметры не требуются. Для любого backend передайте либо preset_id, либо configuration. Эти поля нельзя передавать одновременно.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**providerId** | **String** | ID провайдера. Обязателен при создании из Git-репозитория. Не требуется для готового Docker-образа. |  [optional] |
|**type** | [**TypeEnum**](#TypeEnum) | Тип приложения: frontend — статический сайт, backend — серверное приложение или Docker-образ. |  |
|**repositoryId** | **String** | ID репозитория. Обязателен при создании из Git-репозитория. Не требуется для готового Docker-образа. |  [optional] |
|**buildCmd** | **String** | Команда сборки приложения из Git-репозитория. Обязательна для Git; можно передать пустую строку, если сборка не нужна. |  [optional] |
|**envs** | **Map&lt;String, String&gt;** | Переменные окружения приложения. Ключи и значения — строки. Длина ключа — не более 255 символов, значения — не более 10240. По умолчанию пустой объект. |  [optional] |
|**branchName** | **String** | Ветка Git-репозитория, из которой необходимо собрать приложение. Обязательна при создании из Git. |  [optional] |
|**isAutoDeploy** | **Boolean** | Автоматически запускать деплой при изменении Git-репозитория. Поле обязательно при создании из Git. Для провайдера git и готового Docker-образа автодеплой выключен. |  [optional] |
|**commitSha** | **String** | Хэш коммита для первого деплоя. Обязателен при создании из Git. |  [optional] |
|**name** | **String** | Имя приложения, от 1 до 255 символов. Допустимы латинские и русские буквы, цифры, пробелы и символы - . ( ) , &amp; # [ ] _. |  |
|**comment** | **String** | Комментарий к приложению. Обязательное поле; можно передать пустую строку. Допустимы те же символы, что и в имени приложения. |  |
|**presetId** | **Integer** | ID тарифа приложения. Обязателен для frontend. Для backend передайте либо preset_id, либо configuration. Нельзя передавать вместе с configuration. Доступные тарифы: GET /api/v1/presets/apps. |  [optional] |
|**envVersion** | **String** | Версия окружения для сборки и запуска, например версия Node.js. Можно передать null. |  [optional] |
|**framework** | [**FrameworkEnum**](#FrameworkEnum) | Фреймворк приложения, строка с учётом регистра. Обязателен при создании из Git. Для frontend: &#x60;angular&#x60;, &#x60;astro&#x60;, &#x60;ember&#x60;, &#x60;next.js&#x60;, &#x60;nuxt&#x60;, &#x60;preact&#x60;, &#x60;react&#x60;, &#x60;remix&#x60;, &#x60;solid&#x60;, &#x60;solid-start&#x60;, &#x60;svelte&#x60;, &#x60;sveltekit&#x60;, &#x60;tanstack-start&#x60;, &#x60;vue&#x60;, &#x60;static-nobuild&#x60;, &#x60;another&#x60;. Для backend: &#x60;django&#x60;, &#x60;express&#x60;, &#x60;phoenix&#x60;, &#x60;Spring&#x60;, &#x60;laravel&#x60;, &#x60;beego&#x60;, &#x60;fastapi&#x60;, &#x60;ASP.NET Core&#x60;, &#x60;hapi&#x60;, &#x60;celery&#x60;, &#x60;flask&#x60;, &#x60;gin&#x60;, &#x60;docker&#x60;, &#x60;docker-compose&#x60;, &#x60;fastify&#x60;, &#x60;nest&#x60;, &#x60;symfony&#x60;, &#x60;yii&#x60;, &#x60;next.js&#x60;, &#x60;nuxt&#x60;, &#x60;astro&#x60;, &#x60;tanstack-start&#x60;, &#x60;solid-start&#x60;, &#x60;ktor&#x60;, &#x60;axum&#x60;, &#x60;actix&#x60;, &#x60;tornado&#x60;, &#x60;aiohttp&#x60;, &#x60;fiber&#x60;, &#x60;echo&#x60;, &#x60;Micronaut&#x60;, &#x60;Quarkus&#x60;, &#x60;remix&#x60;, &#x60;sveltekit&#x60;. Для готового Docker-образа не требуется. |  [optional] |
|**indexDir** | **String** | Путь к каталогу с собранными статическими файлами, например /dist. Обязателен для frontend. Не используется для backend. |  [optional] |
|**runCmd** | **String** | Команда запуска backend-приложения из Git. Обязательна для backend из Git. Не требуется для готового Docker-образа и frontend. |  [optional] |
|**systemDependencies** | **List&lt;String&gt;** | Системные пакеты для установки в окружение приложения. Допустимы строчные латинские буквы, цифры и символы . + - &#x3D;. По умолчанию пустой массив. |  [optional] |
|**projectId** | **Integer** | ID проекта, в который необходимо добавить приложение. Проект должен принадлежать аккаунту. |  [optional] |
|**_configuration** | [**CreateAppV2Configuration**](CreateAppV2Configuration.md) |  |  [optional] |
|**language** | [**LanguageEnum**](#LanguageEnum) | Язык или способ развёртывания. Обязателен в v2. Для frontend: &#x60;javascript&#x60;. Для backend: &#x60;node&#x60;, &#x60;elixir&#x60;, &#x60;go&#x60;, &#x60;php&#x60;, &#x60;python&#x60;, &#x60;ruby&#x60;, &#x60;docker&#x60;, &#x60;docker-compose&#x60;, &#x60;dotnet&#x60;, &#x60;java&#x60;, &#x60;kotlin&#x60;, &#x60;rust&#x60;, &#x60;bun&#x60;, &#x60;deno&#x60;, &#x60;custom-image&#x60;. Для SSR-приложений из Git используйте node, для готового Docker-образа — custom-image. |  |
|**buildEntryPath** | **String** | Каталог приложения в Git-репозитории, относительно которого выполняется сборка. Непустой путь начинается с /, не содержит .. или двойной начальный /. Допустимы латинские буквы, цифры, . - _ /. Можно передать пустую строку. |  [optional] |
|**network** | [**CreateAppV2Network**](CreateAppV2Network.md) |  |  [optional] |
|**healthCheck** | **String** | HTTP-путь для проверки работоспособности backend-приложения. |  [optional] |
|**globalEnvIds** | **List&lt;BigDecimal&gt;** | ID глобальных переменных окружения аккаунта, которые необходимо связать с приложением. |  [optional] |
|**dockerImage** | **String** | Имя готового Docker-образа для backend, без тега. Передавайте вместе с docker_image_tag. При указании обоих полей Git-параметры не требуются. |  [optional] |
|**dockerImageTag** | **String** | Тег готового Docker-образа. Передавайте вместе с docker_image. |  [optional] |
|**isRussianTrustedCa** | **Boolean** | Добавить доверенные корневые сертификаты Минцифры в окружение backend-приложения. Применяется в российском регионе. |  [optional] |
|**isTciTrustedCa** | **Boolean** | Добавить доверенные корневые сертификаты ТЦИ в окружение backend-приложения. Применяется в российском регионе. |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| FRONTEND | &quot;frontend&quot; |
| BACKEND | &quot;backend&quot; |



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



