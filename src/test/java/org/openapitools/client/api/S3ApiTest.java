/*
 * Timeweb Cloud API
 * # Введение API Timeweb Cloud позволяет вам управлять ресурсами в облаке программным способом с использованием обычных HTTP-запросов.  Множество функций, которые доступны в панели управления Timeweb Cloud, также доступны через API, что позволяет вам автоматизировать ваши собственные сценарии.  В этой документации сперва будет описан общий дизайн и принципы работы API, а после этого конкретные конечные точки. Также будут приведены примеры запросов к ним.   ## Запросы Запросы должны выполняться по протоколу `HTTPS`, чтобы гарантировать шифрование транзакций. Поддерживаются следующие методы запроса: |Метод|Применение| |--- |--- | |GET|Извлекает данные о коллекциях и отдельных ресурсах.| |POST|Для коллекций создает новый ресурс этого типа. Также используется для выполнения действий с конкретным ресурсом.| |PUT|Обновляет существующий ресурс.| |PATCH|Некоторые ресурсы поддерживают частичное обновление, то есть обновление только части атрибутов ресурса, в этом случае вместо метода PUT будет использован PATCH.| |DELETE|Удаляет ресурс.|  Методы `POST`, `PUT` и `PATCH` могут включать объект в тело запроса с типом содержимого `application/json`.  ### Параметры в запросах Некоторые коллекции поддерживают пагинацию, поиск или сортировку в запросах. В параметрах запроса требуется передать: - `limit` — обозначает количество записей, которое необходимо вернуть  - `offset` — указывает на смещение, относительно начала списка  - `search` — позволяет указать набор символов для поиска  - `sort` — можно задать правило сортировки коллекции  ## Ответы Запросы вернут один из следующих кодов состояния ответа HTTP:  |Статус|Описание| |--- |--- | |200 OK|Действие с ресурсом было выполнено успешно.| |201 Created|Ресурс был успешно создан. При этом ресурс может быть как уже готовым к использованию, так и находиться в процессе запуска.| |204 No Content|Действие с ресурсом было выполнено успешно, и ответ не содержит дополнительной информации в теле.| |400 Bad Request|Был отправлен неверный запрос, например, в нем отсутствуют обязательные параметры и т. д. Тело ответа будет содержать дополнительную информацию об ошибке.| |401 Unauthorized|Ошибка аутентификации.| |403 Forbidden|Аутентификация прошла успешно, но недостаточно прав для выполнения действия.| |404 Not Found|Запрашиваемый ресурс не найден.| |409 Conflict|Запрос конфликтует с текущим состоянием.| |423 Locked|Ресурс из запроса заблокирован от применения к нему указанного метода.| |429 Too Many Requests|Был достигнут лимит по количеству запросов в единицу времени.| |500 Internal Server Error|При выполнении запроса произошла какая-то внутренняя ошибка. Чтобы решить эту проблему, лучше всего создать тикет в панели управления.|  ### Структура успешного ответа Все конечные точки будут возвращать данные в формате `JSON`. Ответы на `GET`-запросы будут иметь на верхнем уровне следующую структуру атрибутов:  |Название поля|Тип|Описание| |--- |--- |--- | |[entity_name]|object, object[], string[], number[], boolean|Динамическое поле, которое будет меняться в зависимости от запрашиваемого ресурса и будет содержать все атрибуты, необходимые для описания этого ресурса. Например, при запросе списка баз данных будет возвращаться поле `dbs`, а при запросе конкретного облачного сервера `server`. Для некоторых конечных точек в ответе может возвращаться сразу несколько ресурсов.| |meta|object|Опционально. Объект, который содержит вспомогательную информацию о ресурсе. Чаще всего будет встречаться при запросе коллекций и содержать поле `total`, которое будет указывать на количество элементов в коллекции.| |response_id|string|Опционально. В большинстве случаев в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее. Также вы можете использовать этот идентификатор, чтобы убедиться, что это новый ответ на запрос и результат не был получен из кэша.|  Пример запроса на получение списка SSH-ключей: ```     HTTP/2.0 200 OK     {       \"ssh_keys\":[           {             \"body\":\"ssh-rsa AAAAB3NzaC1sdfghjkOAsBwWhs= example@device.local\",             \"created_at\":\"2021-09-15T19:52:27Z\",             \"expired_at\":null,             \"id\":5297,             \"is_default\":false,             \"name\":\"example@device.local\",             \"used_at\":null,             \"used_by\":[]           }       ],       \"meta\":{           \"total\":1       },       \"response_id\":\"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ### Структура ответа с ошибкой |Название поля|Тип|Описание| |--- |--- |--- | |status_code|number|Короткий числовой идентификатор ошибки.| |error_code|string|Короткий текстовый идентификатор ошибки, который уточняет числовой идентификатор и удобен для программной обработки. Самый простой пример — это код `not_found` для ошибки 404.| |message|string, string[]|Опционально. В большинстве случаев в ответе будет содержаться человекочитаемое подробное описание ошибки или ошибок, которые помогут понять, что нужно исправить.| |response_id|string|Опционально. В большинстве случае в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее.|  Пример: ```     HTTP/2.0 403 Forbidden     {       \"status_code\": 403,       \"error_code\":  \"forbidden\",       \"message\":     \"You do not have access for the attempted action\",       \"response_id\": \"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ## Статусы ресурсов Важно учесть, что при создании большинства ресурсов внутри платформы вам будет сразу возвращен ответ от сервера со статусом `200 OK` или `201 Created` и идентификатором созданного ресурса в теле ответа, но при этом этот ресурс может быть ещё в *состоянии запуска*.  Для того чтобы понять, в каком состоянии сейчас находится ваш ресурс, мы добавили поле `status` в ответ на получение информации о ресурсе.  Список статусов будет отличаться в зависимости от типа ресурса. Увидеть поддерживаемый список статусов вы сможете в описании каждого конкретного ресурса.     ## Ограничение скорости запросов (Rate Limiting) Чтобы обеспечить стабильность для всех пользователей, Timeweb Cloud защищает API от всплесков входящего трафика, анализируя количество запросов c каждого аккаунта к каждой конечной точке.  Если ваше приложение отправляет более 20 запросов в секунду на одну конечную точку, то для этого запроса API может вернуть код состояния HTTP `429 Too Many Requests`.   ## Аутентификация Доступ к API осуществляется с помощью JWT-токена. Токенами можно управлять внутри панели управления Timeweb Cloud в разделе *API и Terraform*.  Токен необходимо передавать в заголовке каждого запроса в формате: ```   Authorization: Bearer $TIMEWEB_CLOUD_TOKEN ```  ## Формат примеров API Примеры в этой документации описаны с помощью `curl`, HTTP-клиента командной строки. На компьютерах `Linux` и `macOS` обычно по умолчанию установлен `curl`, и он доступен для загрузки на всех популярных платформах, включая `Windows`.  Каждый пример разделен на несколько строк символом `\\`, который совместим с `bash`. Типичный пример выглядит так: ```   curl -X PATCH      -H \"Content-Type: application/json\"      -H \"Authorization: Bearer $TIMEWEB_CLOUD_TOKEN\"      -d '{\"name\":\"Cute Corvus\",\"comment\":\"Development Server\"}'      \"https://api.timeweb.cloud/api/v1/dedicated/1051\" ``` - Параметр `-X` задает метод запроса. Для согласованности метод будет указан во всех примерах, даже если он явно не требуется для методов `GET`. - Строки `-H` задают требуемые HTTP-заголовки. - Примеры, для которых требуется объект JSON в теле запроса, передают требуемые данные через параметр `-d`.  Чтобы использовать приведенные примеры, не подставляя каждый раз в них свой токен, вы можете добавить токен один раз в переменные окружения в вашей консоли. Например, на `Linux` это можно сделать с помощью команды:  ``` TIMEWEB_CLOUD_TOKEN=\"token\" ```  После этого токен будет автоматически подставляться в ваши запросы.  Обратите внимание, что все значения в этой документации являются примерами. Не полагайтесь на идентификаторы операционных систем, тарифов и т.д., используемые в примерах. Используйте соответствующую конечную точку для получения значений перед созданием ресурсов.   ## Версионирование API построено согласно принципам [семантического версионирования](https://semver.org/lang/ru). Это значит, что мы гарантируем обратную совместимость всех изменений в пределах одной мажорной версии.  Мажорная версия каждой конечной точки обозначается в пути запроса, например, запрос `/api/v1/servers` указывает, что этот метод имеет версию 1.
 *
 * The version of the OpenAPI document: 
 * Contact: info@timeweb.cloud
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.AddStorageSubdomainCertificateRequest;
import org.openapitools.client.model.AddStorageSubdomains200Response;
import org.openapitools.client.model.AddStorageSubdomainsRequest;
import org.openapitools.client.model.CopyStorageFileRequest;
import org.openapitools.client.model.CreateDatabaseBackup409Response;
import org.openapitools.client.model.CreateFolderInStorageRequest;
import org.openapitools.client.model.CreateStorage201Response;
import org.openapitools.client.model.CreateStorageRequest;
import org.openapitools.client.model.DeleteStorage200Response;
import org.openapitools.client.model.DeleteStorageFileRequest;
import java.io.File;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances403Response;
import org.openapitools.client.model.GetFinances404Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetProjectStorages200Response;
import org.openapitools.client.model.GetStorageFilesList200Response;
import org.openapitools.client.model.GetStorageSubdomains200Response;
import org.openapitools.client.model.GetStorageTransferStatus200Response;
import org.openapitools.client.model.GetStorageUsers200Response;
import org.openapitools.client.model.GetStoragesPresets200Response;
import org.openapitools.client.model.RenameStorageFileRequest;
import org.openapitools.client.model.TransferStorageRequest;
import org.openapitools.client.model.UpdateStorageRequest;
import org.openapitools.client.model.UpdateStorageUser200Response;
import org.openapitools.client.model.UpdateStorageUserRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for S3Api
 */
