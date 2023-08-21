# openapi-java-client

Timeweb Cloud API
- API version: 
  - Build date: 2023-08-21T14:21:17.845880Z[Etc/UTC]

# Введение
API Timeweb Cloud позволяет вам управлять ресурсами в облаке программным способом с использованием обычных HTTP-запросов.

Множество функций, которые доступны в панели управления Timeweb Cloud, также доступны через API, что позволяет вам автоматизировать ваши собственные сценарии.

В этой документации сперва будет описан общий дизайн и принципы работы API, а после этого конкретные конечные точки. Также будут приведены примеры запросов к ним.


## Запросы
Запросы должны выполняться по протоколу `HTTPS`, чтобы гарантировать шифрование транзакций. Поддерживаются следующие методы запроса:
|Метод|Применение|
|--- |--- |
|GET|Извлекает данные о коллекциях и отдельных ресурсах.|
|POST|Для коллекций создает новый ресурс этого типа. Также используется для выполнения действий с конкретным ресурсом.|
|PUT|Обновляет существующий ресурс.|
|PATCH|Некоторые ресурсы поддерживают частичное обновление, то есть обновление только части атрибутов ресурса, в этом случае вместо метода PUT будет использован PATCH.|
|DELETE|Удаляет ресурс.|

Методы `POST`, `PUT` и `PATCH` могут включать объект в тело запроса с типом содержимого `application/json`.

### Параметры в запросах
Некоторые коллекции поддерживают пагинацию, поиск или сортировку в запросах. В параметрах запроса требуется передать:
- `limit` — обозначает количество записей, которое необходимо вернуть
 - `offset` — указывает на смещение, относительно начала списка
 - `search` — позволяет указать набор символов для поиска
 - `sort` — можно задать правило сортировки коллекции

## Ответы
Запросы вернут один из следующих кодов состояния ответа HTTP:

|Статус|Описание|
|--- |--- |
|200 OK|Действие с ресурсом было выполнено успешно.|
|201 Created|Ресурс был успешно создан. При этом ресурс может быть как уже готовым к использованию, так и находиться в процессе запуска.|
|204 No Content|Действие с ресурсом было выполнено успешно, и ответ не содержит дополнительной информации в теле.|
|400 Bad Request|Был отправлен неверный запрос, например, в нем отсутствуют обязательные параметры и т. д. Тело ответа будет содержать дополнительную информацию об ошибке.|
|401 Unauthorized|Ошибка аутентификации.|
|403 Forbidden|Аутентификация прошла успешно, но недостаточно прав для выполнения действия.|
|404 Not Found|Запрашиваемый ресурс не найден.|
|409 Conflict|Запрос конфликтует с текущим состоянием.|
|423 Locked|Ресурс из запроса заблокирован от применения к нему указанного метода.|
|429 Too Many Requests|Был достигнут лимит по количеству запросов в единицу времени.|
|500 Internal Server Error|При выполнении запроса произошла какая-то внутренняя ошибка. Чтобы решить эту проблему, лучше всего создать тикет в панели управления.|

### Структура успешного ответа
Все конечные точки будут возвращать данные в формате `JSON`. Ответы на `GET`-запросы будут иметь на верхнем уровне следующую структуру атрибутов: 
|Название поля|Тип|Описание|
|--- |--- |--- |
|[entity_name]|object, object[], string[], number[], boolean|Динамическое поле, которое будет меняться в зависимости от запрашиваемого ресурса и будет содержать все атрибуты, необходимые для описания этого ресурса. Например, при запросе списка баз данных будет возвращаться поле `dbs`, а при запросе конкретного облачного сервера `server`. Для некоторых конечных точек в ответе может возвращаться сразу несколько ресурсов.|
|meta|object|Опционально. Объект, который содержит вспомогательную информацию о ресурсе. Чаще всего будет встречаться при запросе коллекций и содержать поле `total`, которое будет указывать на количество элементов в коллекции.|
|response_id|string|Опционально. В большинстве случаев в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее. Также вы можете использовать этот идентификатор, чтобы убедиться, что это новый ответ на запрос и результат не был получен из кэша.|

Пример запроса на получение списка SSH-ключей:
```
    HTTP/2.0 200 OK
    {
      \"ssh_keys\":[
          {
            \"body\":\"ssh-rsa AAAAB3NzaC1sdfghjkOAsBwWhs= example@device.local\",
            \"created_at\":\"2021-09-15T19:52:27Z\",
            \"expired_at\":null,
            \"id\":5297,
            \"is_default\":false,
            \"name\":\"example@device.local\",
            \"used_at\":null,
            \"used_by\":[]
          }
      ],
      \"meta\":{
          \"total\":1
      },
      \"response_id\":\"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"
    }
```

### Структура ответа с ошибкой
|Название поля|Тип|Описание|
|--- |--- |--- |
|status_code|number|Короткий числовой идентификатор ошибки.|
|error_code|string|Короткий текстовый идентификатор ошибки, который уточняет числовой идентификатор и удобен для программной обработки. Самый простой пример — это код `not_found` для ошибки 404.|
|message|string, string[]|Опционально. В большинстве случаев в ответе будет содержаться человекочитаемое подробное описание ошибки или ошибок, которые помогут понять, что нужно исправить.|
|response_id|string|Опционально. В большинстве случае в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее.|

Пример:
```
    HTTP/2.0 403 Forbidden
    {
      \"status_code\": 403,
      \"error_code\":  \"forbidden\",
      \"message\":     \"You do not have access for the attempted action\",
      \"response_id\": \"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"
    }
```

## Статусы ресурсов
Важно учесть, что при создании большинства ресурсов внутри платформы вам будет сразу возвращен ответ от сервера со статусом `200 OK` или `201 Created` и идентификатором созданного ресурса в теле ответа, но при этом этот ресурс может быть ещё в *состоянии запуска*.

Для того чтобы понять, в каком состоянии сейчас находится ваш ресурс, мы добавили поле `status` в ответ на получение информации о ресурсе.

Список статусов будет отличаться в зависимости от типа ресурса. Увидеть поддерживаемый список статусов вы сможете в описании каждого конкретного ресурса.

 

## Ограничение скорости запросов (Rate Limiting)
Чтобы обеспечить стабильность для всех пользователей, Timeweb Cloud защищает API от всплесков входящего трафика, анализируя количество запросов c каждого аккаунта к каждой конечной точке.

Если ваше приложение отправляет более 20 запросов в секунду на одну конечную точку, то для этого запроса API может вернуть код состояния HTTP `429 Too Many Requests`.


## Аутентификация
Доступ к API осуществляется с помощью JWT-токена. Токенами можно управлять внутри панели управления Timeweb Cloud в разделе *API и Terraform*.

Токен необходимо передавать в заголовке каждого запроса в формате:
```
  Authorization: Bearer $TIMEWEB_CLOUD_TOKEN
```

## Формат примеров API
Примеры в этой документации описаны с помощью `curl`, HTTP-клиента командной строки. На компьютерах `Linux` и `macOS` обычно по умолчанию установлен `curl`, и он доступен для загрузки на всех популярных платформах, включая `Windows`.

Каждый пример разделен на несколько строк символом `\\`, который совместим с `bash`. Типичный пример выглядит так:
```
  curl -X PATCH 
    -H \"Content-Type: application/json\" 
    -H \"Authorization: Bearer $TIMEWEB_CLOUD_TOKEN\" 
    -d '{\"name\":\"Cute Corvus\",\"comment\":\"Development Server\"}' 
    \"https://api.timeweb.cloud/api/v1/dedicated/1051\"
```
- Параметр `-X` задает метод запроса. Для согласованности метод будет указан во всех примерах, даже если он явно не требуется для методов `GET`.
- Строки `-H` задают требуемые HTTP-заголовки.
- Примеры, для которых требуется объект JSON в теле запроса, передают требуемые данные через параметр `-d`.

Чтобы использовать приведенные примеры, не подставляя каждый раз в них свой токен, вы можете добавить токен один раз в переменные окружения в вашей консоли. Например, на `Linux` это можно сделать с помощью команды:

```
TIMEWEB_CLOUD_TOKEN=\"token\"
```

После этого токен будет автоматически подставляться в ваши запросы.

Обратите внимание, что все значения в этой документации являются примерами. Не полагайтесь на идентификаторы операционных систем, тарифов и т.д., используемые в примерах. Используйте соответствующую конечную точку для получения значений перед созданием ресурсов.


