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
import org.openapitools.client.model.AddGithub;
import org.openapitools.client.model.AddProvider201Response;
import org.openapitools.client.model.AppsPresets;
import org.openapitools.client.model.AvailableFrameworks;
import org.openapitools.client.model.CreateApp;
import org.openapitools.client.model.CreateApp201Response;
import org.openapitools.client.model.CreateDatabaseBackup409Response;
import org.openapitools.client.model.CreateDeploy201Response;
import org.openapitools.client.model.CreateDeployRequest;
import org.openapitools.client.model.GetAppDeploys200Response;
import org.openapitools.client.model.GetAppLogs200Response;
import org.openapitools.client.model.GetApps200Response;
import org.openapitools.client.model.GetBranches200Response;
import org.openapitools.client.model.GetCommits200Response;
import org.openapitools.client.model.GetDeployLogs200Response;
import org.openapitools.client.model.GetDeploySettings200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances403Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import org.openapitools.client.model.GetProviders200Response;
import org.openapitools.client.model.GetRepositories200Response;
import org.openapitools.client.model.GetServerStatistics200Response;
import org.openapitools.client.model.UpdateAppSettings200Response;
import org.openapitools.client.model.UpdeteSettings;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AppsApi
 */
@Disabled
public class AppsApiTest {

    private final AppsApi api = new AppsApi();

