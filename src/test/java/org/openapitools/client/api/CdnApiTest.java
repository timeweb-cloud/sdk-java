/*
 * Timeweb Cloud API
 * # Введение API Timeweb Cloud позволяет вам управлять ресурсами в облаке программным способом с использованием обычных HTTP-запросов.  Множество функций, которые доступны в панели управления Timeweb Cloud, также доступны через API, что позволяет вам автоматизировать ваши собственные сценарии.  В этой документации сперва будет описан общий дизайн и принципы работы API, а после этого конкретные конечные точки. Также будут приведены примеры запросов к ним.   ## Запросы Запросы должны выполняться по протоколу `HTTPS`, чтобы гарантировать шифрование транзакций. Поддерживаются следующие методы запроса: |Метод|Применение| |--- |--- | |GET|Извлекает данные о коллекциях и отдельных ресурсах.| |POST|Для коллекций создает новый ресурс этого типа. Также используется для выполнения действий с конкретным ресурсом.| |PUT|Обновляет существующий ресурс.| |PATCH|Некоторые ресурсы поддерживают частичное обновление, то есть обновление только части атрибутов ресурса, в этом случае вместо метода PUT будет использован PATCH.| |DELETE|Удаляет ресурс.|  Методы `POST`, `PUT` и `PATCH` могут включать объект в тело запроса с типом содержимого `application/json`.  ### Параметры в запросах Некоторые коллекции поддерживают пагинацию, поиск или сортировку в запросах. В параметрах запроса требуется передать: - `limit` — обозначает количество записей, которое необходимо вернуть  - `offset` — указывает на смещение, относительно начала списка  - `search` — позволяет указать набор символов для поиска  - `sort` — можно задать правило сортировки коллекции  ## Ответы Запросы вернут один из следующих кодов состояния ответа HTTP:  |Статус|Описание| |--- |--- | |200 OK|Действие с ресурсом было выполнено успешно.| |201 Created|Ресурс был успешно создан. При этом ресурс может быть как уже готовым к использованию, так и находиться в процессе запуска.| |204 No Content|Действие с ресурсом было выполнено успешно, и ответ не содержит дополнительной информации в теле.| |400 Bad Request|Был отправлен неверный запрос, например, в нем отсутствуют обязательные параметры и т. д. Тело ответа будет содержать дополнительную информацию об ошибке.| |401 Unauthorized|Ошибка аутентификации.| |403 Forbidden|Аутентификация прошла успешно, но недостаточно прав для выполнения действия.| |404 Not Found|Запрашиваемый ресурс не найден.| |409 Conflict|Запрос конфликтует с текущим состоянием.| |423 Locked|Ресурс из запроса заблокирован от применения к нему указанного метода.| |429 Too Many Requests|Был достигнут лимит по количеству запросов в единицу времени.| |500 Internal Server Error|При выполнении запроса произошла какая-то внутренняя ошибка. Чтобы решить эту проблему, лучше всего создать тикет в панели управления.|  ### Структура успешного ответа Все конечные точки будут возвращать данные в формате `JSON`. Ответы на `GET`-запросы будут иметь на верхнем уровне следующую структуру атрибутов:  |Название поля|Тип|Описание| |--- |--- |--- | |[entity_name]|object, object[], string[], number[], boolean|Динамическое поле, которое будет меняться в зависимости от запрашиваемого ресурса и будет содержать все атрибуты, необходимые для описания этого ресурса. Например, при запросе списка баз данных будет возвращаться поле `dbs`, а при запросе конкретного облачного сервера `server`. Для некоторых конечных точек в ответе может возвращаться сразу несколько ресурсов.| |meta|object|Опционально. Объект, который содержит вспомогательную информацию о ресурсе. Чаще всего будет встречаться при запросе коллекций и содержать поле `total`, которое будет указывать на количество элементов в коллекции.| |response_id|string|Опционально. В большинстве случаев в ответе будет содержаться ID ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот ID— так мы сможем найти ответ на него намного быстрее. Также вы можете использовать этот ID, чтобы убедиться, что это новый ответ на запрос и результат не был получен из кэша.|  Пример запроса на получение списка SSH-ключей: ```     HTTP/2.0 200 OK     {       \"ssh_keys\":[           {             \"body\":\"ssh-rsa AAAAB3NzaC1sdfghjkOAsBwWhs= example@device.local\",             \"created_at\":\"2021-09-15T19:52:27Z\",             \"expired_at\":null,             \"id\":5297,             \"is_default\":false,             \"name\":\"example@device.local\",             \"used_at\":null,             \"used_by\":[]           }       ],       \"meta\":{           \"total\":1       },       \"response_id\":\"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ### Структура ответа с ошибкой |Название поля|Тип|Описание| |--- |--- |--- | |status_code|number|Короткий числовой идентификатор ошибки.| |error_code|string|Короткий текстовый идентификатор ошибки, который уточняет числовой идентификатор и удобен для программной обработки. Самый простой пример — это код `not_found` для ошибки 404.| |message|string, string[]|Опционально. В большинстве случаев в ответе будет содержаться человекочитаемое подробное описание ошибки или ошибок, которые помогут понять, что нужно исправить.| |response_id|string|Опционально. В большинстве случае в ответе будет содержаться ID ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот ID — так мы сможем найти ответ на него намного быстрее.|  Пример: ```     HTTP/2.0 403 Forbidden     {       \"status_code\": 403,       \"error_code\":  \"forbidden\",       \"message\":     \"You do not have access for the attempted action\",       \"response_id\": \"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ## Статусы ресурсов Важно учесть, что при создании большинства ресурсов внутри платформы вам будет сразу возвращен ответ от сервера со статусом `200 OK` или `201 Created` и ID созданного ресурса в теле ответа, но при этом этот ресурс может быть ещё в *состоянии запуска*.  Для того чтобы понять, в каком состоянии сейчас находится ваш ресурс, мы добавили поле `status` в ответ на получение информации о ресурсе.  Список статусов будет отличаться в зависимости от типа ресурса. Увидеть поддерживаемый список статусов вы сможете в описании каждого конкретного ресурса.     ## Ограничение скорости запросов (Rate Limiting) Чтобы обеспечить стабильность для всех пользователей, Timeweb Cloud защищает API от всплесков входящего трафика, анализируя количество запросов c каждого аккаунта к каждой конечной точке.  Если ваше приложение отправляет более 20 запросов в секунду на одну конечную точку, то для этого запроса API может вернуть код состояния HTTP `429 Too Many Requests`.   ## Аутентификация Доступ к API осуществляется с помощью JWT-токена. Токенами можно управлять внутри панели управления Timeweb Cloud в разделе *API и Terraform*.  Токен необходимо передавать в заголовке каждого запроса в формате: ```   Authorization: Bearer $TIMEWEB_CLOUD_TOKEN ```  ## Формат примеров API Примеры в этой документации описаны с помощью `curl`, HTTP-клиента командной строки. На компьютерах `Linux` и `macOS` обычно по умолчанию установлен `curl`, и он доступен для загрузки на всех популярных платформах, включая `Windows`.  Каждый пример разделен на несколько строк символом `\\`, который совместим с `bash`. Типичный пример выглядит так: ```   curl -X PATCH      -H \"Content-Type: application/json\"      -H \"Authorization: Bearer $TIMEWEB_CLOUD_TOKEN\"      -d '{\"name\":\"Cute Corvus\",\"comment\":\"Development Server\"}'      \"https://api.timeweb.cloud/api/v1/dedicated/1051\" ``` - Параметр `-X` задает метод запроса. Для согласованности метод будет указан во всех примерах, даже если он явно не требуется для методов `GET`. - Строки `-H` задают требуемые HTTP-заголовки. - Примеры, для которых требуется объект JSON в теле запроса, передают требуемые данные через параметр `-d`.  Чтобы использовать приведенные примеры, не подставляя каждый раз в них свой токен, вы можете добавить токен один раз в переменные окружения в вашей консоли. Например, на `Linux` это можно сделать с помощью команды:  ``` TIMEWEB_CLOUD_TOKEN=\"token\" ```  После этого токен будет автоматически подставляться в ваши запросы.  Обратите внимание, что все значения в этой документации являются примерами. Не полагайтесь на IDы операционных систем, тарифов и т.д., используемые в примерах. Используйте соответствующую конечную точку для получения значений перед созданием ресурсов.   ## Версионирование API построено согласно принципам [семантического версионирования](https://semver.org/lang/ru). Это значит, что мы гарантируем обратную совместимость всех изменений в пределах одной мажорной версии.  Мажорная версия каждой конечной точки обозначается в пути запроса, например, запрос `/api/v1/servers` указывает, что этот метод имеет версию 1.
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
import org.openapitools.client.model.AddCdnCertificate422Response;
import org.openapitools.client.model.AddCertificate;
import org.openapitools.client.model.ClearCache;
import org.openapitools.client.model.CreateCdnResource201Response;
import org.openapitools.client.model.CreateHttpResource;
import org.openapitools.client.model.GetAccountStatus403Response;
import org.openapitools.client.model.GetCdnCertificateTasks200Response;
import org.openapitools.client.model.GetCdnCertificates200Response;
import org.openapitools.client.model.GetCdnOriginNodes200Response;
import org.openapitools.client.model.GetCdnPresets200Response;
import org.openapitools.client.model.GetCdnResourceConfiguration200Response;
import org.openapitools.client.model.GetCdnResourceNodes200Response;
import org.openapitools.client.model.GetCdnResourceStatistics200Response;
import org.openapitools.client.model.GetCdnResources200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import org.openapitools.client.model.IssueCertificate;
import java.time.OffsetDateTime;
import org.openapitools.client.model.PreloadCache;
import org.openapitools.client.model.UpdateDatabaseInstance409Response;
import org.openapitools.client.model.UpdateHttpResource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for CdnApi
 */
