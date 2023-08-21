# DedicatedServersApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createDedicatedServer**](DedicatedServersApi.md#createDedicatedServer) | **POST** /api/v1/dedicated-servers | Создание выделенного сервера |
| [**deleteDedicatedServer**](DedicatedServersApi.md#deleteDedicatedServer) | **DELETE** /api/v1/dedicated-servers/{dedicated_id} | Удаление выделенного сервера |
| [**getDedicatedServer**](DedicatedServersApi.md#getDedicatedServer) | **GET** /api/v1/dedicated-servers/{dedicated_id} | Получение выделенного сервера |
| [**getDedicatedServerPresetAdditionalServices**](DedicatedServersApi.md#getDedicatedServerPresetAdditionalServices) | **GET** /api/v1/presets/dedicated-servers/{preset_id}/additional-services | Получение дополнительных услуг для выделенного сервера |
| [**getDedicatedServers**](DedicatedServersApi.md#getDedicatedServers) | **GET** /api/v1/dedicated-servers | Получение списка выделенных серверов |
| [**getDedicatedServersPresets**](DedicatedServersApi.md#getDedicatedServersPresets) | **GET** /api/v1/presets/dedicated-servers | Получение списка тарифов для выделенного сервера |
| [**updateDedicatedServer**](DedicatedServersApi.md#updateDedicatedServer) | **PATCH** /api/v1/dedicated-servers/{dedicated_id} | Обновление выделенного сервера |


<a id="createDedicatedServer"></a>
# **createDedicatedServer**
> CreateDedicatedServer201Response createDedicatedServer(createDedicatedServer)

Создание выделенного сервера

Чтобы создать выделенный сервер, отправьте POST-запрос в &#x60;api/v1/dedicated-servers&#x60;, задав необходимые атрибуты.  Выделенный сервер будет создан с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданном выделенном сервере.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DedicatedServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DedicatedServersApi apiInstance = new DedicatedServersApi(defaultClient);
    CreateDedicatedServer createDedicatedServer = new CreateDedicatedServer(); // CreateDedicatedServer | 
    try {
      CreateDedicatedServer201Response result = apiInstance.createDedicatedServer(createDedicatedServer);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DedicatedServersApi#createDedicatedServer");
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
| **createDedicatedServer** | [**CreateDedicatedServer**](CreateDedicatedServer.md)|  | |

### Return type

[**CreateDedicatedServer201Response**](CreateDedicatedServer201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteDedicatedServer"></a>
# **deleteDedicatedServer**
> deleteDedicatedServer(dedicatedId)

Удаление выделенного сервера

Чтобы удалить выделенный сервер, отправьте запрос DELETE в &#x60;api/v1/dedicated-servers/{dedicated_id}&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DedicatedServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DedicatedServersApi apiInstance = new DedicatedServersApi(defaultClient);
    Integer dedicatedId = 1051; // Integer | Уникальный идентификатор выделенного сервера.
    try {
      apiInstance.deleteDedicatedServer(dedicatedId);
    } catch (ApiException e) {
      System.err.println("Exception when calling DedicatedServersApi#deleteDedicatedServer");
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
| **dedicatedId** | **Integer**| Уникальный идентификатор выделенного сервера. | |

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
| **204** | Выделенный сервер успешно удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDedicatedServer"></a>
# **getDedicatedServer**
> CreateDedicatedServer201Response getDedicatedServer(dedicatedId)

Получение выделенного сервера

Чтобы отобразить информацию об отдельном выделенном сервере, отправьте запрос GET на &#x60;api/v1/dedicated-servers/{dedicated_id}&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DedicatedServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DedicatedServersApi apiInstance = new DedicatedServersApi(defaultClient);
    Integer dedicatedId = 1051; // Integer | Уникальный идентификатор выделенного сервера.
    try {
      CreateDedicatedServer201Response result = apiInstance.getDedicatedServer(dedicatedId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DedicatedServersApi#getDedicatedServer");
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
| **dedicatedId** | **Integer**| Уникальный идентификатор выделенного сервера. | |

### Return type

[**CreateDedicatedServer201Response**](CreateDedicatedServer201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;dedicated_server&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDedicatedServerPresetAdditionalServices"></a>
# **getDedicatedServerPresetAdditionalServices**
> GetDedicatedServerPresetAdditionalServices200Response getDedicatedServerPresetAdditionalServices(presetId)

Получение дополнительных услуг для выделенного сервера

Чтобы получить список всех дополнительных услуг для выделенных серверов, отправьте GET-запрос на &#x60;/api/v1/presets/dedicated-servers/{preset_id}/additional-services&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DedicatedServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DedicatedServersApi apiInstance = new DedicatedServersApi(defaultClient);
    Integer presetId = 1051; // Integer | Уникальный идентификатор тарифа выделенного сервера.
    try {
      GetDedicatedServerPresetAdditionalServices200Response result = apiInstance.getDedicatedServerPresetAdditionalServices(presetId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DedicatedServersApi#getDedicatedServerPresetAdditionalServices");
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
| **presetId** | **Integer**| Уникальный идентификатор тарифа выделенного сервера. | |

### Return type

[**GetDedicatedServerPresetAdditionalServices200Response**](GetDedicatedServerPresetAdditionalServices200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключем &#x60;dedicated_server_additional_services&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDedicatedServers"></a>
# **getDedicatedServers**
> GetDedicatedServers200Response getDedicatedServers()

Получение списка выделенных серверов

Чтобы получить список всех выделенных серверов на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/dedicated-servers&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;dedicated_servers&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DedicatedServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DedicatedServersApi apiInstance = new DedicatedServersApi(defaultClient);
    try {
      GetDedicatedServers200Response result = apiInstance.getDedicatedServers();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DedicatedServersApi#getDedicatedServers");
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

[**GetDedicatedServers200Response**](GetDedicatedServers200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;dedicated_servers&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDedicatedServersPresets"></a>
# **getDedicatedServersPresets**
> GetDedicatedServersPresets200Response getDedicatedServersPresets(location)

Получение списка тарифов для выделенного сервера

Чтобы получить список всех тарифов выделенных серверов, отправьте GET-запрос на &#x60;/api/v1/presets/dedicated-servers&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DedicatedServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DedicatedServersApi apiInstance = new DedicatedServersApi(defaultClient);
    String location = "ru-1"; // String | Получение тарифов определенной локации.
    try {
      GetDedicatedServersPresets200Response result = apiInstance.getDedicatedServersPresets(location);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DedicatedServersApi#getDedicatedServersPresets");
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
| **location** | **String**| Получение тарифов определенной локации. | [optional] [enum: ru-1, ru-2, kz-1, pl-1] |

### Return type

[**GetDedicatedServersPresets200Response**](GetDedicatedServersPresets200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключем &#x60;dedicated_servers_presets&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateDedicatedServer"></a>
# **updateDedicatedServer**
> CreateDedicatedServer201Response updateDedicatedServer(dedicatedId, updateDedicatedServerRequest)

Обновление выделенного сервера

Чтобы обновить только определенные атрибуты выделенного сервера, отправьте запрос PATCH в &#x60;api/v1/dedicated-servers/{dedicated_id}&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DedicatedServersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DedicatedServersApi apiInstance = new DedicatedServersApi(defaultClient);
    Integer dedicatedId = 1051; // Integer | Уникальный идентификатор выделенного сервера.
    UpdateDedicatedServerRequest updateDedicatedServerRequest = new UpdateDedicatedServerRequest(); // UpdateDedicatedServerRequest | 
    try {
      CreateDedicatedServer201Response result = apiInstance.updateDedicatedServer(dedicatedId, updateDedicatedServerRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DedicatedServersApi#updateDedicatedServer");
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
| **dedicatedId** | **Integer**| Уникальный идентификатор выделенного сервера. | |
| **updateDedicatedServerRequest** | [**UpdateDedicatedServerRequest**](UpdateDedicatedServerRequest.md)|  | [optional] |

### Return type

[**CreateDedicatedServer201Response**](CreateDedicatedServer201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;dedicated_server&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