    /**
     * Привязка vcs провайдера
     *
     * Чтобы привязать аккаунт провайдера отправьте POST-запрос в &#x60;/api/v1/vcs-provider&#x60;, задав необходимые атрибуты.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addProviderTest() throws ApiException {
        AddGithub addGithub = null;
        AddProvider201Response response = api.addProvider(addGithub);
        // TODO: test validations
    }

    /**
     * Создание приложения
     *
     * Чтобы создать приложение, отправьте POST-запрос в &#x60;/api/v1/apps&#x60;, задав необходимые атрибуты.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAppTest() throws ApiException {
        CreateApp createApp = null;
        CreateApp201Response response = api.createApp(createApp);
        // TODO: test validations
    }

    /**
     * Запуск деплоя приложения
     *
     * Чтобы запустить деплой приложения, отправьте POST-запрос в &#x60;/api/v1/apps/{app_id}/deploy&#x60;, задав необходимые атрибуты.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createDeployTest() throws ApiException {
        String appId = null;
        CreateDeployRequest createDeployRequest = null;
        CreateDeploy201Response response = api.createDeploy(appId, createDeployRequest);
        // TODO: test validations
    }

    /**
     * Удаление приложения
     *
     * Чтобы удалить приложение, отправьте DELETE-запрос в &#x60;/api/v1/apps/{app_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAppTest() throws ApiException {
        String appId = null;
        api.deleteApp(appId);
        // TODO: test validations
    }

    /**
     * Отвязка vcs провайдера от аккаунта
     *
     * Чтобы отвязать vcs провайдера от аккаунта, отправьте DELETE-запрос в &#x60;/api/v1/vcs-provider/{provider_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteProviderTest() throws ApiException {
        String providerId = null;
        api.deleteProvider(providerId);
        // TODO: test validations
    }

    /**
     * Остановка деплоя приложения
     *
     * Чтобы остановить деплой приложения, отправьте POST-запрос в &#x60;api/v1/apps/{app_id}/deploy/{deploy_id}/stop&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deployActionTest() throws ApiException {
        String appId = null;
        String deployId = null;
        CreateDeploy201Response response = api.deployAction(appId, deployId);
        // TODO: test validations
    }

    /**
     * Получение приложения по id
     *
     * Чтобы получить приложение по id, отправьте GET-запрос на &#x60;/api/v1/apps/{app_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAppTest() throws ApiException {
        String appId = null;
        CreateApp201Response response = api.getApp(appId);
        // TODO: test validations
    }

    /**
     * Получение списка деплоев приложения
     *
     * Чтобы получить список деплоев приложения, отправьте GET-запрос на &#x60;/api/v1/apps/{app_id}/deploys&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAppDeploysTest() throws ApiException {
        String appId = null;
        Integer limit = null;
        Integer offset = null;
        GetAppDeploys200Response response = api.getAppDeploys(appId, limit, offset);
        // TODO: test validations
    }

    /**
     * Получение логов приложения
     *
     * Чтобы получить логи приложения, отправьте GET-запрос на &#x60;/api/v1/apps/{app_id}/logs&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAppLogsTest() throws ApiException {
        String appId = null;
        GetAppLogs200Response response = api.getAppLogs(appId);
        // TODO: test validations
    }

    /**
     * Получение статистики приложения
     *
     * Чтобы получить статистику сервера, отправьте GET-запрос на &#x60;/api/v1/apps/{app_id}/statistics&#x60;. Метод поддерживает только приложения &#x60;type: backend&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAppStatisticsTest() throws ApiException {
        String appId = null;
        String dateFrom = null;
        String dateTo = null;
        GetServerStatistics200Response response = api.getAppStatistics(appId, dateFrom, dateTo);
        // TODO: test validations
    }

    /**
     * Получение списка приложений
     *
     * Чтобы получить список приложений, отправьте GET-запрос на &#x60;/api/v1/apps&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAppsTest() throws ApiException {
        GetApps200Response response = api.getApps();
        // TODO: test validations
    }

    /**
     * Получение списка доступных тарифов для приложения
     *
     * Чтобы получить список доступных тарифов, отправьте GET-запрос на &#x60;/api/v1/presets/apps&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAppsPresetsTest() throws ApiException {
        String appId = null;
        AppsPresets response = api.getAppsPresets(appId);
        // TODO: test validations
    }

    /**
     * Получение списка веток репозитория
     *
     * Чтобы получить список веток репозитория, отправьте GET-запрос на &#x60;/api/v1/vcs-provider/{provider_id}/repository/{repository_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getBranchesTest() throws ApiException {
        String providerId = null;
        String repositoryId = null;
        GetBranches200Response response = api.getBranches(providerId, repositoryId);
        // TODO: test validations
    }

    /**
     * Получение списка коммитов ветки репозитория
     *
     * Чтобы получить список коммитов ветки репозитория, отправьте GET-запрос на &#x60;/api/v1/vcs-provider/{provider_id}/repository/{repository_id}/branch&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getCommitsTest() throws ApiException {
        String accountId = null;
        String providerId = null;
        String repositoryId = null;
        String name = null;
        GetCommits200Response response = api.getCommits(accountId, providerId, repositoryId, name);
        // TODO: test validations
    }

    /**
     * Получение логов деплоя приложения
     *
     * Чтобы получить информацию о деплое, отправьте GET-запрос на &#x60;api/v1/apps/{app_id}/deploy/{deploy_id}/logs&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDeployLogsTest() throws ApiException {
        String appId = null;
        String deployId = null;
        Boolean debug = null;
        GetDeployLogs200Response response = api.getDeployLogs(appId, deployId, debug);
        // TODO: test validations
    }

    /**
     * Получение списка дефолтных настроек деплоя для приложения
     *
     * Чтобы получить список настроек деплоя, отправьте GET-запрос на &#x60;/api/v1/deploy-settings/apps&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDeploySettingsTest() throws ApiException {
        String appId = null;
        GetDeploySettings200Response response = api.getDeploySettings(appId);
        // TODO: test validations
    }

    /**
     * Получение списка доступных фреймворков для приложения
     *
     * Чтобы получить список доступных фреймворков, отправьте GET-запрос на &#x60;/api/v1/frameworks/apps&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getFrameworksTest() throws ApiException {
        String appId = null;
        AvailableFrameworks response = api.getFrameworks(appId);
        // TODO: test validations
    }

    /**
     * Получение списка vcs провайдеров
     *
     * Чтобы получить список vcs провайдеров, отправьте GET-запрос на &#x60;/api/v1/vcs-provider&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getProvidersTest() throws ApiException {
        GetProviders200Response response = api.getProviders();
        // TODO: test validations
    }

    /**
     * Получение списка репозиториев vcs провайдера
     *
     * Чтобы получить список репозиториев vcs провайдера, отправьте GET-запрос на &#x60;/api/v1/vcs-provider/{provider_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getRepositoriesTest() throws ApiException {
        String providerId = null;
        GetRepositories200Response response = api.getRepositories(providerId);
        // TODO: test validations
    }

    /**
     * Изменение настроек приложения
     *
     * Чтобы изменить настройки приложения отправьте PATCH-запрос в &#x60;/api/v1/apps/{app_id}&#x60;, задав необходимые атрибуты.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateAppSettingsTest() throws ApiException {
        String appId = null;
        UpdeteSettings updeteSettings = null;
        UpdateAppSettings200Response response = api.updateAppSettings(appId, updeteSettings);
        // TODO: test validations
    }

    /**
     * Изменение состояния приложения
     *
     * Чтобы изменить состояние приложения отправьте PATCH-запрос в &#x60;/api/v1/apps/{app_id}/action/{action}&#x60;, задав необходимые атрибуты.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateAppStateTest() throws ApiException {
        String appId = null;
        String action = null;
        api.updateAppState(appId, action);
        // TODO: test validations
    }

}
