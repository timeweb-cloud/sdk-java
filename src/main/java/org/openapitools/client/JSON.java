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


package org.openapitools.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.JsonElement;
import io.gsonfire.GsonFireBuilder;
import io.gsonfire.TypeSelector;

import okio.ByteString;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

/*
 * A JSON utility class
 *
 * NOTE: in the future, this class may be converted to static, which may break
 *       backward-compatibility
 */
public class JSON {
    private static Gson gson;
    private static boolean isLenientOnJson = false;
    private static DateTypeAdapter dateTypeAdapter = new DateTypeAdapter();
    private static SqlDateTypeAdapter sqlDateTypeAdapter = new SqlDateTypeAdapter();
    private static OffsetDateTimeTypeAdapter offsetDateTimeTypeAdapter = new OffsetDateTimeTypeAdapter();
    private static LocalDateTypeAdapter localDateTypeAdapter = new LocalDateTypeAdapter();
    private static ByteArrayAdapter byteArrayAdapter = new ByteArrayAdapter();

    @SuppressWarnings("unchecked")
    public static GsonBuilder createGson() {
        GsonFireBuilder fireBuilder = new GsonFireBuilder()
        ;
        GsonBuilder builder = fireBuilder.createGsonBuilder();
        return builder;
    }

    private static String getDiscriminatorValue(JsonElement readElement, String discriminatorField) {
        JsonElement element = readElement.getAsJsonObject().get(discriminatorField);
        if (null == element) {
            throw new IllegalArgumentException("missing discriminator field: <" + discriminatorField + ">");
        }
        return element.getAsString();
    }

    /**
     * Returns the Java class that implements the OpenAPI schema for the specified discriminator value.
     *
     * @param classByDiscriminatorValue The map of discriminator values to Java classes.
     * @param discriminatorValue The value of the OpenAPI discriminator in the input data.
     * @return The Java class that implements the OpenAPI schema
     */
    private static Class getClassByDiscriminator(Map classByDiscriminatorValue, String discriminatorValue) {
        Class clazz = (Class) classByDiscriminatorValue.get(discriminatorValue);
        if (null == clazz) {
            throw new IllegalArgumentException("cannot determine model class of name: <" + discriminatorValue + ">");
        }
        return clazz;
    }

