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
import org.openapitools.client.model.AutoBackup;
import org.openapitools.client.model.CreateAdmin;
import org.openapitools.client.model.CreateCluster;
import org.openapitools.client.model.CreateDatabase201Response;
import org.openapitools.client.model.CreateDatabaseBackup201Response;
import org.openapitools.client.model.CreateDatabaseBackup409Response;
import org.openapitools.client.model.CreateDatabaseCluster201Response;
import org.openapitools.client.model.CreateDatabaseInstance201Response;
import org.openapitools.client.model.CreateDatabaseUser201Response;
import org.openapitools.client.model.CreateDb;
import org.openapitools.client.model.CreateInstance;
import org.openapitools.client.model.DeleteDatabase200Response;
import org.openapitools.client.model.DeleteDatabaseCluster200Response;
import org.openapitools.client.model.GetAccountStatus403Response;
import org.openapitools.client.model.GetDatabaseAutoBackupsSettings200Response;
import org.openapitools.client.model.GetDatabaseBackups200Response;
import org.openapitools.client.model.GetDatabaseClusterTypes200Response;
import org.openapitools.client.model.GetDatabaseClusters200Response;
import org.openapitools.client.model.GetDatabaseInstances200Response;
import org.openapitools.client.model.GetDatabaseUsers200Response;
import org.openapitools.client.model.GetDatabases200Response;
import org.openapitools.client.model.GetDatabasesPresets200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import org.openapitools.client.model.UpdateAdmin;
import org.openapitools.client.model.UpdateCluster;
import org.openapitools.client.model.UpdateDb;
import org.openapitools.client.model.UpdateInstance;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for DatabasesApi
 */
@Disabled
public class DatabasesApiTest {

    private final DatabasesApi api = new DatabasesApi();

