# KnowledgeBasesApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addAdditionalTokenPackageToKnowledgebase**](KnowledgeBasesApi.md#addAdditionalTokenPackageToKnowledgebase) | **POST** /api/v1/cloud-ai/knowledge-bases/{id}/add-additional-token-package | Покупка дополнительных пакетов токенов |
| [**createKnowledgebase**](KnowledgeBasesApi.md#createKnowledgebase) | **POST** /api/v1/cloud-ai/knowledge-bases | Создание базы знаний |
| [**createKnowledgebaseDocument**](KnowledgeBasesApi.md#createKnowledgebaseDocument) | **POST** /api/v1/cloud-ai/knowledge-bases/{id}/documents | Добавление документа по ссылке |
| [**deleteDocument**](KnowledgeBasesApi.md#deleteDocument) | **DELETE** /api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id} | Удаление документа из базы знаний |
| [**deleteKnowledgebase**](KnowledgeBasesApi.md#deleteKnowledgebase) | **DELETE** /api/v1/cloud-ai/knowledge-bases/{id} | Удаление базы знаний |
| [**downloadDocument**](KnowledgeBasesApi.md#downloadDocument) | **GET** /api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}/download | Скачивание файла документа |
| [**getKnowledgebase**](KnowledgeBasesApi.md#getKnowledgebase) | **GET** /api/v1/cloud-ai/knowledge-bases/{id} | Получение базы знаний со списком документов |
| [**getKnowledgebaseDocumentsV2**](KnowledgeBasesApi.md#getKnowledgebaseDocumentsV2) | **GET** /api/v2/cloud-ai/knowledge-bases/{id}/documents | Получение списка документов базы знаний |
| [**getKnowledgebaseV3**](KnowledgeBasesApi.md#getKnowledgebaseV3) | **GET** /api/v3/cloud-ai/knowledge-bases/{id} | Получение базы знаний |
| [**getKnowledgebases**](KnowledgeBasesApi.md#getKnowledgebases) | **GET** /api/v1/cloud-ai/knowledge-bases | Получение списка баз знаний (v1) |
| [**getKnowledgebasesV2**](KnowledgeBasesApi.md#getKnowledgebasesV2) | **GET** /api/v2/cloud-ai/knowledge-bases | Получение списка баз знаний (v2) |
| [**getKnowledgebasesV3**](KnowledgeBasesApi.md#getKnowledgebasesV3) | **GET** /api/v3/cloud-ai/knowledge-bases | Получение списка баз знаний |
| [**linkKnowledgebaseToAgent**](KnowledgeBasesApi.md#linkKnowledgebaseToAgent) | **POST** /api/v1/cloud-ai/knowledge-bases/{id}/link/{agent_id} | Привязка базы знаний к агенту |
| [**reindexDocument**](KnowledgeBasesApi.md#reindexDocument) | **POST** /api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}/reindex | Переиндексация документа |
| [**unlinkKnowledgebaseFromAgent**](KnowledgeBasesApi.md#unlinkKnowledgebaseFromAgent) | **DELETE** /api/v1/cloud-ai/knowledge-bases/{id}/link/{agent_id} | Отвязка базы знаний от агента |
| [**updateKnowledgebase**](KnowledgeBasesApi.md#updateKnowledgebase) | **PATCH** /api/v1/cloud-ai/knowledge-bases/{id} | Изменение базы знаний |
| [**updateKnowledgebaseAgentLink**](KnowledgeBasesApi.md#updateKnowledgebaseAgentLink) | **PATCH** /api/v1/cloud-ai/knowledge-bases/{id}/link/{agent_id} | Изменение режима поиска по базе знаний для агента |
| [**updateKnowledgebaseDocument**](KnowledgeBasesApi.md#updateKnowledgebaseDocument) | **PATCH** /api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id} | Изменение документа |
| [**uploadFilesToKnowledgebase**](KnowledgeBasesApi.md#uploadFilesToKnowledgebase) | **POST** /api/v1/cloud-ai/knowledge-bases/{id}/upload | Загрузка файлов в базу знаний |


<a id="addAdditionalTokenPackageToKnowledgebase"></a>
# **addAdditionalTokenPackageToKnowledgebase**
> addAdditionalTokenPackageToKnowledgebase(id, knowledgebaseAddTokenPackage)

Покупка дополнительных пакетов токенов

Чтобы докупить токены для базы знаний, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/add-additional-token-package&#x60;.  Покупается дополнительный пакет (&#x60;package_type: additional&#x60;) из &#x60;GET /api/v1/cloud-ai/token-packages/knowledge-bases&#x60; в количестве &#x60;count&#x60;. Стоимость списывается с баланса, токены добавляются к текущему периоду подписки, заблокированная база знаний разблокируется.  Возможные ошибки: - &#x60;400 insufficient_balance&#x60; — на балансе недостаточно средств - &#x60;400 subscription_expired&#x60; — база знаний заблокирована и дата продления подписки прошла: продлите подписку в панели управления - &#x60;404 additional_token_package_not_found&#x60; — дополнительный пакет недоступен

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
    KnowledgebaseAddTokenPackage knowledgebaseAddTokenPackage = new KnowledgebaseAddTokenPackage(); // KnowledgebaseAddTokenPackage | 
    try {
      apiInstance.addAdditionalTokenPackageToKnowledgebase(id, knowledgebaseAddTokenPackage);
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
| **knowledgebaseAddTokenPackage** | [**KnowledgebaseAddTokenPackage**](KnowledgebaseAddTokenPackage.md)|  | [optional] |

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
| **204** | Пакеты токенов куплены |  -  |
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

Чтобы создать базу знаний, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases&#x60;, задав необходимые атрибуты.  Для базы знаний в вашем аккаунте создаётся кластер базы данных OpenSearch по выбранному тарифу в указанной приватной сети, а с баланса сразу списывается стоимость пакета токенов. Подписка продлевается автоматически раз в период пакета.  Пока кластер создаётся, база знаний находится в статусе &#x60;creating&#x60;; документы, добавленные в это время, будут проиндексированы после перехода в &#x60;active&#x60;.  Возможные ошибки: - &#x60;404 token_package_not_found&#x60; — пакет токенов не найден или недоступен - &#x60;400 invalid_token_package_type&#x60; — пакет не является базовым пакетом для баз знаний - &#x60;400 insufficient_balance&#x60; — на балансе недостаточно средств для оплаты пакета  Тело ответа будет представлять собой объект JSON с ключом &#x60;knowledge_base&#x60;.

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
| **201** | Объект JSON c ключом &#x60;knowledge_base&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createKnowledgebaseDocument"></a>
# **createKnowledgebaseDocument**
> CreateKnowledgebaseDocument201Response createKnowledgebaseDocument(id, createDocument)

Добавление документа по ссылке

Чтобы добавить в базу знаний документ по ссылке, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/documents&#x60;.  Перед добавлением выполняется HEAD-запрос к &#x60;url&#x60;: адрес должен быть публично доступен, а ответ — содержать заголовок &#x60;Content-Type&#x60; одного из поддерживаемых форматов (текст, Markdown, HTML, XML, CSV, PDF, DOC, DOCX, XLS, XLSX; JSON не поддерживается). Документ сразу ставится в очередь на индексацию. Чтобы содержимое по ссылке переиндексировалось автоматически, задайте &#x60;indexing_schedule&#x60;.  Возможные ошибки: - &#x60;400 url_is_not_accessible&#x60; — адрес недоступен или вернул ошибку - &#x60;400 unsupported_file_type&#x60; — неподдерживаемый &#x60;Content-Type&#x60;  Тело ответа будет представлять собой объект JSON с ключом &#x60;document&#x60;.

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
    CreateDocument createDocument = new CreateDocument(); // CreateDocument | 
    try {
      CreateKnowledgebaseDocument201Response result = apiInstance.createKnowledgebaseDocument(id, createDocument);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#createKnowledgebaseDocument");
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
| **createDocument** | [**CreateDocument**](CreateDocument.md)|  | |

### Return type

[**CreateKnowledgebaseDocument201Response**](CreateKnowledgebaseDocument201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;document&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteDocument"></a>
# **deleteDocument**
> deleteDocument(id, documentId)

Удаление документа из базы знаний

Чтобы удалить документ из базы знаний, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}&#x60;.  Документ удаляется вместе с его фрагментами в поисковом индексе.

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
    Integer documentId = 286071; // Integer | ID документа
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
| **204** | Документ удалён |  -  |
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

Чтобы удалить базу знаний, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}&#x60;.  База знаний отвязывается от всех агентов, её кластер базы данных OpenSearch удаляется, подписка закрывается.

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
| **204** | База знаний удалена |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="downloadDocument"></a>
# **downloadDocument**
> File downloadDocument(id, documentId)

Скачивание файла документа

Чтобы скачать загруженный файл документа, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}/download&#x60;.  Доступно только для документов типа &#x60;file&#x60;. Файл возвращается потоком с исходным MIME-типом в заголовке &#x60;Content-Type&#x60; и именем в &#x60;Content-Disposition&#x60;.

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
    Integer documentId = 286071; // Integer | ID документа
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
| **200** | Содержимое файла |  * Content-Type - MIME-тип файла <br>  * Content-Disposition - &#x60;attachment&#x60; с именем файла (URL-кодированным) <br>  * Content-Length - Размер файла в байтах <br>  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKnowledgebase"></a>
# **getKnowledgebase**
> CreateKnowledgebase201Response getKnowledgebase(id)

Получение базы знаний со списком документов

Чтобы получить информацию о базе знаний вместе со всеми её документами, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;knowledge_base&#x60;.

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
| **200** | Объект JSON c ключом &#x60;knowledge_base&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKnowledgebaseDocumentsV2"></a>
# **getKnowledgebaseDocumentsV2**
> GetKnowledgebaseDocumentsV2200Response getKnowledgebaseDocumentsV2(id, limit, offset, sortBy, sortOrder, search)

Получение списка документов базы знаний

Чтобы получить список документов базы знаний с пагинацией, отправьте GET-запрос на &#x60;/api/v2/cloud-ai/knowledge-bases/{id}/documents&#x60;.  Некорректные значения параметров пагинации и сортировки не приводят к ошибке — вместо них применяются значения по умолчанию.  Тело ответа будет представлять собой объект JSON с ключами &#x60;knowledgebase_documents&#x60; и &#x60;meta&#x60;.

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
    Integer limit = 10; // Integer | Количество документов на странице. Значения больше 100 уменьшаются до 100
    Integer offset = 0; // Integer | Количество документов, которые нужно пропустить
    String sortBy = "name"; // String | Поле сортировки. `indexing_timestamp` — время последнего изменения документа
    String sortOrder = "ASC"; // String | Порядок сортировки
    String search = "faq"; // String | Фильтр по подстроке в названии документа
    try {
      GetKnowledgebaseDocumentsV2200Response result = apiInstance.getKnowledgebaseDocumentsV2(id, limit, offset, sortBy, sortOrder, search);
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
| **limit** | **Integer**| Количество документов на странице. Значения больше 100 уменьшаются до 100 | [optional] [default to 10] |
| **offset** | **Integer**| Количество документов, которые нужно пропустить | [optional] [default to 0] |
| **sortBy** | **String**| Поле сортировки. &#x60;indexing_timestamp&#x60; — время последнего изменения документа | [optional] [default to indexing_timestamp] [enum: name, size, status, indexing_timestamp] |
| **sortOrder** | **String**| Порядок сортировки | [optional] [default to DESC] [enum: ASC, DESC] |
| **search** | **String**| Фильтр по подстроке в названии документа | [optional] |

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

<a id="getKnowledgebaseV3"></a>
# **getKnowledgebaseV3**
> GetKnowledgebaseV3200Response getKnowledgebaseV3(id)

Получение базы знаний

Чтобы получить информацию о базе знаний, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/knowledge-bases/{id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;knowledge_base&#x60;.

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
      GetKnowledgebaseV3200Response result = apiInstance.getKnowledgebaseV3(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#getKnowledgebaseV3");
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

[**GetKnowledgebaseV3200Response**](GetKnowledgebaseV3200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;knowledge_base&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKnowledgebases"></a>
# **getKnowledgebases**
> GetKnowledgebases200Response getKnowledgebases()

Получение списка баз знаний (v1)

Метод устарел — используйте &#x60;GET /api/v3/cloud-ai/knowledge-bases&#x60;.  Чтобы получить список баз знаний вместе со всеми их документами, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;knowledge_bases&#x60; и &#x60;meta&#x60;.

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
| **200** | Объект JSON c ключами &#x60;knowledge_bases&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKnowledgebasesV2"></a>
# **getKnowledgebasesV2**
> GetKnowledgebasesV2200Response getKnowledgebasesV2()

Получение списка баз знаний (v2)

Чтобы получить список баз знаний, отправьте GET-запрос на &#x60;/api/v2/cloud-ai/knowledge-bases&#x60;.  Вместо списка документов возвращается их количество, привязанные агенты — списком ID. Режим поиска для каждого агента возвращает &#x60;GET /api/v3/cloud-ai/knowledge-bases&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;knowledge_bases&#x60; и &#x60;meta&#x60;.

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
| **200** | Объект JSON c ключами &#x60;knowledge_bases&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKnowledgebasesV3"></a>
# **getKnowledgebasesV3**
> GetKnowledgebasesV3200Response getKnowledgebasesV3()

Получение списка баз знаний

Чтобы получить список баз знаний, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/knowledge-bases&#x60;.  Ответ содержит количество документов и привязанных агентов с режимом поиска. Документы базы знаний запрашивайте отдельно — &#x60;GET /api/v2/cloud-ai/knowledge-bases/{id}/documents&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;knowledge_bases&#x60; и &#x60;meta&#x60;.

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
      GetKnowledgebasesV3200Response result = apiInstance.getKnowledgebasesV3();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#getKnowledgebasesV3");
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

[**GetKnowledgebasesV3200Response**](GetKnowledgebasesV3200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;knowledge_bases&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="linkKnowledgebaseToAgent"></a>
# **linkKnowledgebaseToAgent**
> linkKnowledgebaseToAgent(id, agentId, linkKnowledgebase)

Привязка базы знаний к агенту

Чтобы привязать базу знаний к AI агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/link/{agent_id}&#x60;.  Тело запроса необязательно: в нём можно выбрать режим поиска &#x60;retrievalMode&#x60; (по умолчанию &#x60;agentic&#x60;). Изменить режим для существующей привязки можно запросом PATCH на тот же адрес.  Возможные ошибки: &#x60;404 knowledgebase_not_found&#x60;, &#x60;404 agent_not_found&#x60;.

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
    Integer agentId = 101; // Integer | ID AI агента
    LinkKnowledgebase linkKnowledgebase = new LinkKnowledgebase(); // LinkKnowledgebase | 
    try {
      apiInstance.linkKnowledgebaseToAgent(id, agentId, linkKnowledgebase);
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
| **agentId** | **Integer**| ID AI агента | |
| **linkKnowledgebase** | [**LinkKnowledgebase**](LinkKnowledgebase.md)|  | [optional] |

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
| **204** | База знаний привязана к агенту |  -  |
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

Чтобы переиндексировать документ, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}/reindex&#x60;.  Документ ставится в очередь на индексацию; следите за полем &#x60;status&#x60; документа. Для документа по ссылке содержимое скачивается заново. Если база знаний не в статусе &#x60;active&#x60;, документ будет проиндексирован после активации.  Возможные ошибки: &#x60;404 knowledgebase_not_found&#x60;, &#x60;404 document_not_found&#x60;.

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
    Integer documentId = 286071; // Integer | ID документа
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
| **204** | Документ поставлен в очередь на индексацию |  -  |
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

Чтобы отвязать базу знаний от AI агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/link/{agent_id}&#x60;.  Возможные ошибки: &#x60;404 knowledgebase_not_found&#x60;, &#x60;404 agent_not_found&#x60;.

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
    Integer agentId = 101; // Integer | ID AI агента
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
| **agentId** | **Integer**| ID AI агента | |

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
| **204** | База знаний отвязана от агента |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateKnowledgebase"></a>
# **updateKnowledgebase**
> CreateKnowledgebase201Response updateKnowledgebase(id, updateKnowledgebase)

Изменение базы знаний

Чтобы изменить название или описание базы знаний, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;knowledge_base&#x60;.

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
| **200** | Объект JSON c ключом &#x60;knowledge_base&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateKnowledgebaseAgentLink"></a>
# **updateKnowledgebaseAgentLink**
> updateKnowledgebaseAgentLink(id, agentId, linkKnowledgebase)

Изменение режима поиска по базе знаний для агента

Чтобы изменить режим поиска по привязанной базе знаний для AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/link/{agent_id}&#x60;.  Если &#x60;retrievalMode&#x60; не передан, устанавливается &#x60;agentic&#x60;. Если база знаний не привязана к агенту, запрос ничего не меняет и также возвращает &#x60;204&#x60;.  Возможные ошибки: &#x60;404 knowledgebase_not_found&#x60;, &#x60;404 agent_not_found&#x60;.

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
    Integer agentId = 101; // Integer | ID AI агента
    LinkKnowledgebase linkKnowledgebase = new LinkKnowledgebase(); // LinkKnowledgebase | 
    try {
      apiInstance.updateKnowledgebaseAgentLink(id, agentId, linkKnowledgebase);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#updateKnowledgebaseAgentLink");
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
| **agentId** | **Integer**| ID AI агента | |
| **linkKnowledgebase** | [**LinkKnowledgebase**](LinkKnowledgebase.md)|  | [optional] |

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
| **204** | Режим поиска изменён |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateKnowledgebaseDocument"></a>
# **updateKnowledgebaseDocument**
> CreateKnowledgebaseDocument201Response updateKnowledgebaseDocument(id, documentId, updateDocument)

Изменение документа

Чтобы изменить документ базы знаний, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}&#x60;.  Можно изменить название, расписание переиндексации и — только для документов типа &#x60;link&#x60; — адрес. После смены адреса документ переиндексируется; изменение названия и расписания переиндексацию не запускает. Новый адрес проверяется так же, как при добавлении документа.  Тело ответа будет представлять собой объект JSON с ключом &#x60;document&#x60;.

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
    Integer documentId = 286071; // Integer | ID документа
    UpdateDocument updateDocument = new UpdateDocument(); // UpdateDocument | 
    try {
      CreateKnowledgebaseDocument201Response result = apiInstance.updateKnowledgebaseDocument(id, documentId, updateDocument);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KnowledgeBasesApi#updateKnowledgebaseDocument");
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
| **updateDocument** | [**UpdateDocument**](UpdateDocument.md)|  | |

### Return type

[**CreateKnowledgebaseDocument201Response**](CreateKnowledgebaseDocument201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;document&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="uploadFilesToKnowledgebase"></a>
# **uploadFilesToKnowledgebase**
> UploadResult uploadFilesToKnowledgebase(id, files)

Загрузка файлов в базу знаний

Чтобы загрузить файлы в базу знаний, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/upload&#x60; с файлами в поле &#x60;files&#x60; формата &#x60;multipart/form-data&#x60;. Для каждого файла создаётся документ типа &#x60;file&#x60;, после загрузки документы ставятся в очередь на индексацию (если база знаний в статусе &#x60;active&#x60;; иначе — после активации).  Поддерживаемые форматы: TXT, Markdown (&#x60;md&#x60;, &#x60;markdown&#x60;), HTML (&#x60;html&#x60;, &#x60;htm&#x60;), XML, CSV, PDF, DOC, DOCX, XLS, XLSX, а также другие файлы с MIME-типом &#x60;text/_*&#x60;. JSON (&#x60;json&#x60;, &#x60;jsonl&#x60;, &#x60;ndjson&#x60;) не поддерживается. Размер одного файла — не более 50 МБ.  Если хотя бы один файл неподдерживаемого формата, запрос завершается ошибкой.  Тело ответа будет представлять собой объект JSON с ключами &#x60;uploadedFiles&#x60; и &#x60;documents&#x60;.

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
    List<File> files = Arrays.asList(); // List<File> | Файлы для загрузки
    try {
      UploadResult result = apiInstance.uploadFilesToKnowledgebase(id, files);
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
| **files** | **List&lt;File&gt;**| Файлы для загрузки | |

### Return type

[**UploadResult**](UploadResult.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;uploadedFiles&#x60; и &#x60;documents&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

