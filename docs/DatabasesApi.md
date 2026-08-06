# DatabasesApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createDatabaseBackup**](DatabasesApi.md#createDatabaseBackup) | **POST** /api/v1/dbs/{db_id}/backups | Создание бэкапа базы данных |
| [**createDatabaseBackupDownloadUrl**](DatabasesApi.md#createDatabaseBackupDownloadUrl) | **POST** /api/v1/dbs/{db_id}/backups/{backup_id}/download-url | Получение ссылки для скачивания бэкапа базы данных |
| [**createDatabaseCluster**](DatabasesApi.md#createDatabaseCluster) | **POST** /api/v1/databases | Создание кластера базы данных |
| [**createDatabaseInstance**](DatabasesApi.md#createDatabaseInstance) | **POST** /api/v1/databases/{db_cluster_id}/instances | Создание инстанса базы данных |
| [**createDatabaseS3Backup**](DatabasesApi.md#createDatabaseS3Backup) | **POST** /api/v2/databases/{db_id}/backups | Создание S3-бэкапа базы данных |
| [**createDatabaseUser**](DatabasesApi.md#createDatabaseUser) | **POST** /api/v1/databases/{db_cluster_id}/admins | Создание пользователя базы данных |
| [**deleteDatabaseBackup**](DatabasesApi.md#deleteDatabaseBackup) | **DELETE** /api/v1/dbs/{db_id}/backups/{backup_id} | Удаление бэкапа базы данных |
| [**deleteDatabaseCluster**](DatabasesApi.md#deleteDatabaseCluster) | **DELETE** /api/v1/databases/{db_cluster_id} | Удаление кластера базы данных |
| [**deleteDatabaseInstance**](DatabasesApi.md#deleteDatabaseInstance) | **DELETE** /api/v1/databases/{db_cluster_id}/instances/{instance_id} | Удаление инстанса базы данных |
| [**deleteDatabaseS3Backup**](DatabasesApi.md#deleteDatabaseS3Backup) | **DELETE** /api/v2/databases/{db_id}/backups/{backup_id} | Удаление S3-бэкапа базы данных |
| [**deleteDatabaseUser**](DatabasesApi.md#deleteDatabaseUser) | **DELETE** /api/v1/databases/{db_cluster_id}/admins/{admin_id} | Удаление пользователя базы данных |
| [**getDatabaseAutoBackupsSettings**](DatabasesApi.md#getDatabaseAutoBackupsSettings) | **GET** /api/v1/dbs/{db_id}/auto-backups | Получение настроек автобэкапов базы данных |
| [**getDatabaseBackup**](DatabasesApi.md#getDatabaseBackup) | **GET** /api/v1/dbs/{db_id}/backups/{backup_id} | Получение бэкапа базы данных |
| [**getDatabaseBackups**](DatabasesApi.md#getDatabaseBackups) | **GET** /api/v1/dbs/{db_id}/backups | Список бэкапов базы данных |
| [**getDatabaseCluster**](DatabasesApi.md#getDatabaseCluster) | **GET** /api/v1/databases/{db_cluster_id} | Получение кластера базы данных |
| [**getDatabaseClusterReplicas**](DatabasesApi.md#getDatabaseClusterReplicas) | **GET** /api/v1/databases/{db_cluster_id}/replicas | Получение списка реплик кластера базы данных |
| [**getDatabaseClusterTypes**](DatabasesApi.md#getDatabaseClusterTypes) | **GET** /api/v1/database-types | Получение списка типов кластеров баз данных |
| [**getDatabaseClusters**](DatabasesApi.md#getDatabaseClusters) | **GET** /api/v1/databases | Получение списка кластеров баз данных |
| [**getDatabaseConfigurators**](DatabasesApi.md#getDatabaseConfigurators) | **GET** /api/v1/configurator/databases | Получение списка конфигураторов баз данных |
| [**getDatabaseDefaultParameters**](DatabasesApi.md#getDatabaseDefaultParameters) | **GET** /api/v1/dbs/default-parameters | Получение рекомендуемых значений параметров баз данных |
| [**getDatabaseInstance**](DatabasesApi.md#getDatabaseInstance) | **GET** /api/v1/databases/{db_cluster_id}/instances/{instance_id} | Получение инстанса базы данных |
| [**getDatabaseInstances**](DatabasesApi.md#getDatabaseInstances) | **GET** /api/v1/databases/{db_cluster_id}/instances | Получение списка инстансов баз данных |
| [**getDatabaseParameters**](DatabasesApi.md#getDatabaseParameters) | **GET** /api/v1/dbs/parameters | Получение списка параметров баз данных |
| [**getDatabasePreset**](DatabasesApi.md#getDatabasePreset) | **GET** /api/v2/dbs/presets/{preset_id} | Получение тарифа для базы данных |
| [**getDatabasePrivileges**](DatabasesApi.md#getDatabasePrivileges) | **GET** /api/v1/databases/{db_cluster_id}/privileges | Получение привилегий кластера базы данных |
| [**getDatabaseS3Backup**](DatabasesApi.md#getDatabaseS3Backup) | **GET** /api/v2/databases/{db_id}/backups/{backup_id} | Получение S3-бэкапа базы данных |
| [**getDatabaseS3Backups**](DatabasesApi.md#getDatabaseS3Backups) | **GET** /api/v2/databases/{db_id}/backups | Список S3-бэкапов базы данных |
| [**getDatabaseUser**](DatabasesApi.md#getDatabaseUser) | **GET** /api/v1/databases/{db_cluster_id}/admins/{admin_id} | Получение пользователя базы данных |
| [**getDatabaseUsers**](DatabasesApi.md#getDatabaseUsers) | **GET** /api/v1/databases/{db_cluster_id}/admins | Получение списка пользователей базы данных |
| [**getDatabasesPresets**](DatabasesApi.md#getDatabasesPresets) | **GET** /api/v2/presets/dbs | Получение списка тарифов для баз данных |
| [**performDatabaseClusterAction**](DatabasesApi.md#performDatabaseClusterAction) | **POST** /api/v1/databases/{db_cluster_id}/action | Выполнение действия над кластером базы данных |
| [**restoreDatabaseFromBackup**](DatabasesApi.md#restoreDatabaseFromBackup) | **PUT** /api/v1/dbs/{db_id}/backups/{backup_id} | Восстановление базы данных из бэкапа |
| [**restoreDatabaseFromS3Backup**](DatabasesApi.md#restoreDatabaseFromS3Backup) | **POST** /api/v2/databases/{db_id}/backups/{backup_id}/restore | Восстановление базы данных из S3-бэкапа |
| [**updateDatabaseAutoBackupsSettings**](DatabasesApi.md#updateDatabaseAutoBackupsSettings) | **PATCH** /api/v1/dbs/{db_id}/auto-backups | Изменение настроек автобэкапов базы данных |
| [**updateDatabaseBackup**](DatabasesApi.md#updateDatabaseBackup) | **PATCH** /api/v1/dbs/{db_id}/backups/{backup_id} | Изменение комментария к бэкапу базы данных |
| [**updateDatabaseCluster**](DatabasesApi.md#updateDatabaseCluster) | **PATCH** /api/v1/databases/{db_cluster_id} | Изменение кластера базы данных |
| [**updateDatabaseClusterV2**](DatabasesApi.md#updateDatabaseClusterV2) | **PATCH** /api/v2/databases/{db_cluster_id} | Изменение кластера базы данных (v2) |
| [**updateDatabaseInstance**](DatabasesApi.md#updateDatabaseInstance) | **PATCH** /api/v1/databases/{db_cluster_id}/instances/{instance_id} | Изменение инстанса базы данных |
| [**updateDatabaseS3Backup**](DatabasesApi.md#updateDatabaseS3Backup) | **PATCH** /api/v2/databases/{db_id}/backups/{backup_id} | Изменение комментария S3-бэкапа базы данных |
| [**updateDatabaseUser**](DatabasesApi.md#updateDatabaseUser) | **PATCH** /api/v1/databases/{db_cluster_id}/admins/{admin_id} | Изменение пользователя базы данных |


<a id="createDatabaseBackup"></a>
# **createDatabaseBackup**
> CreateDatabaseBackup201Response createDatabaseBackup(dbId, dbsCreateBackup)

Создание бэкапа базы данных

Чтобы создать бэкап базы данных, отправьте запрос POST в &#x60;api/v1/dbs/{db_id}/backups&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    DbsCreateBackup dbsCreateBackup = new DbsCreateBackup(); // DbsCreateBackup | 
    try {
      CreateDatabaseBackup201Response result = apiInstance.createDatabaseBackup(dbId, dbsCreateBackup);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#createDatabaseBackup");
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
| **dbId** | **Integer**| ID базы данных | |
| **dbsCreateBackup** | [**DbsCreateBackup**](DbsCreateBackup.md)|  | [optional] |

### Return type

[**CreateDatabaseBackup201Response**](CreateDatabaseBackup201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. Для кластеров PostgreSQL из нескольких нод значение ключа &#x60;backup&#x60; будет равно &#x60;null&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createDatabaseBackupDownloadUrl"></a>
# **createDatabaseBackupDownloadUrl**
> CreateDatabaseBackupDownloadUrl201Response createDatabaseBackupDownloadUrl(dbId, backupId, backupDownloadUrlRequest)

Получение ссылки для скачивания бэкапа базы данных

Чтобы получить ссылку для скачивания резервной копии базы данных, отправьте POST-запрос на &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}/download-url&#x60;.   Скачивание резервных копий доступно не для всех кластеров. Если для вашего кластера оно недоступно, метод вернет ошибку со статусом &#x60;400&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backup_url&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    Integer backupId = 56; // Integer | ID резервной копии
    BackupDownloadUrlRequest backupDownloadUrlRequest = new BackupDownloadUrlRequest(); // BackupDownloadUrlRequest | 
    try {
      CreateDatabaseBackupDownloadUrl201Response result = apiInstance.createDatabaseBackupDownloadUrl(dbId, backupId, backupDownloadUrlRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#createDatabaseBackupDownloadUrl");
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
| **dbId** | **Integer**| ID базы данных | |
| **backupId** | **Integer**| ID резервной копии | |
| **backupDownloadUrlRequest** | [**BackupDownloadUrlRequest**](BackupDownloadUrlRequest.md)|  | |

### Return type

[**CreateDatabaseBackupDownloadUrl201Response**](CreateDatabaseBackupDownloadUrl201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ответ будет представлять собой объект JSON с ключом &#x60;backup_url&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createDatabaseCluster"></a>
# **createDatabaseCluster**
> CreateDatabaseCluster201Response createDatabaseCluster(createCluster)

Создание кластера базы данных

Чтобы создать кластер базы данных на вашем аккаунте, отправьте POST-запрос на &#x60;/api/v1/databases&#x60;.   Вместе с кластером будет создан один инстанс базы данных и один пользователь.   Размер кластера задается либо тарифом (&#x60;preset_id&#x60;), либо конфигуратором (&#x60;configuration&#x60;). Эти поля взаимоисключающие, но одно из них передать обязательно — запрос без обоих вернется с ошибкой.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    CreateCluster createCluster = new CreateCluster(); // CreateCluster | 
    try {
      CreateDatabaseCluster201Response result = apiInstance.createDatabaseCluster(createCluster);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#createDatabaseCluster");
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
| **createCluster** | [**CreateCluster**](CreateCluster.md)|  | |

### Return type

[**CreateDatabaseCluster201Response**](CreateDatabaseCluster201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createDatabaseInstance"></a>
# **createDatabaseInstance**
> CreateDatabaseInstance201Response createDatabaseInstance(dbClusterId, createInstance)

Создание инстанса базы данных

Чтобы создать инстанс базы данных, отправьте POST-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances&#x60;.\\    Существующие пользователи не будут иметь доступа к новой базе данных после создания. Вы можете изменить привилегии для пользователя через &lt;a href&#x3D;&#39;#tag/Bazy-dannyh/operation/updateDatabaseUser&#39;&gt;метод изменения пользователя&lt;/a&gt; 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    CreateInstance createInstance = new CreateInstance(); // CreateInstance | 
    try {
      CreateDatabaseInstance201Response result = apiInstance.createDatabaseInstance(dbClusterId, createInstance);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#createDatabaseInstance");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |
| **createInstance** | [**CreateInstance**](CreateInstance.md)|  | |

### Return type

[**CreateDatabaseInstance201Response**](CreateDatabaseInstance201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createDatabaseS3Backup"></a>
# **createDatabaseS3Backup**
> CreateDatabaseS3Backup201Response createDatabaseS3Backup(dbId, createS3Backup)

Создание S3-бэкапа базы данных

Чтобы создать резервную копию кластера базы данных в объектном хранилище, отправьте POST-запрос на &#x60;/api/v2/databases/{db_id}/backups&#x60;.   Тело запроса необязательно: единственное поле &#x60;comment&#x60; можно не передавать. Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;.   Копия создается асинхронно. Пока она создается, ее статус — &#x60;running&#x60;, и восстановиться из нее нельзя. Дождитесь статуса &#x60;success&#x60;, опрашивая &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    CreateS3Backup createS3Backup = new CreateS3Backup(); // CreateS3Backup | 
    try {
      CreateDatabaseS3Backup201Response result = apiInstance.createDatabaseS3Backup(dbId, createS3Backup);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#createDatabaseS3Backup");
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
| **dbId** | **Integer**| ID базы данных | |
| **createS3Backup** | [**CreateS3Backup**](CreateS3Backup.md)|  | [optional] |

### Return type

[**CreateDatabaseS3Backup201Response**](CreateDatabaseS3Backup201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createDatabaseUser"></a>
# **createDatabaseUser**
> CreateDatabaseUser201Response createDatabaseUser(dbClusterId, createAdmin)

Создание пользователя базы данных

Чтобы создать пользователя базы данных, отправьте POST-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    CreateAdmin createAdmin = new CreateAdmin(); // CreateAdmin | 
    try {
      CreateDatabaseUser201Response result = apiInstance.createDatabaseUser(dbClusterId, createAdmin);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#createDatabaseUser");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |
| **createAdmin** | [**CreateAdmin**](CreateAdmin.md)|  | |

### Return type

[**CreateDatabaseUser201Response**](CreateDatabaseUser201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteDatabaseBackup"></a>
# **deleteDatabaseBackup**
> deleteDatabaseBackup(dbId, backupId)

Удаление бэкапа базы данных

Чтобы удалить бэкап базы данных, отправьте запрос DELETE в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    Integer backupId = 56; // Integer | ID резервной копии
    try {
      apiInstance.deleteDatabaseBackup(dbId, backupId);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#deleteDatabaseBackup");
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
| **dbId** | **Integer**| ID базы данных | |
| **backupId** | **Integer**| ID резервной копии | |

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
| **204** | Бэкап успешно удален. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteDatabaseCluster"></a>
# **deleteDatabaseCluster**
> DeleteDatabaseCluster200Response deleteDatabaseCluster(dbClusterId)

Удаление кластера базы данных

Чтобы удалить кластер базы данных, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    try {
      DeleteDatabaseCluster200Response result = apiInstance.deleteDatabaseCluster(dbClusterId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#deleteDatabaseCluster");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |

### Return type

[**DeleteDatabaseCluster200Response**](DeleteDatabaseCluster200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Если для удаления кластера требуется подтверждение, кластер не удаляется сразу: ответ будет представлять собой объект JSON c ключом &#x60;database_delete&#x60;. |  -  |
| **204** | Кластер базы данных удален. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteDatabaseInstance"></a>
# **deleteDatabaseInstance**
> deleteDatabaseInstance(dbClusterId, instanceId)

Удаление инстанса базы данных

Чтобы удалить инстанс базы данных, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    Integer instanceId = 56; // Integer | ID инстанса базы данных
    try {
      apiInstance.deleteDatabaseInstance(dbClusterId, instanceId);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#deleteDatabaseInstance");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |
| **instanceId** | **Integer**| ID инстанса базы данных | |

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
| **204** | Инстанс базы данных удален. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteDatabaseS3Backup"></a>
# **deleteDatabaseS3Backup**
> deleteDatabaseS3Backup(dbId, backupId)

Удаление S3-бэкапа базы данных

Чтобы удалить резервную копию кластера базы данных из объектного хранилища, отправьте DELETE-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.   Копия удаляется безвозвратно, тело ответа пустое. На резервные копии из &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}&#x60; этот метод не действует — они удаляются отдельным запросом.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    UUID backupId = UUID.randomUUID(); // UUID | ID резервной копии в формате UUID
    try {
      apiInstance.deleteDatabaseS3Backup(dbId, backupId);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#deleteDatabaseS3Backup");
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
| **dbId** | **Integer**| ID базы данных | |
| **backupId** | **UUID**| ID резервной копии в формате UUID | |

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
| **204** | Резервная копия успешно удалена. Тело ответа пустое. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteDatabaseUser"></a>
# **deleteDatabaseUser**
> deleteDatabaseUser(dbClusterId, adminId)

Удаление пользователя базы данных

Чтобы удалить пользователя базы данных на вашем аккаунте, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    Integer adminId = 56; // Integer | ID пользователя базы данных
    try {
      apiInstance.deleteDatabaseUser(dbClusterId, adminId);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#deleteDatabaseUser");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |
| **adminId** | **Integer**| ID пользователя базы данных | |

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
| **204** | Пользователь базы данных удален. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseAutoBackupsSettings"></a>
# **getDatabaseAutoBackupsSettings**
> GetDatabaseAutoBackupsSettings200Response getDatabaseAutoBackupsSettings(dbId)

Получение настроек автобэкапов базы данных

Чтобы получить список настроек автобэкапов базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/auto-backups&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    try {
      GetDatabaseAutoBackupsSettings200Response result = apiInstance.getDatabaseAutoBackupsSettings(dbId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseAutoBackupsSettings");
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
| **dbId** | **Integer**| ID базы данных | |

### Return type

[**GetDatabaseAutoBackupsSettings200Response**](GetDatabaseAutoBackupsSettings200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;auto_backups_settings&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseBackup"></a>
# **getDatabaseBackup**
> GetDatabaseBackup200Response getDatabaseBackup(dbId, backupId)

Получение бэкапа базы данных

Чтобы получить бэкап базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    Integer backupId = 56; // Integer | ID резервной копии
    try {
      GetDatabaseBackup200Response result = apiInstance.getDatabaseBackup(dbId, backupId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseBackup");
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
| **dbId** | **Integer**| ID базы данных | |
| **backupId** | **Integer**| ID резервной копии | |

### Return type

[**GetDatabaseBackup200Response**](GetDatabaseBackup200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseBackups"></a>
# **getDatabaseBackups**
> GetDatabaseBackups200Response getDatabaseBackups(dbId, limit, offset)

Список бэкапов базы данных

Чтобы получить список бэкапов базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/backups&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    try {
      GetDatabaseBackups200Response result = apiInstance.getDatabaseBackups(dbId, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseBackups");
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
| **dbId** | **Integer**| ID базы данных | |
| **limit** | **Integer**| Обозначает количество записей, которое необходимо вернуть. | [optional] [default to 100] |
| **offset** | **Integer**| Указывает на смещение относительно начала списка. | [optional] [default to 0] |

### Return type

[**GetDatabaseBackups200Response**](GetDatabaseBackups200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;backups&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseCluster"></a>
# **getDatabaseCluster**
> CreateDatabaseCluster201Response getDatabaseCluster(dbClusterId)

Получение кластера базы данных

Чтобы получить кластер базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    try {
      CreateDatabaseCluster201Response result = apiInstance.getDatabaseCluster(dbClusterId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseCluster");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |

### Return type

[**CreateDatabaseCluster201Response**](CreateDatabaseCluster201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseClusterReplicas"></a>
# **getDatabaseClusterReplicas**
> GetDatabaseClusterReplicas200Response getDatabaseClusterReplicas(dbClusterId)

Получение списка реплик кластера базы данных

Чтобы получить список реплик кластера базы данных, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/replicas&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;replicas&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    try {
      GetDatabaseClusterReplicas200Response result = apiInstance.getDatabaseClusterReplicas(dbClusterId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseClusterReplicas");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |

### Return type

[**GetDatabaseClusterReplicas200Response**](GetDatabaseClusterReplicas200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;replicas&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseClusterTypes"></a>
# **getDatabaseClusterTypes**
> GetDatabaseClusterTypes200Response getDatabaseClusterTypes()

Получение списка типов кластеров баз данных

Чтобы получить список типов баз данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/database-types&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    try {
      GetDatabaseClusterTypes200Response result = apiInstance.getDatabaseClusterTypes();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseClusterTypes");
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

[**GetDatabaseClusterTypes200Response**](GetDatabaseClusterTypes200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;types&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseClusters"></a>
# **getDatabaseClusters**
> GetDatabaseClusters200Response getDatabaseClusters(limit, offset)

Получение списка кластеров баз данных

Чтобы получить список кластеров баз данных, отправьте GET-запрос на &#x60;/api/v1/databases&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;dbs&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    try {
      GetDatabaseClusters200Response result = apiInstance.getDatabaseClusters(limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseClusters");
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

[**GetDatabaseClusters200Response**](GetDatabaseClusters200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;dbs&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseConfigurators"></a>
# **getDatabaseConfigurators**
> GetDatabaseConfigurators200Response getDatabaseConfigurators(clusterId, withUnavailable)

Получение списка конфигураторов баз данных

Чтобы получить список конфигураторов баз данных, отправьте GET-запрос на &#x60;/api/v1/configurator/databases&#x60;.   Конфигуратор позволяет создать кластер с произвольным количеством ресурсов вместо готового тарифа: его ID передается при создании кластера в поле &#x60;configuration.configurator_id&#x60;, а допустимые значения ресурсов ограничены объектом &#x60;requirements&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;database_configurators&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer clusterId = 56; // Integer | ID кластера базы данных. Возвращает конфигураторы группы, в пределах которой доступна смена конфигурации этого кластера (сценарий изменения кластера).
    Boolean withUnavailable = true; // Boolean | Включить в ответ конфигураторы, недоступные к заказу из-за нехватки свободных ресурсов. Учитывается только при запросе без `cluster_id`.
    try {
      GetDatabaseConfigurators200Response result = apiInstance.getDatabaseConfigurators(clusterId, withUnavailable);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseConfigurators");
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
| **clusterId** | **Integer**| ID кластера базы данных. Возвращает конфигураторы группы, в пределах которой доступна смена конфигурации этого кластера (сценарий изменения кластера). | [optional] |
| **withUnavailable** | **Boolean**| Включить в ответ конфигураторы, недоступные к заказу из-за нехватки свободных ресурсов. Учитывается только при запросе без &#x60;cluster_id&#x60;. | [optional] |

### Return type

[**GetDatabaseConfigurators200Response**](GetDatabaseConfigurators200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Конфигураторы успешно получены. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseDefaultParameters"></a>
# **getDatabaseDefaultParameters**
> GetDatabaseDefaultParameters200Response getDatabaseDefaultParameters(type, ram, replicaCount)

Получение рекомендуемых значений параметров баз данных

Чтобы получить рекомендуемые значения параметров базы данных, отправьте GET-запрос на &#x60;/api/v1/dbs/default-parameters&#x60;.   Значения рассчитываются для указанного типа кластера, объема оперативной памяти и количества реплик — их можно передать при создании кластера в поле &#x60;config_parameters&#x60;. Список имен параметров, доступных для каждого типа кластера, возвращает &#x60;GET /api/v1/dbs/parameters&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;config_params&#x60;. Рекомендуемые значения рассчитываются только для кластеров MySQL, PostgreSQL и Valkey — для остальных типов возвращается пустой объект.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    String type = "mysql5"; // String | Тип кластера базы данных.
    Integer ram = 2048; // Integer | Объём оперативной памяти кластера (в Мб).
    Integer replicaCount = 1; // Integer | Количество нод (реплик) кластера.
    try {
      GetDatabaseDefaultParameters200Response result = apiInstance.getDatabaseDefaultParameters(type, ram, replicaCount);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseDefaultParameters");
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
| **type** | **String**| Тип кластера базы данных. | [enum: mysql5, mysql, mysql8_4, postgres, postgres14, postgres15, postgres16, postgres17, postgres18, redis, redis7, redis8_1, valkey, valkey7, valkey8_1, valkey9_1, mongodb4, mongodb, mongodb6, mongodb7, mongodb8_0, opensearch, opensearch2_19, clickhouse, clickhouse24, clickhouse25, kafka, rabbitmq, rabbitmq4_0] |
| **ram** | **Integer**| Объём оперативной памяти кластера (в Мб). | |
| **replicaCount** | **Integer**| Количество нод (реплик) кластера. | [optional] [default to 1] |

### Return type

[**GetDatabaseDefaultParameters200Response**](GetDatabaseDefaultParameters200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Рекомендуемые значения параметров успешно получены. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseInstance"></a>
# **getDatabaseInstance**
> CreateDatabaseInstance201Response getDatabaseInstance(dbClusterId, instanceId)

Получение инстанса базы данных

Чтобы получить инстанс базы данных, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    Integer instanceId = 56; // Integer | ID инстанса базы данных
    try {
      CreateDatabaseInstance201Response result = apiInstance.getDatabaseInstance(dbClusterId, instanceId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseInstance");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |
| **instanceId** | **Integer**| ID инстанса базы данных | |

### Return type

[**CreateDatabaseInstance201Response**](CreateDatabaseInstance201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseInstances"></a>
# **getDatabaseInstances**
> GetDatabaseInstances200Response getDatabaseInstances(dbClusterId)

Получение списка инстансов баз данных

Чтобы получить список баз данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    try {
      GetDatabaseInstances200Response result = apiInstance.getDatabaseInstances(dbClusterId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseInstances");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |

### Return type

[**GetDatabaseInstances200Response**](GetDatabaseInstances200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;instances&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseParameters"></a>
# **getDatabaseParameters**
> DbParametersByType getDatabaseParameters()

Получение списка параметров баз данных

Чтобы получить список параметров баз данных, отправьте GET-запрос на &#x60;/api/v1/dbs/parameters&#x60;.   Ответ содержит только имена параметров, доступных для каждого типа кластера. Рекомендуемые значения этих параметров для конкретной конфигурации возвращает &#x60;GET /api/v1/dbs/default-parameters&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    try {
      DbParametersByType result = apiInstance.getDatabaseParameters();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseParameters");
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

[**DbParametersByType**](DbParametersByType.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON, ключи которого — типы кластеров баз данных, а значения — массивы имён доступных параметров. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabasePreset"></a>
# **getDatabasePreset**
> GetDatabasePreset200Response getDatabasePreset(presetId)

Получение тарифа для базы данных

Чтобы получить тариф для базы данных, отправьте GET-запрос на &#x60;/api/v2/dbs/presets/{preset_id}&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;databases_preset&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer presetId = 56; // Integer | ID тарифа
    try {
      GetDatabasePreset200Response result = apiInstance.getDatabasePreset(presetId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabasePreset");
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
| **presetId** | **Integer**| ID тарифа | |

### Return type

[**GetDatabasePreset200Response**](GetDatabasePreset200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Тариф успешно получен. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabasePrivileges"></a>
# **getDatabasePrivileges**
> GetDatabasePrivileges200Response getDatabasePrivileges(dbClusterId)

Получение привилегий кластера базы данных

Чтобы получить список привилегий, которые можно выдать пользователям кластера базы данных, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/privileges&#x60;.\\    Список зависит от типа СУБД кластера и определяется сервером автоматически: возвращаются только те привилегии, которые допустимы для этого кластера. Используйте его, чтобы заполнить поле &#x60;privileges&#x60; при &lt;a href&#x3D;&#39;#tag/Bazy-dannyh/operation/createDatabaseUser&#39;&gt;создании&lt;/a&gt; или &lt;a href&#x3D;&#39;#tag/Bazy-dannyh/operation/updateDatabaseUser&#39;&gt;изменении&lt;/a&gt; пользователя базы данных.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    try {
      GetDatabasePrivileges200Response result = apiInstance.getDatabasePrivileges(dbClusterId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabasePrivileges");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |

### Return type

[**GetDatabasePrivileges200Response**](GetDatabasePrivileges200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;privileges&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseS3Backup"></a>
# **getDatabaseS3Backup**
> CreateDatabaseS3Backup201Response getDatabaseS3Backup(dbId, backupId)

Получение S3-бэкапа базы данных

Чтобы получить информацию о резервной копии кластера базы данных в объектном хранилище, отправьте GET-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;. Обратите внимание, что &#x60;backup_id&#x60; здесь — строка в формате UUID, а не число, как в &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    UUID backupId = UUID.randomUUID(); // UUID | ID резервной копии в формате UUID
    try {
      CreateDatabaseS3Backup201Response result = apiInstance.getDatabaseS3Backup(dbId, backupId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseS3Backup");
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
| **dbId** | **Integer**| ID базы данных | |
| **backupId** | **UUID**| ID резервной копии в формате UUID | |

### Return type

[**CreateDatabaseS3Backup201Response**](CreateDatabaseS3Backup201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseS3Backups"></a>
# **getDatabaseS3Backups**
> GetDatabaseS3Backups200Response getDatabaseS3Backups(dbId)

Список S3-бэкапов базы данных

Чтобы получить список резервных копий кластера базы данных в объектном хранилище, отправьте GET-запрос на &#x60;/api/v2/databases/{db_id}/backups&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backups&#x60;. Копии отсортированы по дате создания по убыванию — сначала самые свежие.   Резервное копирование в объектное хранилище доступно для кластеров MySQL и PostgreSQL. Идентификатор такой копии — строка в формате UUID; это отдельный от &#x60;/api/v1/dbs/{db_id}/backups&#x60; механизм, и идентификаторы копий между ними не взаимозаменяемы.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    try {
      GetDatabaseS3Backups200Response result = apiInstance.getDatabaseS3Backups(dbId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseS3Backups");
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
| **dbId** | **Integer**| ID базы данных | |

### Return type

[**GetDatabaseS3Backups200Response**](GetDatabaseS3Backups200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;backups&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseUser"></a>
# **getDatabaseUser**
> CreateDatabaseUser201Response getDatabaseUser(dbClusterId, adminId)

Получение пользователя базы данных

Чтобы получить пользователя базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    Integer adminId = 56; // Integer | ID пользователя базы данных
    try {
      CreateDatabaseUser201Response result = apiInstance.getDatabaseUser(dbClusterId, adminId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseUser");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |
| **adminId** | **Integer**| ID пользователя базы данных | |

### Return type

[**CreateDatabaseUser201Response**](CreateDatabaseUser201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabaseUsers"></a>
# **getDatabaseUsers**
> GetDatabaseUsers200Response getDatabaseUsers(dbClusterId)

Получение списка пользователей базы данных

Чтобы получить список пользователей базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    try {
      GetDatabaseUsers200Response result = apiInstance.getDatabaseUsers(dbClusterId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabaseUsers");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |

### Return type

[**GetDatabaseUsers200Response**](GetDatabaseUsers200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;admins&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDatabasesPresets"></a>
# **getDatabasesPresets**
> GetDatabasesPresets200Response getDatabasesPresets(clusterId, withUnavailable)

Получение списка тарифов для баз данных

Чтобы получить список тарифов для баз данных, отправьте GET-запрос на &#x60;/api/v2/presets/dbs&#x60;.   Без параметров возвращаются тарифы, доступные к заказу — этот список используется при создании кластера. Если передать &#x60;cluster_id&#x60;, вернутся тарифы группы, в пределах которой можно сменить тариф указанного кластера.   Тело ответа будет представлять собой объект JSON с ключом &#x60;databases_presets&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer clusterId = 56; // Integer | ID кластера базы данных. Возвращает тарифы группы, в пределах которой доступна смена тарифа этого кластера (сценарий изменения кластера).
    Boolean withUnavailable = true; // Boolean | Включить в ответ тарифы, недоступные к заказу из-за нехватки свободных ресурсов. Учитывается только при запросе без `cluster_id`: вместе с `cluster_id` фильтр по свободным ресурсам и так не применяется.
    try {
      GetDatabasesPresets200Response result = apiInstance.getDatabasesPresets(clusterId, withUnavailable);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#getDatabasesPresets");
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
| **clusterId** | **Integer**| ID кластера базы данных. Возвращает тарифы группы, в пределах которой доступна смена тарифа этого кластера (сценарий изменения кластера). | [optional] |
| **withUnavailable** | **Boolean**| Включить в ответ тарифы, недоступные к заказу из-за нехватки свободных ресурсов. Учитывается только при запросе без &#x60;cluster_id&#x60;: вместе с &#x60;cluster_id&#x60; фильтр по свободным ресурсам и так не применяется. | [optional] |

### Return type

[**GetDatabasesPresets200Response**](GetDatabasesPresets200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Тарифы успешно получены. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="performDatabaseClusterAction"></a>
# **performDatabaseClusterAction**
> performDatabaseClusterAction(dbClusterId, clusterAction)

Выполнение действия над кластером базы данных

Чтобы выполнить действие над кластером базы данных, отправьте POST-запрос на &#x60;/api/v1/databases/{db_cluster_id}/action&#x60;.   Доступные действия: &#x60;reboot&#x60; — перезагрузка кластера, &#x60;shutdown&#x60; — выключение кластера, &#x60;start&#x60; — включение кластера.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    ClusterAction clusterAction = new ClusterAction(); // ClusterAction | 
    try {
      apiInstance.performDatabaseClusterAction(dbClusterId, clusterAction);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#performDatabaseClusterAction");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |
| **clusterAction** | [**ClusterAction**](ClusterAction.md)|  | |

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
| **201** | Действие принято к выполнению. Тело ответа пустое. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="restoreDatabaseFromBackup"></a>
# **restoreDatabaseFromBackup**
> restoreDatabaseFromBackup(dbId, backupId)

Восстановление базы данных из бэкапа

Чтобы восстановить базу данных из бэкапа, отправьте запрос PUT в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    Integer backupId = 56; // Integer | ID резервной копии
    try {
      apiInstance.restoreDatabaseFromBackup(dbId, backupId);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#restoreDatabaseFromBackup");
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
| **dbId** | **Integer**| ID базы данных | |
| **backupId** | **Integer**| ID резервной копии | |

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
| **204** | База данных из бэкапа успешно восстановлена. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="restoreDatabaseFromS3Backup"></a>
# **restoreDatabaseFromS3Backup**
> restoreDatabaseFromS3Backup(dbId, backupId)

Восстановление базы данных из S3-бэкапа

Чтобы восстановить кластер базы данных из резервной копии в объектном хранилище, отправьте POST-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}/restore&#x60;.   Тела запроса нет, тело ответа пустое. Восстановиться можно только из копии со статусом &#x60;success&#x60;.   Сразу после запуска кластер переходит в статус &#x60;backup_recovery&#x60;. Пока восстановление не завершится, создание, изменение и удаление резервных копий, а также повторный запуск восстановления недоступны.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    UUID backupId = UUID.randomUUID(); // UUID | ID резервной копии в формате UUID
    try {
      apiInstance.restoreDatabaseFromS3Backup(dbId, backupId);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#restoreDatabaseFromS3Backup");
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
| **dbId** | **Integer**| ID базы данных | |
| **backupId** | **UUID**| ID резервной копии в формате UUID | |

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
| **204** | Восстановление успешно запущено. Тело ответа пустое. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateDatabaseAutoBackupsSettings"></a>
# **updateDatabaseAutoBackupsSettings**
> GetDatabaseAutoBackupsSettings200Response updateDatabaseAutoBackupsSettings(dbId, updateAutoBackup)

Изменение настроек автобэкапов базы данных

Чтобы изменить список настроек автобэкапов базы данных, отправьте запрос PATCH в &#x60;api/v1/dbs/{db_id}/auto-backups&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    UpdateAutoBackup updateAutoBackup = new UpdateAutoBackup(); // UpdateAutoBackup | При значении `is_enabled`: `true`, поля `copy_count`, `creation_start_at`, `interval` являются обязательными
    try {
      GetDatabaseAutoBackupsSettings200Response result = apiInstance.updateDatabaseAutoBackupsSettings(dbId, updateAutoBackup);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#updateDatabaseAutoBackupsSettings");
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
| **dbId** | **Integer**| ID базы данных | |
| **updateAutoBackup** | [**UpdateAutoBackup**](UpdateAutoBackup.md)| При значении &#x60;is_enabled&#x60;: &#x60;true&#x60;, поля &#x60;copy_count&#x60;, &#x60;creation_start_at&#x60;, &#x60;interval&#x60; являются обязательными | |

### Return type

[**GetDatabaseAutoBackupsSettings200Response**](GetDatabaseAutoBackupsSettings200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;auto_backups_settings&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateDatabaseBackup"></a>
# **updateDatabaseBackup**
> GetDatabaseBackup200Response updateDatabaseBackup(dbId, backupId, dbsUpdateBackup)

Изменение комментария к бэкапу базы данных

Чтобы изменить комментарий к бэкапу базы данных, отправьте PATCH-запрос на &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    Integer backupId = 56; // Integer | ID резервной копии
    DbsUpdateBackup dbsUpdateBackup = new DbsUpdateBackup(); // DbsUpdateBackup | 
    try {
      GetDatabaseBackup200Response result = apiInstance.updateDatabaseBackup(dbId, backupId, dbsUpdateBackup);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#updateDatabaseBackup");
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
| **dbId** | **Integer**| ID базы данных | |
| **backupId** | **Integer**| ID резервной копии | |
| **dbsUpdateBackup** | [**DbsUpdateBackup**](DbsUpdateBackup.md)|  | |

### Return type

[**GetDatabaseBackup200Response**](GetDatabaseBackup200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateDatabaseCluster"></a>
# **updateDatabaseCluster**
> UpdateDatabaseCluster200Response updateDatabaseCluster(dbClusterId, updateCluster)

Изменение кластера базы данных

Чтобы изменить кластер базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.   Размер кластера задается либо тарифом (&#x60;preset_id&#x60;), либо конфигуратором (&#x60;configuration&#x60;) — эти поля взаимоисключающие.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    UpdateCluster updateCluster = new UpdateCluster(); // UpdateCluster | 
    try {
      UpdateDatabaseCluster200Response result = apiInstance.updateDatabaseCluster(dbClusterId, updateCluster);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#updateDatabaseCluster");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |
| **updateCluster** | [**UpdateCluster**](UpdateCluster.md)|  | |

### Return type

[**UpdateDatabaseCluster200Response**](UpdateDatabaseCluster200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateDatabaseClusterV2"></a>
# **updateDatabaseClusterV2**
> UpdateDatabaseCluster200Response updateDatabaseClusterV2(dbClusterId, updateClusterV2)

Изменение кластера базы данных (v2)

Чтобы изменить кластер базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v2/databases/{db_cluster_id}&#x60;.   В отличие от &#x60;/api/v1/databases/{db_cluster_id}&#x60;, эта версия дополнительно позволяет привязать плавающий IP-адрес (&#x60;floating_ip&#x60;).   Размер кластера задается либо тарифом (&#x60;preset_id&#x60;), либо конфигуратором (&#x60;configuration&#x60;) — эти поля взаимоисключающие.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    UpdateClusterV2 updateClusterV2 = new UpdateClusterV2(); // UpdateClusterV2 | 
    try {
      UpdateDatabaseCluster200Response result = apiInstance.updateDatabaseClusterV2(dbClusterId, updateClusterV2);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#updateDatabaseClusterV2");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |
| **updateClusterV2** | [**UpdateClusterV2**](UpdateClusterV2.md)|  | |

### Return type

[**UpdateDatabaseCluster200Response**](UpdateDatabaseCluster200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateDatabaseInstance"></a>
# **updateDatabaseInstance**
> CreateDatabaseInstance201Response updateDatabaseInstance(dbClusterId, instanceId, updateInstance)

Изменение инстанса базы данных

Чтобы изменить инстанс базы данных, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.   Изменить название базы данных (&#x60;name&#x60;) и ее владельца (&#x60;owner_id&#x60;) можно только в кластере PostgreSQL, а настройки топика (&#x60;config_parameters&#x60;) — только в кластере Kafka. Если один из этих трех параметров передан для неподходящего типа кластера, запрос вернется с ошибкой 409.   Расширения (&#x60;extensions&#x60;) применимы к кластерам PostgreSQL и RabbitMQ.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    Integer instanceId = 56; // Integer | ID инстанса базы данных
    UpdateInstance updateInstance = new UpdateInstance(); // UpdateInstance | 
    try {
      CreateDatabaseInstance201Response result = apiInstance.updateDatabaseInstance(dbClusterId, instanceId, updateInstance);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#updateDatabaseInstance");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |
| **instanceId** | **Integer**| ID инстанса базы данных | |
| **updateInstance** | [**UpdateInstance**](UpdateInstance.md)|  | |

### Return type

[**CreateDatabaseInstance201Response**](CreateDatabaseInstance201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateDatabaseS3Backup"></a>
# **updateDatabaseS3Backup**
> CreateDatabaseS3Backup201Response updateDatabaseS3Backup(dbId, backupId, updateS3Backup)

Изменение комментария S3-бэкапа базы данных

Чтобы изменить комментарий к резервной копии кластера базы данных, отправьте PATCH-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.   Изменить можно только комментарий: других полей метод не принимает, сама резервная копия при этом не пересоздается. Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbId = 56; // Integer | ID базы данных
    UUID backupId = UUID.randomUUID(); // UUID | ID резервной копии в формате UUID
    UpdateS3Backup updateS3Backup = new UpdateS3Backup(); // UpdateS3Backup | 
    try {
      CreateDatabaseS3Backup201Response result = apiInstance.updateDatabaseS3Backup(dbId, backupId, updateS3Backup);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#updateDatabaseS3Backup");
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
| **dbId** | **Integer**| ID базы данных | |
| **backupId** | **UUID**| ID резервной копии в формате UUID | |
| **updateS3Backup** | [**UpdateS3Backup**](UpdateS3Backup.md)|  | [optional] |

### Return type

[**CreateDatabaseS3Backup201Response**](CreateDatabaseS3Backup201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateDatabaseUser"></a>
# **updateDatabaseUser**
> CreateDatabaseUser201Response updateDatabaseUser(dbClusterId, adminId, updateAdmin)

Изменение пользователя базы данных

Чтобы изменить пользователя базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DatabasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DatabasesApi apiInstance = new DatabasesApi(defaultClient);
    Integer dbClusterId = 56; // Integer | ID кластера базы данных
    Integer adminId = 56; // Integer | ID пользователя базы данных
    UpdateAdmin updateAdmin = new UpdateAdmin(); // UpdateAdmin | 
    try {
      CreateDatabaseUser201Response result = apiInstance.updateDatabaseUser(dbClusterId, adminId, updateAdmin);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DatabasesApi#updateDatabaseUser");
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
| **dbClusterId** | **Integer**| ID кластера базы данных | |
| **adminId** | **Integer**| ID пользователя базы данных | |
| **updateAdmin** | [**UpdateAdmin**](UpdateAdmin.md)|  | |

### Return type

[**CreateDatabaseUser201Response**](CreateDatabaseUser201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

