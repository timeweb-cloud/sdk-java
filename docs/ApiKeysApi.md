# ApiKeysApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createToken**](ApiKeysApi.md#createToken) | **POST** /api/v1/auth/api-keys | Создание токена |
| [**deleteToken**](ApiKeysApi.md#deleteToken) | **DELETE** /api/v1/auth/api-keys/{token_id} | Удалить токен |
| [**getTokens**](ApiKeysApi.md#getTokens) | **GET** /api/v1/auth/api-keys | Получение списка выпущенных токенов |
| [**reissueToken**](ApiKeysApi.md#reissueToken) | **PUT** /api/v1/auth/api-keys/{token_id} | Перевыпустить токен |
| [**updateToken**](ApiKeysApi.md#updateToken) | **PATCH** /api/v1/auth/api-keys/{token_id} | Изменить токен |


<a id="createToken"></a>
# **createToken**
> CreateToken201Response createToken(createApiKey)

Создание токена

Чтобы создать токен, отправьте POST-запрос на &#x60;/api/v1/auth/api-keys&#x60;, задав необходимые атрибуты.  Токен будет создан с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданном токене.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ApiKeysApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ApiKeysApi apiInstance = new ApiKeysApi(defaultClient);
    CreateApiKey createApiKey = new CreateApiKey(); // CreateApiKey | 
    try {
      CreateToken201Response result = apiInstance.createToken(createApiKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApiKeysApi#createToken");
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
| **createApiKey** | [**CreateApiKey**](CreateApiKey.md)|  | |

### Return type

[**CreateToken201Response**](CreateToken201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ответ будет представлять собой объект JSON c ключом &#x60;api_key&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteToken"></a>
# **deleteToken**
> deleteToken(tokenId)

Удалить токен

Чтобы удалить токен, отправьте DELETE-запрос на &#x60;/api/v1/auth/api-keys/{token_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ApiKeysApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ApiKeysApi apiInstance = new ApiKeysApi(defaultClient);
    UUID tokenId = UUID.randomUUID(); // UUID | ID токена
    try {
      apiInstance.deleteToken(tokenId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApiKeysApi#deleteToken");
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
| **tokenId** | **UUID**| ID токена | |

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
| **204** | Токен успешно удален. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getTokens"></a>
# **getTokens**
> GetTokens200Response getTokens()

Получение списка выпущенных токенов

Чтобы получить список выпущенных токенов, отправьте GET-запрос на &#x60;/api/v1/auth/api-keys&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ApiKeysApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ApiKeysApi apiInstance = new ApiKeysApi(defaultClient);
    try {
      GetTokens200Response result = apiInstance.getTokens();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApiKeysApi#getTokens");
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

[**GetTokens200Response**](GetTokens200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;api_keys&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="reissueToken"></a>
# **reissueToken**
> CreateToken201Response reissueToken(tokenId, refreshApiKey)

Перевыпустить токен

Чтобы перевыпустить токен, отправьте PUT-запрос на &#x60;/api/v1/auth/api-keys/{token_id}&#x60;, задав необходимые атрибуты.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ApiKeysApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ApiKeysApi apiInstance = new ApiKeysApi(defaultClient);
    UUID tokenId = UUID.randomUUID(); // UUID | ID токена
    RefreshApiKey refreshApiKey = new RefreshApiKey(); // RefreshApiKey | 
    try {
      CreateToken201Response result = apiInstance.reissueToken(tokenId, refreshApiKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApiKeysApi#reissueToken");
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
| **tokenId** | **UUID**| ID токена | |
| **refreshApiKey** | [**RefreshApiKey**](RefreshApiKey.md)|  | |

### Return type

[**CreateToken201Response**](CreateToken201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;api_key&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateToken"></a>
# **updateToken**
> UpdateToken200Response updateToken(tokenId, editApiKey)

Изменить токен

Чтобы изменить токен, отправьте PATCH-запрос на &#x60;/api/v1/auth/api-keys/{token_id}&#x60;, задав необходимые атрибуты.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ApiKeysApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ApiKeysApi apiInstance = new ApiKeysApi(defaultClient);
    UUID tokenId = UUID.randomUUID(); // UUID | ID токена
    EditApiKey editApiKey = new EditApiKey(); // EditApiKey | 
    try {
      UpdateToken200Response result = apiInstance.updateToken(tokenId, editApiKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ApiKeysApi#updateToken");
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
| **tokenId** | **UUID**| ID токена | |
| **editApiKey** | [**EditApiKey**](EditApiKey.md)|  | |

### Return type

[**UpdateToken200Response**](UpdateToken200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;api_key&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

