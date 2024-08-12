# AppsApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addProvider**](AppsApi.md#addProvider) | **POST** /api/v1/vcs-provider | Привязка vcs провайдера |
| [**createApp**](AppsApi.md#createApp) | **POST** /api/v1/apps | Создание приложения |
| [**createDeploy**](AppsApi.md#createDeploy) | **POST** /api/v1/apps/{app_id}/deploy | Запуск деплоя приложения |
| [**deleteApp**](AppsApi.md#deleteApp) | **DELETE** /api/v1/apps/{app_id} | Удаление приложения |
| [**deleteProvider**](AppsApi.md#deleteProvider) | **DELETE** /api/v1/vcs-provider/{provider_id} | Отвязка vcs провайдера от аккаунта |
| [**deployAction**](AppsApi.md#deployAction) | **POST** /api/v1/apps/{app_id}/deploy/{deploy_id}/stop | Остановка деплоя приложения |
| [**getApp**](AppsApi.md#getApp) | **GET** /api/v1/apps/{app_id} | Получение приложения по id |
| [**getAppDeploys**](AppsApi.md#getAppDeploys) | **GET** /api/v1/apps/{app_id}/deploys | Получение списка деплоев приложения |
| [**getAppLogs**](AppsApi.md#getAppLogs) | **GET** /api/v1/apps/{app_id}/logs | Получение логов приложения |
| [**getAppStatistics**](AppsApi.md#getAppStatistics) | **GET** /api/v1/apps/{app_id}/statistics | Получение статистики приложения |
| [**getApps**](AppsApi.md#getApps) | **GET** /api/v1/apps | Получение списка приложений |
| [**getAppsPresets**](AppsApi.md#getAppsPresets) | **GET** /api/v1/presets/apps | Получение списка доступных тарифов для приложения |
| [**getBranches**](AppsApi.md#getBranches) | **GET** /api/v1/vcs-provider/{provider_id}/repository/{repository_id} | Получение списка веток репозитория |
| [**getCommits**](AppsApi.md#getCommits) | **GET** /api/v1/vcs-provider/{provider_id}/repository/{repository_id}/branch | Получение списка коммитов ветки репозитория |
| [**getDeployLogs**](AppsApi.md#getDeployLogs) | **GET** /api/v1/apps/{app_id}/deploy/{deploy_id}/logs | Получение логов деплоя приложения |
| [**getDeploySettings**](AppsApi.md#getDeploySettings) | **GET** /api/v1/deploy-settings/apps | Получение списка дефолтных настроек деплоя для приложения |
| [**getFrameworks**](AppsApi.md#getFrameworks) | **GET** /api/v1/frameworks/apps | Получение списка доступных фреймворков для приложения |
| [**getProviders**](AppsApi.md#getProviders) | **GET** /api/v1/vcs-provider | Получение списка vcs провайдеров |
| [**getRepositories**](AppsApi.md#getRepositories) | **GET** /api/v1/vcs-provider/{provider_id} | Получение списка репозиториев vcs провайдера |
| [**updateAppSettings**](AppsApi.md#updateAppSettings) | **PATCH** /api/v1/apps/{app_id} | Изменение настроек приложения |
| [**updateAppState**](AppsApi.md#updateAppState) | **PATCH** /api/v1/apps/{app_id}/action/{action} | Изменение состояния приложения |


<a id="addProvider"></a>
# **addProvider**
> AddProvider201Response addProvider(addGithub)

Привязка vcs провайдера

Чтобы привязать аккаунт провайдера отправьте POST-запрос в &#x60;/api/v1/vcs-provider&#x60;, задав необходимые атрибуты.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    AddGithub addGithub = new AddGithub(); // AddGithub | 
    try {
      AddProvider201Response result = apiInstance.addProvider(addGithub);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#addProvider");
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
| **addGithub** | [**AddGithub**](AddGithub.md)|  | |

### Return type

[**AddProvider201Response**](AddProvider201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Добавление аккаунта провайдера: объект JSON c ключом &#x60;provider&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createApp"></a>
# **createApp**
> CreateApp201Response createApp(createApp)

Создание приложения

Чтобы создать приложение, отправьте POST-запрос в &#x60;/api/v1/apps&#x60;, задав необходимые атрибуты.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    CreateApp createApp = new CreateApp(); // CreateApp | 
    try {
      CreateApp201Response result = apiInstance.createApp(createApp);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#createApp");
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
| **createApp** | [**CreateApp**](CreateApp.md)|  | |

### Return type

[**CreateApp201Response**](CreateApp201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;app&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createDeploy"></a>
# **createDeploy**
> CreateDeploy201Response createDeploy(appId, createDeployRequest)

Запуск деплоя приложения

Чтобы запустить деплой приложения, отправьте POST-запрос в &#x60;/api/v1/apps/{app_id}/deploy&#x60;, задав необходимые атрибуты.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String appId = "appId_example"; // String | 
    CreateDeployRequest createDeployRequest = new CreateDeployRequest(); // CreateDeployRequest | 
    try {
      CreateDeploy201Response result = apiInstance.createDeploy(appId, createDeployRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#createDeploy");
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
| **appId** | **String**|  | |
| **createDeployRequest** | [**CreateDeployRequest**](CreateDeployRequest.md)|  | |

### Return type

[**CreateDeploy201Response**](CreateDeploy201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Деплой: объект JSON c ключом &#x60;deploy&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteApp"></a>
# **deleteApp**
> deleteApp(appId)

Удаление приложения

Чтобы удалить приложение, отправьте DELETE-запрос в &#x60;/api/v1/apps/{app_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String appId = "appId_example"; // String | 
    try {
      apiInstance.deleteApp(appId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#deleteApp");
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
| **appId** | **String**|  | |

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
| **204** | Приложение успешно удалено. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteProvider"></a>
# **deleteProvider**
> deleteProvider(providerId)

Отвязка vcs провайдера от аккаунта

Чтобы отвязать vcs провайдера от аккаунта, отправьте DELETE-запрос в &#x60;/api/v1/vcs-provider/{provider_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String providerId = "providerId_example"; // String | 
    try {
      apiInstance.deleteProvider(providerId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#deleteProvider");
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
| **providerId** | **String**|  | |

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
| **204** | Успешное удаление провайдера |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deployAction"></a>
# **deployAction**
> CreateDeploy201Response deployAction(appId, deployId)

Остановка деплоя приложения

Чтобы остановить деплой приложения, отправьте POST-запрос в &#x60;api/v1/apps/{app_id}/deploy/{deploy_id}/stop&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String appId = "appId_example"; // String | 
    String deployId = "deployId_example"; // String | 
    try {
      CreateDeploy201Response result = apiInstance.deployAction(appId, deployId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#deployAction");
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
| **appId** | **String**|  | |
| **deployId** | **String**|  | |

### Return type

[**CreateDeploy201Response**](CreateDeploy201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;deploy&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getApp"></a>
# **getApp**
> CreateApp201Response getApp(appId)

Получение приложения по id

Чтобы получить приложение по id, отправьте GET-запрос на &#x60;/api/v1/apps/{app_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String appId = "appId_example"; // String | 
    try {
      CreateApp201Response result = apiInstance.getApp(appId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#getApp");
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
| **appId** | **String**|  | |

### Return type

[**CreateApp201Response**](CreateApp201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;app&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAppDeploys"></a>
# **getAppDeploys**
> GetAppDeploys200Response getAppDeploys(appId, limit, offset)

Получение списка деплоев приложения

Чтобы получить список деплоев приложения, отправьте GET-запрос на &#x60;/api/v1/apps/{app_id}/deploys&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String appId = "appId_example"; // String | 
    Integer limit = 5; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    try {
      GetAppDeploys200Response result = apiInstance.getAppDeploys(appId, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#getAppDeploys");
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
| **appId** | **String**|  | |
| **limit** | **Integer**| Обозначает количество записей, которое необходимо вернуть. | [optional] [default to 5] |
| **offset** | **Integer**| Указывает на смещение относительно начала списка. | [optional] [default to 0] |

### Return type

[**GetAppDeploys200Response**](GetAppDeploys200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;deploys&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAppLogs"></a>
# **getAppLogs**
> GetAppLogs200Response getAppLogs(appId)

Получение логов приложения

Чтобы получить логи приложения, отправьте GET-запрос на &#x60;/api/v1/apps/{app_id}/logs&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String appId = "appId_example"; // String | 
    try {
      GetAppLogs200Response result = apiInstance.getAppLogs(appId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#getAppLogs");
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
| **appId** | **String**|  | |

### Return type

[**GetAppLogs200Response**](GetAppLogs200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;app_logs&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAppStatistics"></a>
# **getAppStatistics**
> GetServerStatistics200Response getAppStatistics(appId, dateFrom, dateTo)

Получение статистики приложения

Чтобы получить статистику сервера, отправьте GET-запрос на &#x60;/api/v1/apps/{app_id}/statistics&#x60;. Метод поддерживает только приложения &#x60;type: backend&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String appId = "appId_example"; // String | 
    String dateFrom = "dateFrom_example"; // String | Дата начала сбора статистики. Строка в формате ISO 8061, закодированная в ASCII, пример: `2023-05-25%202023-05-25T14%3A35%3A38`
    String dateTo = "dateTo_example"; // String | Дата окончания сбора статистики. Строка в формате ISO 8061, закодированная в ASCII, пример: `2023-05-26%202023-05-25T14%3A35%3A38`
    try {
      GetServerStatistics200Response result = apiInstance.getAppStatistics(appId, dateFrom, dateTo);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#getAppStatistics");
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
| **appId** | **String**|  | |
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

<a id="getApps"></a>
# **getApps**
> GetApps200Response getApps()

Получение списка приложений

Чтобы получить список приложений, отправьте GET-запрос на &#x60;/api/v1/apps&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    try {
      GetApps200Response result = apiInstance.getApps();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#getApps");
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

[**GetApps200Response**](GetApps200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;apps&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAppsPresets"></a>
# **getAppsPresets**
> AppsPresets getAppsPresets(appId)

Получение списка доступных тарифов для приложения

Чтобы получить список доступных тарифов, отправьте GET-запрос на &#x60;/api/v1/presets/apps&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String appId = "appId_example"; // String | 
    try {
      AppsPresets result = apiInstance.getAppsPresets(appId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#getAppsPresets");
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
| **appId** | **String**|  | |

### Return type

[**AppsPresets**](AppsPresets.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами  &#x60;backend_presets&#x60;, &#x60;frontend_presets&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getBranches"></a>
# **getBranches**
> GetBranches200Response getBranches(providerId, repositoryId)

Получение списка веток репозитория

Чтобы получить список веток репозитория, отправьте GET-запрос на &#x60;/api/v1/vcs-provider/{provider_id}/repository/{repository_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String providerId = "providerId_example"; // String | 
    String repositoryId = "repositoryId_example"; // String | 
    try {
      GetBranches200Response result = apiInstance.getBranches(providerId, repositoryId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#getBranches");
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
| **providerId** | **String**|  | |
| **repositoryId** | **String**|  | |

### Return type

[**GetBranches200Response**](GetBranches200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;branches&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getCommits"></a>
# **getCommits**
> GetCommits200Response getCommits(accountId, providerId, repositoryId, name)

Получение списка коммитов ветки репозитория

Чтобы получить список коммитов ветки репозитория, отправьте GET-запрос на &#x60;/api/v1/vcs-provider/{provider_id}/repository/{repository_id}/branch&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String accountId = "accountId_example"; // String | 
    String providerId = "providerId_example"; // String | 
    String repositoryId = "repositoryId_example"; // String | 
    String name = "name_example"; // String | Название ветки
    try {
      GetCommits200Response result = apiInstance.getCommits(accountId, providerId, repositoryId, name);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#getCommits");
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
| **accountId** | **String**|  | |
| **providerId** | **String**|  | |
| **repositoryId** | **String**|  | |
| **name** | **String**| Название ветки | |

### Return type

[**GetCommits200Response**](GetCommits200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;commits&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDeployLogs"></a>
# **getDeployLogs**
> GetDeployLogs200Response getDeployLogs(appId, deployId, debug)

Получение логов деплоя приложения

Чтобы получить информацию о деплое, отправьте GET-запрос на &#x60;/app/{app_id}/deploy/{deploy_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String appId = "appId_example"; // String | 
    String deployId = "deployId_example"; // String | 
    Boolean debug = true; // Boolean | Управляет выводом логов деплоя
    try {
      GetDeployLogs200Response result = apiInstance.getDeployLogs(appId, deployId, debug);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#getDeployLogs");
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
| **appId** | **String**|  | |
| **deployId** | **String**|  | |
| **debug** | **Boolean**| Управляет выводом логов деплоя | [optional] |

### Return type

[**GetDeployLogs200Response**](GetDeployLogs200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;deploy_logs&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDeploySettings"></a>
# **getDeploySettings**
> GetDeploySettings200Response getDeploySettings(appId)

Получение списка дефолтных настроек деплоя для приложения

Чтобы получить список настроек деплоя, отправьте GET-запрос на &#x60;/api/v1/deploy-settings/apps&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String appId = "appId_example"; // String | 
    try {
      GetDeploySettings200Response result = apiInstance.getDeploySettings(appId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#getDeploySettings");
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
| **appId** | **String**|  | |

### Return type

[**GetDeploySettings200Response**](GetDeploySettings200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;default_deploy_settings&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getFrameworks"></a>
# **getFrameworks**
> AvailableFrameworks getFrameworks(appId)

Получение списка доступных фреймворков для приложения

Чтобы получить список доступных фреймворков, отправьте GET-запрос на &#x60;/api/v1/frameworks/apps&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String appId = "appId_example"; // String | 
    try {
      AvailableFrameworks result = apiInstance.getFrameworks(appId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#getFrameworks");
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
| **appId** | **String**|  | |

### Return type

[**AvailableFrameworks**](AvailableFrameworks.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;backend_frameworks&#x60;, &#x60;frontend_frameworks&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getProviders"></a>
# **getProviders**
> GetProviders200Response getProviders()

Получение списка vcs провайдеров

Чтобы получить список vcs провайдеров, отправьте GET-запрос на &#x60;/api/v1/vcs-provider&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    try {
      GetProviders200Response result = apiInstance.getProviders();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#getProviders");
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

[**GetProviders200Response**](GetProviders200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;providers&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getRepositories"></a>
# **getRepositories**
> GetRepositories200Response getRepositories(providerId)

Получение списка репозиториев vcs провайдера

Чтобы получить список репозиториев vcs провайдера, отправьте GET-запрос на &#x60;/api/v1/vcs-provider/{provider_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String providerId = "providerId_example"; // String | 
    try {
      GetRepositories200Response result = apiInstance.getRepositories(providerId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#getRepositories");
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
| **providerId** | **String**|  | |

### Return type

[**GetRepositories200Response**](GetRepositories200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;repositories&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateAppSettings"></a>
# **updateAppSettings**
> UpdateAppSettings200Response updateAppSettings(appId, updeteSettings)

Изменение настроек приложения

Чтобы изменить настройки приложения отправьте PATCH-запрос в &#x60;/api/v1/apps/{app_id}&#x60;, задав необходимые атрибуты.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String appId = "appId_example"; // String | 
    UpdeteSettings updeteSettings = new UpdeteSettings(); // UpdeteSettings | 
    try {
      UpdateAppSettings200Response result = apiInstance.updateAppSettings(appId, updeteSettings);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#updateAppSettings");
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
| **appId** | **String**|  | |
| **updeteSettings** | [**UpdeteSettings**](UpdeteSettings.md)|  | |

### Return type

[**UpdateAppSettings200Response**](UpdateAppSettings200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;app&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateAppState"></a>
# **updateAppState**
> updateAppState(appId, action)

Изменение состояния приложения

Чтобы изменить состояние приложения отправьте PATCH-запрос в &#x60;/api/v1/apps/{app_id}/action/{action}&#x60;, задав необходимые атрибуты.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AppsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AppsApi apiInstance = new AppsApi(defaultClient);
    String appId = "appId_example"; // String | 
    String action = "reboot"; // String | 
    try {
      apiInstance.updateAppState(appId, action);
    } catch (ApiException e) {
      System.err.println("Exception when calling AppsApi#updateAppState");
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
| **appId** | **String**|  | |
| **action** | **String**|  | [enum: reboot, pause, resume] |

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
| **204** | Действие выполнено успешно. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

