# SnapshotsApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**commitRestorePoint**](SnapshotsApi.md#commitRestorePoint) | **POST** /api/v1/restore-points/{vds_id}/commit | Фиксация снапшота |
| [**createRestorePoint**](SnapshotsApi.md#createRestorePoint) | **POST** /api/v1/restore-points/{vds_id}/create | Создание снапшота |
| [**getRestorePoint**](SnapshotsApi.md#getRestorePoint) | **GET** /api/v1/restore-points/{vds_id} | Получение снапшота сервера |
| [**getRestorePoints**](SnapshotsApi.md#getRestorePoints) | **GET** /api/v1/restore-points | Получение списка снапшотов |
| [**rollbackRestorePoint**](SnapshotsApi.md#rollbackRestorePoint) | **POST** /api/v1/restore-points/{vds_id}/rollback | Откат к снапшоту |


<a id="commitRestorePoint"></a>
# **commitRestorePoint**
> commitRestorePoint(vdsId)

Фиксация снапшота

Чтобы зафиксировать (применить) снапшот облачного сервера (VDS), отправьте POST-запрос на &#x60;/api/v1/restore-points/{vds_id}/commit&#x60;.  Фиксация подтверждает текущее состояние сервера. Действие выполняется асинхронно, ответ не содержит тела.  Для выполнения действия сервер должен быть включён, иначе вернётся ошибка &#x60;403&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SnapshotsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    SnapshotsApi apiInstance = new SnapshotsApi(defaultClient);
    Integer vdsId = 1051; // Integer | ID облачного сервера (VDS).
    try {
      apiInstance.commitRestorePoint(vdsId);
    } catch (ApiException e) {
      System.err.println("Exception when calling SnapshotsApi#commitRestorePoint");
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
| **vdsId** | **Integer**| ID облачного сервера (VDS). | |

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
| **204** | Действие над снапшотом запущено |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createRestorePoint"></a>
# **createRestorePoint**
> GetRestorePoint200Response createRestorePoint(vdsId)

Создание снапшота

Чтобы создать снапшот облачного сервера (VDS), отправьте POST-запрос на &#x60;/api/v1/restore-points/{vds_id}/create&#x60;.  Тело ответа будет содержать объект JSON с ключом &#x60;restore_point&#x60; и информацией о созданном снапшоте. Сразу после создания снапшот может находиться в статусе &#x60;creating&#x60;.  Для создания снапшота сервер должен быть включён, иначе вернётся ошибка &#x60;403&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SnapshotsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    SnapshotsApi apiInstance = new SnapshotsApi(defaultClient);
    Integer vdsId = 1051; // Integer | ID облачного сервера (VDS).
    try {
      GetRestorePoint200Response result = apiInstance.createRestorePoint(vdsId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SnapshotsApi#createRestorePoint");
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
| **vdsId** | **Integer**| ID облачного сервера (VDS). | |

### Return type

[**GetRestorePoint200Response**](GetRestorePoint200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;restore_point&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getRestorePoint"></a>
# **getRestorePoint**
> GetRestorePoint200Response getRestorePoint(vdsId)

Получение снапшота сервера

Чтобы получить снапшот облачного сервера (VDS), отправьте GET-запрос на &#x60;/api/v1/restore-points/{vds_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;restore_point&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SnapshotsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    SnapshotsApi apiInstance = new SnapshotsApi(defaultClient);
    Integer vdsId = 1051; // Integer | ID облачного сервера (VDS).
    try {
      GetRestorePoint200Response result = apiInstance.getRestorePoint(vdsId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SnapshotsApi#getRestorePoint");
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
| **vdsId** | **Integer**| ID облачного сервера (VDS). | |

### Return type

[**GetRestorePoint200Response**](GetRestorePoint200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;restore_point&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getRestorePoints"></a>
# **getRestorePoints**
> GetRestorePoints200Response getRestorePoints()

Получение списка снапшотов

Чтобы получить список снапшотов аккаунта, отправьте GET-запрос на &#x60;/api/v1/restore-points&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;restore_points&#x60;.  Снапшот — это снимок состояния облачного сервера (VDS), к которому можно вернуться. Каждому снапшоту соответствует один сервер.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SnapshotsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    SnapshotsApi apiInstance = new SnapshotsApi(defaultClient);
    try {
      GetRestorePoints200Response result = apiInstance.getRestorePoints();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SnapshotsApi#getRestorePoints");
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

[**GetRestorePoints200Response**](GetRestorePoints200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;restore_points&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="rollbackRestorePoint"></a>
# **rollbackRestorePoint**
> rollbackRestorePoint(vdsId)

Откат к снапшоту

Чтобы откатить облачный сервер (VDS) к снапшоту, отправьте POST-запрос на &#x60;/api/v1/restore-points/{vds_id}/rollback&#x60;.  Откат возвращает сервер к состоянию, сохранённому в снапшоте. Действие выполняется асинхронно, ответ не содержит тела.  Для выполнения действия сервер должен быть включён, иначе вернётся ошибка &#x60;403&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SnapshotsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    SnapshotsApi apiInstance = new SnapshotsApi(defaultClient);
    Integer vdsId = 1051; // Integer | ID облачного сервера (VDS).
    try {
      apiInstance.rollbackRestorePoint(vdsId);
    } catch (ApiException e) {
      System.err.println("Exception when calling SnapshotsApi#rollbackRestorePoint");
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
| **vdsId** | **Integer**| ID облачного сервера (VDS). | |

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
| **204** | Действие над снапшотом запущено |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

