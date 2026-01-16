# MailApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createDomainMailbox**](MailApi.md#createDomainMailbox) | **POST** /api/v1/mail/domains/{domain} | Создание почтового ящика |
| [**createDomainMailboxV2**](MailApi.md#createDomainMailboxV2) | **POST** /api/v2/mail/domains/{domain} | Создание почтового ящика |
| [**createMultipleDomainMailboxes**](MailApi.md#createMultipleDomainMailboxes) | **POST** /api/v1/mail/domains/{domain}/batch | Множественное создание почтовых ящиков |
| [**createMultipleDomainMailboxesV2**](MailApi.md#createMultipleDomainMailboxesV2) | **POST** /api/v2/mail/domains/{domain}/batch | Множественное создание почтовых ящиков |
| [**deleteMailbox**](MailApi.md#deleteMailbox) | **DELETE** /api/v1/mail/domains/{domain}/mailboxes/{mailbox} | Удаление почтового ящика |
| [**getAllMailboxesV2**](MailApi.md#getAllMailboxesV2) | **GET** /api/v2/mail | Получение списка всех почтовых ящиков аккаунта |
| [**getDomainMailInfo**](MailApi.md#getDomainMailInfo) | **GET** /api/v1/mail/domains/{domain}/info | Получение почтовой информации о домене |
| [**getDomainMailboxes**](MailApi.md#getDomainMailboxes) | **GET** /api/v1/mail/domains/{domain} | Получение списка почтовых ящиков домена |
| [**getMailbox**](MailApi.md#getMailbox) | **GET** /api/v1/mail/domains/{domain}/mailboxes/{mailbox} | Получение почтового ящика |
| [**getMailboxV2**](MailApi.md#getMailboxV2) | **GET** /api/v2/mail/domains/{domain}/mailboxes/{mailbox} | Получение почтового ящика |
| [**getMailboxes**](MailApi.md#getMailboxes) | **GET** /api/v1/mail | Получение списка почтовых ящиков аккаунта |
| [**updateDomainMailInfo**](MailApi.md#updateDomainMailInfo) | **PATCH** /api/v1/mail/domains/{domain}/info | Изменение почтовой информации о домене |
| [**updateMailbox**](MailApi.md#updateMailbox) | **PATCH** /api/v1/mail/domains/{domain}/mailboxes/{mailbox} | Изменение почтового ящика |
| [**updateMailboxV2**](MailApi.md#updateMailboxV2) | **PATCH** /api/v2/mail/domains/{domain}/mailboxes/{mailbox} | Изменение почтового ящика |


<a id="createDomainMailbox"></a>
# **createDomainMailbox**
> CreateDomainMailbox201Response createDomainMailbox(domain, createDomainMailboxRequest)

Создание почтового ящика

Чтобы создать почтовый ящик, отправьте POST-запрос на &#x60;/api/v1/mail/domains/{domain}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MailApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    MailApi apiInstance = new MailApi(defaultClient);
    String domain = "somedomain.ru"; // String | Полное имя домена
    CreateDomainMailboxRequest createDomainMailboxRequest = new CreateDomainMailboxRequest(); // CreateDomainMailboxRequest | 
    try {
      CreateDomainMailbox201Response result = apiInstance.createDomainMailbox(domain, createDomainMailboxRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MailApi#createDomainMailbox");
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
| **domain** | **String**| Полное имя домена | |
| **createDomainMailboxRequest** | [**CreateDomainMailboxRequest**](CreateDomainMailboxRequest.md)|  | |

### Return type

[**CreateDomainMailbox201Response**](CreateDomainMailbox201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ответ будет представлять собой объект JSON c ключом &#x60;mailbox&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createDomainMailboxV2"></a>
# **createDomainMailboxV2**
> CreateDomainMailboxV2201Response createDomainMailboxV2(domain, createDomainMailboxV2Request)

Создание почтового ящика

Чтобы создать почтовый ящик, отправьте POST-запрос на &#x60;/api/v2/mail/domains/{domain}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MailApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    MailApi apiInstance = new MailApi(defaultClient);
    String domain = "somedomain.ru"; // String | Полное имя домена
    CreateDomainMailboxV2Request createDomainMailboxV2Request = new CreateDomainMailboxV2Request(); // CreateDomainMailboxV2Request | 
    try {
      CreateDomainMailboxV2201Response result = apiInstance.createDomainMailboxV2(domain, createDomainMailboxV2Request);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MailApi#createDomainMailboxV2");
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
| **domain** | **String**| Полное имя домена | |
| **createDomainMailboxV2Request** | [**CreateDomainMailboxV2Request**](CreateDomainMailboxV2Request.md)|  | |

### Return type

[**CreateDomainMailboxV2201Response**](CreateDomainMailboxV2201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ответ будет представлять собой объект JSON c ключом &#x60;mailbox&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createMultipleDomainMailboxes"></a>
# **createMultipleDomainMailboxes**
> CreateMultipleDomainMailboxes201Response createMultipleDomainMailboxes(domain, createMultipleDomainMailboxesRequest)

Множественное создание почтовых ящиков

Чтобы создать почтовый ящики, отправьте POST-запрос на &#x60;/api/v1/mail/domains/{domain}/batch&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MailApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    MailApi apiInstance = new MailApi(defaultClient);
    String domain = "somedomain.ru"; // String | Полное имя домена
    CreateMultipleDomainMailboxesRequest createMultipleDomainMailboxesRequest = new CreateMultipleDomainMailboxesRequest(); // CreateMultipleDomainMailboxesRequest | 
    try {
      CreateMultipleDomainMailboxes201Response result = apiInstance.createMultipleDomainMailboxes(domain, createMultipleDomainMailboxesRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MailApi#createMultipleDomainMailboxes");
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
| **domain** | **String**| Полное имя домена | |
| **createMultipleDomainMailboxesRequest** | [**CreateMultipleDomainMailboxesRequest**](CreateMultipleDomainMailboxesRequest.md)|  | |

### Return type

[**CreateMultipleDomainMailboxes201Response**](CreateMultipleDomainMailboxes201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ответ будет представлять собой объект JSON c ключом &#x60;mailboxes&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createMultipleDomainMailboxesV2"></a>
# **createMultipleDomainMailboxesV2**
> CreateMultipleDomainMailboxesV2201Response createMultipleDomainMailboxesV2(domain, createMultipleDomainMailboxesV2RequestInner)

Множественное создание почтовых ящиков

Чтобы создать несколько почтовых ящиков одновременно, отправьте POST-запрос на &#x60;/api/v2/mail/domains/{domain}/batch&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MailApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    MailApi apiInstance = new MailApi(defaultClient);
    String domain = "somedomain.ru"; // String | Полное имя домена
    List<CreateMultipleDomainMailboxesV2RequestInner> createMultipleDomainMailboxesV2RequestInner = Arrays.asList(); // List<CreateMultipleDomainMailboxesV2RequestInner> | 
    try {
      CreateMultipleDomainMailboxesV2201Response result = apiInstance.createMultipleDomainMailboxesV2(domain, createMultipleDomainMailboxesV2RequestInner);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MailApi#createMultipleDomainMailboxesV2");
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
| **domain** | **String**| Полное имя домена | |
| **createMultipleDomainMailboxesV2RequestInner** | [**List&lt;CreateMultipleDomainMailboxesV2RequestInner&gt;**](CreateMultipleDomainMailboxesV2RequestInner.md)|  | |

### Return type

[**CreateMultipleDomainMailboxesV2201Response**](CreateMultipleDomainMailboxesV2201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ответ будет представлять собой объект JSON c ключом &#x60;mailboxes_batch&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteMailbox"></a>
# **deleteMailbox**
> deleteMailbox(domain, mailbox)

Удаление почтового ящика

Чтобы удалить почтовый ящик, отправьте DELETE-запрос на &#x60;/api/v1/mail/domains/{domain}/mailboxes/{mailbox}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MailApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    MailApi apiInstance = new MailApi(defaultClient);
    String domain = "somedomain.ru"; // String | Полное имя домена
    String mailbox = "mailbox"; // String | Название почтового ящика
    try {
      apiInstance.deleteMailbox(domain, mailbox);
    } catch (ApiException e) {
      System.err.println("Exception when calling MailApi#deleteMailbox");
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
| **domain** | **String**| Полное имя домена | |
| **mailbox** | **String**| Название почтового ящика | |

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
| **204** | Успешное удаление почтового ящика |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAllMailboxesV2"></a>
# **getAllMailboxesV2**
> GetAllMailboxesV2200Response getAllMailboxesV2(limit, offset, search)

Получение списка всех почтовых ящиков аккаунта

Чтобы получить список всех почтовых ящиков, отправьте GET-запрос на &#x60;/api/v2/mail&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MailApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    MailApi apiInstance = new MailApi(defaultClient);
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    String search = "search_example"; // String | Поиск почтового ящика по названию
    try {
      GetAllMailboxesV2200Response result = apiInstance.getAllMailboxesV2(limit, offset, search);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MailApi#getAllMailboxesV2");
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
| **limit** | **Integer**| Обозначает количество записей, которое необходимо вернуть. | [optional] [default to 100] |
| **offset** | **Integer**| Указывает на смещение относительно начала списка. | [optional] [default to 0] |
| **search** | **String**| Поиск почтового ящика по названию | [optional] |

### Return type

[**GetAllMailboxesV2200Response**](GetAllMailboxesV2200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;mailboxes&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDomainMailInfo"></a>
# **getDomainMailInfo**
> GetDomainMailInfo200Response getDomainMailInfo(domain)

Получение почтовой информации о домене

Чтобы получить почтовую информацию о домене, отправьте GET-запрос на &#x60;/api/v1/mail/domains/{domain}/info&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MailApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    MailApi apiInstance = new MailApi(defaultClient);
    String domain = "somedomain.ru"; // String | Полное имя домена
    try {
      GetDomainMailInfo200Response result = apiInstance.getDomainMailInfo(domain);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MailApi#getDomainMailInfo");
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
| **domain** | **String**| Полное имя домена | |

### Return type

[**GetDomainMailInfo200Response**](GetDomainMailInfo200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;domain_info&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDomainMailboxes"></a>
# **getDomainMailboxes**
> GetMailboxes200Response getDomainMailboxes(domain, limit, offset, search)

Получение списка почтовых ящиков домена

Чтобы получить список почтовых ящиков домена, отправьте GET-запрос на &#x60;/api/v1/mail/domains/{domain}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MailApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    MailApi apiInstance = new MailApi(defaultClient);
    String domain = "somedomain.ru"; // String | Полное имя домена
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    String search = "search_example"; // String | Поиск почтового ящика по названию
    try {
      GetMailboxes200Response result = apiInstance.getDomainMailboxes(domain, limit, offset, search);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MailApi#getDomainMailboxes");
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
| **domain** | **String**| Полное имя домена | |
| **limit** | **Integer**| Обозначает количество записей, которое необходимо вернуть. | [optional] [default to 100] |
| **offset** | **Integer**| Указывает на смещение относительно начала списка. | [optional] [default to 0] |
| **search** | **String**| Поиск почтового ящика по названию | [optional] |

### Return type

[**GetMailboxes200Response**](GetMailboxes200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;mailboxes&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getMailbox"></a>
# **getMailbox**
> CreateDomainMailbox201Response getMailbox(domain, mailbox)

Получение почтового ящика

Чтобы получить почтовый ящик, отправьте GET-запрос на &#x60;/api/v1/mail/domains/{domain}/mailboxes/{mailbox}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MailApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    MailApi apiInstance = new MailApi(defaultClient);
    String domain = "somedomain.ru"; // String | Полное имя домена
    String mailbox = "mailbox"; // String | Название почтового ящика
    try {
      CreateDomainMailbox201Response result = apiInstance.getMailbox(domain, mailbox);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MailApi#getMailbox");
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
| **domain** | **String**| Полное имя домена | |
| **mailbox** | **String**| Название почтового ящика | |

### Return type

[**CreateDomainMailbox201Response**](CreateDomainMailbox201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;mailbox&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getMailboxV2"></a>
# **getMailboxV2**
> CreateDomainMailboxV2201Response getMailboxV2(domain, mailbox)

Получение почтового ящика

Чтобы получить почтовый ящик, отправьте GET-запрос на &#x60;/api/v2/mail/domains/{domain}/mailboxes/{mailbox}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MailApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    MailApi apiInstance = new MailApi(defaultClient);
    String domain = "somedomain.ru"; // String | Полное имя домена
    String mailbox = "mailbox"; // String | Название почтового ящика
    try {
      CreateDomainMailboxV2201Response result = apiInstance.getMailboxV2(domain, mailbox);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MailApi#getMailboxV2");
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
| **domain** | **String**| Полное имя домена | |
| **mailbox** | **String**| Название почтового ящика | |

### Return type

[**CreateDomainMailboxV2201Response**](CreateDomainMailboxV2201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;mailbox&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getMailboxes"></a>
# **getMailboxes**
> GetMailboxes200Response getMailboxes(limit, offset, search)

Получение списка почтовых ящиков аккаунта

Чтобы получить список почтовых ящиков аккаунта, отправьте GET-запрос на &#x60;/api/v1/mail&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MailApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    MailApi apiInstance = new MailApi(defaultClient);
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    String search = "search_example"; // String | Поиск почтового ящика по названию
    try {
      GetMailboxes200Response result = apiInstance.getMailboxes(limit, offset, search);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MailApi#getMailboxes");
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
| **limit** | **Integer**| Обозначает количество записей, которое необходимо вернуть. | [optional] [default to 100] |
| **offset** | **Integer**| Указывает на смещение относительно начала списка. | [optional] [default to 0] |
| **search** | **String**| Поиск почтового ящика по названию | [optional] |

### Return type

[**GetMailboxes200Response**](GetMailboxes200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;mailboxes&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateDomainMailInfo"></a>
# **updateDomainMailInfo**
> GetDomainMailInfo200Response updateDomainMailInfo(domain, updateDomainMailInfoRequest)

Изменение почтовой информации о домене

Чтобы изменить почтовую информацию о домене, отправьте PATCH-запрос на &#x60;/api/v1/mail/domains/{domain}/info&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MailApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    MailApi apiInstance = new MailApi(defaultClient);
    String domain = "somedomain.ru"; // String | Полное имя домена
    UpdateDomainMailInfoRequest updateDomainMailInfoRequest = new UpdateDomainMailInfoRequest(); // UpdateDomainMailInfoRequest | 
    try {
      GetDomainMailInfo200Response result = apiInstance.updateDomainMailInfo(domain, updateDomainMailInfoRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MailApi#updateDomainMailInfo");
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
| **domain** | **String**| Полное имя домена | |
| **updateDomainMailInfoRequest** | [**UpdateDomainMailInfoRequest**](UpdateDomainMailInfoRequest.md)|  | |

### Return type

[**GetDomainMailInfo200Response**](GetDomainMailInfo200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;domain_info&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateMailbox"></a>
# **updateMailbox**
> CreateDomainMailbox201Response updateMailbox(domain, mailbox, updateMailbox)

Изменение почтового ящика

Чтобы изменить почтовый ящик, отправьте PATCH-запрос на &#x60;/api/v1/mail/domains/{domain}/mailboxes/{mailbox}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MailApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    MailApi apiInstance = new MailApi(defaultClient);
    String domain = "somedomain.ru"; // String | Полное имя домена
    String mailbox = "mailbox"; // String | Название почтового ящика
    UpdateMailbox updateMailbox = new UpdateMailbox(); // UpdateMailbox | 
    try {
      CreateDomainMailbox201Response result = apiInstance.updateMailbox(domain, mailbox, updateMailbox);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MailApi#updateMailbox");
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
| **domain** | **String**| Полное имя домена | |
| **mailbox** | **String**| Название почтового ящика | |
| **updateMailbox** | [**UpdateMailbox**](UpdateMailbox.md)|  | |

### Return type

[**CreateDomainMailbox201Response**](CreateDomainMailbox201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;mailbox&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateMailboxV2"></a>
# **updateMailboxV2**
> UpdateMailboxV2200Response updateMailboxV2(domain, mailbox, updateMailboxV2)

Изменение почтового ящика

Чтобы изменить почтовый ящик, отправьте PATCH-запрос на &#x60;/api/v2/mail/domains/{domain}/mailboxes/{mailbox}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MailApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    MailApi apiInstance = new MailApi(defaultClient);
    String domain = "somedomain.ru"; // String | Полное имя домена
    String mailbox = "mailbox"; // String | Название почтового ящика
    UpdateMailboxV2 updateMailboxV2 = new UpdateMailboxV2(); // UpdateMailboxV2 | 
    try {
      UpdateMailboxV2200Response result = apiInstance.updateMailboxV2(domain, mailbox, updateMailboxV2);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MailApi#updateMailboxV2");
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
| **domain** | **String**| Полное имя домена | |
| **mailbox** | **String**| Название почтового ящика | |
| **updateMailboxV2** | [**UpdateMailboxV2**](UpdateMailboxV2.md)|  | |

### Return type

[**UpdateMailboxV2200Response**](UpdateMailboxV2200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;mailbox&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

