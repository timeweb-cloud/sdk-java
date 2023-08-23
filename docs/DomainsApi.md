# DomainsApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addDomain**](DomainsApi.md#addDomain) | **POST** /api/v1/add-domain/{fqdn} | Добавление домена на аккаунт |
| [**addSubdomain**](DomainsApi.md#addSubdomain) | **POST** /api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn} | Добавление поддомена |
| [**checkDomain**](DomainsApi.md#checkDomain) | **GET** /api/v1/check-domain/{fqdn} | Проверить, доступен ли домен для регистрации |
| [**createDomainDNSRecord**](DomainsApi.md#createDomainDNSRecord) | **POST** /api/v1/domains/{fqdn}/dns-records | Добавить информацию о DNS-записи для домена или поддомена |
| [**createDomainRequest**](DomainsApi.md#createDomainRequest) | **POST** /api/v1/domains-requests | Создание заявки на регистрацию/продление/трансфер домена |
| [**deleteDomain**](DomainsApi.md#deleteDomain) | **DELETE** /api/v1/domains/{fqdn} | Удаление домена |
| [**deleteDomainDNSRecord**](DomainsApi.md#deleteDomainDNSRecord) | **DELETE** /api/v1/domains/{fqdn}/dns-records/{record_id} | Удалить информацию о DNS-записи для домена или поддомена |
| [**deleteSubdomain**](DomainsApi.md#deleteSubdomain) | **DELETE** /api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn} | Удаление поддомена |
| [**getDomain**](DomainsApi.md#getDomain) | **GET** /api/v1/domains/{fqdn} | Получение информации о домене |
| [**getDomainDNSRecords**](DomainsApi.md#getDomainDNSRecords) | **GET** /api/v1/domains/{fqdn}/dns-records | Получить информацию обо всех пользовательских DNS-записях домена или поддомена |
| [**getDomainDefaultDNSRecords**](DomainsApi.md#getDomainDefaultDNSRecords) | **GET** /api/v1/domains/{fqdn}/default-dns-records | Получить информацию обо всех DNS-записях по умолчанию домена или поддомена |
| [**getDomainNameServers**](DomainsApi.md#getDomainNameServers) | **GET** /api/v1/domains/{fqdn}/name-servers | Получение списка name-серверов домена |
| [**getDomainRequest**](DomainsApi.md#getDomainRequest) | **GET** /api/v1/domains-requests/{request_id} | Получение заявки на регистрацию/продление/трансфер домена |
| [**getDomainRequests**](DomainsApi.md#getDomainRequests) | **GET** /api/v1/domains-requests | Получение списка заявок на регистрацию/продление/трансфер домена |
| [**getDomains**](DomainsApi.md#getDomains) | **GET** /api/v1/domains | Получение списка всех доменов |
| [**getTLD**](DomainsApi.md#getTLD) | **GET** /api/v1/tlds/{tld_id} | Получить информацию о доменной зоне по идентификатору |
| [**getTLDs**](DomainsApi.md#getTLDs) | **GET** /api/v1/tlds | Получить информацию о доменных зонах |
| [**updateDomainAutoProlongation**](DomainsApi.md#updateDomainAutoProlongation) | **PATCH** /api/v1/domains/{fqdn} | Включение/выключение автопродления домена |
| [**updateDomainDNSRecord**](DomainsApi.md#updateDomainDNSRecord) | **PATCH** /api/v1/domains/{fqdn}/dns-records/{record_id} | Обновить информацию о DNS-записи домена или поддомена |
| [**updateDomainNameServers**](DomainsApi.md#updateDomainNameServers) | **PUT** /api/v1/domains/{fqdn}/name-servers | Изменение name-серверов домена |
| [**updateDomainRequest**](DomainsApi.md#updateDomainRequest) | **PATCH** /api/v1/domains-requests/{request_id} | Оплата/обновление заявки на регистрацию/продление/трансфер домена |


<a id="addDomain"></a>
# **addDomain**
> addDomain(fqdn)

Добавление домена на аккаунт

Чтобы добавить домен на свой аккаунт, отправьте запрос POST на &#x60;/api/v1/add-domain/{fqdn}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    String fqdn = "somedomain.ru"; // String | Полное имя домена.
    try {
      apiInstance.addDomain(fqdn);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#addDomain");
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
| **fqdn** | **String**| Полное имя домена. | |

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
| **204** | Домен успешно добавлен на ваш аккаунт. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="addSubdomain"></a>
# **addSubdomain**
> AddSubdomain201Response addSubdomain(fqdn, subdomainFqdn)

Добавление поддомена

Чтобы добавить поддомен, отправьте запрос POST на &#x60;/api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn}&#x60;, задав необходимые атрибуты.  Поддомен будет добавлен с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о добавленном поддомене.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    String fqdn = "somedomain.ru"; // String | Полное имя домена.
    String subdomainFqdn = "sub.somedomain.ru"; // String | Полное имя поддомена.
    try {
      AddSubdomain201Response result = apiInstance.addSubdomain(fqdn, subdomainFqdn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#addSubdomain");
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
| **fqdn** | **String**| Полное имя домена. | |
| **subdomainFqdn** | **String**| Полное имя поддомена. | |

### Return type

[**AddSubdomain201Response**](AddSubdomain201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ответ будет представлять собой объект JSON c ключом &#x60;subdomain&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="checkDomain"></a>
# **checkDomain**
> CheckDomain200Response checkDomain(fqdn)

Проверить, доступен ли домен для регистрации

Чтобы проверить, доступен ли домен для регистрации, отправьте запрос GET на &#x60;/api/v1/check-domain/{fqdn}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    String fqdn = "somedomain.ru"; // String | Полное имя домена.
    try {
      CheckDomain200Response result = apiInstance.checkDomain(fqdn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#checkDomain");
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
| **fqdn** | **String**| Полное имя домена. | |

### Return type

[**CheckDomain200Response**](CheckDomain200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;is_domain_available&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createDomainDNSRecord"></a>
# **createDomainDNSRecord**
> CreateDomainDNSRecord201Response createDomainDNSRecord(fqdn, createDns)

Добавить информацию о DNS-записи для домена или поддомена

Чтобы добавить информацию о DNS-записи для домена или поддомена, отправьте запрос POST на &#x60;/api/v1/domains/{fqdn}/dns-records&#x60;, задав необходимые атрибуты.  DNS-запись будет добавлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о добавленной DNS-записи.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    String fqdn = "somedomain.ru"; // String | Полное имя домена или поддомена.
    CreateDns createDns = new CreateDns(); // CreateDns | 
    try {
      CreateDomainDNSRecord201Response result = apiInstance.createDomainDNSRecord(fqdn, createDns);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#createDomainDNSRecord");
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
| **fqdn** | **String**| Полное имя домена или поддомена. | |
| **createDns** | [**CreateDns**](CreateDns.md)|  | |

### Return type

[**CreateDomainDNSRecord201Response**](CreateDomainDNSRecord201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createDomainRequest"></a>
# **createDomainRequest**
> CreateDomainRequest201Response createDomainRequest(domainRegister)

Создание заявки на регистрацию/продление/трансфер домена

Чтобы создать заявку на регистрацию/продление/трансфер домена, отправьте POST-запрос в &#x60;api/v1/domains-requests&#x60;, задав необходимые атрибуты.  Заявка будет создана с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданной заявке.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    DomainRegister domainRegister = new DomainRegister(); // DomainRegister | 
    try {
      CreateDomainRequest201Response result = apiInstance.createDomainRequest(domainRegister);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#createDomainRequest");
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
| **domainRegister** | [**DomainRegister**](DomainRegister.md)|  | |

### Return type

[**CreateDomainRequest201Response**](CreateDomainRequest201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteDomain"></a>
# **deleteDomain**
> deleteDomain(fqdn)

Удаление домена

Чтобы удалить домен, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    String fqdn = "somedomain.ru"; // String | Полное имя домена.
    try {
      apiInstance.deleteDomain(fqdn);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#deleteDomain");
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
| **fqdn** | **String**| Полное имя домена. | |

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
| **204** | Домен успешно удален. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteDomainDNSRecord"></a>
# **deleteDomainDNSRecord**
> deleteDomainDNSRecord(fqdn, recordId)

Удалить информацию о DNS-записи для домена или поддомена

Чтобы удалить информацию о DNS-записи для домена или поддомена, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}/dns-records/{record_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    String fqdn = "somedomain.ru"; // String | Полное имя домена или поддомена.
    Integer recordId = 123; // Integer | Идентификатор DNS-записи домена или поддомена.
    try {
      apiInstance.deleteDomainDNSRecord(fqdn, recordId);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#deleteDomainDNSRecord");
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
| **fqdn** | **String**| Полное имя домена или поддомена. | |
| **recordId** | **Integer**| Идентификатор DNS-записи домена или поддомена. | |

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
| **204** | Информация о DNS-записи успешно удалена. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteSubdomain"></a>
# **deleteSubdomain**
> deleteSubdomain(fqdn, subdomainFqdn)

Удаление поддомена

Чтобы удалить поддомен, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    String fqdn = "somedomain.ru"; // String | Полное имя домена.
    String subdomainFqdn = "sub.somedomain.ru"; // String | Полное имя поддомена.
    try {
      apiInstance.deleteSubdomain(fqdn, subdomainFqdn);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#deleteSubdomain");
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
| **fqdn** | **String**| Полное имя домена. | |
| **subdomainFqdn** | **String**| Полное имя поддомена. | |

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
| **204** | Поддомен успешно удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDomain"></a>
# **getDomain**
> GetDomain200Response getDomain(fqdn)

Получение информации о домене

Чтобы отобразить информацию об отдельном домене, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    String fqdn = "somedomain.ru"; // String | Полное имя домена.
    try {
      GetDomain200Response result = apiInstance.getDomain(fqdn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#getDomain");
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
| **fqdn** | **String**| Полное имя домена. | |

### Return type

[**GetDomain200Response**](GetDomain200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;domain&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDomainDNSRecords"></a>
# **getDomainDNSRecords**
> GetDomainDNSRecords200Response getDomainDNSRecords(fqdn, limit, offset)

Получить информацию обо всех пользовательских DNS-записях домена или поддомена

Чтобы получить информацию обо всех пользовательских DNS-записях домена или поддомена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/dns-records&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    String fqdn = "somedomain.ru"; // String | Полное имя домена или поддомена.
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    try {
      GetDomainDNSRecords200Response result = apiInstance.getDomainDNSRecords(fqdn, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#getDomainDNSRecords");
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
| **fqdn** | **String**| Полное имя домена или поддомена. | |
| **limit** | **Integer**| Обозначает количество записей, которое необходимо вернуть. | [optional] [default to 100] |
| **offset** | **Integer**| Указывает на смещение относительно начала списка. | [optional] [default to 0] |

### Return type

[**GetDomainDNSRecords200Response**](GetDomainDNSRecords200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;dns_records&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDomainDefaultDNSRecords"></a>
# **getDomainDefaultDNSRecords**
> GetDomainDNSRecords200Response getDomainDefaultDNSRecords(fqdn, limit, offset)

Получить информацию обо всех DNS-записях по умолчанию домена или поддомена

Чтобы получить информацию обо всех DNS-записях по умолчанию домена или поддомена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/default-dns-records&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    String fqdn = "somedomain.ru"; // String | Полное имя домена или поддомена.
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    try {
      GetDomainDNSRecords200Response result = apiInstance.getDomainDefaultDNSRecords(fqdn, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#getDomainDefaultDNSRecords");
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
| **fqdn** | **String**| Полное имя домена или поддомена. | |
| **limit** | **Integer**| Обозначает количество записей, которое необходимо вернуть. | [optional] [default to 100] |
| **offset** | **Integer**| Указывает на смещение относительно начала списка. | [optional] [default to 0] |

### Return type

[**GetDomainDNSRecords200Response**](GetDomainDNSRecords200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;dns_records&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDomainNameServers"></a>
# **getDomainNameServers**
> GetDomainNameServers200Response getDomainNameServers(fqdn)

Получение списка name-серверов домена

Чтобы получить список name-серверов домена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/name-servers&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    String fqdn = "somedomain.ru"; // String | Полное имя домена.
    try {
      GetDomainNameServers200Response result = apiInstance.getDomainNameServers(fqdn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#getDomainNameServers");
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
| **fqdn** | **String**| Полное имя домена. | |

### Return type

[**GetDomainNameServers200Response**](GetDomainNameServers200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;name_servers&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDomainRequest"></a>
# **getDomainRequest**
> CreateDomainRequest201Response getDomainRequest(requestId)

Получение заявки на регистрацию/продление/трансфер домена

Чтобы получить заявку на регистрацию/продление/трансфер домена, отправьте запрос GET на &#x60;/api/v1/domains-requests/{request_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    Integer requestId = 123; // Integer | Идентификатор заявки на регистрацию/продление/трансфер домена.
    try {
      CreateDomainRequest201Response result = apiInstance.getDomainRequest(requestId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#getDomainRequest");
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
| **requestId** | **Integer**| Идентификатор заявки на регистрацию/продление/трансфер домена. | |

### Return type

[**CreateDomainRequest201Response**](CreateDomainRequest201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDomainRequests"></a>
# **getDomainRequests**
> GetDomainRequests200Response getDomainRequests(personId)

Получение списка заявок на регистрацию/продление/трансфер домена

Чтобы получить список заявок на регистрацию/продление/трансфер домена, отправьте запрос GET на &#x60;/api/v1/domains-requests&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    Integer personId = 123; // Integer | Идентификатор администратора, на которого зарегистрирован домен.
    try {
      GetDomainRequests200Response result = apiInstance.getDomainRequests(personId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#getDomainRequests");
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
| **personId** | **Integer**| Идентификатор администратора, на которого зарегистрирован домен. | [optional] |

### Return type

[**GetDomainRequests200Response**](GetDomainRequests200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;requests&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDomains"></a>
# **getDomains**
> GetDomains200Response getDomains(limit, offset, idnName, linkedIp, order, sort)

Получение списка всех доменов

Чтобы получить список всех доменов на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/domains&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;domains&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    String idnName = "xn--e1afmkfd.xn--p1ai"; // String | Интернационализированное доменное имя.
    String linkedIp = "192.168.1.1"; // String | Привязанный к домену IP-адрес.
    String order = "asc"; // String | Порядок доменов.
    String sort = "idn_name"; // String | Сортировка доменов.
    try {
      GetDomains200Response result = apiInstance.getDomains(limit, offset, idnName, linkedIp, order, sort);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#getDomains");
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
| **idnName** | **String**| Интернационализированное доменное имя. | [optional] |
| **linkedIp** | **String**| Привязанный к домену IP-адрес. | [optional] |
| **order** | **String**| Порядок доменов. | [optional] [enum: asc, dsc] |
| **sort** | **String**| Сортировка доменов. | [optional] [enum: idn_name] |

### Return type

[**GetDomains200Response**](GetDomains200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;domains&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getTLD"></a>
# **getTLD**
> GetTLD200Response getTLD(tldId)

Получить информацию о доменной зоне по идентификатору

Чтобы получить информацию о доменной зоне по идентификатору, отправьте запрос GET на &#x60;/api/v1/tlds/{tld_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    Integer tldId = 123; // Integer | Идентификатор доменной зоны.
    try {
      GetTLD200Response result = apiInstance.getTLD(tldId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#getTLD");
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
| **tldId** | **Integer**| Идентификатор доменной зоны. | |

### Return type

[**GetTLD200Response**](GetTLD200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;top_level_domain&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getTLDs"></a>
# **getTLDs**
> GetTLDs200Response getTLDs(isPublished, isRegistered)

Получить информацию о доменных зонах

Чтобы получить информацию о доменных зонах, отправьте запрос GET на &#x60;/api/v1/tlds&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    Boolean isPublished = true; // Boolean | Это логическое значение, которое показывает, опубликована ли доменная зона.
    Boolean isRegistered = true; // Boolean | Это логическое значение, которое показывает, зарегистрирована ли доменная зона.
    try {
      GetTLDs200Response result = apiInstance.getTLDs(isPublished, isRegistered);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#getTLDs");
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
| **isPublished** | **Boolean**| Это логическое значение, которое показывает, опубликована ли доменная зона. | [optional] |
| **isRegistered** | **Boolean**| Это логическое значение, которое показывает, зарегистрирована ли доменная зона. | [optional] |

### Return type

[**GetTLDs200Response**](GetTLDs200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;top_level_domains&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateDomainAutoProlongation"></a>
# **updateDomainAutoProlongation**
> UpdateDomainAutoProlongation200Response updateDomainAutoProlongation(fqdn, updateDomain)

Включение/выключение автопродления домена

Чтобы включить/выключить автопродление домена, отправьте запрос PATCH на &#x60;/api/v1/domains/{fqdn}&#x60;, передав в теле запроса параметр &#x60;is_autoprolong_enabled&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    String fqdn = "somedomain.ru"; // String | Полное имя домена.
    UpdateDomain updateDomain = new UpdateDomain(); // UpdateDomain | 
    try {
      UpdateDomainAutoProlongation200Response result = apiInstance.updateDomainAutoProlongation(fqdn, updateDomain);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#updateDomainAutoProlongation");
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
| **fqdn** | **String**| Полное имя домена. | |
| **updateDomain** | [**UpdateDomain**](UpdateDomain.md)|  | |

### Return type

[**UpdateDomainAutoProlongation200Response**](UpdateDomainAutoProlongation200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;domain&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateDomainDNSRecord"></a>
# **updateDomainDNSRecord**
> CreateDomainDNSRecord201Response updateDomainDNSRecord(fqdn, recordId, createDns)

Обновить информацию о DNS-записи домена или поддомена

Чтобы обновить информацию о DNS-записи для домена или поддомена, отправьте запрос PATCH на &#x60;/api/v1/domains/{fqdn}/dns-records/{record_id}&#x60;, задав необходимые атрибуты.  DNS-запись будет обновлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией об добавленной DNS-записи.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    String fqdn = "somedomain.ru"; // String | Полное имя домена или поддомена.
    Integer recordId = 123; // Integer | Идентификатор DNS-записи домена или поддомена.
    CreateDns createDns = new CreateDns(); // CreateDns | 
    try {
      CreateDomainDNSRecord201Response result = apiInstance.updateDomainDNSRecord(fqdn, recordId, createDns);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#updateDomainDNSRecord");
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
| **fqdn** | **String**| Полное имя домена или поддомена. | |
| **recordId** | **Integer**| Идентификатор DNS-записи домена или поддомена. | |
| **createDns** | [**CreateDns**](CreateDns.md)|  | |

### Return type

[**CreateDomainDNSRecord201Response**](CreateDomainDNSRecord201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateDomainNameServers"></a>
# **updateDomainNameServers**
> GetDomainNameServers200Response updateDomainNameServers(fqdn, updateDomainNameServers)

Изменение name-серверов домена

Чтобы изменить name-серверы домена, отправьте запрос PUT на &#x60;/api/v1/domains/{fqdn}/name-servers&#x60;, задав необходимые атрибуты.  Name-серверы будут изменены с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о name-серверах домена.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    String fqdn = "somedomain.ru"; // String | Полное имя домена.
    UpdateDomainNameServers updateDomainNameServers = new UpdateDomainNameServers(); // UpdateDomainNameServers | 
    try {
      GetDomainNameServers200Response result = apiInstance.updateDomainNameServers(fqdn, updateDomainNameServers);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#updateDomainNameServers");
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
| **fqdn** | **String**| Полное имя домена. | |
| **updateDomainNameServers** | [**UpdateDomainNameServers**](UpdateDomainNameServers.md)|  | |

### Return type

[**GetDomainNameServers200Response**](GetDomainNameServers200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;name_servers&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateDomainRequest"></a>
# **updateDomainRequest**
> CreateDomainRequest201Response updateDomainRequest(requestId, use)

Оплата/обновление заявки на регистрацию/продление/трансфер домена

Чтобы оплатить/обновить заявку на регистрацию/продление/трансфер домена, отправьте запрос PATCH на &#x60;/api/v1/domains-requests/{request_id}&#x60;, задав необходимые атрибуты.  Заявка будет обновлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о обновленной заявке.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DomainsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    DomainsApi apiInstance = new DomainsApi(defaultClient);
    Integer requestId = 123; // Integer | Идентификатор заявки на регистрацию/продление/трансфер домена.
    Use use = new Use(); // Use | 
    try {
      CreateDomainRequest201Response result = apiInstance.updateDomainRequest(requestId, use);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DomainsApi#updateDomainRequest");
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
| **requestId** | **Integer**| Идентификатор заявки на регистрацию/продление/трансфер домена. | |
| **use** | [**Use**](Use.md)|  | |

### Return type

[**CreateDomainRequest201Response**](CreateDomainRequest201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