## Версионирование
API построено согласно принципам [семантического версионирования](https://semver.org/lang/ru). Это значит, что мы гарантируем обратную совместимость всех изменений в пределах одной мажорной версии.

Мажорная версия каждой конечной точки обозначается в пути запроса, например, запрос `/api/v1/servers` указывает, что этот метод имеет версию 1.


*Automatically generated by the [OpenAPI Generator](https://openapi-generator.tech)*


## Requirements

Building the API client library requires:
1. Java 1.8+
2. Maven (3.8.3+)/Gradle (7.2+)

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn clean install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn clean deploy
```

Refer to the [OSSRH Guide](http://central.sonatype.org/pages/ossrh-guide.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>org.openapitools</groupId>
  <artifactId>openapi-java-client</artifactId>
  <version>1.0.0</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
  repositories {
    mavenCentral()     // Needed if the 'openapi-java-client' jar has been published to maven central.
    mavenLocal()       // Needed if the 'openapi-java-client' jar has been published to the local maven repo.
  }

  dependencies {
     implementation "org.openapitools:openapi-java-client:1.0.0"
  }
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

* `target/openapi-java-client-1.0.0.jar`
* `target/lib/*.jar`

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AccountApi apiInstance = new AccountApi(defaultClient);
    AddCountriesToAllowedListRequest addCountriesToAllowedListRequest = new AddCountriesToAllowedListRequest(); // AddCountriesToAllowedListRequest | 
    try {
      AddCountriesToAllowedList201Response result = apiInstance.addCountriesToAllowedList(addCountriesToAllowedListRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountApi#addCountriesToAllowedList");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://api.timeweb.cloud*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*AccountApi* | [**addCountriesToAllowedList**](docs/AccountApi.md#addCountriesToAllowedList) | **POST** /api/v1/auth/access/countries | Добавление стран в список разрешенных
*AccountApi* | [**addIPsToAllowedList**](docs/AccountApi.md#addIPsToAllowedList) | **POST** /api/v1/auth/access/ips | Добавление IP-адресов в список разрешенных
*AccountApi* | [**deleteCountriesFromAllowedList**](docs/AccountApi.md#deleteCountriesFromAllowedList) | **DELETE** /api/v1/auth/access/countries | Удаление стран из списка разрешенных
*AccountApi* | [**deleteIPsFromAllowedList**](docs/AccountApi.md#deleteIPsFromAllowedList) | **DELETE** /api/v1/auth/access/ips | Удаление IP-адресов из списка разрешенных
*AccountApi* | [**getAccountStatus**](docs/AccountApi.md#getAccountStatus) | **GET** /api/v1/account/status | Получение статуса аккаунта
*AccountApi* | [**getAuthAccessSettings**](docs/AccountApi.md#getAuthAccessSettings) | **GET** /api/v1/auth/access | Получить информацию о ограничениях авторизации пользователя
*AccountApi* | [**getCountries**](docs/AccountApi.md#getCountries) | **GET** /api/v1/auth/access/countries | Получение списка стран
*AccountApi* | [**getFinances**](docs/AccountApi.md#getFinances) | **GET** /api/v1/account/finances | Получение платежной информации
*AccountApi* | [**getNotificationSettings**](docs/AccountApi.md#getNotificationSettings) | **GET** /api/v1/account/notification-settings | Получение настроек уведомлений аккаунта
*AccountApi* | [**updateAuthRestrictionsByCountries**](docs/AccountApi.md#updateAuthRestrictionsByCountries) | **POST** /api/v1/auth/access/countries/enabled | Включение/отключение ограничений по стране
*AccountApi* | [**updateAuthRestrictionsByIP**](docs/AccountApi.md#updateAuthRestrictionsByIP) | **POST** /api/v1/auth/access/ips/enabled | Включение/отключение ограничений по IP-адресу
*AccountApi* | [**updateNotificationSettings**](docs/AccountApi.md#updateNotificationSettings) | **PATCH** /api/v1/account/notification-settings | Изменение настроек уведомлений аккаунта
*ApiKeysApi* | [**createToken**](docs/ApiKeysApi.md#createToken) | **POST** /api/v1/auth/api-keys | Создание токена
*ApiKeysApi* | [**deleteToken**](docs/ApiKeysApi.md#deleteToken) | **DELETE** /api/v1/auth/api-keys/{token_id} | Удалить токен
*ApiKeysApi* | [**getTokens**](docs/ApiKeysApi.md#getTokens) | **GET** /api/v1/auth/api-keys | Получение списка выпущенных токенов
*ApiKeysApi* | [**reissueToken**](docs/ApiKeysApi.md#reissueToken) | **PUT** /api/v1/auth/api-keys/{token_id} | Перевыпустить токен
*ApiKeysApi* | [**updateToken**](docs/ApiKeysApi.md#updateToken) | **PATCH** /api/v1/auth/api-keys/{token_id} | Изменить токен
*BalancersApi* | [**addIPsToBalancer**](docs/BalancersApi.md#addIPsToBalancer) | **POST** /api/v1/balancers/{balancer_id}/ips | Добавление IP-адресов к балансировщику
*BalancersApi* | [**createBalancer**](docs/BalancersApi.md#createBalancer) | **POST** /api/v1/balancers | Создание бaлансировщика
*BalancersApi* | [**createBalancerRule**](docs/BalancersApi.md#createBalancerRule) | **POST** /api/v1/balancers/{balancer_id}/rules | Создание правила для балансировщика
*BalancersApi* | [**deleteBalancer**](docs/BalancersApi.md#deleteBalancer) | **DELETE** /api/v1/balancers/{balancer_id} | Удаление балансировщика
*BalancersApi* | [**deleteBalancerRule**](docs/BalancersApi.md#deleteBalancerRule) | **DELETE** /api/v1/balancers/{balancer_id}/rules/{rule_id} | Удаление правила для балансировщика
*BalancersApi* | [**deleteIPsFromBalancer**](docs/BalancersApi.md#deleteIPsFromBalancer) | **DELETE** /api/v1/balancers/{balancer_id}/ips | Удаление IP-адресов из балансировщика
*BalancersApi* | [**getBalancer**](docs/BalancersApi.md#getBalancer) | **GET** /api/v1/balancers/{balancer_id} | Получение бaлансировщика
*BalancersApi* | [**getBalancerIPs**](docs/BalancersApi.md#getBalancerIPs) | **GET** /api/v1/balancers/{balancer_id}/ips | Получение списка IP-адресов балансировщика
*BalancersApi* | [**getBalancerRules**](docs/BalancersApi.md#getBalancerRules) | **GET** /api/v1/balancers/{balancer_id}/rules | Получение правил балансировщика
*BalancersApi* | [**getBalancers**](docs/BalancersApi.md#getBalancers) | **GET** /api/v1/balancers | Получение списка всех бaлансировщиков
*BalancersApi* | [**getBalancersPresets**](docs/BalancersApi.md#getBalancersPresets) | **GET** /api/v1/presets/balancers | Получение списка тарифов для балансировщика
*BalancersApi* | [**updateBalancer**](docs/BalancersApi.md#updateBalancer) | **PATCH** /api/v1/balancers/{balancer_id} | Обновление балансировщика
*BalancersApi* | [**updateBalancerRule**](docs/BalancersApi.md#updateBalancerRule) | **PATCH** /api/v1/balancers/{balancer_id}/rules/{rule_id} | Обновление правила для балансировщика
*DatabasesApi* | [**createDatabase**](docs/DatabasesApi.md#createDatabase) | **POST** /api/v1/dbs | Создание базы данных
*DatabasesApi* | [**createDatabaseBackup**](docs/DatabasesApi.md#createDatabaseBackup) | **POST** /api/v1/dbs/{db_id}/backups | Создание бэкапа базы данных
*DatabasesApi* | [**createDatabaseCluster**](docs/DatabasesApi.md#createDatabaseCluster) | **POST** /api/v1/databases | Создание кластера базы данных
*DatabasesApi* | [**createDatabaseInstance**](docs/DatabasesApi.md#createDatabaseInstance) | **POST** /api/v1/databases/{db_cluster_id}/instances | Создание инстанса базы данных
*DatabasesApi* | [**createDatabaseUser**](docs/DatabasesApi.md#createDatabaseUser) | **POST** /api/v1/databases/{db_cluster_id}/admins | Создание пользователя базы данных
*DatabasesApi* | [**deleteDatabase**](docs/DatabasesApi.md#deleteDatabase) | **DELETE** /api/v1/dbs/{db_id} | Удаление базы данных
*DatabasesApi* | [**deleteDatabaseBackup**](docs/DatabasesApi.md#deleteDatabaseBackup) | **DELETE** /api/v1/dbs/{db_id}/backups/{backup_id} | Удаление бэкапа базы данных
*DatabasesApi* | [**deleteDatabaseCluster**](docs/DatabasesApi.md#deleteDatabaseCluster) | **DELETE** /api/v1/databases/{db_cluster_id} | Удаление кластера базы данных
*DatabasesApi* | [**deleteDatabaseInstance**](docs/DatabasesApi.md#deleteDatabaseInstance) | **DELETE** /api/v1/databases/{db_cluster_id}/instances/{instance_id} | Удаление инстанса базы данных
*DatabasesApi* | [**deleteDatabaseUser**](docs/DatabasesApi.md#deleteDatabaseUser) | **DELETE** /api/v1/databases/{db_cluster_id}/admins/{admin_id} | Удаление пользователя базы данных
*DatabasesApi* | [**getDatabase**](docs/DatabasesApi.md#getDatabase) | **GET** /api/v1/dbs/{db_id} | Получение базы данных
*DatabasesApi* | [**getDatabaseAutoBackupsSettings**](docs/DatabasesApi.md#getDatabaseAutoBackupsSettings) | **GET** /api/v1/dbs/{db_id}/auto-backups | Получение настроек автобэкапов базы данных
*DatabasesApi* | [**getDatabaseBackup**](docs/DatabasesApi.md#getDatabaseBackup) | **GET** /api/v1/dbs/{db_id}/backups/{backup_id} | Получение бэкапа базы данных
*DatabasesApi* | [**getDatabaseBackups**](docs/DatabasesApi.md#getDatabaseBackups) | **GET** /api/v1/dbs/{db_id}/backups | Список бэкапов базы данных
*DatabasesApi* | [**getDatabaseCluster**](docs/DatabasesApi.md#getDatabaseCluster) | **GET** /api/v1/databases/{db_cluster_id} | Получение кластера базы данных
*DatabasesApi* | [**getDatabaseClusters**](docs/DatabasesApi.md#getDatabaseClusters) | **GET** /api/v1/databases | Получение списка кластеров баз данных
*DatabasesApi* | [**getDatabaseInstance**](docs/DatabasesApi.md#getDatabaseInstance) | **GET** /api/v1/databases/{db_cluster_id}/instances/{instance_id} | Получение инстанса базы данных
*DatabasesApi* | [**getDatabaseInstances**](docs/DatabasesApi.md#getDatabaseInstances) | **GET** /api/v1/databases/{db_cluster_id}/instances | Получение списка инстансов баз данных
*DatabasesApi* | [**getDatabaseUser**](docs/DatabasesApi.md#getDatabaseUser) | **GET** /api/v1/databases/{db_cluster_id}/admins/{admin_id} | Получение пользователя базы данных
*DatabasesApi* | [**getDatabaseUsers**](docs/DatabasesApi.md#getDatabaseUsers) | **GET** /api/v1/databases/{db_cluster_id}/admins | Получение списка пользователей базы данных
*DatabasesApi* | [**getDatabases**](docs/DatabasesApi.md#getDatabases) | **GET** /api/v1/dbs | Получение списка всех баз данных
*DatabasesApi* | [**getDatabasesPresets**](docs/DatabasesApi.md#getDatabasesPresets) | **GET** /api/v1/presets/dbs | Получение списка тарифов для баз данных
*DatabasesApi* | [**restoreDatabaseFromBackup**](docs/DatabasesApi.md#restoreDatabaseFromBackup) | **PUT** /api/v1/dbs/{db_id}/backups/{backup_id} | Восстановление базы данных из бэкапа
*DatabasesApi* | [**updateDatabase**](docs/DatabasesApi.md#updateDatabase) | **PATCH** /api/v1/dbs/{db_id} | Обновление базы данных
*DatabasesApi* | [**updateDatabaseAutoBackupsSettings**](docs/DatabasesApi.md#updateDatabaseAutoBackupsSettings) | **PATCH** /api/v1/dbs/{db_id}/auto-backups | Изменение настроек автобэкапов базы данных
*DatabasesApi* | [**updateDatabaseCluster**](docs/DatabasesApi.md#updateDatabaseCluster) | **PATCH** /api/v1/databases/{db_cluster_id} | Изменение кластера базы данных
*DatabasesApi* | [**updateDatabaseInstance**](docs/DatabasesApi.md#updateDatabaseInstance) | **PATCH** /api/v1/databases/{db_cluster_id}/instances/{instance_id} | Изменение инстанса базы данных
*DatabasesApi* | [**updateDatabaseUser**](docs/DatabasesApi.md#updateDatabaseUser) | **PATCH** /api/v1/databases/{db_cluster_id}/admins/{admin_id} | Изменение пользователя базы данных
*DedicatedServersApi* | [**createDedicatedServer**](docs/DedicatedServersApi.md#createDedicatedServer) | **POST** /api/v1/dedicated-servers | Создание выделенного сервера
*DedicatedServersApi* | [**deleteDedicatedServer**](docs/DedicatedServersApi.md#deleteDedicatedServer) | **DELETE** /api/v1/dedicated-servers/{dedicated_id} | Удаление выделенного сервера
*DedicatedServersApi* | [**getDedicatedServer**](docs/DedicatedServersApi.md#getDedicatedServer) | **GET** /api/v1/dedicated-servers/{dedicated_id} | Получение выделенного сервера
*DedicatedServersApi* | [**getDedicatedServerPresetAdditionalServices**](docs/DedicatedServersApi.md#getDedicatedServerPresetAdditionalServices) | **GET** /api/v1/presets/dedicated-servers/{preset_id}/additional-services | Получение дополнительных услуг для выделенного сервера
*DedicatedServersApi* | [**getDedicatedServers**](docs/DedicatedServersApi.md#getDedicatedServers) | **GET** /api/v1/dedicated-servers | Получение списка выделенных серверов
*DedicatedServersApi* | [**getDedicatedServersPresets**](docs/DedicatedServersApi.md#getDedicatedServersPresets) | **GET** /api/v1/presets/dedicated-servers | Получение списка тарифов для выделенного сервера
*DedicatedServersApi* | [**updateDedicatedServer**](docs/DedicatedServersApi.md#updateDedicatedServer) | **PATCH** /api/v1/dedicated-servers/{dedicated_id} | Обновление выделенного сервера
*DomainsApi* | [**addDomain**](docs/DomainsApi.md#addDomain) | **POST** /api/v1/add-domain/{fqdn} | Добавление домена на аккаунт
*DomainsApi* | [**addSubdomain**](docs/DomainsApi.md#addSubdomain) | **POST** /api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn} | Добавление поддомена
*DomainsApi* | [**checkDomain**](docs/DomainsApi.md#checkDomain) | **GET** /api/v1/check-domain/{fqdn} | Проверить, доступен ли домен для регистрации
*DomainsApi* | [**createDomainDNSRecord**](docs/DomainsApi.md#createDomainDNSRecord) | **POST** /api/v1/domains/{fqdn}/dns-records | Добавить информацию о DNS-записи для домена или поддомена
*DomainsApi* | [**createDomainRequest**](docs/DomainsApi.md#createDomainRequest) | **POST** /api/v1/domains-requests | Создание заявки на регистрацию/продление/трансфер домена
*DomainsApi* | [**deleteDomain**](docs/DomainsApi.md#deleteDomain) | **DELETE** /api/v1/domains/{fqdn} | Удаление домена
*DomainsApi* | [**deleteDomainDNSRecord**](docs/DomainsApi.md#deleteDomainDNSRecord) | **DELETE** /api/v1/domains/{fqdn}/dns-records/{record_id} | Удалить информацию о DNS-записи для домена или поддомена
*DomainsApi* | [**deleteSubdomain**](docs/DomainsApi.md#deleteSubdomain) | **DELETE** /api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn} | Удаление поддомена
*DomainsApi* | [**getDomain**](docs/DomainsApi.md#getDomain) | **GET** /api/v1/domains/{fqdn} | Получение информации о домене
*DomainsApi* | [**getDomainDNSRecords**](docs/DomainsApi.md#getDomainDNSRecords) | **GET** /api/v1/domains/{fqdn}/dns-records | Получить информацию обо всех пользовательских DNS-записях домена или поддомена
*DomainsApi* | [**getDomainDefaultDNSRecords**](docs/DomainsApi.md#getDomainDefaultDNSRecords) | **GET** /api/v1/domains/{fqdn}/default-dns-records | Получить информацию обо всех DNS-записях по умолчанию домена или поддомена
*DomainsApi* | [**getDomainNameServers**](docs/DomainsApi.md#getDomainNameServers) | **GET** /api/v1/domains/{fqdn}/name-servers | Получение списка name-серверов домена
*DomainsApi* | [**getDomainRequest**](docs/DomainsApi.md#getDomainRequest) | **GET** /api/v1/domains-requests/{request_id} | Получение заявки на регистрацию/продление/трансфер домена
*DomainsApi* | [**getDomainRequests**](docs/DomainsApi.md#getDomainRequests) | **GET** /api/v1/domains-requests | Получение списка заявок на регистрацию/продление/трансфер домена
*DomainsApi* | [**getDomains**](docs/DomainsApi.md#getDomains) | **GET** /api/v1/domains | Получение списка всех доменов
*DomainsApi* | [**getTLD**](docs/DomainsApi.md#getTLD) | **GET** /api/v1/tlds/{tld_id} | Получить информацию о доменной зоне по идентификатору
*DomainsApi* | [**getTLDs**](docs/DomainsApi.md#getTLDs) | **GET** /api/v1/tlds | Получить информацию о доменных зонах
*DomainsApi* | [**updateDomainAutoProlongation**](docs/DomainsApi.md#updateDomainAutoProlongation) | **PATCH** /api/v1/domains/{fqdn} | Включение/выключение автопродления домена
*DomainsApi* | [**updateDomainDNSRecord**](docs/DomainsApi.md#updateDomainDNSRecord) | **PATCH** /api/v1/domains/{fqdn}/dns-records/{record_id} | Обновить информацию о DNS-записи домена или поддомена
*DomainsApi* | [**updateDomainNameServers**](docs/DomainsApi.md#updateDomainNameServers) | **PUT** /api/v1/domains/{fqdn}/name-servers | Изменение name-серверов домена
*DomainsApi* | [**updateDomainRequest**](docs/DomainsApi.md#updateDomainRequest) | **PATCH** /api/v1/domains-requests/{request_id} | Оплата/обновление заявки на регистрацию/продление/трансфер домена
*FirewallApi* | [**addResourceToGroup**](docs/FirewallApi.md#addResourceToGroup) | **POST** /api/v1/firewall/groups/{group_id}/resources/{resource_id} | Линковка ресурса в firewall group
*FirewallApi* | [**createGroup**](docs/FirewallApi.md#createGroup) | **POST** /api/v1/firewall/groups | Создание группы правил
*FirewallApi* | [**createGroupRule**](docs/FirewallApi.md#createGroupRule) | **POST** /api/v1/firewall/groups/{group_id}/rules | Создание firewall правила
*FirewallApi* | [**deleteGroup**](docs/FirewallApi.md#deleteGroup) | **DELETE** /api/v1/firewall/groups/{group_id} | Удаление группы правил
*FirewallApi* | [**deleteGroupRule**](docs/FirewallApi.md#deleteGroupRule) | **DELETE** /api/v1/firewall/groups/{group_id}/rules/{rule_id} | Удаление firewall правила
*FirewallApi* | [**deleteResourceFromGroup**](docs/FirewallApi.md#deleteResourceFromGroup) | **DELETE** /api/v1/firewall/groups/{group_id}/resources/{resource_id} | Отлинковка ресурса из firewall group
*FirewallApi* | [**getGroup**](docs/FirewallApi.md#getGroup) | **GET** /api/v1/firewall/groups/{group_id} | Получение информации о группе правил
*FirewallApi* | [**getGroupResources**](docs/FirewallApi.md#getGroupResources) | **GET** /api/v1/firewall/groups/{group_id}/resources | Получение слинкованных ресурсов
*FirewallApi* | [**getGroupRule**](docs/FirewallApi.md#getGroupRule) | **GET** /api/v1/firewall/groups/{group_id}/rules/{rule_id} | Получение информации о правиле
*FirewallApi* | [**getGroupRules**](docs/FirewallApi.md#getGroupRules) | **GET** /api/v1/firewall/groups/{group_id}/rules | Получение списка правил
*FirewallApi* | [**getGroups**](docs/FirewallApi.md#getGroups) | **GET** /api/v1/firewall/groups | Получение групп правил
*FirewallApi* | [**getRulesForResource**](docs/FirewallApi.md#getRulesForResource) | **GET** /api/v1/firewall/service/{resource_type}/{resource_id} | Получение групп правил для ресурса
*FirewallApi* | [**updateGroup**](docs/FirewallApi.md#updateGroup) | **PATCH** /api/v1/firewall/groups/{group_id} | Обновление группы правил
*FirewallApi* | [**updateGroupRule**](docs/FirewallApi.md#updateGroupRule) | **PATCH** /api/v1/firewall/groups/{group_id}/rules/{rule_id} | Обновление firewall правила
*ImagesApi* | [**createImage**](docs/ImagesApi.md#createImage) | **POST** /api/v1/images | Создание образа
*ImagesApi* | [**createImageDownloadUrl**](docs/ImagesApi.md#createImageDownloadUrl) | **POST** /api/v1/images/{image_id}/download-url | Создание ссылки на скачивание образа
*ImagesApi* | [**deleteImage**](docs/ImagesApi.md#deleteImage) | **DELETE** /api/v1/images/{image_id} | Удаление образа
*ImagesApi* | [**deleteImageDownloadURL**](docs/ImagesApi.md#deleteImageDownloadURL) | **DELETE** /api/v1/images/{image_id}/download-url/{image_url_id} | Удаление ссылки на образ
*ImagesApi* | [**getImage**](docs/ImagesApi.md#getImage) | **GET** /api/v1/images/{image_id} | Получение информации о образе
*ImagesApi* | [**getImageDownloadURL**](docs/ImagesApi.md#getImageDownloadURL) | **GET** /api/v1/images/{image_id}/download-url/{image_url_id} | Получение информации о ссылке на скачивание образа
*ImagesApi* | [**getImageDownloadURLs**](docs/ImagesApi.md#getImageDownloadURLs) | **GET** /api/v1/images/{image_id}/download-url | Получение информации о ссылках на скачивание образов
*ImagesApi* | [**getImages**](docs/ImagesApi.md#getImages) | **GET** /api/v1/images | Получение списка образов
*ImagesApi* | [**updateImage**](docs/ImagesApi.md#updateImage) | **PATCH** /api/v1/images/{image_id} | Обновление информации о образе
*ImagesApi* | [**uploadImage**](docs/ImagesApi.md#uploadImage) | **POST** /api/v1/images/{image_id} | Загрузка образа
*KubernetesApi* | [**createCluster**](docs/KubernetesApi.md#createCluster) | **POST** /api/v1/k8s/clusters | Создание кластера
*KubernetesApi* | [**createClusterNodeGroup**](docs/KubernetesApi.md#createClusterNodeGroup) | **POST** /api/v1/k8s/clusters/{cluster_id}/groups | Создание группы нод
*KubernetesApi* | [**deleteCluster**](docs/KubernetesApi.md#deleteCluster) | **DELETE** /api/v1/k8s/clusters/{cluster_id} | Удаление кластера
*KubernetesApi* | [**deleteClusterNode**](docs/KubernetesApi.md#deleteClusterNode) | **DELETE** /api/v1/k8s/clusters/{cluster_id}/nodes/{node_id} | Удаление ноды
*KubernetesApi* | [**deleteClusterNodeGroup**](docs/KubernetesApi.md#deleteClusterNodeGroup) | **DELETE** /api/v1/k8s/clusters/{cluster_id}/groups/{group_id} | Удаление группы нод
*KubernetesApi* | [**getCluster**](docs/KubernetesApi.md#getCluster) | **GET** /api/v1/k8s/clusters/{cluster_id} | Получение информации о кластере
*KubernetesApi* | [**getClusterKubeconfig**](docs/KubernetesApi.md#getClusterKubeconfig) | **GET** /api/v1/k8s/clusters/{cluster_id}/kubeconfig | Получение файла kubeconfig
*KubernetesApi* | [**getClusterNodeGroup**](docs/KubernetesApi.md#getClusterNodeGroup) | **GET** /api/v1/k8s/clusters/{cluster_id}/groups/{group_id} | Получение информации о группе нод
*KubernetesApi* | [**getClusterNodeGroups**](docs/KubernetesApi.md#getClusterNodeGroups) | **GET** /api/v1/k8s/clusters/{cluster_id}/groups | Получение групп нод кластера
*KubernetesApi* | [**getClusterNodes**](docs/KubernetesApi.md#getClusterNodes) | **GET** /api/v1/k8s/clusters/{cluster_id}/nodes | Получение списка нод
*KubernetesApi* | [**getClusterNodesFromGroup**](docs/KubernetesApi.md#getClusterNodesFromGroup) | **GET** /api/v1/k8s/clusters/{cluster_id}/groups/{group_id}/nodes | Получение списка нод, принадлежащих группе
*KubernetesApi* | [**getClusterResources**](docs/KubernetesApi.md#getClusterResources) | **GET** /api/v1/k8s/clusters/{cluster_id}/resources | Получение ресурсов кластера
*KubernetesApi* | [**getClusters**](docs/KubernetesApi.md#getClusters) | **GET** /api/v1/k8s/clusters | Получение списка кластеров
*KubernetesApi* | [**getK8SNetworkDrivers**](docs/KubernetesApi.md#getK8SNetworkDrivers) | **GET** /api/v1/k8s/network_drivers | Получение списка сетевых драйверов k8s
*KubernetesApi* | [**getK8SVersions**](docs/KubernetesApi.md#getK8SVersions) | **GET** /api/v1/k8s/k8s_versions | Получение списка версий k8s
*KubernetesApi* | [**getKubernetesPresets**](docs/KubernetesApi.md#getKubernetesPresets) | **GET** /api/v1/presets/k8s | Получение списка тарифов
*KubernetesApi* | [**increaseCountOfNodesInGroup**](docs/KubernetesApi.md#increaseCountOfNodesInGroup) | **POST** /api/v1/k8s/clusters/{cluster_id}/groups/{group_id}/nodes | Увеличение количества нод в группе на указанное количество
*KubernetesApi* | [**reduceCountOfNodesInGroup**](docs/KubernetesApi.md#reduceCountOfNodesInGroup) | **DELETE** /api/v1/k8s/clusters/{cluster_id}/groups/{group_id}/nodes | Уменьшение количества нод в группе на указанное количество
*KubernetesApi* | [**updateCluster**](docs/KubernetesApi.md#updateCluster) | **PATCH** /api/v1/k8s/clusters/{cluster_id} | Обновление информации о кластере
*MailApi* | [**createDomainMailbox**](docs/MailApi.md#createDomainMailbox) | **POST** /api/v1/mail/domains/{domain} | Создание почтового ящика
*MailApi* | [**deleteMailbox**](docs/MailApi.md#deleteMailbox) | **DELETE** /api/v1/mail/domains/{domain}/mailboxes/{mailbox} | Удаление почтового ящика
*MailApi* | [**getDomainMailInfo**](docs/MailApi.md#getDomainMailInfo) | **GET** /api/v1/mail/domains/{domain}/info | Получение почтовой информации о домене
*MailApi* | [**getDomainMailboxes**](docs/MailApi.md#getDomainMailboxes) | **GET** /api/v1/mail/domains/{domain} | Получение списка почтовых ящиков домена
*MailApi* | [**getMailQuota**](docs/MailApi.md#getMailQuota) | **GET** /api/v1/mail/quota | Получение квоты почты аккаунта
*MailApi* | [**getMailbox**](docs/MailApi.md#getMailbox) | **GET** /api/v1/mail/domains/{domain}/mailboxes/{mailbox} | Получение почтового ящика
*MailApi* | [**getMailboxes**](docs/MailApi.md#getMailboxes) | **GET** /api/v1/mail | Получение списка почтовых ящиков аккаунта
*MailApi* | [**updateDomainMailInfo**](docs/MailApi.md#updateDomainMailInfo) | **PATCH** /api/v1/mail/domains/{domain}/info | Изменение почтовой информации о домене
*MailApi* | [**updateMailQuota**](docs/MailApi.md#updateMailQuota) | **PATCH** /api/v1/mail/quota | Изменение квоты почты аккаунта
*MailApi* | [**updateMailbox**](docs/MailApi.md#updateMailbox) | **PATCH** /api/v1/mail/domains/{domain}/mailboxes/{mailbox} | Изменение почтового ящика
*ProjectsApi* | [**addBalancerToProject**](docs/ProjectsApi.md#addBalancerToProject) | **POST** /api/v1/projects/{project_id}/resources/balancers | Добавление балансировщика в проект
*ProjectsApi* | [**addClusterToProject**](docs/ProjectsApi.md#addClusterToProject) | **POST** /api/v1/projects/{project_id}/resources/clusters | Добавление кластера в проект
*ProjectsApi* | [**addDatabaseToProject**](docs/ProjectsApi.md#addDatabaseToProject) | **POST** /api/v1/projects/{project_id}/resources/databases | Добавление базы данных в проект
*ProjectsApi* | [**addDedicatedServerToProject**](docs/ProjectsApi.md#addDedicatedServerToProject) | **POST** /api/v1/projects/{project_id}/resources/dedicated | Добавление выделенного сервера в проект
*ProjectsApi* | [**addServerToProject**](docs/ProjectsApi.md#addServerToProject) | **POST** /api/v1/projects/{project_id}/resources/servers | Добавление сервера в проект
*ProjectsApi* | [**addStorageToProject**](docs/ProjectsApi.md#addStorageToProject) | **POST** /api/v1/projects/{project_id}/resources/buckets | Добавление хранилища в проект
*ProjectsApi* | [**createProject**](docs/ProjectsApi.md#createProject) | **POST** /api/v1/projects | Создание проекта
*ProjectsApi* | [**deleteProject**](docs/ProjectsApi.md#deleteProject) | **DELETE** /api/v1/projects/{project_id} | Удаление проекта
*ProjectsApi* | [**getAccountBalancers**](docs/ProjectsApi.md#getAccountBalancers) | **GET** /api/v1/projects/resources/balancers | Получение списка всех балансировщиков на аккаунте
*ProjectsApi* | [**getAccountClusters**](docs/ProjectsApi.md#getAccountClusters) | **GET** /api/v1/projects/resources/clusters | Получение списка всех кластеров на аккаунте
*ProjectsApi* | [**getAccountDatabases**](docs/ProjectsApi.md#getAccountDatabases) | **GET** /api/v1/projects/resources/databases | Получение списка всех баз данных на аккаунте
*ProjectsApi* | [**getAccountDedicatedServers**](docs/ProjectsApi.md#getAccountDedicatedServers) | **GET** /api/v1/projects/resources/dedicated | Получение списка всех выделенных серверов на аккаунте
*ProjectsApi* | [**getAccountServers**](docs/ProjectsApi.md#getAccountServers) | **GET** /api/v1/projects/resources/servers | Получение списка всех серверов на аккаунте
*ProjectsApi* | [**getAccountStorages**](docs/ProjectsApi.md#getAccountStorages) | **GET** /api/v1/projects/resources/buckets | Получение списка всех хранилищ на аккаунте
*ProjectsApi* | [**getAllProjectResources**](docs/ProjectsApi.md#getAllProjectResources) | **GET** /api/v1/projects/{project_id}/resources | Получение всех ресурсов проекта
*ProjectsApi* | [**getProject**](docs/ProjectsApi.md#getProject) | **GET** /api/v1/projects/{project_id} | Получение проекта по идентификатору
*ProjectsApi* | [**getProjectBalancers**](docs/ProjectsApi.md#getProjectBalancers) | **GET** /api/v1/projects/{project_id}/resources/balancers | Получение списка балансировщиков проекта
*ProjectsApi* | [**getProjectClusters**](docs/ProjectsApi.md#getProjectClusters) | **GET** /api/v1/projects/{project_id}/resources/clusters | Получение списка кластеров проекта
*ProjectsApi* | [**getProjectDatabases**](docs/ProjectsApi.md#getProjectDatabases) | **GET** /api/v1/projects/{project_id}/resources/databases | Получение списка баз данных проекта
*ProjectsApi* | [**getProjectDedicatedServers**](docs/ProjectsApi.md#getProjectDedicatedServers) | **GET** /api/v1/projects/{project_id}/resources/dedicated | Получение списка выделенных серверов проекта
*ProjectsApi* | [**getProjectServers**](docs/ProjectsApi.md#getProjectServers) | **GET** /api/v1/projects/{project_id}/resources/servers | Получение списка серверов проекта
*ProjectsApi* | [**getProjectStorages**](docs/ProjectsApi.md#getProjectStorages) | **GET** /api/v1/projects/{project_id}/resources/buckets | Получение списка хранилищ проекта
*ProjectsApi* | [**getProjects**](docs/ProjectsApi.md#getProjects) | **GET** /api/v1/projects | Получение списка проектов
*ProjectsApi* | [**transferResourceToAnotherProject**](docs/ProjectsApi.md#transferResourceToAnotherProject) | **PUT** /api/v1/projects/{project_id}/resources/transfer | Перенести ресурс в другой проект
*ProjectsApi* | [**updateProject**](docs/ProjectsApi.md#updateProject) | **PUT** /api/v1/projects/{project_id} | Изменение проекта
*S3Api* | [**addStorageSubdomainCertificate**](docs/S3Api.md#addStorageSubdomainCertificate) | **POST** /api/v1/storages/certificates/generate | Добавление сертификата для поддомена хранилища
*S3Api* | [**addStorageSubdomains**](docs/S3Api.md#addStorageSubdomains) | **POST** /api/v1/storages/buckets/{bucket_id}/subdomains | Добавление поддоменов для хранилища
*S3Api* | [**copyStorageFile**](docs/S3Api.md#copyStorageFile) | **POST** /api/v1/storages/buckets/{bucket_id}/object-manager/copy | Копирование файла/директории в хранилище
*S3Api* | [**createFolderInStorage**](docs/S3Api.md#createFolderInStorage) | **POST** /api/v1/storages/buckets/{bucket_id}/object-manager/mkdir | Создание директории в хранилище
*S3Api* | [**createStorage**](docs/S3Api.md#createStorage) | **POST** /api/v1/storages/buckets | Создание хранилища
*S3Api* | [**deleteStorage**](docs/S3Api.md#deleteStorage) | **DELETE** /api/v1/storages/buckets/{bucket_id} | Удаление хранилища на аккаунте
*S3Api* | [**deleteStorageFile**](docs/S3Api.md#deleteStorageFile) | **DELETE** /api/v1/storages/buckets/{bucket_id}/object-manager/remove | Удаление файла/директории в хранилище
*S3Api* | [**deleteStorageSubdomains**](docs/S3Api.md#deleteStorageSubdomains) | **DELETE** /api/v1/storages/buckets/{bucket_id}/subdomains | Удаление поддоменов хранилища
*S3Api* | [**getStorageFilesList**](docs/S3Api.md#getStorageFilesList) | **GET** /api/v1/storages/buckets/{bucket_id}/object-manager/list | Получение списка файлов в хранилище по префиксу
*S3Api* | [**getStorageSubdomains**](docs/S3Api.md#getStorageSubdomains) | **GET** /api/v1/storages/buckets/{bucket_id}/subdomains | Получение списка поддоменов хранилища
*S3Api* | [**getStorageTransferStatus**](docs/S3Api.md#getStorageTransferStatus) | **GET** /api/v1/storages/buckets/{bucket_id}/transfer-status | Получение статуса переноса хранилища от стороннего S3 в Timeweb Cloud
*S3Api* | [**getStorageUsers**](docs/S3Api.md#getStorageUsers) | **GET** /api/v1/storages/users | Получение списка пользователей хранилищ аккаунта
*S3Api* | [**getStorages**](docs/S3Api.md#getStorages) | **GET** /api/v1/storages/buckets | Получение списка хранилищ аккаунта
*S3Api* | [**getStoragesPresets**](docs/S3Api.md#getStoragesPresets) | **GET** /api/v1/presets/storages | Получение списка тарифов для хранилищ
*S3Api* | [**renameStorageFile**](docs/S3Api.md#renameStorageFile) | **POST** /api/v1/storages/buckets/{bucket_id}/object-manager/rename | Переименование файла/директории в хранилище
*S3Api* | [**transferStorage**](docs/S3Api.md#transferStorage) | **POST** /api/v1/storages/transfer | Перенос хранилища от стороннего провайдера S3 в Timeweb Cloud
*S3Api* | [**updateStorage**](docs/S3Api.md#updateStorage) | **PATCH** /api/v1/storages/buckets/{bucket_id} | Изменение хранилища на аккаунте
*S3Api* | [**updateStorageUser**](docs/S3Api.md#updateStorageUser) | **PATCH** /api/v1/storages/users/{user_id} | Изменение пароля пользователя-администратора хранилища
*S3Api* | [**uploadFileToStorage**](docs/S3Api.md#uploadFileToStorage) | **POST** /api/v1/storages/buckets/{bucket_id}/object-manager/upload | Загрузка файлов в хранилище
*ServersApi* | [**addServerIP**](docs/ServersApi.md#addServerIP) | **POST** /api/v1/servers/{server_id}/ips | Добавление IP-адреса сервера
*ServersApi* | [**cloneServer**](docs/ServersApi.md#cloneServer) | **POST** /api/v1/servers/{server_id}/clone | Клонирование сервера
*ServersApi* | [**createServer**](docs/ServersApi.md#createServer) | **POST** /api/v1/servers | Создание сервера
*ServersApi* | [**createServerDisk**](docs/ServersApi.md#createServerDisk) | **POST** /api/v1/servers/{server_id}/disks | Создание диска сервера
*ServersApi* | [**createServerDiskBackup**](docs/ServersApi.md#createServerDiskBackup) | **POST** /api/v1/servers/{server_id}/disks/{disk_id}/backups | Создание бэкапа диска сервера
*ServersApi* | [**deleteServer**](docs/ServersApi.md#deleteServer) | **DELETE** /api/v1/servers/{server_id} | Удаление сервера
*ServersApi* | [**deleteServerDisk**](docs/ServersApi.md#deleteServerDisk) | **DELETE** /api/v1/servers/{server_id}/disks/{disk_id} | Удаление диска сервера
*ServersApi* | [**deleteServerDiskBackup**](docs/ServersApi.md#deleteServerDiskBackup) | **DELETE** /api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id} | Удаление бэкапа диска сервера
*ServersApi* | [**deleteServerIP**](docs/ServersApi.md#deleteServerIP) | **DELETE** /api/v1/servers/{server_id}/ips | Удаление IP-адреса сервера
*ServersApi* | [**getConfigurators**](docs/ServersApi.md#getConfigurators) | **GET** /api/v1/configurator/servers | Получение списка конфигураторов серверов
*ServersApi* | [**getOsList**](docs/ServersApi.md#getOsList) | **GET** /api/v1/os/servers | Получение списка операционных систем
*ServersApi* | [**getServer**](docs/ServersApi.md#getServer) | **GET** /api/v1/servers/{server_id} | Получение сервера
*ServersApi* | [**getServerDisk**](docs/ServersApi.md#getServerDisk) | **GET** /api/v1/servers/{server_id}/disks/{disk_id} | Получение диска сервера
*ServersApi* | [**getServerDiskAutoBackupSettings**](docs/ServersApi.md#getServerDiskAutoBackupSettings) | **GET** /api/v1/servers/{server_id}/disks/{disk_id}/auto-backups | Получить настройки автобэкапов диска сервера
*ServersApi* | [**getServerDiskBackup**](docs/ServersApi.md#getServerDiskBackup) | **GET** /api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id} | Получение бэкапа диска сервера
*ServersApi* | [**getServerDiskBackups**](docs/ServersApi.md#getServerDiskBackups) | **GET** /api/v1/servers/{server_id}/disks/{disk_id}/backups | Получение списка бэкапов диска сервера
*ServersApi* | [**getServerDisks**](docs/ServersApi.md#getServerDisks) | **GET** /api/v1/servers/{server_id}/disks | Получение списка дисков сервера
*ServersApi* | [**getServerIPs**](docs/ServersApi.md#getServerIPs) | **GET** /api/v1/servers/{server_id}/ips | Получение списка IP-адресов сервера
*ServersApi* | [**getServerLogs**](docs/ServersApi.md#getServerLogs) | **GET** /api/v1/servers/{server_id}/logs | Получение списка логов сервера
*ServersApi* | [**getServerStatistics**](docs/ServersApi.md#getServerStatistics) | **GET** /api/v1/servers/{server_id}/statistics | Получение статистики сервера
*ServersApi* | [**getServers**](docs/ServersApi.md#getServers) | **GET** /api/v1/servers | Получение списка серверов
*ServersApi* | [**getServersPresets**](docs/ServersApi.md#getServersPresets) | **GET** /api/v1/presets/servers | Получение списка тарифов серверов
*ServersApi* | [**getSoftware**](docs/ServersApi.md#getSoftware) | **GET** /api/v1/software/servers | Получение списка ПО из маркетплейса
*ServersApi* | [**imageUnmountAndServerReload**](docs/ServersApi.md#imageUnmountAndServerReload) | **POST** /api/v1/servers/{server_id}/image-unmount | Отмонтирование ISO образа и перезагрузка сервера
*ServersApi* | [**performActionOnBackup**](docs/ServersApi.md#performActionOnBackup) | **POST** /api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id}/action | Выполнение действия над бэкапом диска сервера
*ServersApi* | [**performActionOnServer**](docs/ServersApi.md#performActionOnServer) | **POST** /api/v1/servers/{server_id}/action | Выполнение действия над сервером
*ServersApi* | [**updateServer**](docs/ServersApi.md#updateServer) | **PATCH** /api/v1/servers/{server_id} | Изменение сервера
*ServersApi* | [**updateServerDisk**](docs/ServersApi.md#updateServerDisk) | **PATCH** /api/v1/servers/{server_id}/disks/{disk_id} | Изменение параметров диска сервера
*ServersApi* | [**updateServerDiskAutoBackupSettings**](docs/ServersApi.md#updateServerDiskAutoBackupSettings) | **PATCH** /api/v1/servers/{server_id}/disks/{disk_id}/auto-backups | Изменение настроек автобэкапов диска сервера
*ServersApi* | [**updateServerDiskBackup**](docs/ServersApi.md#updateServerDiskBackup) | **PATCH** /api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id} | Изменение бэкапа диска сервера
*ServersApi* | [**updateServerIP**](docs/ServersApi.md#updateServerIP) | **PATCH** /api/v1/servers/{server_id}/ips | Изменение IP-адреса сервера
*ServersApi* | [**updateServerNAT**](docs/ServersApi.md#updateServerNAT) | **PATCH** /api/v1/servers/{server_id}/local-networks/nat-mode | Изменение правил маршрутизации трафика сервера (NAT)
*ServersApi* | [**updateServerOSBootMode**](docs/ServersApi.md#updateServerOSBootMode) | **POST** /api/v1/servers/{server_id}/boot-mode | Выбор типа загрузки операционной системы сервера
*SshApi* | [**addKeyToServer**](docs/SshApi.md#addKeyToServer) | **POST** /api/v1/servers/{server_id}/ssh-keys | Добавление SSH-ключей на сервер
*SshApi* | [**createKey**](docs/SshApi.md#createKey) | **POST** /api/v1/ssh-keys | Создание SSH-ключа
*SshApi* | [**deleteKey**](docs/SshApi.md#deleteKey) | **DELETE** /api/v1/ssh-keys/{ssh_key_id} | Удаление SSH-ключа по уникальному идентификатору
*SshApi* | [**deleteKeyFromServer**](docs/SshApi.md#deleteKeyFromServer) | **DELETE** /api/v1/servers/{server_id}/ssh-keys/{ssh_key_id} | Удаление SSH-ключей с сервера
*SshApi* | [**getKey**](docs/SshApi.md#getKey) | **GET** /api/v1/ssh-keys/{ssh_key_id} | Получение SSH-ключа по уникальному идентификатору
*SshApi* | [**getKeys**](docs/SshApi.md#getKeys) | **GET** /api/v1/ssh-keys | Получение списка SSH-ключей
*SshApi* | [**updateKey**](docs/SshApi.md#updateKey) | **PATCH** /api/v1/ssh-keys/{ssh_key_id} | Изменение SSH-ключа по уникальному идентификатору
*VpcApi* | [**createVPC**](docs/VpcApi.md#createVPC) | **POST** /api/v2/vpcs | Создание VPC
*VpcApi* | [**deleteVPC**](docs/VpcApi.md#deleteVPC) | **DELETE** /api/v1/vpcs/{vpc_id} | Удаление VPC по идентификатору сети
*VpcApi* | [**getVPC**](docs/VpcApi.md#getVPC) | **GET** /api/v2/vpcs/{vpc_id} | Получение VPC
*VpcApi* | [**getVPCPorts**](docs/VpcApi.md#getVPCPorts) | **GET** /api/v1/vpcs/{vpc_id}/ports | Получение списка портов для VPC
*VpcApi* | [**getVPCServices**](docs/VpcApi.md#getVPCServices) | **GET** /api/v2/vpcs/{vpc_id}/services | Получение списка сервисов в VPC
*VpcApi* | [**getVPCs**](docs/VpcApi.md#getVPCs) | **GET** /api/v2/vpcs | Получение списка VPCs
*VpcApi* | [**updateVPCs**](docs/VpcApi.md#updateVPCs) | **PATCH** /api/v2/vpcs/{vpc_id} | Изменение VPC по идентификатору сети


## Documentation for Models

 - [AddBalancerToProject200Response](docs/AddBalancerToProject200Response.md)
 - [AddBalancerToProjectRequest](docs/AddBalancerToProjectRequest.md)
 - [AddClusterToProjectRequest](docs/AddClusterToProjectRequest.md)
 - [AddCountries](docs/AddCountries.md)
 - [AddCountriesToAllowedList201Response](docs/AddCountriesToAllowedList201Response.md)
 - [AddCountriesToAllowedListRequest](docs/AddCountriesToAllowedListRequest.md)
 - [AddDatabaseToProjectRequest](docs/AddDatabaseToProjectRequest.md)
 - [AddDedicatedServerToProjectRequest](docs/AddDedicatedServerToProjectRequest.md)
 - [AddIPsToAllowedList201Response](docs/AddIPsToAllowedList201Response.md)
 - [AddIPsToAllowedListRequest](docs/AddIPsToAllowedListRequest.md)
 - [AddIPsToBalancerRequest](docs/AddIPsToBalancerRequest.md)
 - [AddIps](docs/AddIps.md)
 - [AddKeyToServerRequest](docs/AddKeyToServerRequest.md)
 - [AddResourceToGroup201Response](docs/AddResourceToGroup201Response.md)
 - [AddServerIP201Response](docs/AddServerIP201Response.md)
 - [AddServerIPRequest](docs/AddServerIPRequest.md)
 - [AddServerToProjectRequest](docs/AddServerToProjectRequest.md)
 - [AddStorageSubdomainCertificateRequest](docs/AddStorageSubdomainCertificateRequest.md)
 - [AddStorageSubdomains200Response](docs/AddStorageSubdomains200Response.md)
 - [AddStorageSubdomainsRequest](docs/AddStorageSubdomainsRequest.md)
 - [AddStorageToProjectRequest](docs/AddStorageToProjectRequest.md)
 - [AddSubdomain201Response](docs/AddSubdomain201Response.md)
 - [AddedSubdomain](docs/AddedSubdomain.md)
 - [ApiKey](docs/ApiKey.md)
 - [Auth](docs/Auth.md)
 - [AutoBackup](docs/AutoBackup.md)
 - [AutoReplyIsDisabled](docs/AutoReplyIsDisabled.md)
 - [AutoReplyIsEnabled](docs/AutoReplyIsEnabled.md)
 - [Backup](docs/Backup.md)
 - [Balancer](docs/Balancer.md)
 - [BaseError](docs/BaseError.md)
 - [Bonus](docs/Bonus.md)
 - [Bucket](docs/Bucket.md)
 - [BucketDiskStats](docs/BucketDiskStats.md)
 - [BucketUser](docs/BucketUser.md)
 - [CheckDomain200Response](docs/CheckDomain200Response.md)
 - [Cidr](docs/Cidr.md)
 - [Cluster](docs/Cluster.md)
 - [ClusterEdit](docs/ClusterEdit.md)
 - [ClusterIn](docs/ClusterIn.md)
 - [ClusterOut](docs/ClusterOut.md)
 - [ClusterResponse](docs/ClusterResponse.md)
 - [Clusterk8s](docs/Clusterk8s.md)
 - [ClustersResponse](docs/ClustersResponse.md)
 - [ConfigParameters](docs/ConfigParameters.md)
 - [CopyStorageFileRequest](docs/CopyStorageFileRequest.md)
 - [Cores](docs/Cores.md)
 - [CreateAdmin](docs/CreateAdmin.md)
 - [CreateApiKey](docs/CreateApiKey.md)
 - [CreateBalancer](docs/CreateBalancer.md)
 - [CreateBalancer200Response](docs/CreateBalancer200Response.md)
 - [CreateBalancerRule200Response](docs/CreateBalancerRule200Response.md)
 - [CreateCluster](docs/CreateCluster.md)
 - [CreateCluster201Response](docs/CreateCluster201Response.md)
 - [CreateClusterAdmin](docs/CreateClusterAdmin.md)
 - [CreateClusterInstance](docs/CreateClusterInstance.md)
 - [CreateClusterNodeGroup201Response](docs/CreateClusterNodeGroup201Response.md)
 - [CreateDatabase201Response](docs/CreateDatabase201Response.md)
 - [CreateDatabaseBackup201Response](docs/CreateDatabaseBackup201Response.md)
 - [CreateDatabaseBackup409Response](docs/CreateDatabaseBackup409Response.md)
 - [CreateDatabaseBackup409ResponseMessage](docs/CreateDatabaseBackup409ResponseMessage.md)
 - [CreateDatabaseCluster201Response](docs/CreateDatabaseCluster201Response.md)
 - [CreateDatabaseInstance201Response](docs/CreateDatabaseInstance201Response.md)
 - [CreateDatabaseUser201Response](docs/CreateDatabaseUser201Response.md)
 - [CreateDb](docs/CreateDb.md)
 - [CreateDedicatedServer](docs/CreateDedicatedServer.md)
 - [CreateDedicatedServer201Response](docs/CreateDedicatedServer201Response.md)
 - [CreateDns](docs/CreateDns.md)
 - [CreateDomainDNSRecord201Response](docs/CreateDomainDNSRecord201Response.md)
 - [CreateDomainMailbox201Response](docs/CreateDomainMailbox201Response.md)
 - [CreateDomainMailboxRequest](docs/CreateDomainMailboxRequest.md)
 - [CreateDomainRequest201Response](docs/CreateDomainRequest201Response.md)
 - [CreateDomainRequestRequest](docs/CreateDomainRequestRequest.md)
 - [CreateFolderInStorageRequest](docs/CreateFolderInStorageRequest.md)
 - [CreateGroup201Response](docs/CreateGroup201Response.md)
 - [CreateGroupRule201Response](docs/CreateGroupRule201Response.md)
 - [CreateImage201Response](docs/CreateImage201Response.md)
 - [CreateImageDownloadUrl201Response](docs/CreateImageDownloadUrl201Response.md)
 - [CreateInstance](docs/CreateInstance.md)
 - [CreateKey201Response](docs/CreateKey201Response.md)
 - [CreateKeyRequest](docs/CreateKeyRequest.md)
 - [CreateProject](docs/CreateProject.md)
 - [CreateProject201Response](docs/CreateProject201Response.md)
 - [CreateRule](docs/CreateRule.md)
 - [CreateServer](docs/CreateServer.md)
 - [CreateServer201Response](docs/CreateServer201Response.md)
 - [CreateServerConfiguration](docs/CreateServerConfiguration.md)
 - [CreateServerDisk201Response](docs/CreateServerDisk201Response.md)
 - [CreateServerDiskBackup201Response](docs/CreateServerDiskBackup201Response.md)
 - [CreateServerDiskBackupRequest](docs/CreateServerDiskBackupRequest.md)
 - [CreateServerDiskRequest](docs/CreateServerDiskRequest.md)
 - [CreateStorage201Response](docs/CreateStorage201Response.md)
 - [CreateStorageRequest](docs/CreateStorageRequest.md)
 - [CreateToken201Response](docs/CreateToken201Response.md)
 - [CreateVPC201Response](docs/CreateVPC201Response.md)
 - [CreateVpc](docs/CreateVpc.md)
 - [CreatedApiKey](docs/CreatedApiKey.md)
 - [DatabaseAdmin](docs/DatabaseAdmin.md)
 - [DatabaseAdminInstancesInner](docs/DatabaseAdminInstancesInner.md)
 - [DatabaseCluster](docs/DatabaseCluster.md)
 - [DatabaseClusterDiskStats](docs/DatabaseClusterDiskStats.md)
 - [DatabaseClusterNetworksInner](docs/DatabaseClusterNetworksInner.md)
 - [DatabaseClusterNetworksInnerIpsInner](docs/DatabaseClusterNetworksInnerIpsInner.md)
 - [DatabaseInstance](docs/DatabaseInstance.md)
 - [Db](docs/Db.md)
 - [DbDiskStats](docs/DbDiskStats.md)
 - [DedicatedServer](docs/DedicatedServer.md)
 - [DedicatedServerAdditionalService](docs/DedicatedServerAdditionalService.md)
 - [DedicatedServerPreset](docs/DedicatedServerPreset.md)
 - [DedicatedServerPresetCpu](docs/DedicatedServerPresetCpu.md)
 - [DedicatedServerPresetDisk](docs/DedicatedServerPresetDisk.md)
 - [DedicatedServerPresetMemory](docs/DedicatedServerPresetMemory.md)
 - [DeleteBalancer200Response](docs/DeleteBalancer200Response.md)
 - [DeleteCluster200Response](docs/DeleteCluster200Response.md)
 - [DeleteCountriesFromAllowedList200Response](docs/DeleteCountriesFromAllowedList200Response.md)
 - [DeleteCountriesFromAllowedListRequest](docs/DeleteCountriesFromAllowedListRequest.md)
 - [DeleteDatabase200Response](docs/DeleteDatabase200Response.md)
 - [DeleteDatabaseCluster200Response](docs/DeleteDatabaseCluster200Response.md)
 - [DeleteIPsFromAllowedList200Response](docs/DeleteIPsFromAllowedList200Response.md)
 - [DeleteIPsFromAllowedListRequest](docs/DeleteIPsFromAllowedListRequest.md)
 - [DeleteServer200Response](docs/DeleteServer200Response.md)
 - [DeleteServerIPRequest](docs/DeleteServerIPRequest.md)
 - [DeleteServiceResponse](docs/DeleteServiceResponse.md)
 - [DeleteStorage200Response](docs/DeleteStorage200Response.md)
 - [DeleteStorageFileRequest](docs/DeleteStorageFileRequest.md)
 - [DeleteVPC204Response](docs/DeleteVPC204Response.md)
 - [DnsRecord](docs/DnsRecord.md)
 - [DnsRecordData](docs/DnsRecordData.md)
 - [Domain](docs/Domain.md)
 - [DomainAllowedBuyPeriodsInner](docs/DomainAllowedBuyPeriodsInner.md)
 - [DomainInfo](docs/DomainInfo.md)
 - [DomainNameServer](docs/DomainNameServer.md)
 - [DomainNameServerItemsInner](docs/DomainNameServerItemsInner.md)
 - [DomainPaymentPeriod](docs/DomainPaymentPeriod.md)
 - [DomainPrimeType](docs/DomainPrimeType.md)
 - [DomainRequest](docs/DomainRequest.md)
 - [Download](docs/Download.md)
 - [EditApiKey](docs/EditApiKey.md)
 - [Finances](docs/Finances.md)
 - [FirewallGroupInAPI](docs/FirewallGroupInAPI.md)
 - [FirewallGroupOutAPI](docs/FirewallGroupOutAPI.md)
 - [FirewallGroupOutResponse](docs/FirewallGroupOutResponse.md)
 - [FirewallGroupResourceOutAPI](docs/FirewallGroupResourceOutAPI.md)
 - [FirewallGroupResourceOutResponse](docs/FirewallGroupResourceOutResponse.md)
 - [FirewallGroupResourcesOutResponse](docs/FirewallGroupResourcesOutResponse.md)
 - [FirewallGroupsOutResponse](docs/FirewallGroupsOutResponse.md)
 - [FirewallRuleDirection](docs/FirewallRuleDirection.md)
 - [FirewallRuleInAPI](docs/FirewallRuleInAPI.md)
 - [FirewallRuleOutAPI](docs/FirewallRuleOutAPI.md)
 - [FirewallRuleOutResponse](docs/FirewallRuleOutResponse.md)
 - [FirewallRuleProtocol](docs/FirewallRuleProtocol.md)
 - [FirewallRulesOutResponse](docs/FirewallRulesOutResponse.md)
 - [ForwardingIncomingIsDisabled](docs/ForwardingIncomingIsDisabled.md)
 - [ForwardingIncomingIsEnabled](docs/ForwardingIncomingIsEnabled.md)
 - [ForwardingOutgoingIsDisabled](docs/ForwardingOutgoingIsDisabled.md)
 - [ForwardingOutgoingIsEnabled](docs/ForwardingOutgoingIsEnabled.md)
 - [Free](docs/Free.md)
 - [GetAccountStatus200Response](docs/GetAccountStatus200Response.md)
 - [GetAllProjectResources200Response](docs/GetAllProjectResources200Response.md)
 - [GetAuthAccessSettings200Response](docs/GetAuthAccessSettings200Response.md)
 - [GetAuthAccessSettings200ResponseAllOfWhiteList](docs/GetAuthAccessSettings200ResponseAllOfWhiteList.md)
 - [GetBalancerIPs200Response](docs/GetBalancerIPs200Response.md)
 - [GetBalancerRules200Response](docs/GetBalancerRules200Response.md)
 - [GetBalancers200Response](docs/GetBalancers200Response.md)
 - [GetBalancersPresets200Response](docs/GetBalancersPresets200Response.md)
 - [GetClusterNodeGroups200Response](docs/GetClusterNodeGroups200Response.md)
 - [GetClusterNodesFromGroup200Response](docs/GetClusterNodesFromGroup200Response.md)
 - [GetClusterResources200Response](docs/GetClusterResources200Response.md)
 - [GetClusters200Response](docs/GetClusters200Response.md)
 - [GetConfigurators200Response](docs/GetConfigurators200Response.md)
 - [GetCountries200Response](docs/GetCountries200Response.md)
 - [GetDatabaseAutoBackupsSettings200Response](docs/GetDatabaseAutoBackupsSettings200Response.md)
 - [GetDatabaseBackups200Response](docs/GetDatabaseBackups200Response.md)
 - [GetDatabaseClusters200Response](docs/GetDatabaseClusters200Response.md)
 - [GetDatabaseInstances200Response](docs/GetDatabaseInstances200Response.md)
 - [GetDatabaseUsers200Response](docs/GetDatabaseUsers200Response.md)
 - [GetDatabases200Response](docs/GetDatabases200Response.md)
 - [GetDatabasesPresets200Response](docs/GetDatabasesPresets200Response.md)
 - [GetDedicatedServerPresetAdditionalServices200Response](docs/GetDedicatedServerPresetAdditionalServices200Response.md)
 - [GetDedicatedServers200Response](docs/GetDedicatedServers200Response.md)
 - [GetDedicatedServersPresets200Response](docs/GetDedicatedServersPresets200Response.md)
 - [GetDomain200Response](docs/GetDomain200Response.md)
 - [GetDomainDNSRecords200Response](docs/GetDomainDNSRecords200Response.md)
 - [GetDomainMailInfo200Response](docs/GetDomainMailInfo200Response.md)
 - [GetDomainNameServers200Response](docs/GetDomainNameServers200Response.md)
 - [GetDomainRequests200Response](docs/GetDomainRequests200Response.md)
 - [GetDomains200Response](docs/GetDomains200Response.md)
 - [GetFinances200Response](docs/GetFinances200Response.md)
 - [GetFinances400Response](docs/GetFinances400Response.md)
 - [GetFinances400ResponseMessage](docs/GetFinances400ResponseMessage.md)
 - [GetFinances401Response](docs/GetFinances401Response.md)
 - [GetFinances401ResponseMessage](docs/GetFinances401ResponseMessage.md)
 - [GetFinances403Response](docs/GetFinances403Response.md)
 - [GetFinances403ResponseMessage](docs/GetFinances403ResponseMessage.md)
 - [GetFinances404Response](docs/GetFinances404Response.md)
 - [GetFinances404ResponseMessage](docs/GetFinances404ResponseMessage.md)
 - [GetFinances429Response](docs/GetFinances429Response.md)
 - [GetFinances429ResponseMessage](docs/GetFinances429ResponseMessage.md)
 - [GetFinances500Response](docs/GetFinances500Response.md)
 - [GetFinances500ResponseMessage](docs/GetFinances500ResponseMessage.md)
 - [GetGroupResources200Response](docs/GetGroupResources200Response.md)
 - [GetGroupRules200Response](docs/GetGroupRules200Response.md)
 - [GetGroups200Response](docs/GetGroups200Response.md)
 - [GetImageDownloadURLs200Response](docs/GetImageDownloadURLs200Response.md)
 - [GetImages200Response](docs/GetImages200Response.md)
 - [GetK8SNetworkDrivers200Response](docs/GetK8SNetworkDrivers200Response.md)
 - [GetK8SVersions200Response](docs/GetK8SVersions200Response.md)
 - [GetKey200Response](docs/GetKey200Response.md)
 - [GetKeys200Response](docs/GetKeys200Response.md)
 - [GetKubernetesPresets200Response](docs/GetKubernetesPresets200Response.md)
 - [GetMailQuota200Response](docs/GetMailQuota200Response.md)
 - [GetMailboxes200Response](docs/GetMailboxes200Response.md)
 - [GetNotificationSettings200Response](docs/GetNotificationSettings200Response.md)
 - [GetOsList200Response](docs/GetOsList200Response.md)
 - [GetProjectBalancers200Response](docs/GetProjectBalancers200Response.md)
 - [GetProjectClusters200Response](docs/GetProjectClusters200Response.md)
 - [GetProjectDatabases200Response](docs/GetProjectDatabases200Response.md)
 - [GetProjectDedicatedServers200Response](docs/GetProjectDedicatedServers200Response.md)
 - [GetProjectServers200Response](docs/GetProjectServers200Response.md)
 - [GetProjectStorages200Response](docs/GetProjectStorages200Response.md)
 - [GetProjects200Response](docs/GetProjects200Response.md)
 - [GetServerDiskAutoBackupSettings200Response](docs/GetServerDiskAutoBackupSettings200Response.md)
 - [GetServerDiskBackup200Response](docs/GetServerDiskBackup200Response.md)
 - [GetServerDiskBackups200Response](docs/GetServerDiskBackups200Response.md)
 - [GetServerDisks200Response](docs/GetServerDisks200Response.md)
 - [GetServerIPs200Response](docs/GetServerIPs200Response.md)
 - [GetServerLogs200Response](docs/GetServerLogs200Response.md)
 - [GetServerStatistics200Response](docs/GetServerStatistics200Response.md)
 - [GetServerStatistics200ResponseAllOfCpuInner](docs/GetServerStatistics200ResponseAllOfCpuInner.md)
 - [GetServerStatistics200ResponseAllOfDiskInner](docs/GetServerStatistics200ResponseAllOfDiskInner.md)
 - [GetServerStatistics200ResponseAllOfNetworkTrafficInner](docs/GetServerStatistics200ResponseAllOfNetworkTrafficInner.md)
 - [GetServerStatistics200ResponseAllOfRamInner](docs/GetServerStatistics200ResponseAllOfRamInner.md)
 - [GetServers200Response](docs/GetServers200Response.md)
 - [GetServersPresets200Response](docs/GetServersPresets200Response.md)
 - [GetSoftware200Response](docs/GetSoftware200Response.md)
 - [GetStorageFilesList200Response](docs/GetStorageFilesList200Response.md)
 - [GetStorageSubdomains200Response](docs/GetStorageSubdomains200Response.md)
 - [GetStorageTransferStatus200Response](docs/GetStorageTransferStatus200Response.md)
 - [GetStorageUsers200Response](docs/GetStorageUsers200Response.md)
 - [GetStoragesPresets200Response](docs/GetStoragesPresets200Response.md)
 - [GetTLD200Response](docs/GetTLD200Response.md)
 - [GetTLDs200Response](docs/GetTLDs200Response.md)
 - [GetTokens200Response](docs/GetTokens200Response.md)
 - [GetVPCPorts200Response](docs/GetVPCPorts200Response.md)
 - [GetVPCServices200Response](docs/GetVPCServices200Response.md)
 - [GetVPCs200Response](docs/GetVPCs200Response.md)
 - [Group](docs/Group.md)
 - [Image](docs/Image.md)
 - [ImageDownloadAPI](docs/ImageDownloadAPI.md)
 - [ImageDownloadResponse](docs/ImageDownloadResponse.md)
 - [ImageDownloadsResponse](docs/ImageDownloadsResponse.md)
 - [ImageInAPI](docs/ImageInAPI.md)
 - [ImageOutAPI](docs/ImageOutAPI.md)
 - [ImageOutResponse](docs/ImageOutResponse.md)
 - [ImageStatus](docs/ImageStatus.md)
 - [ImageUpdateAPI](docs/ImageUpdateAPI.md)
 - [ImageUrlAuth](docs/ImageUrlAuth.md)
 - [ImageUrlIn](docs/ImageUrlIn.md)
 - [ImagesOutResponse](docs/ImagesOutResponse.md)
 - [Invoice](docs/Invoice.md)
 - [K8SPresetsInner](docs/K8SPresetsInner.md)
 - [K8SVersionsResponse](docs/K8SVersionsResponse.md)
 - [Location](docs/Location.md)
 - [Mailbox](docs/Mailbox.md)
 - [MailboxAutoReply](docs/MailboxAutoReply.md)
 - [MailboxForwardingIncoming](docs/MailboxForwardingIncoming.md)
 - [MailboxForwardingOutgoing](docs/MailboxForwardingOutgoing.md)
 - [MailboxSpamFilter](docs/MailboxSpamFilter.md)
 - [MasterPresetOutApi](docs/MasterPresetOutApi.md)
 - [Memory](docs/Memory.md)
 - [Message](docs/Message.md)
 - [Meta](docs/Meta.md)
 - [Meta1](docs/Meta1.md)
 - [Network](docs/Network.md)
 - [NetworkDriversResponse](docs/NetworkDriversResponse.md)
 - [NodeCount](docs/NodeCount.md)
 - [NodeGroup](docs/NodeGroup.md)
 - [NodeGroupIn](docs/NodeGroupIn.md)
 - [NodeGroupOut](docs/NodeGroupOut.md)
 - [NodeGroupResponse](docs/NodeGroupResponse.md)
 - [NodeGroupsResponse](docs/NodeGroupsResponse.md)
 - [NodeOut](docs/NodeOut.md)
 - [NodesResponse](docs/NodesResponse.md)
 - [NotificationSetting](docs/NotificationSetting.md)
 - [NotificationSettingChannel](docs/NotificationSettingChannel.md)
 - [NotificationSettingChannels](docs/NotificationSettingChannels.md)
 - [NotificationSettingType](docs/NotificationSettingType.md)
 - [OS](docs/OS.md)
 - [PerformActionOnBackupRequest](docs/PerformActionOnBackupRequest.md)
 - [PerformActionOnServerRequest](docs/PerformActionOnServerRequest.md)
 - [Pods](docs/Pods.md)
 - [PresetsBalancer](docs/PresetsBalancer.md)
 - [PresetsDbs](docs/PresetsDbs.md)
 - [PresetsResponse](docs/PresetsResponse.md)
 - [PresetsStorage](docs/PresetsStorage.md)
 - [Project](docs/Project.md)
 - [ProjectResource](docs/ProjectResource.md)
 - [Prolong](docs/Prolong.md)
 - [Quota](docs/Quota.md)
 - [RefreshApiKey](docs/RefreshApiKey.md)
 - [Register](docs/Register.md)
 - [RegisterNsInner](docs/RegisterNsInner.md)
 - [RemoveCountries](docs/RemoveCountries.md)
 - [RemoveIps](docs/RemoveIps.md)
 - [RenameStorageFileRequest](docs/RenameStorageFileRequest.md)
 - [Resource](docs/Resource.md)
 - [ResourceTransfer](docs/ResourceTransfer.md)
 - [ResourceType](docs/ResourceType.md)
 - [Resources](docs/Resources.md)
 - [Resources1](docs/Resources1.md)
 - [ResourcesResponse](docs/ResourcesResponse.md)
 - [Rule](docs/Rule.md)
 - [S3Object](docs/S3Object.md)
 - [S3ObjectOwner](docs/S3ObjectOwner.md)
 - [S3Subdomain](docs/S3Subdomain.md)
 - [SchemasBaseError](docs/SchemasBaseError.md)
 - [ServerBackup](docs/ServerBackup.md)
 - [ServerDisk](docs/ServerDisk.md)
 - [ServerIp](docs/ServerIp.md)
 - [ServerLog](docs/ServerLog.md)
 - [ServersConfigurator](docs/ServersConfigurator.md)
 - [ServersConfiguratorRequirements](docs/ServersConfiguratorRequirements.md)
 - [ServersOs](docs/ServersOs.md)
 - [ServersOsRequirements](docs/ServersOsRequirements.md)
 - [ServersPreset](docs/ServersPreset.md)
 - [ServersSoftware](docs/ServersSoftware.md)
 - [ServersSoftwareRequirements](docs/ServersSoftwareRequirements.md)
 - [SettingCondition](docs/SettingCondition.md)
 - [SpamFilterIsDisabled](docs/SpamFilterIsDisabled.md)
 - [SpamFilterIsEnabled](docs/SpamFilterIsEnabled.md)
 - [SshKey](docs/SshKey.md)
 - [SshKeyUsedByInner](docs/SshKeyUsedByInner.md)
 - [Status](docs/Status.md)
 - [StatusCompanyInfo](docs/StatusCompanyInfo.md)
 - [Subdomain](docs/Subdomain.md)
 - [TopLevelDomain](docs/TopLevelDomain.md)
 - [TopLevelDomainAllowedBuyPeriodsInner](docs/TopLevelDomainAllowedBuyPeriodsInner.md)
 - [Transfer](docs/Transfer.md)
 - [TransferStatus](docs/TransferStatus.md)
 - [TransferStatusErrorsInner](docs/TransferStatusErrorsInner.md)
 - [TransferStorageRequest](docs/TransferStorageRequest.md)
 - [URLType](docs/URLType.md)
 - [UpdateAdmin](docs/UpdateAdmin.md)
 - [UpdateAuthRestrictionsByCountriesRequest](docs/UpdateAuthRestrictionsByCountriesRequest.md)
 - [UpdateBalancer](docs/UpdateBalancer.md)
 - [UpdateCluster](docs/UpdateCluster.md)
 - [UpdateDb](docs/UpdateDb.md)
 - [UpdateDedicatedServerRequest](docs/UpdateDedicatedServerRequest.md)
 - [UpdateDomain](docs/UpdateDomain.md)
 - [UpdateDomainAutoProlongation200Response](docs/UpdateDomainAutoProlongation200Response.md)
 - [UpdateDomainMailInfoRequest](docs/UpdateDomainMailInfoRequest.md)
 - [UpdateDomainNameServers](docs/UpdateDomainNameServers.md)
 - [UpdateDomainNameServersNameServersInner](docs/UpdateDomainNameServersNameServersInner.md)
 - [UpdateDomainRequestRequest](docs/UpdateDomainRequestRequest.md)
 - [UpdateInstance](docs/UpdateInstance.md)
 - [UpdateKeyRequest](docs/UpdateKeyRequest.md)
 - [UpdateMailQuotaRequest](docs/UpdateMailQuotaRequest.md)
 - [UpdateMailbox](docs/UpdateMailbox.md)
 - [UpdateMailboxAutoReply](docs/UpdateMailboxAutoReply.md)
 - [UpdateMailboxForwardingIncoming](docs/UpdateMailboxForwardingIncoming.md)
 - [UpdateMailboxForwardingOutgoing](docs/UpdateMailboxForwardingOutgoing.md)
 - [UpdateMailboxSpamFilter](docs/UpdateMailboxSpamFilter.md)
 - [UpdateNotificationSettingsRequest](docs/UpdateNotificationSettingsRequest.md)
 - [UpdateNotificationSettingsRequestSettingsInner](docs/UpdateNotificationSettingsRequestSettingsInner.md)
 - [UpdateNotificationSettingsRequestSettingsInnerChannels](docs/UpdateNotificationSettingsRequestSettingsInnerChannels.md)
 - [UpdateProject](docs/UpdateProject.md)
 - [UpdateRule](docs/UpdateRule.md)
 - [UpdateServer](docs/UpdateServer.md)
 - [UpdateServerConfigurator](docs/UpdateServerConfigurator.md)
 - [UpdateServerDiskBackupRequest](docs/UpdateServerDiskBackupRequest.md)
 - [UpdateServerDiskRequest](docs/UpdateServerDiskRequest.md)
 - [UpdateServerIPRequest](docs/UpdateServerIPRequest.md)
 - [UpdateServerNATRequest](docs/UpdateServerNATRequest.md)
 - [UpdateServerOSBootModeRequest](docs/UpdateServerOSBootModeRequest.md)
 - [UpdateStorageRequest](docs/UpdateStorageRequest.md)
 - [UpdateStorageUser200Response](docs/UpdateStorageUser200Response.md)
 - [UpdateStorageUserRequest](docs/UpdateStorageUserRequest.md)
 - [UpdateToken200Response](docs/UpdateToken200Response.md)
 - [UpdateVpc](docs/UpdateVpc.md)
 - [UploadImage200Response](docs/UploadImage200Response.md)
 - [UploadSuccessful](docs/UploadSuccessful.md)
 - [UploadSuccessfulResponse](docs/UploadSuccessfulResponse.md)
 - [UrlStatus](docs/UrlStatus.md)
 - [Use](docs/Use.md)
 - [Vds](docs/Vds.md)
 - [VdsDisksInner](docs/VdsDisksInner.md)
 - [VdsNetworksInner](docs/VdsNetworksInner.md)
 - [VdsNetworksInnerIpsInner](docs/VdsNetworksInnerIpsInner.md)
 - [VdsOs](docs/VdsOs.md)
 - [VdsSoftware](docs/VdsSoftware.md)
 - [Vpc](docs/Vpc.md)
 - [VpcPort](docs/VpcPort.md)
 - [VpcPortService](docs/VpcPortService.md)
 - [VpcService](docs/VpcService.md)
 - [WorkerPresetOutApi](docs/WorkerPresetOutApi.md)


<a id="documentation-for-authorization"></a>
## Documentation for Authorization


Authentication schemes defined for the API:
<a id="Bearer"></a>
### Bearer

- **Type**: HTTP Bearer Token authentication (JWT)


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

info@timeweb.cloud

