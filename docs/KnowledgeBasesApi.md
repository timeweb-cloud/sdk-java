# KnowledgeBasesApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addAdditionalTokenPackageToKnowledgebase**](KnowledgeBasesApi.md#addAdditionalTokenPackageToKnowledgebase) | **POST** /api/v1/cloud-ai/knowledge-bases/{id}/add-additional-token-package | Добавление дополнительного пакета токенов |
| [**createKnowledgebase**](KnowledgeBasesApi.md#createKnowledgebase) | **POST** /api/v1/cloud-ai/knowledge-bases | Создание базы знаний |
| [**deleteDocument**](KnowledgeBasesApi.md#deleteDocument) | **DELETE** /api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id} | Удаление документа из базы знаний |
| [**deleteKnowledgebase**](KnowledgeBasesApi.md#deleteKnowledgebase) | **DELETE** /api/v1/cloud-ai/knowledge-bases/{id} | Удаление базы знаний |
| [**downloadDocument**](KnowledgeBasesApi.md#downloadDocument) | **GET** /api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}/download | Скачивание документа из базы знаний |
| [**getKnowledgebase**](KnowledgeBasesApi.md#getKnowledgebase) | **GET** /api/v1/cloud-ai/knowledge-bases/{id} | Получение базы знаний |
| [**getKnowledgebaseDocumentsV2**](KnowledgeBasesApi.md#getKnowledgebaseDocumentsV2) | **GET** /api/v2/cloud-ai/knowledge-bases/{id}/documents | Получение списка документов базы знаний |
| [**getKnowledgebaseStatistics**](KnowledgeBasesApi.md#getKnowledgebaseStatistics) | **GET** /api/v1/cloud-ai/knowledge-bases/{id}/statistic | Получение статистики использования токенов базы знаний |
| [**getKnowledgebases**](KnowledgeBasesApi.md#getKnowledgebases) | **GET** /api/v1/cloud-ai/knowledge-bases | Получение списка баз знаний |
| [**getKnowledgebasesV2**](KnowledgeBasesApi.md#getKnowledgebasesV2) | **GET** /api/v2/cloud-ai/knowledge-bases | Получение списка баз знаний (v2) |
| [**linkKnowledgebaseToAgent**](KnowledgeBasesApi.md#linkKnowledgebaseToAgent) | **POST** /api/v1/cloud-ai/knowledge-bases/{id}/link/{agent_id} | Привязка базы знаний к агенту |
| [**reindexDocument**](KnowledgeBasesApi.md#reindexDocument) | **POST** /api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}/reindex | Переиндексация документа |
| [**unlinkKnowledgebaseFromAgent**](KnowledgeBasesApi.md#unlinkKnowledgebaseFromAgent) | **DELETE** /api/v1/cloud-ai/knowledge-bases/{id}/link/{agent_id} | Отвязка базы знаний от агента |
| [**updateKnowledgebase**](KnowledgeBasesApi.md#updateKnowledgebase) | **PATCH** /api/v1/cloud-ai/knowledge-bases/{id} | Обновление базы знаний |
| [**uploadFilesToKnowledgebase**](KnowledgeBasesApi.md#uploadFilesToKnowledgebase) | **POST** /api/v1/cloud-ai/knowledge-bases/{id}/upload | Загрузка файлов в базу знаний |


<a id="addAdditionalTokenPackageToKnowledgebase"></a>
# **addAdditionalTokenPackageToKnowledgebase**
> addAdditionalTokenPackageToKnowledgebase(id, addTokenPackage)

Добавление дополнительного пакета токенов

Чтобы добавить дополнительный пакет токенов для базы знаний, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/add-additional-token-package&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    Integer id = 1; // Integer | ID базы знаний
    AddTokenPackage addTokenPackage = new AddTokenPackage(); // AddTokenPackage | 
    try {
      apiInstance.addAdditionalTokenPackageToKnowledgebase(id, addTokenPackage);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#addAdditionalTokenPackageToKnowledgebase");
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
| **id** | **Integer**| ID базы знаний | |
| **addTokenPackage** | [**AddTokenPackage**](AddTokenPackage.md)|  | [optional] |

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
| **204** | Дополнительный пакет токенов успешно добавлен |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createKnowledgebase"></a>
# **createKnowledgebase**
> CreateKnowledgebase201Response createKnowledgebase(createKnowledgebase)

Создание базы знаний

Чтобы создать базу знаний, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases&#x60;, задав необходимые атрибуты.  База знаний будет создана с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданной базе знаний.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    CreateKnowledgebase createKnowledgebase = new CreateKnowledgebase(); // CreateKnowledgebase | 
    try {
      CreateKnowledgebase201Response result = apiInstance.createKnowledgebase(createKnowledgebase);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#createKnowledgebase");
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
| **createKnowledgebase** | [**CreateKnowledgebase**](CreateKnowledgebase.md)|  | |

### Return type

[**CreateKnowledgebase201Response**](CreateKnowledgebase201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;knowledgebase&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteDocument"></a>
# **deleteDocument**
> deleteDocument(id, documentId)

Удаление документа из базы знаний

Чтобы удалить документ из базы знаний, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    Integer id = 1; // Integer | ID базы знаний
    Integer documentId = 1; // Integer | ID документа
    try {
      apiInstance.deleteDocument(id, documentId);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#deleteDocument");
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
| **id** | **Integer**| ID базы знаний | |
| **documentId** | **Integer**| ID документа | |

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
| **204** | Документ успешно удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteKnowledgebase"></a>
# **deleteKnowledgebase**
> deleteKnowledgebase(id)

Удаление базы знаний

Чтобы удалить базу знаний, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    Integer id = 1; // Integer | ID базы знаний
    try {
      apiInstance.deleteKnowledgebase(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#deleteKnowledgebase");
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
| **id** | **Integer**| ID базы знаний | |

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
| **204** | База знаний успешно удалена |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="downloadDocument"></a>
# **downloadDocument**
> File downloadDocument(id, documentId)

Скачивание документа из базы знаний

Чтобы скачать документ из базы знаний, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}/download&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    Integer id = 1; // Integer | ID базы знаний
    Integer documentId = 1; // Integer | ID документа
    try {
      File result = apiInstance.downloadDocument(id, documentId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#downloadDocument");
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
| **id** | **Integer**| ID базы знаний | |
| **documentId** | **Integer**| ID документа | |

### Return type

[**File**](File.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/octet-stream, application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Файл документа |  * Content-Type - MIME тип файла <br>  * Content-Disposition - Attachment с именем файла <br>  * Content-Length - Размер файла в байтах <br>  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKnowledgebase"></a>
# **getKnowledgebase**
> CreateKnowledgebase201Response getKnowledgebase(id)

Получение базы знаний

Чтобы получить информацию о базе знаний, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    Integer id = 1; // Integer | ID базы знаний
    try {
      CreateKnowledgebase201Response result = apiInstance.getKnowledgebase(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#getKnowledgebase");
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
| **id** | **Integer**| ID базы знаний | |

### Return type

[**CreateKnowledgebase201Response**](CreateKnowledgebase201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;knowledgebase&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKnowledgebaseDocumentsV2"></a>
# **getKnowledgebaseDocumentsV2**
> GetKnowledgebaseDocumentsV2200Response getKnowledgebaseDocumentsV2(id, limit, offset, sortBy, sortOrder)

Получение списка документов базы знаний

Чтобы получить список документов базы знаний, отправьте GET-запрос на &#x60;/api/v2/cloud-ai/knowledge-bases/{id}/documents&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;knowledgebase_documents&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    Integer id = 1; // Integer | ID базы знаний
    Integer limit = 10; // Integer | Количество документов на странице (по умолчанию: 10, максимум: 100)
    Integer offset = 0; // Integer | Количество документов для пропуска (по умолчанию: 0)
    String sortBy = "name"; // String | Поле для сортировки (по умолчанию: indexing_timestamp - время последней индексации документа)
    String sortOrder = "ASC"; // String | Порядок сортировки (по умолчанию: DESC)
    try {
      GetKnowledgebaseDocumentsV2200Response result = apiInstance.getKnowledgebaseDocumentsV2(id, limit, offset, sortBy, sortOrder);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#getKnowledgebaseDocumentsV2");
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
| **id** | **Integer**| ID базы знаний | |
| **limit** | **Integer**| Количество документов на странице (по умолчанию: 10, максимум: 100) | [optional] [default to 10] |
| **offset** | **Integer**| Количество документов для пропуска (по умолчанию: 0) | [optional] [default to 0] |
| **sortBy** | **String**| Поле для сортировки (по умолчанию: indexing_timestamp - время последней индексации документа) | [optional] [default to indexing_timestamp] [enum: name, size, status, indexing_timestamp] |
| **sortOrder** | **String**| Порядок сортировки (по умолчанию: DESC) | [optional] [default to DESC] [enum: ASC, DESC] |

### Return type

[**GetKnowledgebaseDocumentsV2200Response**](GetKnowledgebaseDocumentsV2200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;knowledgebase_documents&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKnowledgebaseStatistics"></a>
# **getKnowledgebaseStatistics**
> GetKnowledgebaseStatistics200Response getKnowledgebaseStatistics(id, startTime, endTime, interval)

Получение статистики использования токенов базы знаний

Чтобы получить статистику использования токенов базы знаний, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/statistic&#x60;.  Можно указать временной диапазон и интервал агрегации.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    Integer id = 1; // Integer | ID базы знаний
    OffsetDateTime startTime = OffsetDateTime.parse("2024-10-01T00:00Z"); // OffsetDateTime | Начало временного диапазона (ISO 8601)
    OffsetDateTime endTime = OffsetDateTime.parse("2024-10-16T23:59:59.999Z"); // OffsetDateTime | Конец временного диапазона (ISO 8601)
    BigDecimal interval = new BigDecimal("60"); // BigDecimal | Интервал в минутах (по умолчанию 60)
    try {
      GetKnowledgebaseStatistics200Response result = apiInstance.getKnowledgebaseStatistics(id, startTime, endTime, interval);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#getKnowledgebaseStatistics");
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
| **id** | **Integer**| ID базы знаний | |
| **startTime** | **OffsetDateTime**| Начало временного диапазона (ISO 8601) | [optional] |
| **endTime** | **OffsetDateTime**| Конец временного диапазона (ISO 8601) | [optional] |
| **interval** | **BigDecimal**| Интервал в минутах (по умолчанию 60) | [optional] [default to 60] |

### Return type

[**GetKnowledgebaseStatistics200Response**](GetKnowledgebaseStatistics200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;knowledgebase_statistics&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKnowledgebases"></a>
# **getKnowledgebases**
> GetKnowledgebases200Response getKnowledgebases()

Получение списка баз знаний

Чтобы получить список баз знаний, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;knowledgebases&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    try {
      GetKnowledgebases200Response result = apiInstance.getKnowledgebases();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#getKnowledgebases");
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

[**GetKnowledgebases200Response**](GetKnowledgebases200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;knowledgebases&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKnowledgebasesV2"></a>
# **getKnowledgebasesV2**
> GetKnowledgebasesV2200Response getKnowledgebasesV2()

Получение списка баз знаний (v2)

Чтобы получить список баз знаний, отправьте GET-запрос на &#x60;/api/v2/cloud-ai/knowledge-bases&#x60;.  Версия API v2 возвращает оптимизированный ответ с количеством документов вместо полного списка документов.  Тело ответа будет представлять собой объект JSON с ключом &#x60;knowledgebases&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    try {
      GetKnowledgebasesV2200Response result = apiInstance.getKnowledgebasesV2();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#getKnowledgebasesV2");
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

[**GetKnowledgebasesV2200Response**](GetKnowledgebasesV2200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;knowledgebases&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="linkKnowledgebaseToAgent"></a>
# **linkKnowledgebaseToAgent**
> linkKnowledgebaseToAgent(id, agentId)

Привязка базы знаний к агенту

Чтобы привязать базу знаний к AI агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/link/{agent_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    Integer id = 1; // Integer | ID базы знаний
    Integer agentId = 1; // Integer | ID агента
    try {
      apiInstance.linkKnowledgebaseToAgent(id, agentId);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#linkKnowledgebaseToAgent");
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
| **id** | **Integer**| ID базы знаний | |
| **agentId** | **Integer**| ID агента | |

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
| **204** | База знаний успешно привязана к агенту |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="reindexDocument"></a>
# **reindexDocument**
> reindexDocument(id, documentId)

Переиндексация документа

Чтобы переиндексировать документ в базе знаний, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}/reindex&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    Integer id = 1; // Integer | ID базы знаний
    Integer documentId = 1; // Integer | ID документа
    try {
      apiInstance.reindexDocument(id, documentId);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#reindexDocument");
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
| **id** | **Integer**| ID базы знаний | |
| **documentId** | **Integer**| ID документа | |

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
| **204** | Переиндексация документа начата |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="unlinkKnowledgebaseFromAgent"></a>
# **unlinkKnowledgebaseFromAgent**
> unlinkKnowledgebaseFromAgent(id, agentId)

Отвязка базы знаний от агента

Чтобы отвязать базу знаний от AI агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/link/{agent_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    Integer id = 1; // Integer | ID базы знаний
    Integer agentId = 1; // Integer | ID агента
    try {
      apiInstance.unlinkKnowledgebaseFromAgent(id, agentId);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#unlinkKnowledgebaseFromAgent");
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
| **id** | **Integer**| ID базы знаний | |
| **agentId** | **Integer**| ID агента | |

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
| **200** | База знаний успешно отвязана от агента |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateKnowledgebase"></a>
# **updateKnowledgebase**
> CreateKnowledgebase201Response updateKnowledgebase(id, updateKnowledgebase)

Обновление базы знаний

Чтобы обновить базу знаний, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    Integer id = 1; // Integer | ID базы знаний
    UpdateKnowledgebase updateKnowledgebase = new UpdateKnowledgebase(); // UpdateKnowledgebase | 
    try {
      CreateKnowledgebase201Response result = apiInstance.updateKnowledgebase(id, updateKnowledgebase);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#updateKnowledgebase");
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
| **id** | **Integer**| ID базы знаний | |
| **updateKnowledgebase** | [**UpdateKnowledgebase**](UpdateKnowledgebase.md)|  | |

### Return type

[**CreateKnowledgebase201Response**](CreateKnowledgebase201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;knowledgebase&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="uploadFilesToKnowledgebase"></a>
# **uploadFilesToKnowledgebase**
> UploadFilesToKnowledgebase200Response uploadFilesToKnowledgebase(id, files)

Загрузка файлов в базу знаний

Чтобы загрузить файлы в базу знаний, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/upload&#x60; с файлами в формате multipart/form-data.  Поддерживаемые форматы: CSV, XML, Markdown (md), HTML, TXT. JSON не поддерживается. Максимум 10 файлов, до 10 МБ каждый.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KnowledgeBasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KnowledgeBasesApi apiInstance = new KnowledgeBasesApi(defaultClient);
    Integer id = 1; // Integer | ID базы знаний
    List<File> files = Arrays.asList(); // List<File> | Файлы для загрузки (максимум 10 файлов, 10 МБ каждый)
    try {
      UploadFilesToKnowledgebase200Response result = apiInstance.uploadFilesToKnowledgebase(id, files);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#uploadFilesToKnowledgebase");
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
| **id** | **Integer**| ID базы знаний | |
| **files** | **List&lt;File&gt;**| Файлы для загрузки (максимум 10 файлов, 10 МБ каждый) | |

### Return type

[**UploadFilesToKnowledgebase200Response**](UploadFilesToKnowledgebase200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;uploaded_files&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

