

# ClusterIn


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Название кластера |  |
|**description** | **String** | Описание кластера |  [optional] |
|**ha** | **Boolean** | Описание появится позднее |  |
|**k8sVersion** | **String** | Версия Kubernetes |  |
|**networkDriver** | **String** | Тип используемого сетевого драйвера в кластере |  |
|**ingress** | **Boolean** | Логическое значение, которое показывает, использовать ли Ingress в кластере |  |
|**presetId** | **Integer** | Идентификатор тарифа мастер-ноды |  |
|**workerGroups** | [**List&lt;NodeGroupIn&gt;**](NodeGroupIn.md) | Группы воркеров в кластере |  [optional] |



