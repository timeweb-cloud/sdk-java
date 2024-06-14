# ServersApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addServerIP**](ServersApi.md#addServerIP) | **POST** /api/v1/servers/{server_id}/ips | Добавление IP-адреса сервера |
| [**cloneServer**](ServersApi.md#cloneServer) | **POST** /api/v1/servers/{server_id}/clone | Клонирование сервера |
| [**createServer**](ServersApi.md#createServer) | **POST** /api/v1/servers | Создание сервера |
| [**createServerDisk**](ServersApi.md#createServerDisk) | **POST** /api/v1/servers/{server_id}/disks | Создание диска сервера |
| [**createServerDiskBackup**](ServersApi.md#createServerDiskBackup) | **POST** /api/v1/servers/{server_id}/disks/{disk_id}/backups | Создание бэкапа диска сервера |
| [**deleteServer**](ServersApi.md#deleteServer) | **DELETE** /api/v1/servers/{server_id} | Удаление сервера |
| [**deleteServerDisk**](ServersApi.md#deleteServerDisk) | **DELETE** /api/v1/servers/{server_id}/disks/{disk_id} | Удаление диска сервера |
| [**deleteServerDiskBackup**](ServersApi.md#deleteServerDiskBackup) | **DELETE** /api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id} | Удаление бэкапа диска сервера |
| [**deleteServerIP**](ServersApi.md#deleteServerIP) | **DELETE** /api/v1/servers/{server_id}/ips | Удаление IP-адреса сервера |
| [**getConfigurators**](ServersApi.md#getConfigurators) | **GET** /api/v1/configurator/servers | Получение списка конфигураторов серверов |
| [**getOsList**](ServersApi.md#getOsList) | **GET** /api/v1/os/servers | Получение списка операционных систем |
| [**getServer**](ServersApi.md#getServer) | **GET** /api/v1/servers/{server_id} | Получение сервера |
| [**getServerDisk**](ServersApi.md#getServerDisk) | **GET** /api/v1/servers/{server_id}/disks/{disk_id} | Получение диска сервера |
| [**getServerDiskAutoBackupSettings**](ServersApi.md#getServerDiskAutoBackupSettings) | **GET** /api/v1/servers/{server_id}/disks/{disk_id}/auto-backups | Получить настройки автобэкапов диска сервера |
| [**getServerDiskBackup**](ServersApi.md#getServerDiskBackup) | **GET** /api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id} | Получение бэкапа диска сервера |
| [**getServerDiskBackups**](ServersApi.md#getServerDiskBackups) | **GET** /api/v1/servers/{server_id}/disks/{disk_id}/backups | Получение списка бэкапов диска сервера |
| [**getServerDisks**](ServersApi.md#getServerDisks) | **GET** /api/v1/servers/{server_id}/disks | Получение списка дисков сервера |
| [**getServerIPs**](ServersApi.md#getServerIPs) | **GET** /api/v1/servers/{server_id}/ips | Получение списка IP-адресов сервера |
| [**getServerLogs**](ServersApi.md#getServerLogs) | **GET** /api/v1/servers/{server_id}/logs | Получение списка логов сервера |
| [**getServerStatistics**](ServersApi.md#getServerStatistics) | **GET** /api/v1/servers/{server_id}/statistics | Получение статистики сервера |
| [**getServers**](ServersApi.md#getServers) | **GET** /api/v1/servers | Получение списка серверов |
| [**getServersPresets**](ServersApi.md#getServersPresets) | **GET** /api/v1/presets/servers | Получение списка тарифов серверов |
| [**getSoftware**](ServersApi.md#getSoftware) | **GET** /api/v1/software/servers | Получение списка ПО из маркетплейса |
| [**hardShutdownServer**](ServersApi.md#hardShutdownServer) | **POST** /api/v1/servers/{server_id}/hard-shutdown | Принудительное выключение сервера |
| [**imageUnmountAndServerReload**](ServersApi.md#imageUnmountAndServerReload) | **POST** /api/v1/servers/{server_id}/image-unmount | Отмонтирование ISO образа и перезагрузка сервера |
| [**installServer**](ServersApi.md#installServer) | **POST** /api/v1/servers/{server_id}/install | Установка сервера |
| [**performActionOnBackup**](ServersApi.md#performActionOnBackup) | **POST** /api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id}/action | Выполнение действия над бэкапом диска сервера |
| [**performActionOnServer**](ServersApi.md#performActionOnServer) | **POST** /api/v1/servers/{server_id}/action | Выполнение действия над сервером |
| [**rebootServer**](ServersApi.md#rebootServer) | **POST** /api/v1/servers/{server_id}/reboot | Перезагрузка сервера |
| [**resetServerPassword**](ServersApi.md#resetServerPassword) | **POST** /api/v1/servers/{server_id}/reset-password | Сброс пароля сервера |
| [**shutdownServer**](ServersApi.md#shutdownServer) | **POST** /api/v1/servers/{server_id}/shutdown | Выключение сервера |
| [**startServer**](ServersApi.md#startServer) | **POST** /api/v1/servers/{server_id}/start | Запуск сервера |
| [**updateServer**](ServersApi.md#updateServer) | **PATCH** /api/v1/servers/{server_id} | Изменение сервера |
| [**updateServerDisk**](ServersApi.md#updateServerDisk) | **PATCH** /api/v1/servers/{server_id}/disks/{disk_id} | Изменение параметров диска сервера |
| [**updateServerDiskAutoBackupSettings**](ServersApi.md#updateServerDiskAutoBackupSettings) | **PATCH** /api/v1/servers/{server_id}/disks/{disk_id}/auto-backups | Изменение настроек автобэкапов диска сервера |
| [**updateServerDiskBackup**](ServersApi.md#updateServerDiskBackup) | **PATCH** /api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id} | Изменение бэкапа диска сервера |
| [**updateServerIP**](ServersApi.md#updateServerIP) | **PATCH** /api/v1/servers/{server_id}/ips | Изменение IP-адреса сервера |
| [**updateServerNAT**](ServersApi.md#updateServerNAT) | **PATCH** /api/v1/servers/{server_id}/local-networks/nat-mode | Изменение правил маршрутизации трафика сервера (NAT) |
| [**updateServerOSBootMode**](ServersApi.md#updateServerOSBootMode) | **POST** /api/v1/servers/{server_id}/boot-mode | Выбор типа загрузки операционной системы сервера |


<a id="addServerIP"></a>
# **addServerIP**
> AddServerIP201Response addServerIP(serverId, addServerIPRequest)

Добавление IP-адреса сервера

Чтобы добавить IP-адрес сервера, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/ips&#x60;. \\  На данный момент IPv6 доступны только для серверов с локацией &#x60;ru-1&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    AddServerIPRequest addServerIPRequest = new AddServerIPRequest(); // AddServerIPRequest | 
    try {
      AddServerIP201Response result = apiInstance.addServerIP(serverId, addServerIPRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#addServerIP");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **addServerIPRequest** | [**AddServerIPRequest**](AddServerIPRequest.md)|  | |

### Return type

[**AddServerIP201Response**](AddServerIP201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;server_ip&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="cloneServer"></a>
# **cloneServer**
> CreateServer201Response cloneServer(serverId)

Клонирование сервера

Чтобы клонировать сервер, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/clone&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    try {
      CreateServer201Response result = apiInstance.cloneServer(serverId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#cloneServer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |

### Return type

[**CreateServer201Response**](CreateServer201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;server&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createServer"></a>
# **createServer**
> CreateServer201Response createServer(createServer)

Создание сервера

Чтобы создать сервер, отправьте POST-запрос в &#x60;api/v1/servers&#x60;, задав необходимые атрибуты. Обязательно должен присутствовать один из параметров &#x60;configuration&#x60; или &#x60;preset_id&#x60;, а также &#x60;image_id&#x60; или &#x60;os_id&#x60;.  Cервер будет создан с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданном сервере.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    CreateServer createServer = new CreateServer(); // CreateServer | 
    try {
      CreateServer201Response result = apiInstance.createServer(createServer);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#createServer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **createServer** | [**CreateServer**](CreateServer.md)|  | |

### Return type

[**CreateServer201Response**](CreateServer201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;server&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createServerDisk"></a>
# **createServerDisk**
> CreateServerDisk201Response createServerDisk(serverId, createServerDiskRequest)

Создание диска сервера

Чтобы создать диск сервера, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/disks&#x60;. Системный диск создать нельзя.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    CreateServerDiskRequest createServerDiskRequest = new CreateServerDiskRequest(); // CreateServerDiskRequest | 
    try {
      CreateServerDisk201Response result = apiInstance.createServerDisk(serverId, createServerDiskRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#createServerDisk");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **createServerDiskRequest** | [**CreateServerDiskRequest**](CreateServerDiskRequest.md)|  | [optional] |

### Return type

[**CreateServerDisk201Response**](CreateServerDisk201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Успешное создание диска сервера |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createServerDiskBackup"></a>
# **createServerDiskBackup**
> CreateServerDiskBackup201Response createServerDiskBackup(serverId, diskId, createServerDiskBackupRequest)

Создание бэкапа диска сервера

Чтобы создать бэкап диска сервера, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/backups&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    Integer diskId = 1051; // Integer | Уникальный идентификатор диска сервера.
    CreateServerDiskBackupRequest createServerDiskBackupRequest = new CreateServerDiskBackupRequest(); // CreateServerDiskBackupRequest | 
    try {
      CreateServerDiskBackup201Response result = apiInstance.createServerDiskBackup(serverId, diskId, createServerDiskBackupRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#createServerDiskBackup");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **diskId** | **Integer**| Уникальный идентификатор диска сервера. | |
| **createServerDiskBackupRequest** | [**CreateServerDiskBackupRequest**](CreateServerDiskBackupRequest.md)|  | [optional] |

### Return type

[**CreateServerDiskBackup201Response**](CreateServerDiskBackup201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;backup&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteServer"></a>
# **deleteServer**
> DeleteServer200Response deleteServer(serverId, hash, code)

Удаление сервера

Чтобы удалить сервер, отправьте запрос DELETE в &#x60;/api/v1/servers/{server_id}&#x60;.\\  Обратите внимание, если на аккаунте включено удаление серверов по смс, то вернется ошибка 423.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    String hash = "15095f25-aac3-4d60-a788-96cb5136f186"; // String | Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм.
    String code = "0000"; // String | Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена `is_able_to_delete` установлен в значение `true`
    try {
      DeleteServer200Response result = apiInstance.deleteServer(serverId, hash, code);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#deleteServer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **hash** | **String**| Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм. | [optional] |
| **code** | **String**| Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена &#x60;is_able_to_delete&#x60; установлен в значение &#x60;true&#x60; | [optional] |

### Return type

[**DeleteServer200Response**](DeleteServer200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;server_delete&#x60; |  -  |
| **204** | Успешное удаление сервера |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteServerDisk"></a>
# **deleteServerDisk**
> deleteServerDisk(serverId, diskId)

Удаление диска сервера

Чтобы удалить диск сервера, отправьте DELETE-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}&#x60;. Нельзя удалять системный диск.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    Integer diskId = 1051; // Integer | Уникальный идентификатор диска сервера.
    try {
      apiInstance.deleteServerDisk(serverId, diskId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#deleteServerDisk");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **diskId** | **Integer**| Уникальный идентификатор диска сервера. | |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Успешное удаление диска сервера |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteServerDiskBackup"></a>
# **deleteServerDiskBackup**
> deleteServerDiskBackup(serverId, diskId, backupId)

Удаление бэкапа диска сервера

Чтобы удалить бэкап диска сервера, отправьте DELETE-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    Integer diskId = 1051; // Integer | Уникальный идентификатор диска сервера.
    Integer backupId = 1051; // Integer | Уникальный идентификатор бэкапа сервера.
    try {
      apiInstance.deleteServerDiskBackup(serverId, diskId, backupId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#deleteServerDiskBackup");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **diskId** | **Integer**| Уникальный идентификатор диска сервера. | |
| **backupId** | **Integer**| Уникальный идентификатор бэкапа сервера. | |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Успешное удаление бэкапа. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteServerIP"></a>
# **deleteServerIP**
> deleteServerIP(serverId, deleteServerIPRequest)

Удаление IP-адреса сервера

Чтобы удалить IP-адрес сервера, отправьте DELETE-запрос на &#x60;/api/v1/servers/{server_id}/ips&#x60;. Нельзя удалить основной IP-адрес

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    DeleteServerIPRequest deleteServerIPRequest = new DeleteServerIPRequest(); // DeleteServerIPRequest | 
    try {
      apiInstance.deleteServerIP(serverId, deleteServerIPRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#deleteServerIP");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **deleteServerIPRequest** | [**DeleteServerIPRequest**](DeleteServerIPRequest.md)|  | |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | IP-адрес успешно удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getConfigurators"></a>
# **getConfigurators**
> GetConfigurators200Response getConfigurators()

Получение списка конфигураторов серверов

Чтобы получить список всех конфигураторов серверов, отправьте GET-запрос на &#x60;/api/v1/configurator/servers&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;server_configurators&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    try {
      GetConfigurators200Response result = apiInstance.getConfigurators();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#getConfigurators");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GetConfigurators200Response**](GetConfigurators200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;server_configurators&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getOsList"></a>
# **getOsList**
> GetOsList200Response getOsList()

Получение списка операционных систем

Чтобы получить список всех операционных систем, отправьте GET-запрос на &#x60;/api/v1/os/servers&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;servers_os&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    try {
      GetOsList200Response result = apiInstance.getOsList();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#getOsList");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GetOsList200Response**](GetOsList200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;servers_os&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getServer"></a>
# **getServer**
> CreateServer201Response getServer(serverId)

Получение сервера

Чтобы получить сервер, отправьте запрос GET в &#x60;/api/v1/servers/{server_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    try {
      CreateServer201Response result = apiInstance.getServer(serverId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#getServer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |

### Return type

[**CreateServer201Response**](CreateServer201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;server&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getServerDisk"></a>
# **getServerDisk**
> CreateServerDisk201Response getServerDisk(serverId, diskId)

Получение диска сервера

Чтобы получить диск сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    Integer diskId = 1051; // Integer | Уникальный идентификатор диска сервера.
    try {
      CreateServerDisk201Response result = apiInstance.getServerDisk(serverId, diskId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#getServerDisk");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **diskId** | **Integer**| Уникальный идентификатор диска сервера. | |

### Return type

[**CreateServerDisk201Response**](CreateServerDisk201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Успешное получение диска сервера |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getServerDiskAutoBackupSettings"></a>
# **getServerDiskAutoBackupSettings**
> GetServerDiskAutoBackupSettings200Response getServerDiskAutoBackupSettings(serverId, diskId)

Получить настройки автобэкапов диска сервера

Чтобы полученить настройки автобэкапов диска сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/auto-backups&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    Integer diskId = 1051; // Integer | Уникальный идентификатор диска сервера.
    try {
      GetServerDiskAutoBackupSettings200Response result = apiInstance.getServerDiskAutoBackupSettings(serverId, diskId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#getServerDiskAutoBackupSettings");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **diskId** | **Integer**| Уникальный идентификатор диска сервера. | |

### Return type

[**GetServerDiskAutoBackupSettings200Response**](GetServerDiskAutoBackupSettings200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;auto_backups_settings&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getServerDiskBackup"></a>
# **getServerDiskBackup**
> GetServerDiskBackup200Response getServerDiskBackup(serverId, diskId, backupId)

Получение бэкапа диска сервера

Чтобы получить бэкап диска сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id}&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    Integer diskId = 1051; // Integer | Уникальный идентификатор диска сервера.
    Integer backupId = 1051; // Integer | Уникальный идентификатор бэкапа сервера.
    try {
      GetServerDiskBackup200Response result = apiInstance.getServerDiskBackup(serverId, diskId, backupId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#getServerDiskBackup");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **diskId** | **Integer**| Уникальный идентификатор диска сервера. | |
| **backupId** | **Integer**| Уникальный идентификатор бэкапа сервера. | |

### Return type

[**GetServerDiskBackup200Response**](GetServerDiskBackup200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;backup&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getServerDiskBackups"></a>
# **getServerDiskBackups**
> GetServerDiskBackups200Response getServerDiskBackups(serverId, diskId)

Получение списка бэкапов диска сервера

Чтобы получить список бэкапов диска сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/backups&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backups&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    Integer diskId = 1051; // Integer | Уникальный идентификатор диска сервера.
    try {
      GetServerDiskBackups200Response result = apiInstance.getServerDiskBackups(serverId, diskId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#getServerDiskBackups");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **diskId** | **Integer**| Уникальный идентификатор диска сервера. | |

### Return type

[**GetServerDiskBackups200Response**](GetServerDiskBackups200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;backups&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getServerDisks"></a>
# **getServerDisks**
> GetServerDisks200Response getServerDisks(serverId)

Получение списка дисков сервера

Чтобы получить список дисков сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/disks&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    try {
      GetServerDisks200Response result = apiInstance.getServerDisks(serverId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#getServerDisks");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |

### Return type

[**GetServerDisks200Response**](GetServerDisks200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;server_disks&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getServerIPs"></a>
# **getServerIPs**
> GetServerIPs200Response getServerIPs(serverId)

Получение списка IP-адресов сервера

Чтобы получить список IP-адресов сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/ips&#x60;. \\  На данный момент IPv6 доступны только для локации &#x60;ru-1&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    try {
      GetServerIPs200Response result = apiInstance.getServerIPs(serverId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#getServerIPs");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |

### Return type

[**GetServerIPs200Response**](GetServerIPs200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;server_ips&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getServerLogs"></a>
# **getServerLogs**
> GetServerLogs200Response getServerLogs(serverId, limit, offset, order)

Получение списка логов сервера

Чтобы получить список логов сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/logs&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    String order = "asc"; // String | Сортировка элементов по дате
    try {
      GetServerLogs200Response result = apiInstance.getServerLogs(serverId, limit, offset, order);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#getServerLogs");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **limit** | **Integer**| Обозначает количество записей, которое необходимо вернуть. | [optional] [default to 100] |
| **offset** | **Integer**| Указывает на смещение относительно начала списка. | [optional] [default to 0] |
| **order** | **String**| Сортировка элементов по дате | [optional] [default to asc] [enum: asc, desc] |

### Return type

[**GetServerLogs200Response**](GetServerLogs200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;server_logs&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getServerStatistics"></a>
# **getServerStatistics**
> GetServerStatistics200Response getServerStatistics(serverId, dateFrom, dateTo)

Получение статистики сервера

Чтобы получить статистику сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/statistics&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    String dateFrom = "dateFrom_example"; // String | Дата начала сбора статистики. Строка в формате ISO 8061, закодированная в ASCII, пример: `2023-05-25%202023-05-25T14%3A35%3A38`
    String dateTo = "dateTo_example"; // String | Дата окончания сбора статистики. Строка в формате ISO 8061, закодированная в ASCII, пример: `2023-05-26%202023-05-25T14%3A35%3A38`
    try {
      GetServerStatistics200Response result = apiInstance.getServerStatistics(serverId, dateFrom, dateTo);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#getServerStatistics");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **dateFrom** | **String**| Дата начала сбора статистики. Строка в формате ISO 8061, закодированная в ASCII, пример: &#x60;2023-05-25%202023-05-25T14%3A35%3A38&#x60; | |
| **dateTo** | **String**| Дата окончания сбора статистики. Строка в формате ISO 8061, закодированная в ASCII, пример: &#x60;2023-05-26%202023-05-25T14%3A35%3A38&#x60; | |

### Return type

[**GetServerStatistics200Response**](GetServerStatistics200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;cpu&#x60;, &#x60;disk&#x60;, &#x60;network_traffic&#x60;, &#x60;ram&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getServers"></a>
# **getServers**
> GetServers200Response getServers(limit, offset)

Получение списка серверов

Чтобы получить список серверов, отправьте GET-запрос на &#x60;/api/v1/servers&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;servers&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    try {
      GetServers200Response result = apiInstance.getServers(limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#getServers");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **limit** | **Integer**| Обозначает количество записей, которое необходимо вернуть. | [optional] [default to 100] |
| **offset** | **Integer**| Указывает на смещение относительно начала списка. | [optional] [default to 0] |

### Return type

[**GetServers200Response**](GetServers200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;servers&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getServersPresets"></a>
# **getServersPresets**
> GetServersPresets200Response getServersPresets()

Получение списка тарифов серверов

Чтобы получить список всех тарифов серверов, отправьте GET-запрос на &#x60;/api/v1/presets/servers&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;server_presets&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    try {
      GetServersPresets200Response result = apiInstance.getServersPresets();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#getServersPresets");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GetServersPresets200Response**](GetServersPresets200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;server_presets&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getSoftware"></a>
# **getSoftware**
> GetSoftware200Response getSoftware()

Получение списка ПО из маркетплейса

Чтобы получить список ПО из маркетплейса, отправьте GET-запрос на &#x60;/api/v1/software/servers&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;servers_software&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    try {
      GetSoftware200Response result = apiInstance.getSoftware();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#getSoftware");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GetSoftware200Response**](GetSoftware200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;servers_software&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="hardShutdownServer"></a>
# **hardShutdownServer**
> hardShutdownServer(serverId)

Принудительное выключение сервера

Чтобы выполнить принудительное выключение сервера, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/hard-shutdown&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    try {
      apiInstance.hardShutdownServer(serverId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#hardShutdownServer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Успешное выполнение действия |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="imageUnmountAndServerReload"></a>
# **imageUnmountAndServerReload**
> imageUnmountAndServerReload(serverId)

Отмонтирование ISO образа и перезагрузка сервера

Чтобы отмонтировать ISO образ и перезагрузить сервер, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/image-unmount&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    try {
      apiInstance.imageUnmountAndServerReload(serverId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#imageUnmountAndServerReload");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | ISO образ в процессе отмонтирования |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="installServer"></a>
# **installServer**
> installServer(serverId)

Установка сервера

Чтобы установить сервер, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/install&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    try {
      apiInstance.installServer(serverId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#installServer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Успешное выполнение действия |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="performActionOnBackup"></a>
# **performActionOnBackup**
> performActionOnBackup(serverId, diskId, backupId, performActionOnBackupRequest)

Выполнение действия над бэкапом диска сервера

Чтобы выполнить действие над бэкапом диска сервера, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id}/action&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    Integer diskId = 1051; // Integer | Уникальный идентификатор диска сервера.
    Integer backupId = 1051; // Integer | Уникальный идентификатор бэкапа сервера.
    PerformActionOnBackupRequest performActionOnBackupRequest = new PerformActionOnBackupRequest(); // PerformActionOnBackupRequest | 
    try {
      apiInstance.performActionOnBackup(serverId, diskId, backupId, performActionOnBackupRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#performActionOnBackup");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **diskId** | **Integer**| Уникальный идентификатор диска сервера. | |
| **backupId** | **Integer**| Уникальный идентификатор бэкапа сервера. | |
| **performActionOnBackupRequest** | [**PerformActionOnBackupRequest**](PerformActionOnBackupRequest.md)|  | [optional] |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Успешное выполнение действия |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="performActionOnServer"></a>
# **performActionOnServer**
> performActionOnServer(serverId, performActionOnServerRequest)

Выполнение действия над сервером

Чтобы выполнить действие над сервером, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/action&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    PerformActionOnServerRequest performActionOnServerRequest = new PerformActionOnServerRequest(); // PerformActionOnServerRequest | 
    try {
      apiInstance.performActionOnServer(serverId, performActionOnServerRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#performActionOnServer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **performActionOnServerRequest** | [**PerformActionOnServerRequest**](PerformActionOnServerRequest.md)|  | [optional] |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Успешное выполнение действия |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="rebootServer"></a>
# **rebootServer**
> rebootServer(serverId)

Перезагрузка сервера

Чтобы перезагрузить сервер, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/reboot&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    try {
      apiInstance.rebootServer(serverId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#rebootServer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Успешное выполнение действия |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="resetServerPassword"></a>
# **resetServerPassword**
> resetServerPassword(serverId)

Сброс пароля сервера

Чтобы сбросить пароль сервера, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/reset-password&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    try {
      apiInstance.resetServerPassword(serverId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#resetServerPassword");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Успешное выполнение действия |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="shutdownServer"></a>
# **shutdownServer**
> shutdownServer(serverId)

Выключение сервера

Чтобы выключить сервер, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/shutdown&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    try {
      apiInstance.shutdownServer(serverId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#shutdownServer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Успешное выполнение действия |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="startServer"></a>
# **startServer**
> startServer(serverId)

Запуск сервера

Чтобы запустить сервер, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/start&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    try {
      apiInstance.startServer(serverId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#startServer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Успешное выполнение действия |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateServer"></a>
# **updateServer**
> CreateServer201Response updateServer(serverId, updateServer)

Изменение сервера

Чтобы обновить только определенные атрибуты сервера, отправьте запрос PATCH в &#x60;/api/v1/servers/{server_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    UpdateServer updateServer = new UpdateServer(); // UpdateServer | 
    try {
      CreateServer201Response result = apiInstance.updateServer(serverId, updateServer);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#updateServer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **updateServer** | [**UpdateServer**](UpdateServer.md)|  | |

### Return type

[**CreateServer201Response**](CreateServer201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;server&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateServerDisk"></a>
# **updateServerDisk**
> CreateServerDisk201Response updateServerDisk(serverId, diskId, updateServerDiskRequest)

Изменение параметров диска сервера

Чтобы изменить параметры диска сервера, отправьте PATCH-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    Integer diskId = 1051; // Integer | Уникальный идентификатор диска сервера.
    UpdateServerDiskRequest updateServerDiskRequest = new UpdateServerDiskRequest(); // UpdateServerDiskRequest | 
    try {
      CreateServerDisk201Response result = apiInstance.updateServerDisk(serverId, diskId, updateServerDiskRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#updateServerDisk");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **diskId** | **Integer**| Уникальный идентификатор диска сервера. | |
| **updateServerDiskRequest** | [**UpdateServerDiskRequest**](UpdateServerDiskRequest.md)|  | [optional] |

### Return type

[**CreateServerDisk201Response**](CreateServerDisk201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Успешное изменение параметров диска сервера |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateServerDiskAutoBackupSettings"></a>
# **updateServerDiskAutoBackupSettings**
> GetServerDiskAutoBackupSettings200Response updateServerDiskAutoBackupSettings(serverId, diskId, autoBackup)

Изменение настроек автобэкапов диска сервера

Чтобы изменить настройки автобэкапов диска сервера, отправьте PATCH-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/auto-backups&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    Integer diskId = 1051; // Integer | Уникальный идентификатор диска сервера.
    AutoBackup autoBackup = new AutoBackup(); // AutoBackup | При значении `is_enabled`: `true`, поля `copy_count`, `creation_start_at`, `interval` являются обязательными
    try {
      GetServerDiskAutoBackupSettings200Response result = apiInstance.updateServerDiskAutoBackupSettings(serverId, diskId, autoBackup);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#updateServerDiskAutoBackupSettings");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **diskId** | **Integer**| Уникальный идентификатор диска сервера. | |
| **autoBackup** | [**AutoBackup**](AutoBackup.md)| При значении &#x60;is_enabled&#x60;: &#x60;true&#x60;, поля &#x60;copy_count&#x60;, &#x60;creation_start_at&#x60;, &#x60;interval&#x60; являются обязательными | [optional] |

### Return type

[**GetServerDiskAutoBackupSettings200Response**](GetServerDiskAutoBackupSettings200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;auto_backups_settings&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateServerDiskBackup"></a>
# **updateServerDiskBackup**
> GetServerDiskBackup200Response updateServerDiskBackup(serverId, diskId, backupId, updateServerDiskBackupRequest)

Изменение бэкапа диска сервера

Чтобы изменить бэкап диска сервера, отправьте PATCH-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    Integer diskId = 1051; // Integer | Уникальный идентификатор диска сервера.
    Integer backupId = 1051; // Integer | Уникальный идентификатор бэкапа сервера.
    UpdateServerDiskBackupRequest updateServerDiskBackupRequest = new UpdateServerDiskBackupRequest(); // UpdateServerDiskBackupRequest | 
    try {
      GetServerDiskBackup200Response result = apiInstance.updateServerDiskBackup(serverId, diskId, backupId, updateServerDiskBackupRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#updateServerDiskBackup");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **diskId** | **Integer**| Уникальный идентификатор диска сервера. | |
| **backupId** | **Integer**| Уникальный идентификатор бэкапа сервера. | |
| **updateServerDiskBackupRequest** | [**UpdateServerDiskBackupRequest**](UpdateServerDiskBackupRequest.md)|  | [optional] |

### Return type

[**GetServerDiskBackup200Response**](GetServerDiskBackup200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;backup&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateServerIP"></a>
# **updateServerIP**
> AddServerIP201Response updateServerIP(serverId, updateServerIPRequest)

Изменение IP-адреса сервера

Чтобы изменить IP-адрес сервера, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/ips&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    UpdateServerIPRequest updateServerIPRequest = new UpdateServerIPRequest(); // UpdateServerIPRequest | 
    try {
      AddServerIP201Response result = apiInstance.updateServerIP(serverId, updateServerIPRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#updateServerIP");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **updateServerIPRequest** | [**UpdateServerIPRequest**](UpdateServerIPRequest.md)|  | |

### Return type

[**AddServerIP201Response**](AddServerIP201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;server_ip&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateServerNAT"></a>
# **updateServerNAT**
> updateServerNAT(serverId, updateServerNATRequest)

Изменение правил маршрутизации трафика сервера (NAT)

Чтобы измененить правила маршрутизации трафика сервера (NAT), отправьте PATCH-запрос на &#x60;/api/v1/servers/{server_id}/local-networks/nat-mode&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    UpdateServerNATRequest updateServerNATRequest = new UpdateServerNATRequest(); // UpdateServerNATRequest | 
    try {
      apiInstance.updateServerNAT(serverId, updateServerNATRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#updateServerNAT");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **updateServerNATRequest** | [**UpdateServerNATRequest**](UpdateServerNATRequest.md)|  | [optional] |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Успешна смена правила маршрутизации трафика |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateServerOSBootMode"></a>
# **updateServerOSBootMode**
> updateServerOSBootMode(serverId, updateServerOSBootModeRequest)

Выбор типа загрузки операционной системы сервера

Чтобы изменить тип загрузки операционной системы сервера, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/boot-mode&#x60;. \\  После смены типа загрузки сервер будет перезапущен.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ServersApi apiInstance = new ServersApi(defaultClient);
    Integer serverId = 1051; // Integer | Уникальный идентификатор облачного сервера.
    UpdateServerOSBootModeRequest updateServerOSBootModeRequest = new UpdateServerOSBootModeRequest(); // UpdateServerOSBootModeRequest | 
    try {
      apiInstance.updateServerOSBootMode(serverId, updateServerOSBootModeRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling ServersApi#updateServerOSBootMode");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **serverId** | **Integer**| Уникальный идентификатор облачного сервера. | |
| **updateServerOSBootModeRequest** | [**UpdateServerOSBootModeRequest**](UpdateServerOSBootModeRequest.md)|  | [optional] |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Успешная смена загрузки операционной системы сервера |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

