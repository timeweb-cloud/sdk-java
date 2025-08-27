# ContainerRegistryApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createRegistry**](ContainerRegistryApi.md#createRegistry) | **POST** /api/v1/container-registry | Создание реестра |
| [**deleteRegistry**](ContainerRegistryApi.md#deleteRegistry) | **DELETE** /api/v1/container-registry/{registry_id} | Удаление реестра |
| [**getRegistries**](ContainerRegistryApi.md#getRegistries) | **GET** /api/v1/container-registry | Получение списка реестров контейнеров |
| [**getRegistry**](ContainerRegistryApi.md#getRegistry) | **GET** /api/v1/container-registry/{registry_id} | Получение информации о реестре |
| [**getRegistryPresets**](ContainerRegistryApi.md#getRegistryPresets) | **GET** /api/v1/container-registry/presets | Получение списка тарифов |
| [**getRegistryRepositories**](ContainerRegistryApi.md#getRegistryRepositories) | **GET** /api/v1/container-registry/{registry_id}/repositories | Получение списка репозиториев |
| [**updateRegistry**](ContainerRegistryApi.md#updateRegistry) | **PATCH** /api/v1/container-registry/{registry_id} | Обновление информации о реестре |


<a id="createRegistry"></a>
# **createRegistry**
> RegistryResponse createRegistry(registryIn)

Создание реестра

Чтобы создать реестр, отправьте POST-запрос на &#x60;/api/v1/container-registry&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ContainerRegistryApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ContainerRegistryApi apiInstance = new ContainerRegistryApi(defaultClient);
    RegistryIn registryIn = new RegistryIn(); // RegistryIn | 
    try {
      RegistryResponse result = apiInstance.createRegistry(registryIn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContainerRegistryApi#createRegistry");
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
| **registryIn** | [**RegistryIn**](RegistryIn.md)|  | |

### Return type

[**RegistryResponse**](RegistryResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Информация о реестре |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteRegistry"></a>
# **deleteRegistry**
> deleteRegistry(registryId)

Удаление реестра

Чтобы удалить реестр, отправьте DELETE-запрос в &#x60;/api/v1/container-registry/{registry_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ContainerRegistryApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ContainerRegistryApi apiInstance = new ContainerRegistryApi(defaultClient);
    Integer registryId = 56; // Integer | ID реестра
    try {
      apiInstance.deleteRegistry(registryId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContainerRegistryApi#deleteRegistry");
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
| **registryId** | **Integer**| ID реестра | |

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
| **204** | Реестр удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getRegistries"></a>
# **getRegistries**
> RegistriesResponse getRegistries()

Получение списка реестров контейнеров

Чтобы получить список реестров, отправьте GET-запрос на &#x60;/api/v1/container-registry&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ContainerRegistryApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ContainerRegistryApi apiInstance = new ContainerRegistryApi(defaultClient);
    try {
      RegistriesResponse result = apiInstance.getRegistries();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContainerRegistryApi#getRegistries");
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

[**RegistriesResponse**](RegistriesResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список реестров |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getRegistry"></a>
# **getRegistry**
> RegistryResponse getRegistry(registryId)

Получение информации о реестре

Чтобы получить информацию о реестре, отправьте GET-запрос в &#x60;/api/v1/container-registry/{registry_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ContainerRegistryApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ContainerRegistryApi apiInstance = new ContainerRegistryApi(defaultClient);
    Integer registryId = 56; // Integer | ID реестра
    try {
      RegistryResponse result = apiInstance.getRegistry(registryId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContainerRegistryApi#getRegistry");
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
| **registryId** | **Integer**| ID реестра | |

### Return type

[**RegistryResponse**](RegistryResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Информация о реестре |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getRegistryPresets"></a>
# **getRegistryPresets**
> SchemasPresetsResponse getRegistryPresets()

Получение списка тарифов

Чтобы получить список тарифов, отправьте GET-запрос в &#x60;/api/v1/container-registry/presets&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ContainerRegistryApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ContainerRegistryApi apiInstance = new ContainerRegistryApi(defaultClient);
    try {
      SchemasPresetsResponse result = apiInstance.getRegistryPresets();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContainerRegistryApi#getRegistryPresets");
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

[**SchemasPresetsResponse**](SchemasPresetsResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список тарифов |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getRegistryRepositories"></a>
# **getRegistryRepositories**
> RepositoriesResponse getRegistryRepositories(registryId)

Получение списка репозиториев

Чтобы получить список репозиториев, отправьте GET-запрос в &#x60;/api/v1/container-registry/{registry_id}/repositories&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ContainerRegistryApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ContainerRegistryApi apiInstance = new ContainerRegistryApi(defaultClient);
    Integer registryId = 56; // Integer | ID реестра
    try {
      RepositoriesResponse result = apiInstance.getRegistryRepositories(registryId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContainerRegistryApi#getRegistryRepositories");
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
| **registryId** | **Integer**| ID реестра | |

### Return type

[**RepositoriesResponse**](RepositoriesResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список репозиториев |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateRegistry"></a>
# **updateRegistry**
> RegistryResponse updateRegistry(registryId, registryEdit)

Обновление информации о реестре

Чтобы обновить информацию о реестре, отправьте PATCH-запрос в &#x60;/api/v1/container-registry/{registry_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ContainerRegistryApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ContainerRegistryApi apiInstance = new ContainerRegistryApi(defaultClient);
    Integer registryId = 56; // Integer | ID реестра
    RegistryEdit registryEdit = new RegistryEdit(); // RegistryEdit | 
    try {
      RegistryResponse result = apiInstance.updateRegistry(registryId, registryEdit);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContainerRegistryApi#updateRegistry");
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
| **registryId** | **Integer**| ID реестра | |
| **registryEdit** | [**RegistryEdit**](RegistryEdit.md)|  | |

### Return type

[**RegistryResponse**](RegistryResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Информация о реестре |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

