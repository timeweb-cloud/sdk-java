

# ClusterOut


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | Уникальный идентификатор кластера |  |
|**name** | **String** | Название |  |
|**createdAt** | **OffsetDateTime** | Дата и время создания кластера в формате ISO8601 |  |
|**status** | **String** | Статус |  |
|**description** | **String** | Описание |  |
|**ha** | **Boolean** | Описание появится позже |  |
|**k8sVersion** | **String** | Версия Kubernetes |  |
|**networkDriver** | **String** | Используемый сетевой драйвер |  |
|**ingress** | **Boolean** | Логическое значение, показывающее, включен ли Ingress |  |
|**presetId** | **Integer** | Идентификатор тарифа мастер-ноды |  |
|**cpu** | **Integer** | Общее количество ядер |  [optional] |
|**ram** | **Integer** | Общее количество памяти |  [optional] |
|**disk** | **Integer** | Общее дисковое пространство |  [optional] |