    {
        GsonBuilder gsonBuilder = createGson();
        gsonBuilder.registerTypeAdapter(Date.class, dateTypeAdapter);
        gsonBuilder.registerTypeAdapter(java.sql.Date.class, sqlDateTypeAdapter);
        gsonBuilder.registerTypeAdapter(OffsetDateTime.class, offsetDateTimeTypeAdapter);
        gsonBuilder.registerTypeAdapter(LocalDate.class, localDateTypeAdapter);
        gsonBuilder.registerTypeAdapter(byte[].class, byteArrayAdapter);
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddBalancerToProject200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddBalancerToProjectRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddBitbucket.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddClusterToProjectRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddCountries.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddCountriesToAllowedList201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddCountriesToAllowedListRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddDatabaseToProjectRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddDedicatedServerToProjectRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddGit.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddGithub.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddGitlab.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddIPsToAllowedList201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddIPsToAllowedListRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddIPsToBalancerRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddIps.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddKeyToServerRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddProvider201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddServerIP201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddServerIPRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddServerToProjectRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddStorageSubdomainCertificateRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddStorageSubdomains200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddStorageSubdomainsRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddStorageToProjectRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddSubdomain201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddTokenPackage.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AddedSubdomain.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Agent.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AgentModelSettings.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AgentSettings.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AgentSettingsWidget.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ApiKey.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.App.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AppConfiguration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AppDiskStatus.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AppDomainsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AppProvider.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AppsPresets.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AppsPresetsBackendPresetsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AppsPresetsFrontendPresetsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AutoBackup.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AutoReplyIsDisabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AutoReplyIsEnabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AutoreplyIsDisabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AutoreplyIsEnabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AvailableFrameworks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AvailableFrameworksBackendFrameworksInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.AvailableFrameworksFrontendFrameworksInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Backup.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Balancer.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.BalancerNetworksInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.BalancerNetworksInnerIpsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.BaseError.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.BindFloatingIp.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Bonus.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Branch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Bucket.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.BucketDiskStats.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.BucketUser.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.BucketWebsiteConfig.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.BucketWebsiteConfigErrorPagesInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CheckDomain200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ClusterEdit.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ClusterEditOidcProvider.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ClusterIn.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ClusterInClusterNetworkCidr.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ClusterInConfiguration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ClusterInMaintenanceSlot.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ClusterInOidcProvider.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ClusterOut.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ClusterResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ClusterVersionEdit.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Clusterk8s.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ClustersResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Commit.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ComponentsSchemasBaseError.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ConfigParameters.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ContainerRegistryPresetsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ContainerRegistryRepositoriesInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateAdmin.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateAgent.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateAgent201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateApiKey.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateApp.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateApp201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateBalancer.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateBalancer200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateBalancerCertificates.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateBalancerRule200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateCluster.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateClusterAdmin.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateClusterInstance.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDatabase201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDatabaseBackup201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDatabaseBackup409Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDatabaseCluster201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDatabaseInstance201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDatabaseUser201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDb.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDbAutoBackups.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDedicatedServer.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDedicatedServer201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDeploy201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDeployRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDns.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDomainDNSRecord201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDomainMailbox201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDomainMailboxRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateDomainRequest201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateFloatingIp.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateFloatingIp201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateInstance.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateKey201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateKeyRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateKnowledgebase.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateKnowledgebase201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateMultipleDomainMailboxes201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateMultipleDomainMailboxesRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateMultipleDomainMailboxesRequestMailboxesInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateNetworkDrive.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateNetworkDrive201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateProject.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateProject201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateRule.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateServer.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateServer201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateServerConfiguration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateServerDisk201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateServerDiskBackup201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateServerDiskBackupRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateServerDiskRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateServerNetwork.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateStorage201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateStorageRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateStorageRequestConfigurator.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateToken201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateVPC201Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreateVpc.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.CreatedApiKey.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DatabaseAdmin.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DatabaseAdminInstancesInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DatabaseCluster.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DatabaseClusterDisk.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DatabaseClusterNetworksInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DatabaseClusterNetworksInnerIpsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DatabaseInstance.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DatabaseType.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DatabaseTypeRequirements.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Db.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DbDiskStats.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DbReplication.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DedicatedServer.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DedicatedServerAdditionalService.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DedicatedServerPreset.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DedicatedServerPresetCpu.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DedicatedServerPresetDisk.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DedicatedServerPresetMemory.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DeleteBalancer200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DeleteCluster200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DeleteCountriesFromAllowedList200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DeleteCountriesFromAllowedListRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DeleteDatabase200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DeleteDatabaseCluster200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DeleteIPsFromAllowedList200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DeleteIPsFromAllowedListRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DeleteServer200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DeleteServerIPRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DeleteServiceResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DeleteStorage200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Deploy.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DeploySettingsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DnsRecord.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DnsRecordData.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Document.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DocumentStatusInfo.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DocumentStatusInfoDetails.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Domain.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DomainAllowedBuyPeriodsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DomainInfo.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DomainNameServer.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DomainNameServerItemsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DomainProlong.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DomainRegister.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DomainRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.DomainTransfer.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.EditApiKey.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Finances.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.FirewallGroup.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.FirewallGroupInAPI.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.FirewallGroupOutResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.FirewallGroupResource.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.FirewallGroupResourceOutResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.FirewallGroupResourcesOutResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.FirewallGroupsOutResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.FirewallRule.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.FirewallRuleInAPI.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.FirewallRuleOutResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.FirewallRulesOutResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.FloatingIp.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ForwardIsDisabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ForwardIsEnabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ForwardingIncomingIsDisabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ForwardingIncomingIsEnabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ForwardingOutgoingIsDisabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ForwardingOutgoingIsEnabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Free.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetAccountStatus200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetAccountStatus403Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetAgentStatistics200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetAgentStatistics200ResponseMeta.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetAgents200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetAgents200ResponseMeta.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetAllProjectResources200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetAppDeploys200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetAppLogs200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetApps200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetAuthAccessSettings200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetAuthAccessSettings200ResponseWhiteList.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetBalancerIPs200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetBalancerRules200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetBalancers200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetBalancersPresets200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetBranches200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetCommits200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetConfigurators200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetCountries200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDatabaseAutoBackupsSettings200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDatabaseBackups200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDatabaseClusterTypes200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDatabaseClusters200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDatabaseInstances200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDatabaseUsers200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDatabases200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDatabasesPresets200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDedicatedServerPresetAdditionalServices200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDedicatedServers200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDedicatedServersPresets200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDeployLogs200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDeploySettings200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDomain200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDomainDNSRecords200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDomainMailInfo200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDomainNameServers200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDomainRequests200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetDomains200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetFinances200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetFinances400Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetFinances401Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetFinances429Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetFinances500Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetFloatingIps200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetImage404Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetKey200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetKeys200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetKnowledgebaseDocumentsV2200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetKnowledgebaseDocumentsV2200ResponseMeta.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetKnowledgebaseStatistics200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetKnowledgebases200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetKnowledgebasesV2200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetKnowledgebasesV2200ResponseMeta.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetLocations200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetMailboxes200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetNetworkDrives200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetNetworkDrivesAvailableResources200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetNetworkDrivesPresets200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetNotificationSettings200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetOsList200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetProjectBalancers200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetProjectClusters200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetProjectDatabases200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetProjectDedicatedServers200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetProjectServers200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetProjectStorages200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetProjects200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetProviders200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetRepositories200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServerDiskAutoBackupSettings200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServerDiskBackup200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServerDiskBackups200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServerDisks200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServerIPs200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServerLogs200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServerStatistics200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServerStatistics200ResponseCpuInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServerStatistics200ResponseDiskInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServerStatistics200ResponseNetworkTrafficInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServerStatistics200ResponseRamInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServerStatisticsNew200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServers200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServersPresets200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServicePrices200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetServicePrices200ResponseMeta.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetSoftware200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetStorageSubdomains200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetStorageTransferStatus200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetStorageUsers200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetStoragesPresets200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetTLD200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetTLDs200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetTokens200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetVPCPorts200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetVPCServices200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.GetVPCs200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Image.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ImageDownload.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ImageDownloadResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ImageDownloadsResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ImageInAPI.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ImageOutResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ImageUpdateAPI.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ImageUrlAuth.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ImageUrlIn.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ImagesOutResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.IncreaseNodes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.InfoServicePrice.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Invoice.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.K8SVersionsResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Knowledgebase.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.KnowledgebaseV2.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.LocationDto.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Mailbox.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.MailboxAutoReply.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.MailboxForwardingIncoming.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.MailboxForwardingOutgoing.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.MailboxResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.MailboxSpamFilter.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.MasterPresetOutApi.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Meta.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Meta1.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.MountNetworkDrive.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Network.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NetworkDrive.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NetworkDriveAvailableResource.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NetworkDrivePreset.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NetworkDrivePresetRead.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NetworkDrivePresetWrite.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NetworkDriveServiceListInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NetworkDriversResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NodeGroupIn.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NodeGroupInConfiguration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NodeGroupOut.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NodeGroupResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NodeGroupsResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NodeOut.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NodesResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NotificationSetting.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.NotificationSettingChannels.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.OutgoingIsDisabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.OutgoingIsEnabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.PerformActionOnBackupRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.PerformActionOnServerRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.PresetsBalancer.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.PresetsDbs.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.PresetsResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.PresetsStorage.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Project.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ProjectResource.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Provider.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ReduceNodes.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.RefreshApiKey.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.RegistriesResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.RegistryEdit.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.RegistryIn.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.RegistryInConfiguration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.RegistryOut.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.RegistryOutDiskStats.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.RegistryResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.RemoveCountries.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.RemoveIps.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.RepositoriesResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Repository.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Resource.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ResourceTransfer.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Resources.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ResourcesResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Rule.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.S3Subdomain.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.SchemasBaseError.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.SchemasMeta.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.SchemasPresetsResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServerBackup.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServerDisk.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServerIp.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServerLog.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServersConfigurator.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServersConfiguratorRequirements.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServersOs.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServersOsRequirements.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServersPreset.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServersSoftware.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServersSoftwareRequirements.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServersStatistics.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServersStatisticsListInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServersStatisticsMeta.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServicePrice.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServicePriceConfiguration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServiceServicePrice.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.ServiceServicePriceNodeGroupsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.SetLabels.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.SpamFilterIsDisabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.SpamFilterIsEnabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.SpamProtectionIsDisabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.SpamProtectionIsEnabled.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.SshKey.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.SshKeyUsedByInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Status.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.StatusCompanyInfo.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Subdomain.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Tags.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.TokenStatistic.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.TopLevelDomain.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.TopLevelDomainAllowedBuyPeriodsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.TransferStatus.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.TransferStatusErrorsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.TransferStorageRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateAdmin.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateAgent.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateAgentSettings.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateAppSettings200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateAuthRestrictionsByCountriesRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateBalancer.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateCluster.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateDb.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateDedicatedServerRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateDomain.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateDomainAutoProlongation200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateDomainMailInfoRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateDomainNameServers.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateDomainNameServersNameServersInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateFloatingIp.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateInstance.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateKeyRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateKnowledgebase.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateMailbox.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateMailboxV2.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateMailboxV2200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateNetworkDrive.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateNotificationSettingsRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateNotificationSettingsRequestSettingsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateNotificationSettingsRequestSettingsInnerChannels.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateProject.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateRule.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateServer.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateServerConfigurator.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateServerDiskBackupRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateServerDiskRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateServerIPRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateServerNATRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateServerOSBootModeRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateSettings.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateStorageRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateStorageRequestConfigurator.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateStorageUser200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateStorageUserRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateToken200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UpdateVpc.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UploadFilesToKnowledgebase200Response.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UploadSuccessful.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.UploadSuccessfulResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Use.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Vds.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.VdsDisksInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.VdsImage.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.VdsNetworksInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.VdsNetworksInnerIpsInner.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.VdsOs.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.VdsSoftware.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.Vpc.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.VpcPort.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.VpcPortService.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.VpcService.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new org.openapitools.client.model.WorkerPresetOutApi.CustomTypeAdapterFactory());
        gson = gsonBuilder.create();
    }

    /**
     * Get Gson.
     *
     * @return Gson
     */
    public static Gson getGson() {
        return gson;
    }

    /**
     * Set Gson.
     *
     * @param gson Gson
     */
    public static void setGson(Gson gson) {
        JSON.gson = gson;
    }

    public static void setLenientOnJson(boolean lenientOnJson) {
        isLenientOnJson = lenientOnJson;
    }

    /**
     * Serialize the given Java object into JSON string.
     *
     * @param obj Object
     * @return String representation of the JSON
     */
    public static String serialize(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * Deserialize the given JSON string to Java object.
     *
     * @param <T>        Type
     * @param body       The JSON string
     * @param returnType The type to deserialize into
     * @return The deserialized Java object
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String body, Type returnType) {
        try {
            if (isLenientOnJson) {
                JsonReader jsonReader = new JsonReader(new StringReader(body));
                // see https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/stream/JsonReader.html#setLenient(boolean)
                jsonReader.setLenient(true);
                return gson.fromJson(jsonReader, returnType);
            } else {
                return gson.fromJson(body, returnType);
            }
        } catch (JsonParseException e) {
            // Fallback processing when failed to parse JSON form response body:
            // return the response body string directly for the String return type;
            if (returnType.equals(String.class)) {
                return (T) body;
            } else {
                throw (e);
            }
        }
    }

    /**
     * Gson TypeAdapter for Byte Array type
     */
    public static class ByteArrayAdapter extends TypeAdapter<byte[]> {

        @Override
        public void write(JsonWriter out, byte[] value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(ByteString.of(value).base64());
            }
        }

        @Override
        public byte[] read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String bytesAsBase64 = in.nextString();
                    ByteString byteString = ByteString.decodeBase64(bytesAsBase64);
                    return byteString.toByteArray();
            }
        }
    }

    /**
     * Gson TypeAdapter for JSR310 OffsetDateTime type
     */
    public static class OffsetDateTimeTypeAdapter extends TypeAdapter<OffsetDateTime> {

        private DateTimeFormatter formatter;

        public OffsetDateTimeTypeAdapter() {
            this(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }

        public OffsetDateTimeTypeAdapter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        public void setFormat(DateTimeFormatter dateFormat) {
            this.formatter = dateFormat;
        }

        @Override
        public void write(JsonWriter out, OffsetDateTime date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(date));
            }
        }

        @Override
        public OffsetDateTime read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    if (date.endsWith("+0000")) {
                        date = date.substring(0, date.length()-5) + "Z";
                    }
                    return OffsetDateTime.parse(date, formatter);
            }
        }
    }

    /**
     * Gson TypeAdapter for JSR310 LocalDate type
     */
    public static class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {

        private DateTimeFormatter formatter;

        public LocalDateTypeAdapter() {
            this(DateTimeFormatter.ISO_LOCAL_DATE);
        }

        public LocalDateTypeAdapter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        public void setFormat(DateTimeFormatter dateFormat) {
            this.formatter = dateFormat;
        }

        @Override
        public void write(JsonWriter out, LocalDate date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(date));
            }
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    return LocalDate.parse(date, formatter);
            }
        }
    }

    public static void setOffsetDateTimeFormat(DateTimeFormatter dateFormat) {
        offsetDateTimeTypeAdapter.setFormat(dateFormat);
    }

    public static void setLocalDateFormat(DateTimeFormatter dateFormat) {
        localDateTypeAdapter.setFormat(dateFormat);
    }

    /**
     * Gson TypeAdapter for java.sql.Date type
     * If the dateFormat is null, a simple "yyyy-MM-dd" format will be used
     * (more efficient than SimpleDateFormat).
     */
    public static class SqlDateTypeAdapter extends TypeAdapter<java.sql.Date> {

        private DateFormat dateFormat;

        public SqlDateTypeAdapter() {}

        public SqlDateTypeAdapter(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        public void setFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public void write(JsonWriter out, java.sql.Date date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                String value;
                if (dateFormat != null) {
                    value = dateFormat.format(date);
                } else {
                    value = date.toString();
                }
                out.value(value);
            }
        }

        @Override
        public java.sql.Date read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    try {
                        if (dateFormat != null) {
                            return new java.sql.Date(dateFormat.parse(date).getTime());
                        }
                        return new java.sql.Date(ISO8601Utils.parse(date, new ParsePosition(0)).getTime());
                    } catch (ParseException e) {
                        throw new JsonParseException(e);
                    }
            }
        }
    }

    /**
     * Gson TypeAdapter for java.util.Date type
     * If the dateFormat is null, ISO8601Utils will be used.
     */
    public static class DateTypeAdapter extends TypeAdapter<Date> {

        private DateFormat dateFormat;

        public DateTypeAdapter() {}

        public DateTypeAdapter(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        public void setFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public void write(JsonWriter out, Date date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                String value;
                if (dateFormat != null) {
                    value = dateFormat.format(date);
                } else {
                    value = ISO8601Utils.format(date, true);
                }
                out.value(value);
            }
        }

        @Override
        public Date read(JsonReader in) throws IOException {
            try {
                switch (in.peek()) {
                    case NULL:
                        in.nextNull();
                        return null;
                    default:
                        String date = in.nextString();
                        try {
                            if (dateFormat != null) {
                                return dateFormat.parse(date);
                            }
                            return ISO8601Utils.parse(date, new ParsePosition(0));
                        } catch (ParseException e) {
                            throw new JsonParseException(e);
                        }
                }
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(e);
            }
        }
    }

    public static void setDateFormat(DateFormat dateFormat) {
        dateTypeAdapter.setFormat(dateFormat);
    }

    public static void setSqlDateFormat(DateFormat dateFormat) {
        sqlDateTypeAdapter.setFormat(dateFormat);
    }
}
