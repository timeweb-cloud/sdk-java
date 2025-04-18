# S3Api

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addStorageSubdomainCertificate**](S3Api.md#addStorageSubdomainCertificate) | **POST** /api/v1/storages/certificates/generate | Добавление сертификата для поддомена хранилища |
| [**addStorageSubdomains**](S3Api.md#addStorageSubdomains) | **POST** /api/v1/storages/buckets/{bucket_id}/subdomains | Добавление поддоменов для хранилища |
| [**copyStorageFile**](S3Api.md#copyStorageFile) | **POST** /api/v1/storages/buckets/{bucket_id}/object-manager/copy | Копирование файла/директории в хранилище |
| [**createFolderInStorage**](S3Api.md#createFolderInStorage) | **POST** /api/v1/storages/buckets/{bucket_id}/object-manager/mkdir | Создание директории в хранилище |
| [**createStorage**](S3Api.md#createStorage) | **POST** /api/v1/storages/buckets | Создание хранилища |
| [**deleteStorage**](S3Api.md#deleteStorage) | **DELETE** /api/v1/storages/buckets/{bucket_id} | Удаление хранилища на аккаунте |
| [**deleteStorageFile**](S3Api.md#deleteStorageFile) | **DELETE** /api/v1/storages/buckets/{bucket_id}/object-manager/remove | Удаление файла/директории в хранилище |
| [**deleteStorageSubdomains**](S3Api.md#deleteStorageSubdomains) | **DELETE** /api/v1/storages/buckets/{bucket_id}/subdomains | Удаление поддоменов хранилища |
| [**getStorageFilesList**](S3Api.md#getStorageFilesList) | **GET** /api/v1/storages/buckets/{bucket_id}/object-manager/list | Получение списка файлов в хранилище по префиксу |
| [**getStorageSubdomains**](S3Api.md#getStorageSubdomains) | **GET** /api/v1/storages/buckets/{bucket_id}/subdomains | Получение списка поддоменов хранилища |
| [**getStorageTransferStatus**](S3Api.md#getStorageTransferStatus) | **GET** /api/v1/storages/buckets/{bucket_id}/transfer-status | Получение статуса переноса хранилища от стороннего S3 в Timeweb Cloud |
| [**getStorageUsers**](S3Api.md#getStorageUsers) | **GET** /api/v1/storages/users | Получение списка пользователей хранилищ аккаунта |
| [**getStorages**](S3Api.md#getStorages) | **GET** /api/v1/storages/buckets | Получение списка хранилищ аккаунта |
| [**getStoragesPresets**](S3Api.md#getStoragesPresets) | **GET** /api/v1/presets/storages | Получение списка тарифов для хранилищ |
| [**renameStorageFile**](S3Api.md#renameStorageFile) | **POST** /api/v1/storages/buckets/{bucket_id}/object-manager/rename | Переименование файла/директории в хранилище |
| [**transferStorage**](S3Api.md#transferStorage) | **POST** /api/v1/storages/transfer | Перенос хранилища от стороннего провайдера S3 в Timeweb Cloud |
| [**updateStorage**](S3Api.md#updateStorage) | **PATCH** /api/v1/storages/buckets/{bucket_id} | Изменение хранилища на аккаунте |
| [**updateStorageUser**](S3Api.md#updateStorageUser) | **PATCH** /api/v1/storages/users/{user_id} | Изменение пароля пользователя-администратора хранилища |
| [**uploadFileToStorage**](S3Api.md#uploadFileToStorage) | **POST** /api/v1/storages/buckets/{bucket_id}/object-manager/upload | Загрузка файлов в хранилище |


<a id="addStorageSubdomainCertificate"></a>
# **addStorageSubdomainCertificate**
> addStorageSubdomainCertificate(addStorageSubdomainCertificateRequest)

Добавление сертификата для поддомена хранилища

Чтобы добавить сертификат для поддомена хранилища, отправьте POST-запрос на &#x60;/api/v1/storages/certificates/generate&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    AddStorageSubdomainCertificateRequest addStorageSubdomainCertificateRequest = new AddStorageSubdomainCertificateRequest(); // AddStorageSubdomainCertificateRequest | 
    try {
      apiInstance.addStorageSubdomainCertificate(addStorageSubdomainCertificateRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#addStorageSubdomainCertificate");
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
| **addStorageSubdomainCertificateRequest** | [**AddStorageSubdomainCertificateRequest**](AddStorageSubdomainCertificateRequest.md)|  | |

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
| **204** | Сертификат добавлен |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="addStorageSubdomains"></a>
# **addStorageSubdomains**
> AddStorageSubdomains200Response addStorageSubdomains(bucketId, addStorageSubdomainsRequest)

Добавление поддоменов для хранилища

Чтобы добавить поддомены для хранилища, отправьте POST-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/subdomains&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    Integer bucketId = 1051; // Integer | ID хранилища.
    AddStorageSubdomainsRequest addStorageSubdomainsRequest = new AddStorageSubdomainsRequest(); // AddStorageSubdomainsRequest | 
    try {
      AddStorageSubdomains200Response result = apiInstance.addStorageSubdomains(bucketId, addStorageSubdomainsRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#addStorageSubdomains");
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
| **bucketId** | **Integer**| ID хранилища. | |
| **addStorageSubdomainsRequest** | [**AddStorageSubdomainsRequest**](AddStorageSubdomainsRequest.md)|  | |

### Return type

[**AddStorageSubdomains200Response**](AddStorageSubdomains200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;subdomains&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="copyStorageFile"></a>
# **copyStorageFile**
> copyStorageFile(bucketId, copyStorageFileRequest)

Копирование файла/директории в хранилище

Чтобы скопировать файла или директорию с вложениями, отправьте POST-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/object-manager/copy&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    Integer bucketId = 1051; // Integer | ID хранилища.
    CopyStorageFileRequest copyStorageFileRequest = new CopyStorageFileRequest(); // CopyStorageFileRequest | 
    try {
      apiInstance.copyStorageFile(bucketId, copyStorageFileRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#copyStorageFile");
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
| **bucketId** | **Integer**| ID хранилища. | |
| **copyStorageFileRequest** | [**CopyStorageFileRequest**](CopyStorageFileRequest.md)|  | |

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
| **204** | Задание на копирование отправлено |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createFolderInStorage"></a>
# **createFolderInStorage**
> createFolderInStorage(bucketId, createFolderInStorageRequest)

Создание директории в хранилище

Чтобы создать директорию в хранилище, отправьте POST-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/object-manager/mkdir&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    Integer bucketId = 1051; // Integer | ID хранилища.
    CreateFolderInStorageRequest createFolderInStorageRequest = new CreateFolderInStorageRequest(); // CreateFolderInStorageRequest | 
    try {
      apiInstance.createFolderInStorage(bucketId, createFolderInStorageRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#createFolderInStorage");
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
| **bucketId** | **Integer**| ID хранилища. | |
| **createFolderInStorageRequest** | [**CreateFolderInStorageRequest**](CreateFolderInStorageRequest.md)|  | |

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
| **201** | Директория успешно создана |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createStorage"></a>
# **createStorage**
> CreateStorage201Response createStorage(createStorageRequest)

Создание хранилища

Чтобы создать хранилище, отправьте POST-запрос на &#x60;/api/v1/storages/buckets&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    CreateStorageRequest createStorageRequest = new CreateStorageRequest(); // CreateStorageRequest | 
    try {
      CreateStorage201Response result = apiInstance.createStorage(createStorageRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#createStorage");
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
| **createStorageRequest** | [**CreateStorageRequest**](CreateStorageRequest.md)|  | |

### Return type

[**CreateStorage201Response**](CreateStorage201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;bucket&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteStorage"></a>
# **deleteStorage**
> DeleteStorage200Response deleteStorage(bucketId, hash, code)

Удаление хранилища на аккаунте

Чтобы удалить хранилище, отправьте DELETE-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    Integer bucketId = 1051; // Integer | ID хранилища.
    String hash = "15095f25-aac3-4d60-a788-96cb5136f186"; // String | Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм.
    String code = "0000"; // String | Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена `is_able_to_delete` установлен в значение `true`
    try {
      DeleteStorage200Response result = apiInstance.deleteStorage(bucketId, hash, code);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#deleteStorage");
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
| **bucketId** | **Integer**| ID хранилища. | |
| **hash** | **String**| Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм. | [optional] |
| **code** | **String**| Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена &#x60;is_able_to_delete&#x60; установлен в значение &#x60;true&#x60; | [optional] |

### Return type

[**DeleteStorage200Response**](DeleteStorage200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;bucket_delete&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteStorageFile"></a>
# **deleteStorageFile**
> deleteStorageFile(bucketId, deleteStorageFileRequest, isMultipart)

Удаление файла/директории в хранилище

Чтобы удалить файл или директорию с вложениями, отправьте DELETE-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/object-manager/remove&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    Integer bucketId = 1051; // Integer | ID хранилища.
    DeleteStorageFileRequest deleteStorageFileRequest = new DeleteStorageFileRequest(); // DeleteStorageFileRequest | 
    Boolean isMultipart = true; // Boolean | Это логическое значение, которое используется для обозначения multipart-загрузки.
    try {
      apiInstance.deleteStorageFile(bucketId, deleteStorageFileRequest, isMultipart);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#deleteStorageFile");
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
| **bucketId** | **Integer**| ID хранилища. | |
| **deleteStorageFileRequest** | [**DeleteStorageFileRequest**](DeleteStorageFileRequest.md)|  | |
| **isMultipart** | **Boolean**| Это логическое значение, которое используется для обозначения multipart-загрузки. | [optional] |

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
| **204** | Задание на удаление отправлено |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteStorageSubdomains"></a>
# **deleteStorageSubdomains**
> AddStorageSubdomains200Response deleteStorageSubdomains(bucketId, addStorageSubdomainsRequest)

Удаление поддоменов хранилища

Чтобы удалить поддомены хранилища, отправьте DELETE-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/subdomains&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    Integer bucketId = 1051; // Integer | ID хранилища.
    AddStorageSubdomainsRequest addStorageSubdomainsRequest = new AddStorageSubdomainsRequest(); // AddStorageSubdomainsRequest | 
    try {
      AddStorageSubdomains200Response result = apiInstance.deleteStorageSubdomains(bucketId, addStorageSubdomainsRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#deleteStorageSubdomains");
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
| **bucketId** | **Integer**| ID хранилища. | |
| **addStorageSubdomainsRequest** | [**AddStorageSubdomainsRequest**](AddStorageSubdomainsRequest.md)|  | |

### Return type

[**AddStorageSubdomains200Response**](AddStorageSubdomains200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;subdomains&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getStorageFilesList"></a>
# **getStorageFilesList**
> GetStorageFilesList200Response getStorageFilesList(bucketId, prefix, isMultipart)

Получение списка файлов в хранилище по префиксу

Чтобы получить список файлов в хранилище по префиксу, отправьте GET-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/object-manager/list&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    Integer bucketId = 1051; // Integer | ID хранилища.
    String prefix = "example"; // String | Префикс для поиска файла.
    Boolean isMultipart = true; // Boolean | Это логическое значение, которое используется для обозначения multipart-загрузки.
    try {
      GetStorageFilesList200Response result = apiInstance.getStorageFilesList(bucketId, prefix, isMultipart);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#getStorageFilesList");
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
| **bucketId** | **Integer**| ID хранилища. | |
| **prefix** | **String**| Префикс для поиска файла. | [optional] |
| **isMultipart** | **Boolean**| Это логическое значение, которое используется для обозначения multipart-загрузки. | [optional] |

### Return type

[**GetStorageFilesList200Response**](GetStorageFilesList200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;files&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getStorageSubdomains"></a>
# **getStorageSubdomains**
> GetStorageSubdomains200Response getStorageSubdomains(bucketId)

Получение списка поддоменов хранилища

Чтобы получить список поддоменов хранилища, отправьте GET-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/subdomains&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    Integer bucketId = 1051; // Integer | ID хранилища.
    try {
      GetStorageSubdomains200Response result = apiInstance.getStorageSubdomains(bucketId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#getStorageSubdomains");
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
| **bucketId** | **Integer**| ID хранилища. | |

### Return type

[**GetStorageSubdomains200Response**](GetStorageSubdomains200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;subdomains&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getStorageTransferStatus"></a>
# **getStorageTransferStatus**
> GetStorageTransferStatus200Response getStorageTransferStatus(bucketId)

Получение статуса переноса хранилища от стороннего S3 в Timeweb Cloud

Чтобы получить статус переноса хранилища от стороннего S3 в Timeweb Cloud, отправьте GET-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/transfer-status&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    Integer bucketId = 1051; // Integer | ID хранилища.
    try {
      GetStorageTransferStatus200Response result = apiInstance.getStorageTransferStatus(bucketId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#getStorageTransferStatus");
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
| **bucketId** | **Integer**| ID хранилища. | |

### Return type

[**GetStorageTransferStatus200Response**](GetStorageTransferStatus200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;transfer_status&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getStorageUsers"></a>
# **getStorageUsers**
> GetStorageUsers200Response getStorageUsers()

Получение списка пользователей хранилищ аккаунта

Чтобы получить список пользователей хранилищ аккаунта, отправьте GET-запрос на &#x60;/api/v1/storages/users&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    try {
      GetStorageUsers200Response result = apiInstance.getStorageUsers();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#getStorageUsers");
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

[**GetStorageUsers200Response**](GetStorageUsers200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;users&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getStorages"></a>
# **getStorages**
> GetProjectStorages200Response getStorages()

Получение списка хранилищ аккаунта

Чтобы получить список хранилищ аккаунта, отправьте GET-запрос на &#x60;/api/v1/storages/buckets&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    try {
      GetProjectStorages200Response result = apiInstance.getStorages();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#getStorages");
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

[**GetProjectStorages200Response**](GetProjectStorages200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;buckets&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getStoragesPresets"></a>
# **getStoragesPresets**
> GetStoragesPresets200Response getStoragesPresets()

Получение списка тарифов для хранилищ

Чтобы получить список тарифов для хранилищ, отправьте GET-запрос на &#x60;/api/v1/presets/storages&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;storages_presets&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    try {
      GetStoragesPresets200Response result = apiInstance.getStoragesPresets();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#getStoragesPresets");
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

[**GetStoragesPresets200Response**](GetStoragesPresets200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON с ключом &#x60;storages_presets&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="renameStorageFile"></a>
# **renameStorageFile**
> renameStorageFile(bucketId, renameStorageFileRequest)

Переименование файла/директории в хранилище

Чтобы переименовать файл/директорию в хранилище, отправьте POST-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/object-manager/rename&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    Integer bucketId = 1051; // Integer | ID хранилища.
    RenameStorageFileRequest renameStorageFileRequest = new RenameStorageFileRequest(); // RenameStorageFileRequest | 
    try {
      apiInstance.renameStorageFile(bucketId, renameStorageFileRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#renameStorageFile");
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
| **bucketId** | **Integer**| ID хранилища. | |
| **renameStorageFileRequest** | [**RenameStorageFileRequest**](RenameStorageFileRequest.md)|  | |

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
| **204** | Файл успешно переименован |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="transferStorage"></a>
# **transferStorage**
> transferStorage(transferStorageRequest)

Перенос хранилища от стороннего провайдера S3 в Timeweb Cloud

Чтобы перенести хранилище от стороннего провайдера S3 в Timeweb Cloud, отправьте POST-запрос на &#x60;/api/v1/storages/transfer&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    TransferStorageRequest transferStorageRequest = new TransferStorageRequest(); // TransferStorageRequest | 
    try {
      apiInstance.transferStorage(transferStorageRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#transferStorage");
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
| **transferStorageRequest** | [**TransferStorageRequest**](TransferStorageRequest.md)|  | |

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
| **204** | Задание на перенос отправлено |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateStorage"></a>
# **updateStorage**
> CreateStorage201Response updateStorage(bucketId, updateStorageRequest)

Изменение хранилища на аккаунте

Чтобы изменить хранилище, отправьте PATCH-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    Integer bucketId = 1051; // Integer | ID хранилища.
    UpdateStorageRequest updateStorageRequest = new UpdateStorageRequest(); // UpdateStorageRequest | 
    try {
      CreateStorage201Response result = apiInstance.updateStorage(bucketId, updateStorageRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#updateStorage");
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
| **bucketId** | **Integer**| ID хранилища. | |
| **updateStorageRequest** | [**UpdateStorageRequest**](UpdateStorageRequest.md)|  | |

### Return type

[**CreateStorage201Response**](CreateStorage201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;bucket&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateStorageUser"></a>
# **updateStorageUser**
> UpdateStorageUser200Response updateStorageUser(userId, updateStorageUserRequest)

Изменение пароля пользователя-администратора хранилища

Чтобы изменить пароль пользователя-администратора хранилища, отправьте POST-запрос на &#x60;/api/v1/storages/users/{user_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    Integer userId = 1051; // Integer | ID пользователя хранилища.
    UpdateStorageUserRequest updateStorageUserRequest = new UpdateStorageUserRequest(); // UpdateStorageUserRequest | 
    try {
      UpdateStorageUser200Response result = apiInstance.updateStorageUser(userId, updateStorageUserRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#updateStorageUser");
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
| **userId** | **Integer**| ID пользователя хранилища. | |
| **updateStorageUserRequest** | [**UpdateStorageUserRequest**](UpdateStorageUserRequest.md)|  | |

### Return type

[**UpdateStorageUser200Response**](UpdateStorageUser200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;user&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="uploadFileToStorage"></a>
# **uploadFileToStorage**
> uploadFileToStorage(bucketId, files, path)

Загрузка файлов в хранилище

Чтобы загрузить файлы в хранилище, отправьте POST-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/object-manager/upload&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.S3Api;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    S3Api apiInstance = new S3Api(defaultClient);
    Integer bucketId = 1051; // Integer | ID хранилища.
    List<File> files = Arrays.asList(); // List<File> | 
    String path = "test1/tes2"; // String | Путь до директории в хранилище
    try {
      apiInstance.uploadFileToStorage(bucketId, files, path);
    } catch (ApiException e) {
      System.err.println("Exception when calling S3Api#uploadFileToStorage");
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
| **bucketId** | **Integer**| ID хранилища. | |
| **files** | **List&lt;File&gt;**|  | |
| **path** | **String**| Путь до директории в хранилище | [optional] |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Файл успешно загружен |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

