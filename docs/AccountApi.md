# AccountApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addCountriesToAllowedList**](AccountApi.md#addCountriesToAllowedList) | **POST** /api/v1/auth/access/countries | Добавление стран в список разрешенных |
| [**addIPsToAllowedList**](AccountApi.md#addIPsToAllowedList) | **POST** /api/v1/auth/access/ips | Добавление IP-адресов в список разрешенных |
| [**deleteCountriesFromAllowedList**](AccountApi.md#deleteCountriesFromAllowedList) | **DELETE** /api/v1/auth/access/countries | Удаление стран из списка разрешенных |
| [**deleteIPsFromAllowedList**](AccountApi.md#deleteIPsFromAllowedList) | **DELETE** /api/v1/auth/access/ips | Удаление IP-адресов из списка разрешенных |
| [**getAccountStatus**](AccountApi.md#getAccountStatus) | **GET** /api/v1/account/status | Получение статуса аккаунта |
| [**getAuthAccessSettings**](AccountApi.md#getAuthAccessSettings) | **GET** /api/v1/auth/access | Получить информацию о ограничениях авторизации пользователя |
| [**getCountries**](AccountApi.md#getCountries) | **GET** /api/v1/auth/access/countries | Получение списка стран |
| [**getNotificationSettings**](AccountApi.md#getNotificationSettings) | **GET** /api/v1/account/notification-settings | Получение настроек уведомлений аккаунта |
| [**updateAuthRestrictionsByCountries**](AccountApi.md#updateAuthRestrictionsByCountries) | **POST** /api/v1/auth/access/countries/enabled | Включение/отключение ограничений по стране |
| [**updateAuthRestrictionsByIP**](AccountApi.md#updateAuthRestrictionsByIP) | **POST** /api/v1/auth/access/ips/enabled | Включение/отключение ограничений по IP-адресу |
| [**updateNotificationSettings**](AccountApi.md#updateNotificationSettings) | **PATCH** /api/v1/account/notification-settings | Изменение настроек уведомлений аккаунта |


<a id="addCountriesToAllowedList"></a>
# **addCountriesToAllowedList**
> AddCountriesToAllowedList201Response addCountriesToAllowedList(addCountriesToAllowedListRequest)

Добавление стран в список разрешенных

Чтобы добавить страны в список разрешенных, отправьте POST-запрос в &#x60;api/v1/access/countries&#x60;, передав в теле запроса параметр &#x60;countries&#x60; со списком стран.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AccountApi apiInstance = new AccountApi(defaultClient);
    AddCountriesToAllowedListRequest addCountriesToAllowedListRequest = new AddCountriesToAllowedListRequest(); // AddCountriesToAllowedListRequest | 
    try {
      AddCountriesToAllowedList201Response result = apiInstance.addCountriesToAllowedList(addCountriesToAllowedListRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountApi#addCountriesToAllowedList");
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
| **addCountriesToAllowedListRequest** | [**AddCountriesToAllowedListRequest**](AddCountriesToAllowedListRequest.md)|  | |

### Return type

[**AddCountriesToAllowedList201Response**](AddCountriesToAllowedList201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;countries&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="addIPsToAllowedList"></a>
# **addIPsToAllowedList**
> AddIPsToAllowedList201Response addIPsToAllowedList(addIPsToAllowedListRequest)

Добавление IP-адресов в список разрешенных

Чтобы добавить IP-адреса в список разрешенных, отправьте POST-запрос в &#x60;api/v1/access/ips&#x60;, передав в теле запроса параметр &#x60;ips&#x60; со списком IP-адресов.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AccountApi apiInstance = new AccountApi(defaultClient);
    AddIPsToAllowedListRequest addIPsToAllowedListRequest = new AddIPsToAllowedListRequest(); // AddIPsToAllowedListRequest | 
    try {
      AddIPsToAllowedList201Response result = apiInstance.addIPsToAllowedList(addIPsToAllowedListRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountApi#addIPsToAllowedList");
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
| **addIPsToAllowedListRequest** | [**AddIPsToAllowedListRequest**](AddIPsToAllowedListRequest.md)|  | |

### Return type

[**AddIPsToAllowedList201Response**](AddIPsToAllowedList201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;ips&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteCountriesFromAllowedList"></a>
# **deleteCountriesFromAllowedList**
> DeleteCountriesFromAllowedList200Response deleteCountriesFromAllowedList(deleteCountriesFromAllowedListRequest)

Удаление стран из списка разрешенных

Чтобы удалить страны из списка разрешенных, отправьте DELETE-запрос в &#x60;api/v1/access/countries&#x60;, передав в теле запроса параметр &#x60;countries&#x60; со списком стран.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AccountApi apiInstance = new AccountApi(defaultClient);
    DeleteCountriesFromAllowedListRequest deleteCountriesFromAllowedListRequest = new DeleteCountriesFromAllowedListRequest(); // DeleteCountriesFromAllowedListRequest | 
    try {
      DeleteCountriesFromAllowedList200Response result = apiInstance.deleteCountriesFromAllowedList(deleteCountriesFromAllowedListRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountApi#deleteCountriesFromAllowedList");
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
| **deleteCountriesFromAllowedListRequest** | [**DeleteCountriesFromAllowedListRequest**](DeleteCountriesFromAllowedListRequest.md)|  | |

### Return type

[**DeleteCountriesFromAllowedList200Response**](DeleteCountriesFromAllowedList200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;countries&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteIPsFromAllowedList"></a>
# **deleteIPsFromAllowedList**
> DeleteIPsFromAllowedList200Response deleteIPsFromAllowedList(deleteIPsFromAllowedListRequest)

Удаление IP-адресов из списка разрешенных

Чтобы удалить IP-адреса из списка разрешенных, отправьте DELETE-запрос в &#x60;api/v1/access/ips&#x60;, передав в теле запроса параметр &#x60;ips&#x60; со списком IP-адресов.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AccountApi apiInstance = new AccountApi(defaultClient);
    DeleteIPsFromAllowedListRequest deleteIPsFromAllowedListRequest = new DeleteIPsFromAllowedListRequest(); // DeleteIPsFromAllowedListRequest | 
    try {
      DeleteIPsFromAllowedList200Response result = apiInstance.deleteIPsFromAllowedList(deleteIPsFromAllowedListRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountApi#deleteIPsFromAllowedList");
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
| **deleteIPsFromAllowedListRequest** | [**DeleteIPsFromAllowedListRequest**](DeleteIPsFromAllowedListRequest.md)|  | |

### Return type

[**DeleteIPsFromAllowedList200Response**](DeleteIPsFromAllowedList200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;ips&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAccountStatus"></a>
# **getAccountStatus**
> GetAccountStatus200Response getAccountStatus()

Получение статуса аккаунта

Чтобы получить статус аккаунта, отправьте GET-запрос на &#x60;/api/v1/account/status&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AccountApi apiInstance = new AccountApi(defaultClient);
    try {
      GetAccountStatus200Response result = apiInstance.getAccountStatus();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountApi#getAccountStatus");
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

[**GetAccountStatus200Response**](GetAccountStatus200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;status&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAuthAccessSettings"></a>
# **getAuthAccessSettings**
> GetAuthAccessSettings200Response getAuthAccessSettings()

Получить информацию о ограничениях авторизации пользователя

Чтобы получить информацию о ограничениях авторизации пользователя, отправьте GET-запрос на &#x60;/api/v1/auth/access&#x60;.   Тело ответа будет представлять собой объект JSON с ключами &#x60;is_ip_restrictions_enabled&#x60;, &#x60;is_country_restrictions_enabled&#x60; и &#x60;white_list&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AccountApi apiInstance = new AccountApi(defaultClient);
    try {
      GetAuthAccessSettings200Response result = apiInstance.getAuthAccessSettings();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountApi#getAuthAccessSettings");
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

[**GetAuthAccessSettings200Response**](GetAuthAccessSettings200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;is_ip_restrictions_enabled&#x60;, &#x60;is_country_restrictions_enabled&#x60; и &#x60;white_list&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getCountries"></a>
# **getCountries**
> GetCountries200Response getCountries()

Получение списка стран

Чтобы получить список стран, отправьте GET-запрос на &#x60;/api/v1/auth/access/countries&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;countries&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AccountApi apiInstance = new AccountApi(defaultClient);
    try {
      GetCountries200Response result = apiInstance.getCountries();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountApi#getCountries");
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

[**GetCountries200Response**](GetCountries200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;countries&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getNotificationSettings"></a>
# **getNotificationSettings**
> GetNotificationSettings200Response getNotificationSettings()

Получение настроек уведомлений аккаунта

Чтобы получить статус аккаунта, отправьте GET запрос на &#x60;/api/v1/account/notification-settings&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AccountApi apiInstance = new AccountApi(defaultClient);
    try {
      GetNotificationSettings200Response result = apiInstance.getNotificationSettings();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountApi#getNotificationSettings");
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

[**GetNotificationSettings200Response**](GetNotificationSettings200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;notification_settings&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateAuthRestrictionsByCountries"></a>
# **updateAuthRestrictionsByCountries**
> updateAuthRestrictionsByCountries(updateAuthRestrictionsByCountriesRequest)

Включение/отключение ограничений по стране

Чтобы включить/отключить ограничения по стране, отправьте POST-запрос в &#x60;api/v1/access/countries/enabled&#x60;, передав в теле запроса параметр &#x60;is_enabled&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AccountApi apiInstance = new AccountApi(defaultClient);
    UpdateAuthRestrictionsByCountriesRequest updateAuthRestrictionsByCountriesRequest = new UpdateAuthRestrictionsByCountriesRequest(); // UpdateAuthRestrictionsByCountriesRequest | 
    try {
      apiInstance.updateAuthRestrictionsByCountries(updateAuthRestrictionsByCountriesRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountApi#updateAuthRestrictionsByCountries");
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
| **updateAuthRestrictionsByCountriesRequest** | [**UpdateAuthRestrictionsByCountriesRequest**](UpdateAuthRestrictionsByCountriesRequest.md)|  | |

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
| **204** | Ограничения по странам успешно изменены |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateAuthRestrictionsByIP"></a>
# **updateAuthRestrictionsByIP**
> updateAuthRestrictionsByIP(updateAuthRestrictionsByCountriesRequest)

Включение/отключение ограничений по IP-адресу

Чтобы включить/отключить ограничения по IP-адресу, отправьте POST-запрос в &#x60;api/v1/access/ips/enabled&#x60;, передав в теле запроса параметр &#x60;is_enabled&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AccountApi apiInstance = new AccountApi(defaultClient);
    UpdateAuthRestrictionsByCountriesRequest updateAuthRestrictionsByCountriesRequest = new UpdateAuthRestrictionsByCountriesRequest(); // UpdateAuthRestrictionsByCountriesRequest | 
    try {
      apiInstance.updateAuthRestrictionsByIP(updateAuthRestrictionsByCountriesRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountApi#updateAuthRestrictionsByIP");
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
| **updateAuthRestrictionsByCountriesRequest** | [**UpdateAuthRestrictionsByCountriesRequest**](UpdateAuthRestrictionsByCountriesRequest.md)|  | |

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
| **204** | Ограничения по IP-адресу успешно изменены |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateNotificationSettings"></a>
# **updateNotificationSettings**
> GetNotificationSettings200Response updateNotificationSettings(updateNotificationSettingsRequest)

Изменение настроек уведомлений аккаунта

Чтобы изменить настройки уведомлений аккаунта, отправьте PATCH запрос на &#x60;/api/v1/account/notification-settings&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AccountApi apiInstance = new AccountApi(defaultClient);
    UpdateNotificationSettingsRequest updateNotificationSettingsRequest = new UpdateNotificationSettingsRequest(); // UpdateNotificationSettingsRequest | 
    try {
      GetNotificationSettings200Response result = apiInstance.updateNotificationSettings(updateNotificationSettingsRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountApi#updateNotificationSettings");
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
| **updateNotificationSettingsRequest** | [**UpdateNotificationSettingsRequest**](UpdateNotificationSettingsRequest.md)|  | |

### Return type

[**GetNotificationSettings200Response**](GetNotificationSettings200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;notification_settings&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

