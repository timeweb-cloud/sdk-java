# FloatingIpApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**bindFloatingIp**](FloatingIpApi.md#bindFloatingIp) | **POST** /api/v1/floating-ips/{floating_ip_id}/bind | Привязать IP к сервису |
| [**createFloatingIp**](FloatingIpApi.md#createFloatingIp) | **POST** /api/v1/floating-ips | Создание плавающего IP |
| [**deleteFloatingIP**](FloatingIpApi.md#deleteFloatingIP) | **DELETE** /api/v1/floating-ips/{floating_ip_id} | Удаление плавающего IP по идентификатору |
| [**getFloatingIp**](FloatingIpApi.md#getFloatingIp) | **GET** /api/v1/floating-ips/{floating_ip_id} | Получение плавающего IP |
| [**getFloatingIps**](FloatingIpApi.md#getFloatingIps) | **GET** /api/v1/floating-ips | Получение списка плавающих IP |
| [**unbindFloatingIp**](FloatingIpApi.md#unbindFloatingIp) | **POST** /api/v1/floating-ips/{floating_ip_id}/unbind | Отвязать IP от сервиса |
| [**updateFloatingIP**](FloatingIpApi.md#updateFloatingIP) | **PATCH** /api/v1/floating-ips/{floating_ip_id} | Изменение плавающего IP по идентификатору |


<a id="bindFloatingIp"></a>
# **bindFloatingIp**
> bindFloatingIp(floatingIpId, bindFloatingIp)

Привязать IP к сервису

Чтобы привязать IP к сервису, отправьте POST-запрос на &#x60;/api/v1/floating-ip/{floating_ip_id}/bind&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FloatingIpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FloatingIpApi apiInstance = new FloatingIpApi(defaultClient);
    String floatingIpId = "87fa289f-1513-4c4d-8d49-5707f411f14b"; // String | Идентификатор плавающего IP
    BindFloatingIp bindFloatingIp = new BindFloatingIp(); // BindFloatingIp | 
    try {
      apiInstance.bindFloatingIp(floatingIpId, bindFloatingIp);
    } catch (ApiException e) {
      System.err.println("Exception when calling FloatingIpApi#bindFloatingIp");
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
| **floatingIpId** | **String**| Идентификатор плавающего IP | |
| **bindFloatingIp** | [**BindFloatingIp**](BindFloatingIp.md)|  | |

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
| **204** | Плавающий IP успешно привязан |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createFloatingIp"></a>
# **createFloatingIp**
> CreateFloatingIp201Response createFloatingIp(createFloatingIp)

Создание плавающего IP

Чтобы создать создать плавающий IP, отправьте POST-запрос в &#x60;/api/v1/floating-ips&#x60;, задав необходимые атрибуты.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FloatingIpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FloatingIpApi apiInstance = new FloatingIpApi(defaultClient);
    CreateFloatingIp createFloatingIp = new CreateFloatingIp(); // CreateFloatingIp | 
    try {
      CreateFloatingIp201Response result = apiInstance.createFloatingIp(createFloatingIp);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FloatingIpApi#createFloatingIp");
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
| **createFloatingIp** | [**CreateFloatingIp**](CreateFloatingIp.md)|  | |

### Return type

[**CreateFloatingIp201Response**](CreateFloatingIp201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;ip&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteFloatingIP"></a>
# **deleteFloatingIP**
> deleteFloatingIP(floatingIpId)

Удаление плавающего IP по идентификатору

Чтобы удалить плавающий IP, отправьте DELETE-запрос на &#x60;/api/v1/floating-ips/{floating_ip_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FloatingIpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FloatingIpApi apiInstance = new FloatingIpApi(defaultClient);
    String floatingIpId = "87fa289f-1513-4c4d-8d49-5707f411f14b"; // String | Идентификатор плавающего IP
    try {
      apiInstance.deleteFloatingIP(floatingIpId);
    } catch (ApiException e) {
      System.err.println("Exception when calling FloatingIpApi#deleteFloatingIP");
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
| **floatingIpId** | **String**| Идентификатор плавающего IP | |

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
| **204** | Плавающий IP успешно удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getFloatingIp"></a>
# **getFloatingIp**
> CreateFloatingIp201Response getFloatingIp(floatingIpId)

Получение плавающего IP

Чтобы отобразить информацию об отдельном плавающем IP, отправьте запрос GET на &#x60;api/v1/floating-ips/{floating_ip_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FloatingIpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FloatingIpApi apiInstance = new FloatingIpApi(defaultClient);
    String floatingIpId = "87fa289f-1513-4c4d-8d49-5707f411f14b"; // String | Идентификатор плавающего IP
    try {
      CreateFloatingIp201Response result = apiInstance.getFloatingIp(floatingIpId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FloatingIpApi#getFloatingIp");
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
| **floatingIpId** | **String**| Идентификатор плавающего IP | |

### Return type

[**CreateFloatingIp201Response**](CreateFloatingIp201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;ip&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getFloatingIps"></a>
# **getFloatingIps**
> GetFloatingIps200Response getFloatingIps()

Получение списка плавающих IP

Чтобы получить список плавающих IP, отправьте GET-запрос на &#x60;/api/v1/floating-ips&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FloatingIpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FloatingIpApi apiInstance = new FloatingIpApi(defaultClient);
    try {
      GetFloatingIps200Response result = apiInstance.getFloatingIps();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FloatingIpApi#getFloatingIps");
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

[**GetFloatingIps200Response**](GetFloatingIps200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;ips&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="unbindFloatingIp"></a>
# **unbindFloatingIp**
> unbindFloatingIp(floatingIpId)

Отвязать IP от сервиса

Чтобы отвязать IP от сервиса, отправьте POST-запрос на &#x60;/api/v1/floating-ip/{floating_ip_id}/unbind&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FloatingIpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FloatingIpApi apiInstance = new FloatingIpApi(defaultClient);
    String floatingIpId = "87fa289f-1513-4c4d-8d49-5707f411f14b"; // String | Идентификатор плавающего IP
    try {
      apiInstance.unbindFloatingIp(floatingIpId);
    } catch (ApiException e) {
      System.err.println("Exception when calling FloatingIpApi#unbindFloatingIp");
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
| **floatingIpId** | **String**| Идентификатор плавающего IP | |

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
| **204** | Плавающий IP успешно отвязан |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateFloatingIP"></a>
# **updateFloatingIP**
> CreateFloatingIp201Response updateFloatingIP(floatingIpId, updateFloatingIp)

Изменение плавающего IP по идентификатору

Чтобы изменить плавающий IP, отправьте PATCH-запрос на &#x60;/api/v1/floating-ips/{floating_ip_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FloatingIpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FloatingIpApi apiInstance = new FloatingIpApi(defaultClient);
    String floatingIpId = "87fa289f-1513-4c4d-8d49-5707f411f14b"; // String | Идентификатор плавающего IP
    UpdateFloatingIp updateFloatingIp = new UpdateFloatingIp(); // UpdateFloatingIp | 
    try {
      CreateFloatingIp201Response result = apiInstance.updateFloatingIP(floatingIpId, updateFloatingIp);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FloatingIpApi#updateFloatingIP");
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
| **floatingIpId** | **String**| Идентификатор плавающего IP | |
| **updateFloatingIp** | [**UpdateFloatingIp**](UpdateFloatingIp.md)|  | |

### Return type

[**CreateFloatingIp201Response**](CreateFloatingIp201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;ip&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

