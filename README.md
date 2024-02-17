
# Opis

Jest to aplikacja serwerowa oparta o REST API pozwalająca na komunikację z Github.




## Technologie

- Java 21
- Spring Boot 3
- JUnit 5 i Mockito
- Maven
## Jak uruchomić

Aby uruchomić aplikację, trzeba mieć na swoim komputerze zainstalowanego Maven. Pod następującym linkiem znajduje się poradnik jak to zrobić: https://maven.apache.org/install.html. Następnie, przy pomocy konsoli systemowej (np. cmd albo bash) przejść do głównego folderu aplikacji (github-api) i wykonać komendę:  

```
mvn spring-boot:run
```
## Endpoint

#### Pobierz wszystkie repozytoria użytkownika

```
GET http://localhost:8080/api/repositories?username={USERNAME}
```

| Parametr | Typ     | Opis                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Wymagany**. Nazwa użytkownika |

