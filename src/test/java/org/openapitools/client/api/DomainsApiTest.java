/*
 * Timeweb Cloud API
 * # Введение API Timeweb Cloud позволяет вам управлять ресурсами в облаке программным способом с использованием обычных HTTP-запросов.  Множество функций, которые доступны в панели управления Timeweb Cloud, также доступны через API, что позволяет вам автоматизировать ваши собственные сценарии.  В этой документации сперва будет описан общий дизайн и принципы работы API, а после этого конкретные конечные точки. Также будут приведены примеры запросов к ним.   ## Запросы Запросы должны выполняться по протоколу `HTTPS`, чтобы гарантировать шифрование транзакций. Поддерживаются следующие методы запроса: |Метод|Применение| |--- |--- | |GET|Извлекает данные о коллекциях и отдельных ресурсах.| |POST|Для коллекций создает новый ресурс этого типа. Также используется для выполнения действий с конкретным ресурсом.| |PUT|Обновляет существующий ресурс.| |PATCH|Некоторые ресурсы поддерживают частичное обновление, то есть обновление только части атрибутов ресурса, в этом случае вместо метода PUT будет использован PATCH.| |DELETE|Удаляет ресурс.|  Методы `POST`, `PUT` и `PATCH` могут включать объект в тело запроса с типом содержимого `application/json`.  ### Параметры в запросах Некоторые коллекции поддерживают пагинацию, поиск или сортировку в запросах. В параметрах запроса требуется передать: - `limit` — обозначает количество записей, которое необходимо вернуть  - `offset` — указывает на смещение, относительно начала списка  - `search` — позволяет указать набор символов для поиска  - `sort` — можно задать правило сортировки коллекции  ## Ответы Запросы вернут один из следующих кодов состояния ответа HTTP:  |Статус|Описание| |--- |--- | |200 OK|Действие с ресурсом было выполнено успешно.| |201 Created|Ресурс был успешно создан. При этом ресурс может быть как уже готовым к использованию, так и находиться в процессе запуска.| |204 No Content|Действие с ресурсом было выполнено успешно, и ответ не содержит дополнительной информации в теле.| |400 Bad Request|Был отправлен неверный запрос, например, в нем отсутствуют обязательные параметры и т. д. Тело ответа будет содержать дополнительную информацию об ошибке.| |401 Unauthorized|Ошибка аутентификации.| |403 Forbidden|Аутентификация прошла успешно, но недостаточно прав для выполнения действия.| |404 Not Found|Запрашиваемый ресурс не найден.| |409 Conflict|Запрос конфликтует с текущим состоянием.| |423 Locked|Ресурс из запроса заблокирован от применения к нему указанного метода.| |429 Too Many Requests|Был достигнут лимит по количеству запросов в единицу времени.| |500 Internal Server Error|При выполнении запроса произошла какая-то внутренняя ошибка. Чтобы решить эту проблему, лучше всего создать тикет в панели управления.|  ### Структура успешного ответа Все конечные точки будут возвращать данные в формате `JSON`. Ответы на `GET`-запросы будут иметь на верхнем уровне следующую структуру атрибутов:  |Название поля|Тип|Описание| |--- |--- |--- | |[entity_name]|object, object[], string[], number[], boolean|Динамическое поле, которое будет меняться в зависимости от запрашиваемого ресурса и будет содержать все атрибуты, необходимые для описания этого ресурса. Например, при запросе списка баз данных будет возвращаться поле `dbs`, а при запросе конкретного облачного сервера `server`. Для некоторых конечных точек в ответе может возвращаться сразу несколько ресурсов.| |meta|object|Опционально. Объект, который содержит вспомогательную информацию о ресурсе. Чаще всего будет встречаться при запросе коллекций и содержать поле `total`, которое будет указывать на количество элементов в коллекции.| |response_id|string|Опционально. В большинстве случаев в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее. Также вы можете использовать этот идентификатор, чтобы убедиться, что это новый ответ на запрос и результат не был получен из кэша.|  Пример запроса на получение списка SSH-ключей: ```     HTTP/2.0 200 OK     {       \"ssh_keys\":[           {             \"body\":\"ssh-rsa AAAAB3NzaC1sdfghjkOAsBwWhs= example@device.local\",             \"created_at\":\"2021-09-15T19:52:27Z\",             \"expired_at\":null,             \"id\":5297,             \"is_default\":false,             \"name\":\"example@device.local\",             \"used_at\":null,             \"used_by\":[]           }       ],       \"meta\":{           \"total\":1       },       \"response_id\":\"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ### Структура ответа с ошибкой |Название поля|Тип|Описание| |--- |--- |--- | |status_code|number|Короткий числовой идентификатор ошибки.| |error_code|string|Короткий текстовый идентификатор ошибки, который уточняет числовой идентификатор и удобен для программной обработки. Самый простой пример — это код `not_found` для ошибки 404.| |message|string, string[]|Опционально. В большинстве случаев в ответе будет содержаться человекочитаемое подробное описание ошибки или ошибок, которые помогут понять, что нужно исправить.| |response_id|string|Опционально. В большинстве случае в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее.|  Пример: ```     HTTP/2.0 403 Forbidden     {       \"status_code\": 403,       \"error_code\":  \"forbidden\",       \"message\":     \"You do not have access for the attempted action\",       \"response_id\": \"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ## Статусы ресурсов Важно учесть, что при создании большинства ресурсов внутри платформы вам будет сразу возвращен ответ от сервера со статусом `200 OK` или `201 Created` и идентификатором созданного ресурса в теле ответа, но при этом этот ресурс может быть ещё в *состоянии запуска*.  Для того чтобы понять, в каком состоянии сейчас находится ваш ресурс, мы добавили поле `status` в ответ на получение информации о ресурсе.  Список статусов будет отличаться в зависимости от типа ресурса. Увидеть поддерживаемый список статусов вы сможете в описании каждого конкретного ресурса.     ## Ограничение скорости запросов (Rate Limiting) Чтобы обеспечить стабильность для всех пользователей, Timeweb Cloud защищает API от всплесков входящего трафика, анализируя количество запросов c каждого аккаунта к каждой конечной точке.  Если ваше приложение отправляет более 20 запросов в секунду на одну конечную точку, то для этого запроса API может вернуть код состояния HTTP `429 Too Many Requests`.   ## Аутентификация Доступ к API осуществляется с помощью JWT-токена. Токенами можно управлять внутри панели управления Timeweb Cloud в разделе *API и Terraform*.  Токен необходимо передавать в заголовке каждого запроса в формате: ```   Authorization: Bearer $TIMEWEB_CLOUD_TOKEN ```  ## Формат примеров API Примеры в этой документации описаны с помощью `curl`, HTTP-клиента командной строки. На компьютерах `Linux` и `macOS` обычно по умолчанию установлен `curl`, и он доступен для загрузки на всех популярных платформах, включая `Windows`.  Каждый пример разделен на несколько строк символом `\\`, который совместим с `bash`. Типичный пример выглядит так: ```   curl -X PATCH      -H \"Content-Type: application/json\"      -H \"Authorization: Bearer $TIMEWEB_CLOUD_TOKEN\"      -d '{\"name\":\"Cute Corvus\",\"comment\":\"Development Server\"}'      \"https://api.timeweb.cloud/api/v1/dedicated/1051\" ``` - Параметр `-X` задает метод запроса. Для согласованности метод будет указан во всех примерах, даже если он явно не требуется для методов `GET`. - Строки `-H` задают требуемые HTTP-заголовки. - Примеры, для которых требуется объект JSON в теле запроса, передают требуемые данные через параметр `-d`.  Чтобы использовать приведенные примеры, не подставляя каждый раз в них свой токен, вы можете добавить токен один раз в переменные окружения в вашей консоли. Например, на `Linux` это можно сделать с помощью команды:  ``` TIMEWEB_CLOUD_TOKEN=\"token\" ```  После этого токен будет автоматически подставляться в ваши запросы.  Обратите внимание, что все значения в этой документации являются примерами. Не полагайтесь на идентификаторы операционных систем, тарифов и т.д., используемые в примерах. Используйте соответствующую конечную точку для получения значений перед созданием ресурсов.   ## Версионирование API построено согласно принципам [семантического версионирования](https://semver.org/lang/ru). Это значит, что мы гарантируем обратную совместимость всех изменений в пределах одной мажорной версии.  Мажорная версия каждой конечной точки обозначается в пути запроса, например, запрос `/api/v1/servers` указывает, что этот метод имеет версию 1.
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: info@timeweb.cloud
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.AddSubdomain201Response;
import org.openapitools.client.model.CheckDomain200Response;
import org.openapitools.client.model.CreateDatabaseBackup409Response;
import org.openapitools.client.model.CreateDns;
import org.openapitools.client.model.CreateDomainDNSRecord201Response;
import org.openapitools.client.model.CreateDomainRequest201Response;
import org.openapitools.client.model.DomainRegister;
import org.openapitools.client.model.GetDomain200Response;
import org.openapitools.client.model.GetDomainDNSRecords200Response;
import org.openapitools.client.model.GetDomainNameServers200Response;
import org.openapitools.client.model.GetDomainRequests200Response;
import org.openapitools.client.model.GetDomains200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import org.openapitools.client.model.GetTLD200Response;
import org.openapitools.client.model.GetTLDs200Response;
import org.openapitools.client.model.UpdateDomain;
import org.openapitools.client.model.UpdateDomainAutoProlongation200Response;
import org.openapitools.client.model.UpdateDomainNameServers;
import org.openapitools.client.model.Use;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for DomainsApi
 */
