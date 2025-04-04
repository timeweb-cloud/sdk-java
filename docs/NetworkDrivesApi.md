# NetworkDrivesApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNetworkDrive**](NetworkDrivesApi.md#createNetworkDrive) | **POST** /api/v1/network-drives | Создание сетевого диска |
| [**deleteNetworkDrive**](NetworkDrivesApi.md#deleteNetworkDrive) | **DELETE** /api/v1/network-drives/{network_drive_id} | Удаление сетевого диска по идентификатору |
| [**getNetworkDrive**](NetworkDrivesApi.md#getNetworkDrive) | **GET** /api/v1/network-drives/{network_drive_id} | Получение сетевого диска |
| [**getNetworkDrives**](NetworkDrivesApi.md#getNetworkDrives) | **GET** /api/v1/network-drives | Получение списка cетевых дисков |
| [**getNetworkDrivesAvailableResources**](NetworkDrivesApi.md#getNetworkDrivesAvailableResources) | **GET** /api/v1/network-drives/available-resources | Получение списка сервисов доступных для подключения диска |
| [**getNetworkDrivesPresets**](NetworkDrivesApi.md#getNetworkDrivesPresets) | **GET** /api/v1/presets/network-drives | Получение списка доступных тарифов для сетевого диска |
| [**mountNetworkDrive**](NetworkDrivesApi.md#mountNetworkDrive) | **POST** /api/v1/network-drives/{network_drive_id}/mount | Подключить сетевой диск к сервису |
| [**unmountNetworkDrive**](NetworkDrivesApi.md#unmountNetworkDrive) | **POST** /api/v1/network-drives/{network_drive_id}/unmount | Отключить сетевой диск от сервиса |
| [**updateNetworkDrive**](NetworkDrivesApi.md#updateNetworkDrive) | **PATCH** /api/v1/network-drives/{network_drive_id} | Изменение сетевого диска по ID |


<a id="createNetworkDrive"></a>
# **createNetworkDrive**
> CreateNetworkDrive201Response createNetworkDrive(createNetworkDrive)

Создание сетевого диска

Чтобы создать создать сетевой диск, отправьте POST-запрос в &#x60;/api/v1/network-drives&#x60;, задав необходимые атрибуты.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.NetworkDrivesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    NetworkDrivesApi apiInstance = new NetworkDrivesApi(defaultClient);
    CreateNetworkDrive createNetworkDrive = new CreateNetworkDrive(); // CreateNetworkDrive | 
    try {
      CreateNetworkDrive201Response result = apiInstance.createNetworkDrive(createNetworkDrive);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling NetworkDrivesApi#createNetworkDrive");
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
| **createNetworkDrive** | [**CreateNetworkDrive**](CreateNetworkDrive.md)|  | |

### Return type

[**CreateNetworkDrive201Response**](CreateNetworkDrive201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;network_drive&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteNetworkDrive"></a>
# **deleteNetworkDrive**
> deleteNetworkDrive(networkDriveId)

Удаление сетевого диска по идентификатору

Чтобы удалить сетевой диск, отправьте DELETE-запрос на &#x60;/api/v1/network-drives/{network_drive_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.NetworkDrivesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    NetworkDrivesApi apiInstance = new NetworkDrivesApi(defaultClient);
    String networkDriveId = "87fa289f-1513-4c4d-8d49-5707f411f14b"; // String | ID сетевого диска
    try {
      apiInstance.deleteNetworkDrive(networkDriveId);
    } catch (ApiException e) {
      System.err.println("Exception when calling NetworkDrivesApi#deleteNetworkDrive");
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
| **networkDriveId** | **String**| ID сетевого диска | |

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
| **204** | Сетевой диск успешно удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getNetworkDrive"></a>
# **getNetworkDrive**
> CreateNetworkDrive201Response getNetworkDrive(networkDriveId)

Получение сетевого диска

Чтобы отобразить информацию об отдельном сетевом диске, отправьте запрос GET на &#x60;api/v1/network-drives/{network_drive_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.NetworkDrivesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    NetworkDrivesApi apiInstance = new NetworkDrivesApi(defaultClient);
    String networkDriveId = "87fa289f-1513-4c4d-8d49-5707f411f14b"; // String | ID сетевого диска
    try {
      CreateNetworkDrive201Response result = apiInstance.getNetworkDrive(networkDriveId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling NetworkDrivesApi#getNetworkDrive");
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
| **networkDriveId** | **String**| ID сетевого диска | |

### Return type

[**CreateNetworkDrive201Response**](CreateNetworkDrive201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;network_drive&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getNetworkDrives"></a>
# **getNetworkDrives**
> GetNetworkDrives200Response getNetworkDrives()

Получение списка cетевых дисков

Чтобы получить список сетевых дисков, отправьте GET-запрос на &#x60;/api/v1/network-drives&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.NetworkDrivesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    NetworkDrivesApi apiInstance = new NetworkDrivesApi(defaultClient);
    try {
      GetNetworkDrives200Response result = apiInstance.getNetworkDrives();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling NetworkDrivesApi#getNetworkDrives");
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

[**GetNetworkDrives200Response**](GetNetworkDrives200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;network_drives&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getNetworkDrivesAvailableResources"></a>
# **getNetworkDrivesAvailableResources**
> GetNetworkDrivesAvailableResources200Response getNetworkDrivesAvailableResources()

Получение списка сервисов доступных для подключения диска

Чтобы получить список сервисов, отправьте GET-запрос на &#x60;/api/v1/network-drives/available-resources&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.NetworkDrivesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    NetworkDrivesApi apiInstance = new NetworkDrivesApi(defaultClient);
    try {
      GetNetworkDrivesAvailableResources200Response result = apiInstance.getNetworkDrivesAvailableResources();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling NetworkDrivesApi#getNetworkDrivesAvailableResources");
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

[**GetNetworkDrivesAvailableResources200Response**](GetNetworkDrivesAvailableResources200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;available_resources&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getNetworkDrivesPresets"></a>
# **getNetworkDrivesPresets**
> GetNetworkDrivesPresets200Response getNetworkDrivesPresets()

Получение списка доступных тарифов для сетевого диска

Чтобы получить список тарифов, отправьте GET-запрос на &#x60;/api/v1/presets/network-drives&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.NetworkDrivesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    NetworkDrivesApi apiInstance = new NetworkDrivesApi(defaultClient);
    try {
      GetNetworkDrivesPresets200Response result = apiInstance.getNetworkDrivesPresets();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling NetworkDrivesApi#getNetworkDrivesPresets");
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

[**GetNetworkDrivesPresets200Response**](GetNetworkDrivesPresets200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;network_drive_presets&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="mountNetworkDrive"></a>
# **mountNetworkDrive**
> mountNetworkDrive(networkDriveId, mountNetworkDrive)

Подключить сетевой диск к сервису

Чтобы подключить сетевой диск к сервису, отправьте POST-запрос на &#x60;/api/v1/network-drives/{network_drive_id}/mount&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.NetworkDrivesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    NetworkDrivesApi apiInstance = new NetworkDrivesApi(defaultClient);
    String networkDriveId = "87fa289f-1513-4c4d-8d49-5707f411f14b"; // String | ID сетевого диска
    MountNetworkDrive mountNetworkDrive = new MountNetworkDrive(); // MountNetworkDrive | 
    try {
      apiInstance.mountNetworkDrive(networkDriveId, mountNetworkDrive);
    } catch (ApiException e) {
      System.err.println("Exception when calling NetworkDrivesApi#mountNetworkDrive");
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
| **networkDriveId** | **String**| ID сетевого диска | |
| **mountNetworkDrive** | [**MountNetworkDrive**](MountNetworkDrive.md)|  | |

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
| **201** | Сетевой диск успешно подключен |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="unmountNetworkDrive"></a>
# **unmountNetworkDrive**
> unmountNetworkDrive(networkDriveId)

Отключить сетевой диск от сервиса

Чтобы отключить сетевой диск от сервиса, отправьте POST-запрос на &#x60;/api/v1/network-drives/{network_drive_id}/unmount&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.NetworkDrivesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    NetworkDrivesApi apiInstance = new NetworkDrivesApi(defaultClient);
    String networkDriveId = "87fa289f-1513-4c4d-8d49-5707f411f14b"; // String | ID сетевого диска
    try {
      apiInstance.unmountNetworkDrive(networkDriveId);
    } catch (ApiException e) {
      System.err.println("Exception when calling NetworkDrivesApi#unmountNetworkDrive");
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
| **networkDriveId** | **String**| ID сетевого диска | |

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
| **201** | Сетевой диск успешно отключен |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateNetworkDrive"></a>
# **updateNetworkDrive**
> CreateNetworkDrive201Response updateNetworkDrive(networkDriveId, updateNetworkDrive)

Изменение сетевого диска по ID

Чтобы изменить сетевой диск, отправьте PATCH-запрос на &#x60;/api/v1/network-drives/{network_drive_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.NetworkDrivesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    NetworkDrivesApi apiInstance = new NetworkDrivesApi(defaultClient);
    String networkDriveId = "87fa289f-1513-4c4d-8d49-5707f411f14b"; // String | ID сетевого диска
    UpdateNetworkDrive updateNetworkDrive = new UpdateNetworkDrive(); // UpdateNetworkDrive | 
    try {
      CreateNetworkDrive201Response result = apiInstance.updateNetworkDrive(networkDriveId, updateNetworkDrive);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling NetworkDrivesApi#updateNetworkDrive");
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
| **networkDriveId** | **String**| ID сетевого диска | |
| **updateNetworkDrive** | [**UpdateNetworkDrive**](UpdateNetworkDrive.md)|  | |

### Return type

[**CreateNetworkDrive201Response**](CreateNetworkDrive201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;network_drive&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