@Disabled
public class CdnApiTest {

    private final CdnApi api = new CdnApi();

    /**
     * Загрузка собственного сертификата CDN
     *
     * Чтобы загрузить собственный SSL-сертификат, отправьте POST-запрос на &#x60;/api/v1/cdn/certificates&#x60;.  После загрузки сертификат появится в списке &#x60;/api/v1/cdn/certificates&#x60; — привязать его к ресурсу можно, передав его ID в поле &#x60;config.security.certificate_id&#x60; PATCH-запроса на &#x60;/api/v1/cdn/http-resources/{resource_id}&#x60;.  Если сертификат или приватный ключ не проходят проверку — например, истек срок действия или ключ не соответствует сертификату — вернется ошибка &#x60;422&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addCdnCertificateTest() throws ApiException {
        AddCertificate addCertificate = null;
        api.addCdnCertificate(addCertificate);
        // TODO: test validations
    }

    /**
     * Архивация задачи на выпуск сертификата
     *
     * Чтобы убрать из списка задачу на выпуск сертификата, отправьте POST-запрос на &#x60;/api/v1/cdn/certificates/tasks/{task_id}/archive&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void archiveCdnCertificateTaskTest() throws ApiException {
        Integer taskId = null;
        api.archiveCdnCertificateTask(taskId);
        // TODO: test validations
    }

    /**
     * Очистка кэша CDN-ресурса
     *
     * Чтобы очистить кэш на узлах CDN, отправьте POST-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}/clear-cache&#x60;.  При &#x60;purge_type&#x60; &#x3D; &#x60;full&#x60; очищается весь кэш ресурса, при &#x60;purge_type&#x60; &#x3D; &#x60;partial&#x60; — только файлы из списка &#x60;paths&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void clearCdnResourceCacheTest() throws ApiException {
        Integer resourceId = null;
        ClearCache clearCache = null;
        api.clearCdnResourceCache(resourceId, clearCache);
        // TODO: test validations
    }

    /**
     * Создание CDN-ресурса
     *
     * Чтобы создать CDN-ресурс, отправьте POST-запрос на &#x60;/api/v1/cdn/http-resources&#x60;.  Источник контента задается ровно одним из полей: &#x60;storage_id&#x60; для S3-хранилища или &#x60;server&#x60; для произвольного origin-сервера. Если ни одно из них не передано, вернется ошибка &#x60;400&#x60;.  Сразу после создания ресурсу выдается технический домен &#x60;cdn_domain&#x60;, а сам ресурс какое-то время находится в статусе &#x60;processing&#x60;, пока конфигурация применяется на узлах CDN.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createCdnResourceTest() throws ApiException {
        CreateHttpResource createHttpResource = null;
        CreateCdnResource201Response response = api.createCdnResource(createHttpResource);
        // TODO: test validations
    }

    /**
     * Удаление сертификата CDN
     *
     * Чтобы удалить SSL-сертификат, отправьте DELETE-запрос на &#x60;/api/v1/cdn/certificates/{certificate_id}&#x60;.  Если сертификат привязан к CDN-ресурсу, вернется ошибка &#x60;409&#x60; — сначала отвяжите его, передав &#x60;config.security.certificate_id&#x60; &#x3D; &#x60;null&#x60; в PATCH-запросе на &#x60;/api/v1/cdn/http-resources/{resource_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteCdnCertificateTest() throws ApiException {
        Integer certificateId = null;
        api.deleteCdnCertificate(certificateId);
        // TODO: test validations
    }

    /**
     * Удаление CDN-ресурса
     *
     * Чтобы удалить CDN-ресурс, отправьте DELETE-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}&#x60;. Вместе с ресурсом освобождается его технический домен, а привязанный сертификат отвязывается.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteCdnResourceTest() throws ApiException {
        Integer resourceId = null;
        api.deleteCdnResource(resourceId);
        // TODO: test validations
    }

    /**
     * Получение списка задач на выпуск сертификатов
     *
     * Чтобы получить список задач на выпуск сертификатов Let&#39;s Encrypt, отправьте GET-запрос на &#x60;/api/v1/cdn/certificates/tasks&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getCdnCertificateTasksTest() throws ApiException {
        Integer resourceId = null;
        GetCdnCertificateTasks200Response response = api.getCdnCertificateTasks(resourceId);
        // TODO: test validations
    }

    /**
     * Получение списка сертификатов CDN
     *
     * Чтобы получить список SSL-сертификатов, доступных для доменов CDN-ресурсов, отправьте GET-запрос на &#x60;/api/v1/cdn/certificates&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getCdnCertificatesTest() throws ApiException {
        Integer resourceId = null;
        GetCdnCertificates200Response response = api.getCdnCertificates(resourceId);
        // TODO: test validations
    }

    /**
     * Получение списка подсетей узлов CDN
     *
     * Чтобы получить список IP-адресов и подсетей, с которых узлы CDN обращаются к источнику контента, отправьте GET-запрос на &#x60;/api/v1/cdn/nodes/origin&#x60;. Этот список удобно использовать, чтобы разрешить доступ к origin-серверу только для узлов CDN.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getCdnOriginNodesTest() throws ApiException {
        Boolean withExtraZones = null;
        GetCdnOriginNodes200Response response = api.getCdnOriginNodes(withExtraZones);
        // TODO: test validations
    }

    /**
     * Получение списка тарифов CDN
     *
     * Чтобы получить список доступных тарифов CDN, отправьте GET-запрос на &#x60;/api/v1/cdn/presets&#x60;. ID тарифа из этого списка указывается в поле &#x60;preset_id&#x60; при создании и изменении ресурса.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getCdnPresetsTest() throws ApiException {
        GetCdnPresets200Response response = api.getCdnPresets();
        // TODO: test validations
    }

    /**
     * Получение CDN-ресурса
     *
     * Чтобы получить информацию об отдельном CDN-ресурсе, отправьте GET-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getCdnResourceTest() throws ApiException {
        Integer resourceId = null;
        CreateCdnResource201Response response = api.getCdnResource(resourceId);
        // TODO: test validations
    }

    /**
     * Получение конфигурации CDN-ресурса
     *
     * Чтобы получить текущую конфигурацию CDN-ресурса, отправьте GET-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}/configuration&#x60;.  Изменить конфигурацию можно в поле &#x60;config&#x60; PATCH-запроса на &#x60;/api/v1/cdn/http-resources/{resource_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getCdnResourceConfigurationTest() throws ApiException {
        Integer resourceId = null;
        GetCdnResourceConfiguration200Response response = api.getCdnResourceConfiguration(resourceId);
        // TODO: test validations
    }

    /**
     * Получение списка раздающих узлов CDN-ресурса
     *
     * Чтобы получить список узлов, которые раздают контент доменов ресурса, отправьте GET-запрос на &#x60;/api/v1/cdn/nodes/http-resources/{resource_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getCdnResourceNodesTest() throws ApiException {
        Integer resourceId = null;
        Boolean withExtraZones = null;
        List<String> country = null;
        GetCdnResourceNodes200Response response = api.getCdnResourceNodes(resourceId, withExtraZones, country);
        // TODO: test validations
    }

    /**
     * Получение статистики CDN-ресурса
     *
     * Чтобы получить статистику трафика и запросов CDN-ресурса, отправьте GET-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}/statistics&#x60;.  Данные возвращаются с разбивкой по часовым интервалам. Если период не указан, вернется статистика за последние 6 часов.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getCdnResourceStatisticsTest() throws ApiException {
        Integer resourceId = null;
        OffsetDateTime from = null;
        OffsetDateTime to = null;
        GetCdnResourceStatistics200Response response = api.getCdnResourceStatistics(resourceId, from, to);
        // TODO: test validations
    }

    /**
     * Получение списка CDN-ресурсов
     *
     * Чтобы получить список CDN-ресурсов, отправьте GET-запрос на &#x60;/api/v1/cdn/http-resources&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getCdnResourcesTest() throws ApiException {
        Integer bucketId = null;
        GetCdnResources200Response response = api.getCdnResources(bucketId);
        // TODO: test validations
    }

    /**
     * Выпуск сертификата Let&#39;s Encrypt для CDN-ресурса
     *
     * Чтобы выпустить бесплатный сертификат Let&#39;s Encrypt для доменов CDN-ресурса, отправьте POST-запрос на &#x60;/api/v1/cdn/certificates/issue&#x60;.  Выпуск выполняется асинхронно: в ответ возвращается код &#x60;202&#x60;, а следить за ходом выпуска можно по списку задач &#x60;/api/v1/cdn/certificates/tasks&#x60;. Готовый сертификат привязывается к ресурсу автоматически.  Перед выпуском убедитесь, что домены ресурса указывают на его технический домен &#x60;cdn_domain&#x60; — иначе вернется ошибка &#x60;422&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void issueCdnCertificateTest() throws ApiException {
        IssueCertificate issueCertificate = null;
        api.issueCdnCertificate(issueCertificate);
        // TODO: test validations
    }

    /**
     * Предварительная загрузка кэша CDN-ресурса
     *
     * Чтобы заранее загрузить файлы в кэш узлов CDN, не дожидаясь первого обращения пользователей, отправьте POST-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}/preload-cache&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void preloadCdnResourceCacheTest() throws ApiException {
        Integer resourceId = null;
        PreloadCache preloadCache = null;
        api.preloadCdnResourceCache(resourceId, preloadCache);
        // TODO: test validations
    }

    /**
     * Возобновление раздачи CDN-ресурса
     *
     * Чтобы возобновить раздачу контента после приостановки, отправьте POST-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}/resume&#x60;.  Если ресурс заблокирован, вернется ошибка &#x60;409&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void resumeCdnResourceTest() throws ApiException {
        Integer resourceId = null;
        CreateCdnResource201Response response = api.resumeCdnResource(resourceId);
        // TODO: test validations
    }

    /**
     * Приостановка раздачи CDN-ресурса
     *
     * Чтобы приостановить раздачу контента, отправьте POST-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}/suspend&#x60;. Ресурс перейдет в статус &#x60;stopped&#x60;, его настройки и домены сохранятся.  Если ресурс заблокирован, вернется ошибка &#x60;409&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void suspendCdnResourceTest() throws ApiException {
        Integer resourceId = null;
        CreateCdnResource201Response response = api.suspendCdnResource(resourceId);
        // TODO: test validations
    }

    /**
     * Изменение CDN-ресурса
     *
     * Чтобы изменить CDN-ресурс, отправьте PATCH-запрос на &#x60;/api/v1/cdn/http-resources/{resource_id}&#x60;.  Передавайте только те поля, которые нужно изменить: переданные значения накладываются на текущую конфигурацию, а непереданные остаются без изменений. Чтобы сбросить настройку, передайте в соответствующем поле &#x60;null&#x60;.  Поля &#x60;storage_id&#x60; и &#x60;config.origin.servers&#x60; нельзя передавать вместе — источник контента может быть только один.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateCdnResourceTest() throws ApiException {
        Integer resourceId = null;
        UpdateHttpResource updateHttpResource = null;
        CreateCdnResource201Response response = api.updateCdnResource(resourceId, updateHttpResource);
        // TODO: test validations
    }

}
