# SshApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addKeyToServer**](SshApi.md#addKeyToServer) | **POST** /api/v1/servers/{server_id}/ssh-keys | Добавление SSH-ключей на сервер |
| [**createKey**](SshApi.md#createKey) | **POST** /api/v1/ssh-keys | Создание SSH-ключа |
| [**deleteKey**](SshApi.md#deleteKey) | **DELETE** /api/v1/ssh-keys/{ssh_key_id} | Удаление SSH-ключа по уникальному идентификатору |
| [**deleteKeyFromServer**](SshApi.md#deleteKeyFromServer) | **DELETE** /api/v1/servers/{server_id}/ssh-keys/{ssh_key_id} | Удаление SSH-ключей с сервера |
| [**getKey**](SshApi.md#getKey) | **GET** /api/v1/ssh-keys/{ssh_key_id} | Получение SSH-ключа по уникальному идентификатору |
| [**getKeys**](SshApi.md#getKeys) | **GET** /api/v1/ssh-keys | Получение списка SSH-ключей |
| [**updateKey**](SshApi.md#updateKey) | **PATCH** /api/v1/ssh-keys/{ssh_key_id} | Изменение SSH-ключа по уникальному идентификатору |


<a id="addKeyToServer"></a>
# **addKeyToServer**
> addKeyToServer(serverId, addKeyToServerRequest)

Добавление SSH-ключей на сервер

Чтобы добавить SSH-ключи на сервер, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/ssh-keys&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SshApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    SshApi apiInstance = new SshApi(defaultClient);
    Integer serverId = 1051; // Integer | ID облачного сервера.
    AddKeyToServerRequest addKeyToServerRequest = new AddKeyToServerRequest(); // AddKeyToServerRequest | 
    try {
      apiInstance.addKeyToServer(serverId, addKeyToServerRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling SshApi#addKeyToServer");
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
| **serverId** | **Integer**| ID облачного сервера. | |
| **addKeyToServerRequest** | [**AddKeyToServerRequest**](AddKeyToServerRequest.md)|  | |

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
| **204** | Успешное добавление SSH-ключей на сервер |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createKey"></a>
# **createKey**
> CreateKey201Response createKey(createKeyRequest)

Создание SSH-ключа

Чтобы создать создать SSH-ключ, отправьте POST-запрос в &#x60;/api/v1/ssh-keys&#x60;, задав необходимые атрибуты.  

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SshApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    SshApi apiInstance = new SshApi(defaultClient);
    CreateKeyRequest createKeyRequest = new CreateKeyRequest(); // CreateKeyRequest | 
    try {
      CreateKey201Response result = apiInstance.createKey(createKeyRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SshApi#createKey");
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
| **createKeyRequest** | [**CreateKeyRequest**](CreateKeyRequest.md)|  | |

### Return type

[**CreateKey201Response**](CreateKey201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;ssh-key&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteKey"></a>
# **deleteKey**
> deleteKey(sshKeyId)

Удаление SSH-ключа по уникальному идентификатору

Чтобы удалить SSH-ключ, отправьте DELETE-запрос на &#x60;/api/v1/ssh-keys/{ssh_key_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SshApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    SshApi apiInstance = new SshApi(defaultClient);
    Integer sshKeyId = 1051; // Integer | ID SSH-ключа
    try {
      apiInstance.deleteKey(sshKeyId);
    } catch (ApiException e) {
      System.err.println("Exception when calling SshApi#deleteKey");
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
| **sshKeyId** | **Integer**| ID SSH-ключа | |

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
| **204** | Успешное удаление SSH-ключа |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteKeyFromServer"></a>
# **deleteKeyFromServer**
> deleteKeyFromServer(serverId, sshKeyId)

Удаление SSH-ключей с сервера

Чтобы удалить SSH-ключ с сервера, отправьте DELETE-запрос на &#x60;/api/v1/servers/{server_id}/ssh-keys/{ssh_key_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SshApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    SshApi apiInstance = new SshApi(defaultClient);
    Integer serverId = 1051; // Integer | ID облачного сервера.
    Integer sshKeyId = 1051; // Integer | ID SSH-ключа
    try {
      apiInstance.deleteKeyFromServer(serverId, sshKeyId);
    } catch (ApiException e) {
      System.err.println("Exception when calling SshApi#deleteKeyFromServer");
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
| **serverId** | **Integer**| ID облачного сервера. | |
| **sshKeyId** | **Integer**| ID SSH-ключа | |

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
| **204** | Успешное удаление SSH-ключа с сервера |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKey"></a>
# **getKey**
> GetKey200Response getKey(sshKeyId)

Получение SSH-ключа по уникальному идентификатору

Чтобы получить SSH-ключ, отправьте GET-запрос на &#x60;/api/v1/ssh-keys/{ssh_key_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SshApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    SshApi apiInstance = new SshApi(defaultClient);
    Integer sshKeyId = 1051; // Integer | ID SSH-ключа
    try {
      GetKey200Response result = apiInstance.getKey(sshKeyId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SshApi#getKey");
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
| **sshKeyId** | **Integer**| ID SSH-ключа | |

### Return type

[**GetKey200Response**](GetKey200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;ssh_key&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKeys"></a>
# **getKeys**
> GetKeys200Response getKeys()

Получение списка SSH-ключей

Чтобы получить список SSH-ключей, отправьте GET-запрос на &#x60;/api/v1/ssh-keys&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SshApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    SshApi apiInstance = new SshApi(defaultClient);
    try {
      GetKeys200Response result = apiInstance.getKeys();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SshApi#getKeys");
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

[**GetKeys200Response**](GetKeys200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;ssh_keys&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateKey"></a>
# **updateKey**
> GetKey200Response updateKey(sshKeyId, updateKeyRequest)

Изменение SSH-ключа по уникальному идентификатору

Чтобы изменить SSH-ключ, отправьте PATCH-запрос на &#x60;/api/v1/ssh-keys/{ssh_key_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SshApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    SshApi apiInstance = new SshApi(defaultClient);
    Integer sshKeyId = 1051; // Integer | ID SSH-ключа
    UpdateKeyRequest updateKeyRequest = new UpdateKeyRequest(); // UpdateKeyRequest | 
    try {
      GetKey200Response result = apiInstance.updateKey(sshKeyId, updateKeyRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SshApi#updateKey");
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
| **sshKeyId** | **Integer**| ID SSH-ключа | |
| **updateKeyRequest** | [**UpdateKeyRequest**](UpdateKeyRequest.md)|  | |

### Return type

[**GetKey200Response**](GetKey200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;ssh_key&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