    /**
     * Создание базы данных
     *
     * Чтобы создать базу данных на вашем аккаунте, отправьте POST-запрос на &#x60;/api/v1/dbs&#x60;, задав необходимые атрибуты.  База данных будет создана с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданной базе данных.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createDatabaseTest() throws ApiException {
        CreateDb createDb = null;
        CreateDatabase201Response response = api.createDatabase(createDb);
        // TODO: test validations
    }

    /**
     * Создание бэкапа базы данных
     *
     * Чтобы создать бэкап базы данных, отправьте запрос POST в &#x60;api/v1/dbs/{db_id}/backups&#x60;. 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createDatabaseBackupTest() throws ApiException {
        Integer dbId = null;
        CreateDatabaseBackup201Response response = api.createDatabaseBackup(dbId);
        // TODO: test validations
    }

    /**
     * Создание кластера базы данных
     *
     * Чтобы создать кластер базы данных на вашем аккаунте, отправьте POST-запрос на &#x60;/api/v1/databases&#x60;.   Вместе с кластером будет создан один инстанс базы данных и один пользователь.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createDatabaseClusterTest() throws ApiException {
        CreateCluster createCluster = null;
        CreateDatabaseCluster201Response response = api.createDatabaseCluster(createCluster);
        // TODO: test validations
    }

    /**
     * Создание инстанса базы данных
     *
     * Чтобы создать инстанс базы данных, отправьте POST-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances&#x60;.\\    Существующие пользователи не будут иметь доступа к новой базе данных после создания. Вы можете изменить привилегии для пользователя через &lt;a href&#x3D;&#39;#tag/Bazy-dannyh/operation/updateDatabaseUser&#39;&gt;метод изменения пользователя&lt;/a&gt; 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createDatabaseInstanceTest() throws ApiException {
        Integer dbClusterId = null;
        CreateInstance createInstance = null;
        CreateDatabaseInstance201Response response = api.createDatabaseInstance(dbClusterId, createInstance);
        // TODO: test validations
    }

    /**
     * Создание пользователя базы данных
     *
     * Чтобы создать пользователя базы данных, отправьте POST-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createDatabaseUserTest() throws ApiException {
        Integer dbClusterId = null;
        CreateAdmin createAdmin = null;
        CreateDatabaseUser201Response response = api.createDatabaseUser(dbClusterId, createAdmin);
        // TODO: test validations
    }

    /**
     * Удаление базы данных
     *
     * Чтобы удалить базу данных, отправьте запрос DELETE в &#x60;api/v1/dbs/{db_id}&#x60;. 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteDatabaseTest() throws ApiException {
        Integer dbId = null;
        String hash = null;
        String code = null;
        DeleteDatabase200Response response = api.deleteDatabase(dbId, hash, code);
        // TODO: test validations
    }

    /**
     * Удаление бэкапа базы данных
     *
     * Чтобы удалить бэкап базы данных, отправьте запрос DELETE в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteDatabaseBackupTest() throws ApiException {
        Integer dbId = null;
        Integer backupId = null;
        api.deleteDatabaseBackup(dbId, backupId);
        // TODO: test validations
    }

    /**
     * Удаление кластера базы данных
     *
     * Чтобы удалить кластер базы данных, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteDatabaseClusterTest() throws ApiException {
        Integer dbClusterId = null;
        String hash = null;
        String code = null;
        DeleteDatabaseCluster200Response response = api.deleteDatabaseCluster(dbClusterId, hash, code);
        // TODO: test validations
    }

    /**
     * Удаление инстанса базы данных
     *
     * Чтобы удалить инстанс базы данных, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteDatabaseInstanceTest() throws ApiException {
        Integer dbClusterId = null;
        Integer instanceId = null;
        api.deleteDatabaseInstance(dbClusterId, instanceId);
        // TODO: test validations
    }

    /**
     * Удаление пользователя базы данных
     *
     * Чтобы удалить пользователя базы данных на вашем аккаунте, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteDatabaseUserTest() throws ApiException {
        Integer dbClusterId = null;
        Integer adminId = null;
        api.deleteDatabaseUser(dbClusterId, adminId);
        // TODO: test validations
    }

    /**
     * Получение базы данных
     *
     * Чтобы отобразить информацию об отдельной базе данных, отправьте запрос GET на &#x60;api/v1/dbs/{db_id}&#x60;. 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDatabaseTest() throws ApiException {
        Integer dbId = null;
        CreateDatabase201Response response = api.getDatabase(dbId);
        // TODO: test validations
    }

    /**
     * Получение настроек автобэкапов базы данных
     *
     * Чтобы получить список настроек автобэкапов базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/auto-backups&#x60;
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDatabaseAutoBackupsSettingsTest() throws ApiException {
        Integer dbId = null;
        GetDatabaseAutoBackupsSettings200Response response = api.getDatabaseAutoBackupsSettings(dbId);
        // TODO: test validations
    }

    /**
     * Получение бэкапа базы данных
     *
     * Чтобы получить бэкап базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDatabaseBackupTest() throws ApiException {
        Integer dbId = null;
        Integer backupId = null;
        CreateDatabaseBackup201Response response = api.getDatabaseBackup(dbId, backupId);
        // TODO: test validations
    }

    /**
     * Список бэкапов базы данных
     *
     * Чтобы получить список бэкапов базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/backups&#x60;. 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDatabaseBackupsTest() throws ApiException {
        Integer dbId = null;
        Integer limit = null;
        Integer offset = null;
        GetDatabaseBackups200Response response = api.getDatabaseBackups(dbId, limit, offset);
        // TODO: test validations
    }

    /**
     * Получение кластера базы данных
     *
     * Чтобы получить кластер базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDatabaseClusterTest() throws ApiException {
        Integer dbClusterId = null;
        CreateDatabaseCluster201Response response = api.getDatabaseCluster(dbClusterId);
        // TODO: test validations
    }

    /**
     * Получение списка типов кластеров баз данных
     *
     * Чтобы получить список типов баз данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/database-types&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDatabaseClusterTypesTest() throws ApiException {
        GetDatabaseClusterTypes200Response response = api.getDatabaseClusterTypes();
        // TODO: test validations
    }

    /**
     * Получение списка кластеров баз данных
     *
     * Чтобы получить список кластеров баз данных, отправьте GET-запрос на &#x60;/api/v1/databases&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;dbs&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDatabaseClustersTest() throws ApiException {
        Integer limit = null;
        Integer offset = null;
        GetDatabaseClusters200Response response = api.getDatabaseClusters(limit, offset);
        // TODO: test validations
    }

    /**
     * Получение инстанса базы данных
     *
     * Чтобы получить инстанс базы данных, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDatabaseInstanceTest() throws ApiException {
        Integer dbClusterId = null;
        Integer instanceId = null;
        CreateDatabaseInstance201Response response = api.getDatabaseInstance(dbClusterId, instanceId);
        // TODO: test validations
    }

    /**
     * Получение списка инстансов баз данных
     *
     * Чтобы получить список баз данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDatabaseInstancesTest() throws ApiException {
        Integer dbClusterId = null;
        GetDatabaseInstances200Response response = api.getDatabaseInstances(dbClusterId);
        // TODO: test validations
    }

    /**
     * Получение списка параметров баз данных
     *
     * Чтобы получить список параметров баз данных, отправьте GET-запрос на &#x60;/api/v1/dbs/parameters&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDatabaseParametersTest() throws ApiException {
        Map<String, List<String>> response = api.getDatabaseParameters();
        // TODO: test validations
    }

    /**
     * Получение пользователя базы данных
     *
     * Чтобы получить пользователя базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDatabaseUserTest() throws ApiException {
        Integer dbClusterId = null;
        Integer adminId = null;
        CreateDatabaseUser201Response response = api.getDatabaseUser(dbClusterId, adminId);
        // TODO: test validations
    }

    /**
     * Получение списка пользователей базы данных
     *
     * Чтобы получить список пользователей базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDatabaseUsersTest() throws ApiException {
        Integer dbClusterId = null;
        GetDatabaseUsers200Response response = api.getDatabaseUsers(dbClusterId);
        // TODO: test validations
    }

    /**
     * Получение списка всех баз данных
     *
     * Чтобы получить список всех баз данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/dbs&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;dbs&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDatabasesTest() throws ApiException {
        Integer limit = null;
        Integer offset = null;
        GetDatabases200Response response = api.getDatabases(limit, offset);
        // TODO: test validations
    }

    /**
     * Получение списка тарифов для баз данных
     *
     * Чтобы получить список тарифов для баз данных, отправьте GET-запрос на &#x60;/api/v1/presets/dbs&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;databases_presets&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDatabasesPresetsTest() throws ApiException {
        GetDatabasesPresets200Response response = api.getDatabasesPresets();
        // TODO: test validations
    }

    /**
     * Восстановление базы данных из бэкапа
     *
     * Чтобы восстановить базу данных из бэкапа, отправьте запрос PUT в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void restoreDatabaseFromBackupTest() throws ApiException {
        Integer dbId = null;
        Integer backupId = null;
        api.restoreDatabaseFromBackup(dbId, backupId);
        // TODO: test validations
    }

    /**
     * Обновление базы данных
     *
     * Чтобы обновить только определенные атрибуты базы данных, отправьте запрос PATCH в &#x60;api/v1/dbs/{db_id}&#x60;. 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateDatabaseTest() throws ApiException {
        Integer dbId = null;
        UpdateDb updateDb = null;
        CreateDatabase201Response response = api.updateDatabase(dbId, updateDb);
        // TODO: test validations
    }

    /**
     * Изменение настроек автобэкапов базы данных
     *
     * Чтобы изменить список настроек автобэкапов базы данных, отправьте запрос PATCH в &#x60;api/v1/dbs/{db_id}/auto-backups&#x60;
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateDatabaseAutoBackupsSettingsTest() throws ApiException {
        Integer dbId = null;
        AutoBackup autoBackup = null;
        GetDatabaseAutoBackupsSettings200Response response = api.updateDatabaseAutoBackupsSettings(dbId, autoBackup);
        // TODO: test validations
    }

    /**
     * Изменение кластера базы данных
     *
     * Чтобы изменить кластер базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateDatabaseClusterTest() throws ApiException {
        Integer dbClusterId = null;
        UpdateCluster updateCluster = null;
        CreateDatabaseCluster201Response response = api.updateDatabaseCluster(dbClusterId, updateCluster);
        // TODO: test validations
    }

    /**
     * Изменение инстанса базы данных
     *
     * Чтобы изменить инстанс базы данных, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateDatabaseInstanceTest() throws ApiException {
        Integer dbClusterId = null;
        UpdateInstance updateInstance = null;
        CreateDatabaseInstance201Response response = api.updateDatabaseInstance(dbClusterId, updateInstance);
        // TODO: test validations
    }

    /**
     * Изменение пользователя базы данных
     *
     * Чтобы изменить пользователя базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateDatabaseUserTest() throws ApiException {
        Integer dbClusterId = null;
        Integer adminId = null;
        UpdateAdmin updateAdmin = null;
        CreateDatabaseUser201Response response = api.updateDatabaseUser(dbClusterId, adminId, updateAdmin);
        // TODO: test validations
    }

}
