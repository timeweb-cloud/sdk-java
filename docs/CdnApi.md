# CdnApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addCdnCertificate**](CdnApi.md#addCdnCertificate) | **POST** /api/v1/cdn/certificates | Загрузка собственного сертификата CDN |
| [**archiveCdnCertificateTask**](CdnApi.md#archiveCdnCertificateTask) | **POST** /api/v1/cdn/certificates/tasks/{task_id}/archive | Архивация задачи на выпуск сертификата |
| [**clearCdnResourceCache**](CdnApi.md#clearCdnResourceCache) | **POST** /api/v1/cdn/http-resources/{resource_id}/clear-cache | Очистка кэша CDN-ресурса |
| [**createCdnResource**](CdnApi.md#createCdnResource) | **POST** /api/v1/cdn/http-resources | Создание CDN-ресурса |
| [**deleteCdnCertificate**](CdnApi.md#deleteCdnCertificate) | **DELETE** /api/v1/cdn/certificates/{certificate_id} | Удаление сертификата CDN |
| [**deleteCdnResource**](CdnApi.md#deleteCdnResource) | **DELETE** /api/v1/cdn/http-resources/{resource_id} | Удаление CDN-ресурса |
| [**getCdnCertificateTasks**](CdnApi.md#getCdnCertificateTasks) | **GET** /api/v1/cdn/certificates/tasks | Получение списка задач на выпуск сертификатов |
| [**getCdnCertificates**](CdnApi.md#getCdnCertificates) | **GET** /api/v1/cdn/certificates | Получение списка сертификатов CDN |
| [**getCdnOriginNodes**](CdnApi.md#getCdnOriginNodes) | **GET** /api/v1/cdn/nodes/origin | Получение списка подсетей узлов CDN |
| [**getCdnPresets**](CdnApi.md#getCdnPresets) | **GET** /api/v1/cdn/presets | Получение списка тарифов CDN |
| [**getCdnResource**](CdnApi.md#getCdnResource) | **GET** /api/v1/cdn/http-resources/{resource_id} | Получение CDN-ресурса |
| [**getCdnResourceConfiguration**](CdnApi.md#getCdnResourceConfiguration) | **GET** /api/v1/cdn/http-resources/{resource_id}/configuration | Получение конфигурации CDN-ресурса |
| [**getCdnResourceNodes**](CdnApi.md#getCdnResourceNodes) | **GET** /api/v1/cdn/nodes/http-resources/{resource_id} | Получение списка раздающих узлов CDN-ресурса |
| [**getCdnResourceStatistics**](CdnApi.md#getCdnResourceStatistics) | **GET** /api/v1/cdn/http-resources/{resource_id}/statistics | Получение статистики CDN-ресурса |
| [**getCdnResources**](CdnApi.md#getCdnResources) | **GET** /api/v1/cdn/http-resources | Получение списка CDN-ресурсов |
| [**issueCdnCertificate**](CdnApi.md#issueCdnCertificate) | **POST** /api/v1/cdn/certificates/issue | Выпуск сертификата Let&#39;s Encrypt для CDN-ресурса |
| [**preloadCdnResourceCache**](CdnApi.md#preloadCdnResourceCache) | **POST** /api/v1/cdn/http-resources/{resource_id}/preload-cache | Предварительная загрузка кэша CDN-ресурса |
| [**resumeCdnResource**](CdnApi.md#resumeCdnResource) | **POST** /api/v1/cdn/http-resources/{resource_id}/resume | Возобновление раздачи CDN-ресурса |
| [**suspendCdnResource**](CdnApi.md#suspendCdnResource) | **POST** /api/v1/cdn/http-resources/{resource_id}/suspend | Приостановка раздачи CDN-ресурса |
| [**updateCdnResource**](CdnApi.md#updateCdnResource) | **PATCH** /api/v1/cdn/http-resources/{resource_id} | Изменение CDN-ресурса |


<a id="addCdnCertificate"></a>
# **addCdnCertificate**
> addCdnCertificate(addCertificate)

Загрузка собственного сертификата CDN

Чтобы загрузить собственный SSL-сертификат, отправьте POST-запрос на &#x60;/api/v1/cdn/certificates&#x60;.  После загрузки сертификат появится в списке &#x60;/api/v1/cdn/certificates&#x60; — привязать его к ресурсу можно, передав его ID в поле &#x60;config.security.certificate_id&#x60; PATCH-запроса на &#x60;/api/v1/cdn/http-resources/{resource_id}&#x60;.  Если сертификат или приватный ключ не проходят проверку — например, истек срок действия или ключ не соответствует сертификату — вернется ошибка &#x60;422&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    AddCertificate addCertificate = new AddCertificate(); // AddCertificate | 
    try {
      apiInstance.addCdnCertificate(addCertificate);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#addCdnCertificate");
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
| **addCertificate** | [**AddCertificate**](AddCertificate.md)|  | |

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
| **204** | Сертификат успешно добавлен |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **422** | Не удалось обработать сертификат |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="archiveCdnCertificateTask"></a>
# **archiveCdnCertificateTask**
> archiveCdnCertificateTask(taskId)

Архивация задачи на выпуск сертификата

Чтобы убрать из списка задачу на выпуск сертификата, отправьте POST-запрос на &#x60;/api/v1/cdn/certificates/tasks/{task_id}/archive&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer taskId = 42; // Integer | ID задачи на выпуск сертификата
    try {
      apiInstance.archiveCdnCertificateTask(taskId);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#archiveCdnCertificateTask");
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
| **taskId** | **Integer**| ID задачи на выпуск сертификата | |

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
| **204** | Задача на выпуск сертификата успешно архивирована |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="clearCdnResourceCache"></a>
# **clearCdnResourceCache**
> clearCdnResourceCache(resourceId, clearCache)

Очистка кэша CDN-ресурса

Чтобы очистить кэш на узлах CDN, отправьте POST-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}/clear-cache&#x60;.  При &#x60;purge_type&#x60; &#x3D; &#x60;full&#x60; очищается весь кэш ресурса, при &#x60;purge_type&#x60; &#x3D; &#x60;partial&#x60; — только файлы из списка &#x60;paths&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer resourceId = 1234; // Integer | ID CDN-ресурса
    ClearCache clearCache = new ClearCache(); // ClearCache | 
    try {
      apiInstance.clearCdnResourceCache(resourceId, clearCache);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#clearCdnResourceCache");
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
| **resourceId** | **Integer**| ID CDN-ресурса | |
| **clearCache** | [**ClearCache**](ClearCache.md)|  | |

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
| **204** | Запрос на очистку кэша принят |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createCdnResource"></a>
# **createCdnResource**
> CreateCdnResource201Response createCdnResource(createHttpResource)

Создание CDN-ресурса

Чтобы создать CDN-ресурс, отправьте POST-запрос на &#x60;/api/v1/cdn/http-resources&#x60;.  Источник контента задается ровно одним из полей: &#x60;storage_id&#x60; для S3-хранилища или &#x60;server&#x60; для произвольного origin-сервера. Если ни одно из них не передано, вернется ошибка &#x60;400&#x60;.  Сразу после создания ресурсу выдается технический домен &#x60;cdn_domain&#x60;, а сам ресурс какое-то время находится в статусе &#x60;processing&#x60;, пока конфигурация применяется на узлах CDN.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    CreateHttpResource createHttpResource = new CreateHttpResource(); // CreateHttpResource | 
    try {
      CreateCdnResource201Response result = apiInstance.createCdnResource(createHttpResource);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#createCdnResource");
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
| **createHttpResource** | [**CreateHttpResource**](CreateHttpResource.md)|  | |

### Return type

[**CreateCdnResource201Response**](CreateCdnResource201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;http_resource&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteCdnCertificate"></a>
# **deleteCdnCertificate**
> deleteCdnCertificate(certificateId)

Удаление сертификата CDN

Чтобы удалить SSL-сертификат, отправьте DELETE-запрос на &#x60;/api/v1/cdn/certificates/{certificate_id}&#x60;.  Если сертификат привязан к CDN-ресурсу, вернется ошибка &#x60;409&#x60; — сначала отвяжите его, передав &#x60;config.security.certificate_id&#x60; &#x3D; &#x60;null&#x60; в PATCH-запросе на &#x60;/api/v1/cdn/http-resources/{resource_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer certificateId = 5678; // Integer | ID сертификата
    try {
      apiInstance.deleteCdnCertificate(certificateId);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#deleteCdnCertificate");
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
| **certificateId** | **Integer**| ID сертификата | |

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
| **204** | Сертификат успешно удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteCdnResource"></a>
# **deleteCdnResource**
> deleteCdnResource(resourceId)

Удаление CDN-ресурса

Чтобы удалить CDN-ресурс, отправьте DELETE-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}&#x60;. Вместе с ресурсом освобождается его технический домен, а привязанный сертификат отвязывается.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer resourceId = 1234; // Integer | ID CDN-ресурса
    try {
      apiInstance.deleteCdnResource(resourceId);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#deleteCdnResource");
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
| **resourceId** | **Integer**| ID CDN-ресурса | |

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
| **204** | CDN-ресурс успешно удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getCdnCertificateTasks"></a>
# **getCdnCertificateTasks**
> GetCdnCertificateTasks200Response getCdnCertificateTasks(resourceId)

Получение списка задач на выпуск сертификатов

Чтобы получить список задач на выпуск сертификатов Let&#39;s Encrypt, отправьте GET-запрос на &#x60;/api/v1/cdn/certificates/tasks&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer resourceId = 1234; // Integer | Оставить в выдаче только задачи указанного CDN-ресурса.
    try {
      GetCdnCertificateTasks200Response result = apiInstance.getCdnCertificateTasks(resourceId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#getCdnCertificateTasks");
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
| **resourceId** | **Integer**| Оставить в выдаче только задачи указанного CDN-ресурса. | [optional] |

### Return type

[**GetCdnCertificateTasks200Response**](GetCdnCertificateTasks200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;certificate_tasks&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getCdnCertificates"></a>
# **getCdnCertificates**
> GetCdnCertificates200Response getCdnCertificates(resourceId)

Получение списка сертификатов CDN

Чтобы получить список SSL-сертификатов, доступных для доменов CDN-ресурсов, отправьте GET-запрос на &#x60;/api/v1/cdn/certificates&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer resourceId = 1234; // Integer | Оставить в выдаче только сертификаты, подходящие для доменов указанного CDN-ресурса.
    try {
      GetCdnCertificates200Response result = apiInstance.getCdnCertificates(resourceId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#getCdnCertificates");
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
| **resourceId** | **Integer**| Оставить в выдаче только сертификаты, подходящие для доменов указанного CDN-ресурса. | [optional] |

### Return type

[**GetCdnCertificates200Response**](GetCdnCertificates200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;certificates&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getCdnOriginNodes"></a>
# **getCdnOriginNodes**
> GetCdnOriginNodes200Response getCdnOriginNodes(withExtraZones)

Получение списка подсетей узлов CDN

Чтобы получить список IP-адресов и подсетей, с которых узлы CDN обращаются к источнику контента, отправьте GET-запрос на &#x60;/api/v1/cdn/nodes/origin&#x60;. Этот список удобно использовать, чтобы разрешить доступ к origin-серверу только для узлов CDN.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Boolean withExtraZones = false; // Boolean | Добавить в выдачу узлы дополнительных зон раздачи.
    try {
      GetCdnOriginNodes200Response result = apiInstance.getCdnOriginNodes(withExtraZones);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#getCdnOriginNodes");
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
| **withExtraZones** | **Boolean**| Добавить в выдачу узлы дополнительных зон раздачи. | [optional] [default to false] |

### Return type

[**GetCdnOriginNodes200Response**](GetCdnOriginNodes200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;origin_nodes&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getCdnPresets"></a>
# **getCdnPresets**
> GetCdnPresets200Response getCdnPresets()

Получение списка тарифов CDN

Чтобы получить список доступных тарифов CDN, отправьте GET-запрос на &#x60;/api/v1/cdn/presets&#x60;. ID тарифа из этого списка указывается в поле &#x60;preset_id&#x60; при создании и изменении ресурса.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    try {
      GetCdnPresets200Response result = apiInstance.getCdnPresets();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#getCdnPresets");
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

[**GetCdnPresets200Response**](GetCdnPresets200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;http_resource_presets&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getCdnResource"></a>
# **getCdnResource**
> CreateCdnResource201Response getCdnResource(resourceId)

Получение CDN-ресурса

Чтобы получить информацию об отдельном CDN-ресурсе, отправьте GET-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer resourceId = 1234; // Integer | ID CDN-ресурса
    try {
      CreateCdnResource201Response result = apiInstance.getCdnResource(resourceId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#getCdnResource");
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
| **resourceId** | **Integer**| ID CDN-ресурса | |

### Return type

[**CreateCdnResource201Response**](CreateCdnResource201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;http_resource&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getCdnResourceConfiguration"></a>
# **getCdnResourceConfiguration**
> GetCdnResourceConfiguration200Response getCdnResourceConfiguration(resourceId)

Получение конфигурации CDN-ресурса

Чтобы получить текущую конфигурацию CDN-ресурса, отправьте GET-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}/configuration&#x60;.  Изменить конфигурацию можно в поле &#x60;config&#x60; PATCH-запроса на &#x60;/api/v1/cdn/http-resources/{resource_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer resourceId = 1234; // Integer | ID CDN-ресурса
    try {
      GetCdnResourceConfiguration200Response result = apiInstance.getCdnResourceConfiguration(resourceId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#getCdnResourceConfiguration");
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
| **resourceId** | **Integer**| ID CDN-ресурса | |

### Return type

[**GetCdnResourceConfiguration200Response**](GetCdnResourceConfiguration200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;http_resource_configuration&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getCdnResourceNodes"></a>
# **getCdnResourceNodes**
> GetCdnResourceNodes200Response getCdnResourceNodes(resourceId, withExtraZones, country)

Получение списка раздающих узлов CDN-ресурса

Чтобы получить список узлов, которые раздают контент доменов ресурса, отправьте GET-запрос на &#x60;/api/v1/cdn/nodes/http-resources/{resource_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer resourceId = 1234; // Integer | ID CDN-ресурса
    Boolean withExtraZones = false; // Boolean | Добавить в выдачу узлы дополнительных зон раздачи.
    List<String> country = Arrays.asList(); // List<String> | Оставить в выдаче только основные зоны раздачи в указанных странах. Коды стран в формате ISO 3166-1 alpha-2.
    try {
      GetCdnResourceNodes200Response result = apiInstance.getCdnResourceNodes(resourceId, withExtraZones, country);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#getCdnResourceNodes");
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
| **resourceId** | **Integer**| ID CDN-ресурса | |
| **withExtraZones** | **Boolean**| Добавить в выдачу узлы дополнительных зон раздачи. | [optional] [default to false] |
| **country** | [**List&lt;String&gt;**](String.md)| Оставить в выдаче только основные зоны раздачи в указанных странах. Коды стран в формате ISO 3166-1 alpha-2. | [optional] |

### Return type

[**GetCdnResourceNodes200Response**](GetCdnResourceNodes200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;user_nodes&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getCdnResourceStatistics"></a>
# **getCdnResourceStatistics**
> GetCdnResourceStatistics200Response getCdnResourceStatistics(resourceId, from, to)

Получение статистики CDN-ресурса

Чтобы получить статистику трафика и запросов CDN-ресурса, отправьте GET-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}/statistics&#x60;.  Данные возвращаются с разбивкой по часовым интервалам. Если период не указан, вернется статистика за последние 6 часов.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer resourceId = 1234; // Integer | ID CDN-ресурса
    OffsetDateTime from = OffsetDateTime.parse("2026-04-16T00:00Z"); // OffsetDateTime | Начало периода в формате ISO 8601. По умолчанию — 6 часов назад.
    OffsetDateTime to = OffsetDateTime.parse("2026-04-16T23:59:59Z"); // OffsetDateTime | Конец периода в формате ISO 8601. По умолчанию — текущий момент. Должен быть не раньше `from`.
    try {
      GetCdnResourceStatistics200Response result = apiInstance.getCdnResourceStatistics(resourceId, from, to);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#getCdnResourceStatistics");
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
| **resourceId** | **Integer**| ID CDN-ресурса | |
| **from** | **OffsetDateTime**| Начало периода в формате ISO 8601. По умолчанию — 6 часов назад. | [optional] |
| **to** | **OffsetDateTime**| Конец периода в формате ISO 8601. По умолчанию — текущий момент. Должен быть не раньше &#x60;from&#x60;. | [optional] |

### Return type

[**GetCdnResourceStatistics200Response**](GetCdnResourceStatistics200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;statistics&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getCdnResources"></a>
# **getCdnResources**
> GetCdnResources200Response getCdnResources(bucketId)

Получение списка CDN-ресурсов

Чтобы получить список CDN-ресурсов, отправьте GET-запрос на &#x60;/api/v1/cdn/http-resources&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer bucketId = 4210; // Integer | Оставить в выдаче только ресурсы, источником контента которых является указанное S3-хранилище.
    try {
      GetCdnResources200Response result = apiInstance.getCdnResources(bucketId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#getCdnResources");
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
| **bucketId** | **Integer**| Оставить в выдаче только ресурсы, источником контента которых является указанное S3-хранилище. | [optional] |

### Return type

[**GetCdnResources200Response**](GetCdnResources200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;http_resources&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="issueCdnCertificate"></a>
# **issueCdnCertificate**
> issueCdnCertificate(issueCertificate)

Выпуск сертификата Let&#39;s Encrypt для CDN-ресурса

Чтобы выпустить бесплатный сертификат Let&#39;s Encrypt для доменов CDN-ресурса, отправьте POST-запрос на &#x60;/api/v1/cdn/certificates/issue&#x60;.  Выпуск выполняется асинхронно: в ответ возвращается код &#x60;202&#x60;, а следить за ходом выпуска можно по списку задач &#x60;/api/v1/cdn/certificates/tasks&#x60;. Готовый сертификат привязывается к ресурсу автоматически.  Перед выпуском убедитесь, что домены ресурса указывают на его технический домен &#x60;cdn_domain&#x60; — иначе вернется ошибка &#x60;422&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    IssueCertificate issueCertificate = new IssueCertificate(); // IssueCertificate | 
    try {
      apiInstance.issueCdnCertificate(issueCertificate);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#issueCdnCertificate");
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
| **issueCertificate** | [**IssueCertificate**](IssueCertificate.md)|  | |

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
| **202** | Запрос на выпуск сертификата принят |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **422** | Не удалось обработать сертификат |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="preloadCdnResourceCache"></a>
# **preloadCdnResourceCache**
> preloadCdnResourceCache(resourceId, preloadCache)

Предварительная загрузка кэша CDN-ресурса

Чтобы заранее загрузить файлы в кэш узлов CDN, не дожидаясь первого обращения пользователей, отправьте POST-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}/preload-cache&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer resourceId = 1234; // Integer | ID CDN-ресурса
    PreloadCache preloadCache = new PreloadCache(); // PreloadCache | 
    try {
      apiInstance.preloadCdnResourceCache(resourceId, preloadCache);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#preloadCdnResourceCache");
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
| **resourceId** | **Integer**| ID CDN-ресурса | |
| **preloadCache** | [**PreloadCache**](PreloadCache.md)|  | |

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
| **204** | Запрос на загрузку файлов в кэш принят |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="resumeCdnResource"></a>
# **resumeCdnResource**
> CreateCdnResource201Response resumeCdnResource(resourceId)

Возобновление раздачи CDN-ресурса

Чтобы возобновить раздачу контента после приостановки, отправьте POST-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}/resume&#x60;.  Если ресурс заблокирован, вернется ошибка &#x60;409&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer resourceId = 1234; // Integer | ID CDN-ресурса
    try {
      CreateCdnResource201Response result = apiInstance.resumeCdnResource(resourceId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#resumeCdnResource");
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
| **resourceId** | **Integer**| ID CDN-ресурса | |

### Return type

[**CreateCdnResource201Response**](CreateCdnResource201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;http_resource&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="suspendCdnResource"></a>
# **suspendCdnResource**
> CreateCdnResource201Response suspendCdnResource(resourceId)

Приостановка раздачи CDN-ресурса

Чтобы приостановить раздачу контента, отправьте POST-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}/suspend&#x60;. Ресурс перейдет в статус &#x60;stopped&#x60;, его настройки и домены сохранятся.  Если ресурс заблокирован, вернется ошибка &#x60;409&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer resourceId = 1234; // Integer | ID CDN-ресурса
    try {
      CreateCdnResource201Response result = apiInstance.suspendCdnResource(resourceId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#suspendCdnResource");
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
| **resourceId** | **Integer**| ID CDN-ресурса | |

### Return type

[**CreateCdnResource201Response**](CreateCdnResource201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;http_resource&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateCdnResource"></a>
# **updateCdnResource**
> CreateCdnResource201Response updateCdnResource(resourceId, updateHttpResource)

Изменение CDN-ресурса

Чтобы изменить CDN-ресурс, отправьте PATCH-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}&#x60;.  Передавайте только те поля, которые нужно изменить: переданные значения накладываются на текущую конфигурацию, а непереданные остаются без изменений. Чтобы сбросить настройку, передайте в соответствующем поле &#x60;null&#x60;.  Поля &#x60;storage_id&#x60; и &#x60;config.origin.servers&#x60; нельзя передавать вместе — источник контента может быть только один.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CdnApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    CdnApi apiInstance = new CdnApi(defaultClient);
    Integer resourceId = 1234; // Integer | ID CDN-ресурса
    UpdateHttpResource updateHttpResource = new UpdateHttpResource(); // UpdateHttpResource | 
    try {
      CreateCdnResource201Response result = apiInstance.updateCdnResource(resourceId, updateHttpResource);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CdnApi#updateCdnResource");
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
| **resourceId** | **Integer**| ID CDN-ресурса | |
| **updateHttpResource** | [**UpdateHttpResource**](UpdateHttpResource.md)|  | |

### Return type

[**CreateCdnResource201Response**](CreateCdnResource201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;http_resource&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

