

# Clusterk8s

Кластер

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **BigDecimal** | Уникальный идентификатор для каждого экземпляра крастера. Автоматически генерируется при создании. |  |
|**name** | **String** | Удобочитаемое имя, установленное для кластера. |  |
|**createdAt** | **OffsetDateTime** | Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда был создан кластер. |  |
|**status** | [**StatusEnum**](#StatusEnum) | Статус кластера. |  |
|**description** | **String** | Описание кластера. |  |
|**ha** | **Boolean** | Описание появится позднее. |  |
|**k8sVersion** | **String** | Версия k8s. |  |
|**networkDriver** | **String** | Описание появится позднее. |  |
|**ingress** | **Boolean** | Описание появится позднее. |  |
|**cpu** | **BigDecimal** | Количество ядер процессора кластера. |  |
|**ram** | **BigDecimal** | Количество (в Мб) оперативной памяти кластера. |  |
|**disk** | **BigDecimal** | Размер (в Гб) диска кластера. |  |
|**presetId** | **BigDecimal** | Тип сервиса кластера. |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| INSTALLING | &quot;installing&quot; |
| PROVISIONING | &quot;provisioning&quot; |
| ACTIVE | &quot;active&quot; |
| UNPAID | &quot;unpaid&quot; |