@Disabled
public class S3ApiTest {

    private final S3Api api = new S3Api();

    /**
     * Добавление сертификата для поддомена хранилища
     *
     * Чтобы добавить сертификат для поддомена хранилища, отправьте POST-запрос на &#x60;/api/v1/storages/certificates/generate&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addStorageSubdomainCertificateTest() throws ApiException {
        AddStorageSubdomainCertificateRequest addStorageSubdomainCertificateRequest = null;
        api.addStorageSubdomainCertificate(addStorageSubdomainCertificateRequest);
        // TODO: test validations
    }

    /**
     * Добавление поддоменов для хранилища
     *
     * Чтобы добавить поддомены для хранилища, отправьте POST-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/subdomains&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addStorageSubdomainsTest() throws ApiException {
        Integer bucketId = null;
        AddStorageSubdomainsRequest addStorageSubdomainsRequest = null;
        AddStorageSubdomains200Response response = api.addStorageSubdomains(bucketId, addStorageSubdomainsRequest);
        // TODO: test validations
    }

    /**
     * Копирование файла/директории в хранилище
     *
     * Чтобы скопировать файла или директорию с вложениями, отправьте POST-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/object-manager/copy&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void copyStorageFileTest() throws ApiException {
        Integer bucketId = null;
        CopyStorageFileRequest copyStorageFileRequest = null;
        api.copyStorageFile(bucketId, copyStorageFileRequest);
        // TODO: test validations
    }

    /**
     * Создание директории в хранилище
     *
     * Чтобы создать директорию в хранилище, отправьте POST-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/object-manager/mkdir&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createFolderInStorageTest() throws ApiException {
        Integer bucketId = null;
        CreateFolderInStorageRequest createFolderInStorageRequest = null;
        api.createFolderInStorage(bucketId, createFolderInStorageRequest);
        // TODO: test validations
    }

    /**
     * Создание хранилища
     *
     * Чтобы создать хранилище, отправьте POST-запрос на &#x60;/api/v1/storages/buckets&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createStorageTest() throws ApiException {
        CreateStorageRequest createStorageRequest = null;
        CreateStorage201Response response = api.createStorage(createStorageRequest);
        // TODO: test validations
    }

    /**
     * Удаление хранилища на аккаунте
     *
     * Чтобы удалить хранилище, отправьте DELETE-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteStorageTest() throws ApiException {
        Integer bucketId = null;
        String hash = null;
        String code = null;
        DeleteStorage200Response response = api.deleteStorage(bucketId, hash, code);
        // TODO: test validations
    }

    /**
     * Удаление файла/директории в хранилище
     *
     * Чтобы удалить файл или директорию с вложениями, отправьте DELETE-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/object-manager/remove&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteStorageFileTest() throws ApiException {
        Integer bucketId = null;
        DeleteStorageFileRequest deleteStorageFileRequest = null;
        Boolean isMultipart = null;
        api.deleteStorageFile(bucketId, deleteStorageFileRequest, isMultipart);
        // TODO: test validations
    }

    /**
     * Удаление поддоменов хранилища
     *
     * Чтобы удалить поддомены хранилища, отправьте DELETE-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/subdomains&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteStorageSubdomainsTest() throws ApiException {
        Integer bucketId = null;
        AddStorageSubdomainsRequest addStorageSubdomainsRequest = null;
        AddStorageSubdomains200Response response = api.deleteStorageSubdomains(bucketId, addStorageSubdomainsRequest);
        // TODO: test validations
    }

    /**
     * Получение списка файлов в хранилище по префиксу
     *
     * Чтобы получить список файлов в хранилище по префиксу, отправьте GET-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/object-manager/list&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getStorageFilesListTest() throws ApiException {
        Integer bucketId = null;
        String prefix = null;
        Boolean isMultipart = null;
        GetStorageFilesList200Response response = api.getStorageFilesList(bucketId, prefix, isMultipart);
        // TODO: test validations
    }

    /**
     * Получение списка поддоменов хранилища
     *
     * Чтобы получить список поддоменов хранилища, отправьте GET-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/subdomains&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getStorageSubdomainsTest() throws ApiException {
        Integer bucketId = null;
        GetStorageSubdomains200Response response = api.getStorageSubdomains(bucketId);
        // TODO: test validations
    }

    /**
     * Получение статуса переноса хранилища от стороннего S3 в Timeweb Cloud
     *
     * Чтобы получить статус переноса хранилища от стороннего S3 в Timeweb Cloud, отправьте GET-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/transfer-status&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getStorageTransferStatusTest() throws ApiException {
        Integer bucketId = null;
        GetStorageTransferStatus200Response response = api.getStorageTransferStatus(bucketId);
        // TODO: test validations
    }

    /**
     * Получение списка пользователей хранилищ аккаунта
     *
     * Чтобы получить список пользователей хранилищ аккаунта, отправьте GET-запрос на &#x60;/api/v1/storages/users&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getStorageUsersTest() throws ApiException {
        GetStorageUsers200Response response = api.getStorageUsers();
        // TODO: test validations
    }

    /**
     * Получение списка хранилищ аккаунта
     *
     * Чтобы получить список хранилищ аккаунта, отправьте GET-запрос на &#x60;/api/v1/storages/buckets&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getStoragesTest() throws ApiException {
        GetProjectStorages200Response response = api.getStorages();
        // TODO: test validations
    }

    /**
     * Получение списка тарифов для хранилищ
     *
     * Чтобы получить список тарифов для хранилищ, отправьте GET-запрос на &#x60;/api/v1/presets/storages&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;storages_presets&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getStoragesPresetsTest() throws ApiException {
        GetStoragesPresets200Response response = api.getStoragesPresets();
        // TODO: test validations
    }

    /**
     * Переименование файла/директории в хранилище
     *
     * Чтобы переименовать файл/директорию в хранилище, отправьте POST-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/object-manager/rename&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void renameStorageFileTest() throws ApiException {
        Integer bucketId = null;
        RenameStorageFileRequest renameStorageFileRequest = null;
        api.renameStorageFile(bucketId, renameStorageFileRequest);
        // TODO: test validations
    }

    /**
     * Перенос хранилища от стороннего провайдера S3 в Timeweb Cloud
     *
     * Чтобы перенести хранилище от стороннего провайдера S3 в Timeweb Cloud, отправьте POST-запрос на &#x60;/api/v1/storages/transfer&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void transferStorageTest() throws ApiException {
        TransferStorageRequest transferStorageRequest = null;
        api.transferStorage(transferStorageRequest);
        // TODO: test validations
    }

    /**
     * Изменение хранилища на аккаунте
     *
     * Чтобы изменить хранилище, отправьте PATCH-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateStorageTest() throws ApiException {
        Integer bucketId = null;
        UpdateStorageRequest updateStorageRequest = null;
        CreateStorage201Response response = api.updateStorage(bucketId, updateStorageRequest);
        // TODO: test validations
    }

    /**
     * Изменение пароля пользователя-администратора хранилища
     *
     * Чтобы изменить пароль пользователя-администратора хранилища, отправьте POST-запрос на &#x60;/api/v1/storages/users/{user_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateStorageUserTest() throws ApiException {
        Integer userId = null;
        UpdateStorageUserRequest updateStorageUserRequest = null;
        UpdateStorageUser200Response response = api.updateStorageUser(userId, updateStorageUserRequest);
        // TODO: test validations
    }

    /**
     * Загрузка файлов в хранилище
     *
     * Чтобы загрузить файлы в хранилище, отправьте POST-запрос на &#x60;/api/v1/storages/buckets/{bucket_id}/object-manager/upload&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void uploadFileToStorageTest() throws ApiException {
        Integer bucketId = null;
        List<File> files = null;
        String path = null;
        api.uploadFileToStorage(bucketId, files, path);
        // TODO: test validations
    }

}