@Disabled
public class DomainsApiTest {

    private final DomainsApi api = new DomainsApi();

    /**
     * Добавление домена на аккаунт
     *
     * Чтобы добавить домен на свой аккаунт, отправьте запрос POST на &#x60;/api/v1/add-domain/{fqdn}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addDomainTest() throws ApiException {
        String fqdn = null;
        api.addDomain(fqdn);
        // TODO: test validations
    }

    /**
     * Добавление поддомена
     *
     * Чтобы добавить поддомен, отправьте запрос POST на &#x60;/api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn}&#x60;, задав необходимые атрибуты.  Поддомен будет добавлен с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о добавленном поддомене.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addSubdomainTest() throws ApiException {
        String fqdn = null;
        String subdomainFqdn = null;
        AddSubdomain201Response response = api.addSubdomain(fqdn, subdomainFqdn);
        // TODO: test validations
    }

    /**
     * Проверить, доступен ли домен для регистрации
     *
     * Чтобы проверить, доступен ли домен для регистрации, отправьте запрос GET на &#x60;/api/v1/check-domain/{fqdn}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void checkDomainTest() throws ApiException {
        String fqdn = null;
        CheckDomain200Response response = api.checkDomain(fqdn);
        // TODO: test validations
    }

    /**
     * Добавить информацию о DNS-записи для домена или поддомена
     *
     * Чтобы добавить информацию о DNS-записи для домена или поддомена, отправьте запрос POST на &#x60;/api/v1/domains/{fqdn}/dns-records&#x60;, задав необходимые атрибуты.  DNS-запись будет добавлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о добавленной DNS-записи.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createDomainDNSRecordTest() throws ApiException {
        String fqdn = null;
        CreateDns createDns = null;
        CreateDomainDNSRecord201Response response = api.createDomainDNSRecord(fqdn, createDns);
        // TODO: test validations
    }

    /**
     * Создание заявки на регистрацию/продление/трансфер домена
     *
     * Чтобы создать заявку на регистрацию/продление/трансфер домена, отправьте POST-запрос в &#x60;api/v1/domains-requests&#x60;, задав необходимые атрибуты.  Заявка будет создана с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданной заявке.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createDomainRequestTest() throws ApiException {
        DomainRegister domainRegister = null;
        CreateDomainRequest201Response response = api.createDomainRequest(domainRegister);
        // TODO: test validations
    }

    /**
     * Удаление домена
     *
     * Чтобы удалить домен, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteDomainTest() throws ApiException {
        String fqdn = null;
        api.deleteDomain(fqdn);
        // TODO: test validations
    }

    /**
     * Удалить информацию о DNS-записи для домена или поддомена
     *
     * Чтобы удалить информацию о DNS-записи для домена или поддомена, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}/dns-records/{record_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteDomainDNSRecordTest() throws ApiException {
        String fqdn = null;
        Integer recordId = null;
        api.deleteDomainDNSRecord(fqdn, recordId);
        // TODO: test validations
    }

    /**
     * Удаление поддомена
     *
     * Чтобы удалить поддомен, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteSubdomainTest() throws ApiException {
        String fqdn = null;
        String subdomainFqdn = null;
        api.deleteSubdomain(fqdn, subdomainFqdn);
        // TODO: test validations
    }

    /**
     * Получение информации о домене
     *
     * Чтобы отобразить информацию об отдельном домене, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDomainTest() throws ApiException {
        String fqdn = null;
        GetDomain200Response response = api.getDomain(fqdn);
        // TODO: test validations
    }

    /**
     * Получить информацию обо всех пользовательских DNS-записях домена или поддомена
     *
     * Чтобы получить информацию обо всех пользовательских DNS-записях домена или поддомена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/dns-records&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDomainDNSRecordsTest() throws ApiException {
        String fqdn = null;
        Integer limit = null;
        Integer offset = null;
        GetDomainDNSRecords200Response response = api.getDomainDNSRecords(fqdn, limit, offset);
        // TODO: test validations
    }

    /**
     * Получить информацию обо всех DNS-записях по умолчанию домена или поддомена
     *
     * Чтобы получить информацию обо всех DNS-записях по умолчанию домена или поддомена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/default-dns-records&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDomainDefaultDNSRecordsTest() throws ApiException {
        String fqdn = null;
        Integer limit = null;
        Integer offset = null;
        GetDomainDNSRecords200Response response = api.getDomainDefaultDNSRecords(fqdn, limit, offset);
        // TODO: test validations
    }

    /**
     * Получение списка name-серверов домена
     *
     * Чтобы получить список name-серверов домена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/name-servers&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDomainNameServersTest() throws ApiException {
        String fqdn = null;
        GetDomainNameServers200Response response = api.getDomainNameServers(fqdn);
        // TODO: test validations
    }

    /**
     * Получение заявки на регистрацию/продление/трансфер домена
     *
     * Чтобы получить заявку на регистрацию/продление/трансфер домена, отправьте запрос GET на &#x60;/api/v1/domains-requests/{request_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDomainRequestTest() throws ApiException {
        Integer requestId = null;
        CreateDomainRequest201Response response = api.getDomainRequest(requestId);
        // TODO: test validations
    }

    /**
     * Получение списка заявок на регистрацию/продление/трансфер домена
     *
     * Чтобы получить список заявок на регистрацию/продление/трансфер домена, отправьте запрос GET на &#x60;/api/v1/domains-requests&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDomainRequestsTest() throws ApiException {
        Integer personId = null;
        GetDomainRequests200Response response = api.getDomainRequests(personId);
        // TODO: test validations
    }

    /**
     * Получение списка всех доменов
     *
     * Чтобы получить список всех доменов на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/domains&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;domains&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDomainsTest() throws ApiException {
        Integer limit = null;
        Integer offset = null;
        String idnName = null;
        String linkedIp = null;
        String order = null;
        String sort = null;
        GetDomains200Response response = api.getDomains(limit, offset, idnName, linkedIp, order, sort);
        // TODO: test validations
    }

    /**
     * Получить информацию о доменной зоне по идентификатору
     *
     * Чтобы получить информацию о доменной зоне по идентификатору, отправьте запрос GET на &#x60;/api/v1/tlds/{tld_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getTLDTest() throws ApiException {
        Integer tldId = null;
        GetTLD200Response response = api.getTLD(tldId);
        // TODO: test validations
    }

    /**
     * Получить информацию о доменных зонах
     *
     * Чтобы получить информацию о доменных зонах, отправьте запрос GET на &#x60;/api/v1/tlds&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getTLDsTest() throws ApiException {
        Boolean isPublished = null;
        Boolean isRegistered = null;
        GetTLDs200Response response = api.getTLDs(isPublished, isRegistered);
        // TODO: test validations
    }

    /**
     * Включение/выключение автопродления домена
     *
     * Чтобы включить/выключить автопродление домена, отправьте запрос PATCH на &#x60;/api/v1/domains/{fqdn}&#x60;, передав в теле запроса параметр &#x60;is_autoprolong_enabled&#x60;
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateDomainAutoProlongationTest() throws ApiException {
        String fqdn = null;
        UpdateDomain updateDomain = null;
        UpdateDomainAutoProlongation200Response response = api.updateDomainAutoProlongation(fqdn, updateDomain);
        // TODO: test validations
    }

    /**
     * Обновить информацию о DNS-записи домена или поддомена
     *
     * Чтобы обновить информацию о DNS-записи для домена или поддомена, отправьте запрос PATCH на &#x60;/api/v1/domains/{fqdn}/dns-records/{record_id}&#x60;, задав необходимые атрибуты.  DNS-запись будет обновлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией об добавленной DNS-записи.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateDomainDNSRecordTest() throws ApiException {
        String fqdn = null;
        Integer recordId = null;
        CreateDns createDns = null;
        CreateDomainDNSRecord201Response response = api.updateDomainDNSRecord(fqdn, recordId, createDns);
        // TODO: test validations
    }

    /**
     * Изменение name-серверов домена
     *
     * Чтобы изменить name-серверы домена, отправьте запрос PUT на &#x60;/api/v1/domains/{fqdn}/name-servers&#x60;, задав необходимые атрибуты.  Name-серверы будут изменены с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о name-серверах домена.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateDomainNameServersTest() throws ApiException {
        String fqdn = null;
        UpdateDomainNameServers updateDomainNameServers = null;
        GetDomainNameServers200Response response = api.updateDomainNameServers(fqdn, updateDomainNameServers);
        // TODO: test validations
    }

    /**
     * Оплата/обновление заявки на регистрацию/продление/трансфер домена
     *
     * Чтобы оплатить/обновить заявку на регистрацию/продление/трансфер домена, отправьте запрос PATCH на &#x60;/api/v1/domains-requests/{request_id}&#x60;, задав необходимые атрибуты.  Заявка будет обновлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о обновленной заявке.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateDomainRequestTest() throws ApiException {
        Integer requestId = null;
        Use use = null;
        CreateDomainRequest201Response response = api.updateDomainRequest(requestId, use);
        // TODO: test validations
    }

}
