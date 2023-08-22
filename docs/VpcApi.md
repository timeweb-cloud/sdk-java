# VpcApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createVPC**](VpcApi.md#createVPC) | **POST** /api/v2/vpcs | Создание VPC |
| [**deleteVPC**](VpcApi.md#deleteVPC) | **DELETE** /api/v1/vpcs/{vpc_id} | Удаление VPC по идентификатору сети |
| [**getVPC**](VpcApi.md#getVPC) | **GET** /api/v2/vpcs/{vpc_id} | Получение VPC |
| [**getVPCPorts**](VpcApi.md#getVPCPorts) | **GET** /api/v1/vpcs/{vpc_id}/ports | Получение списка портов для VPC |
| [**getVPCServices**](VpcApi.md#getVPCServices) | **GET** /api/v2/vpcs/{vpc_id}/services | Получение списка сервисов в VPC |
| [**getVPCs**](VpcApi.md#getVPCs) | **GET** /api/v2/vpcs | Получение списка VPCs |
| [**updateVPCs**](VpcApi.md#updateVPCs) | **PATCH** /api/v2/vpcs/{vpc_id} | Изменение VPC по идентификатору сети |


<a id="createVPC"></a>
# **createVPC**
> CreateVPC201Response createVPC(createVpc)

Создание VPC

Чтобы создать создать VPC, отправьте POST-запрос в &#x60;/api/v2/vpcs&#x60;, задав необходимые атрибуты.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.VpcApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    VpcApi apiInstance = new VpcApi(defaultClient);
    CreateVpc createVpc = new CreateVpc(); // CreateVpc | 
    try {
      CreateVPC201Response result = apiInstance.createVPC(createVpc);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling VpcApi#createVPC");
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
| **createVpc** | [**CreateVpc**](CreateVpc.md)|  | |

### Return type

[**CreateVPC201Response**](CreateVPC201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;vpc&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteVPC"></a>
# **deleteVPC**
> CreateVPC201Response deleteVPC(vpcId)

Удаление VPC по идентификатору сети

Чтобы удалить VPC, отправьте DELETE-запрос на &#x60;/api/v1/vpcs/{vpc_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.VpcApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    VpcApi apiInstance = new VpcApi(defaultClient);
    String vpcId = "network-1234567890"; // String | Идентификатор сети
    try {
      CreateVPC201Response result = apiInstance.deleteVPC(vpcId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling VpcApi#deleteVPC");
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
| **vpcId** | **String**| Идентификатор сети | |

### Return type

[**CreateVPC201Response**](CreateVPC201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Объект JSON c ключом &#x60;vpc&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getVPC"></a>
# **getVPC**
> CreateVPC201Response getVPC(vpcId)

Получение VPC

Чтобы отобразить информацию об отдельном VPC, отправьте запрос GET на &#x60;api/v2/vpcs/{vpc_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.VpcApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    VpcApi apiInstance = new VpcApi(defaultClient);
    String vpcId = "network-1234567890"; // String | Идентификатор сети
    try {
      CreateVPC201Response result = apiInstance.getVPC(vpcId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling VpcApi#getVPC");
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
| **vpcId** | **String**| Идентификатор сети | |

### Return type

[**CreateVPC201Response**](CreateVPC201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;vpc&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getVPCPorts"></a>
# **getVPCPorts**
> GetVPCPorts200Response getVPCPorts(vpcId)

Получение списка портов для VPC

Чтобы получить список портов для VPC, отправьте GET-запрос на &#x60;/api/v1/vpcs/{vpc_id}/ports&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.VpcApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    VpcApi apiInstance = new VpcApi(defaultClient);
    String vpcId = "network-1234567890"; // String | Идентификатор сети
    try {
      GetVPCPorts200Response result = apiInstance.getVPCPorts(vpcId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling VpcApi#getVPCPorts");
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
| **vpcId** | **String**| Идентификатор сети | |

### Return type

[**GetVPCPorts200Response**](GetVPCPorts200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;vpc_ports&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getVPCServices"></a>
# **getVPCServices**
> GetVPCServices200Response getVPCServices(vpcId)

Получение списка сервисов в VPC

Чтобы получить список сервисов, отправьте GET-запрос на &#x60;/api/v2/vpcs/{vpc_id}/services&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.VpcApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    VpcApi apiInstance = new VpcApi(defaultClient);
    String vpcId = "network-1234567890"; // String | Идентификатор сети
    try {
      GetVPCServices200Response result = apiInstance.getVPCServices(vpcId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling VpcApi#getVPCServices");
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
| **vpcId** | **String**| Идентификатор сети | |

### Return type

[**GetVPCServices200Response**](GetVPCServices200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;services&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getVPCs"></a>
# **getVPCs**
> GetVPCs200Response getVPCs()

Получение списка VPCs

Чтобы получить список VPCs, отправьте GET-запрос на &#x60;/api/v2/vpcs&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.VpcApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    VpcApi apiInstance = new VpcApi(defaultClient);
    try {
      GetVPCs200Response result = apiInstance.getVPCs();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling VpcApi#getVPCs");
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

[**GetVPCs200Response**](GetVPCs200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;vpcs&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateVPCs"></a>
# **updateVPCs**
> CreateVPC201Response updateVPCs(vpcId, updateVpc)

Изменение VPC по идентификатору сети

Чтобы изменить VPC, отправьте PATCH-запрос на &#x60;/api/v2/vpcs/{vpc_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.VpcApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    VpcApi apiInstance = new VpcApi(defaultClient);
    String vpcId = "network-1234567890"; // String | Идентификатор сети
    UpdateVpc updateVpc = new UpdateVpc(); // UpdateVpc | 
    try {
      CreateVPC201Response result = apiInstance.updateVPCs(vpcId, updateVpc);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling VpcApi#updateVPCs");
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
| **vpcId** | **String**| Идентификатор сети | |
| **updateVpc** | [**UpdateVpc**](UpdateVpc.md)|  | |

### Return type

[**CreateVPC201Response**](CreateVPC201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;vpc&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

