# ImagesApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createImage**](ImagesApi.md#createImage) | **POST** /api/v1/images | Создание образа |
| [**createImageDownloadUrl**](ImagesApi.md#createImageDownloadUrl) | **POST** /api/v1/images/{image_id}/download-url | Создание ссылки на скачивание образа |
| [**deleteImage**](ImagesApi.md#deleteImage) | **DELETE** /api/v1/images/{image_id} | Удаление образа |
| [**deleteImageDownloadURL**](ImagesApi.md#deleteImageDownloadURL) | **DELETE** /api/v1/images/{image_id}/download-url/{image_url_id} | Удаление ссылки на образ |
| [**getImage**](ImagesApi.md#getImage) | **GET** /api/v1/images/{image_id} | Получение информации о образе |
| [**getImageDownloadURL**](ImagesApi.md#getImageDownloadURL) | **GET** /api/v1/images/{image_id}/download-url/{image_url_id} | Получение информации о ссылке на скачивание образа |
| [**getImageDownloadURLs**](ImagesApi.md#getImageDownloadURLs) | **GET** /api/v1/images/{image_id}/download-url | Получение информации о ссылках на скачивание образов |
| [**getImages**](ImagesApi.md#getImages) | **GET** /api/v1/images | Получение списка образов |
| [**updateImage**](ImagesApi.md#updateImage) | **PATCH** /api/v1/images/{image_id} | Обновление информации о образе |
| [**uploadImage**](ImagesApi.md#uploadImage) | **POST** /api/v1/images/{image_id} | Загрузка образа |


<a id="createImage"></a>
# **createImage**
> ImageOutResponse createImage(imageInAPI)

Создание образа

Чтобы создать образ, отправьте POST запрос в &#x60;/api/v1/images&#x60;, задав необходимые атрибуты.   Для загрузки собственного образа вам нужно отправить параметры &#x60;location&#x60;, &#x60;os&#x60; и не указывать &#x60;disk_id&#x60;. Поддерживается два способа загрузки:  1. По ссылке. Для этого укажите &#x60;upload_url&#x60; с ссылкой на загрузку образа 2. Из файла. Для этого воспользуйтесь методом POST &#x60;/api/v1/images/{image_id}&#x60; Образ будет создан с использованием предоставленной информации.    Тело ответа будет содержать объект JSON с информацией о созданном образе.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ImagesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ImagesApi apiInstance = new ImagesApi(defaultClient);
    ImageInAPI imageInAPI = new ImageInAPI(); // ImageInAPI | 
    try {
      ImageOutResponse result = apiInstance.createImage(imageInAPI);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ImagesApi#createImage");
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
| **imageInAPI** | [**ImageInAPI**](ImageInAPI.md)|  | |

### Return type

[**ImageOutResponse**](ImageOutResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;image&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createImageDownloadUrl"></a>
# **createImageDownloadUrl**
> ImageDownloadResponse createImageDownloadUrl(imageId, imageUrlIn)

Создание ссылки на скачивание образа

Чтобы создать ссылку на скачивание образа, отправьте запрос POST в &#x60;/api/v1/images/{image_id}/download-url&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ImagesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ImagesApi apiInstance = new ImagesApi(defaultClient);
    String imageId = "imageId_example"; // String | ID образа.
    ImageUrlIn imageUrlIn = new ImageUrlIn(); // ImageUrlIn | 
    try {
      ImageDownloadResponse result = apiInstance.createImageDownloadUrl(imageId, imageUrlIn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ImagesApi#createImageDownloadUrl");
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
| **imageId** | **String**| ID образа. | |
| **imageUrlIn** | [**ImageUrlIn**](ImageUrlIn.md)|  | |

### Return type

[**ImageDownloadResponse**](ImageDownloadResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;download&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **409** | Образ уже загружен в облачное хранилище |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteImage"></a>
# **deleteImage**
> deleteImage(imageId)

Удаление образа

Чтобы удалить образ, отправьте запрос DELETE в &#x60;/api/v1/images/{image_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ImagesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ImagesApi apiInstance = new ImagesApi(defaultClient);
    String imageId = "imageId_example"; // String | ID образа.
    try {
      apiInstance.deleteImage(imageId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ImagesApi#deleteImage");
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
| **imageId** | **String**| ID образа. | |

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
| **204** | Образ удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteImageDownloadURL"></a>
# **deleteImageDownloadURL**
> deleteImageDownloadURL(imageId, imageUrlId)

Удаление ссылки на образ

Чтобы удалить ссылку на образ, отправьте DELETE запрос в &#x60;/api/v1/images/{image_id}/download-url/{image_url_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ImagesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ImagesApi apiInstance = new ImagesApi(defaultClient);
    String imageId = "imageId_example"; // String | ID образа.
    String imageUrlId = "imageUrlId_example"; // String | ID ссылки.
    try {
      apiInstance.deleteImageDownloadURL(imageId, imageUrlId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ImagesApi#deleteImageDownloadURL");
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
| **imageId** | **String**| ID образа. | |
| **imageUrlId** | **String**| ID ссылки. | |

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
| **204** | Ссылка удалена |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getImage"></a>
# **getImage**
> ImageOutResponse getImage(imageId)

Получение информации о образе

Чтобы получить образ, отправьте запрос GET в &#x60;/api/v1/images/{image_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ImagesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ImagesApi apiInstance = new ImagesApi(defaultClient);
    String imageId = "imageId_example"; // String | ID образа.
    try {
      ImageOutResponse result = apiInstance.getImage(imageId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ImagesApi#getImage");
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
| **imageId** | **String**| ID образа. | |

### Return type

[**ImageOutResponse**](ImageOutResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;image&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getImageDownloadURL"></a>
# **getImageDownloadURL**
> ImageDownloadResponse getImageDownloadURL(imageId, imageUrlId)

Получение информации о ссылке на скачивание образа

Чтобы получить информацию о ссылке на скачивание образа, отправьте запрос GET в &#x60;/api/v1/images/{image_id}/download-url/{image_url_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ImagesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ImagesApi apiInstance = new ImagesApi(defaultClient);
    String imageId = "imageId_example"; // String | ID образа.
    String imageUrlId = "imageUrlId_example"; // String | ID ссылки.
    try {
      ImageDownloadResponse result = apiInstance.getImageDownloadURL(imageId, imageUrlId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ImagesApi#getImageDownloadURL");
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
| **imageId** | **String**| ID образа. | |
| **imageUrlId** | **String**| ID ссылки. | |

### Return type

[**ImageDownloadResponse**](ImageDownloadResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;download&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getImageDownloadURLs"></a>
# **getImageDownloadURLs**
> ImageDownloadsResponse getImageDownloadURLs(imageId, limit, offset)

Получение информации о ссылках на скачивание образов

Чтобы получить информацию о ссылках на скачивание образов, отправьте запрос GET в &#x60;/api/v1/images/{image_id}/download-url&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ImagesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ImagesApi apiInstance = new ImagesApi(defaultClient);
    String imageId = "imageId_example"; // String | ID образа.
    Integer limit = 100; // Integer | 
    Integer offset = 0; // Integer | 
    try {
      ImageDownloadsResponse result = apiInstance.getImageDownloadURLs(imageId, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ImagesApi#getImageDownloadURLs");
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
| **imageId** | **String**| ID образа. | |
| **limit** | **Integer**|  | [optional] [default to 100] |
| **offset** | **Integer**|  | [optional] [default to 0] |

### Return type

[**ImageDownloadsResponse**](ImageDownloadsResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;downloads&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getImages"></a>
# **getImages**
> ImagesOutResponse getImages(limit, offset)

Получение списка образов

Чтобы получить список образов, отправьте GET запрос на &#x60;/api/v1/images&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ImagesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ImagesApi apiInstance = new ImagesApi(defaultClient);
    Integer limit = 100; // Integer | 
    Integer offset = 0; // Integer | 
    try {
      ImagesOutResponse result = apiInstance.getImages(limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ImagesApi#getImages");
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
| **limit** | **Integer**|  | [optional] [default to 100] |
| **offset** | **Integer**|  | [optional] [default to 0] |

### Return type

[**ImagesOutResponse**](ImagesOutResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;images&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateImage"></a>
# **updateImage**
> ImageOutResponse updateImage(imageId, imageUpdateAPI)

Обновление информации о образе

Чтобы обновить только определенные атрибуты образа, отправьте запрос PATCH в &#x60;/api/v1/images/{image_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ImagesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ImagesApi apiInstance = new ImagesApi(defaultClient);
    String imageId = "imageId_example"; // String | ID образа.
    ImageUpdateAPI imageUpdateAPI = new ImageUpdateAPI(); // ImageUpdateAPI | 
    try {
      ImageOutResponse result = apiInstance.updateImage(imageId, imageUpdateAPI);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ImagesApi#updateImage");
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
| **imageId** | **String**| ID образа. | |
| **imageUpdateAPI** | [**ImageUpdateAPI**](ImageUpdateAPI.md)|  | |

### Return type

[**ImageOutResponse**](ImageOutResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;image&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="uploadImage"></a>
# **uploadImage**
> UploadSuccessfulResponse uploadImage(imageId, contentDisposition)

Загрузка образа

Чтобы загрузить свой образ, отправьте POST запрос в &#x60;/api/v1/images/{image_id}&#x60;, отправив файл как &#x60;multipart/form-data&#x60;, указав имя файла в заголовке &#x60;Content-Disposition&#x60;.   Перед загрузкой, нужно создать образ используя POST &#x60;/api/v1/images&#x60;, указав параметры &#x60;location&#x60;, &#x60;os&#x60;   Тело ответа будет содержать объект JSON с информацией о загруженном образе.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ImagesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    ImagesApi apiInstance = new ImagesApi(defaultClient);
    String imageId = "imageId_example"; // String | 
    String contentDisposition = "contentDisposition_example"; // String | 
    try {
      UploadSuccessfulResponse result = apiInstance.uploadImage(imageId, contentDisposition);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ImagesApi#uploadImage");
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
| **imageId** | **String**|  | |
| **contentDisposition** | **String**|  | [optional] |

### Return type

[**UploadSuccessfulResponse**](UploadSuccessfulResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;upload_successful&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

