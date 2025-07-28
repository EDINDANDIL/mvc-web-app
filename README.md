### **Краткое описание проекта**  

Данный проект представляет собой **веб-приложение на Java**, использующий **Spring Framework** в качестве основы. Приложение использует стиль **REST-архитектуру** для взаимодействия с клиентом и реализует **паттерн MVC** через модуль **Spring MVC**. Для работы с базой данных (**PostgreSQL**) применён **DAO-паттерн** с использованием **Spring JDBC (JdbcTemplate)**.  

### **Ключевые аспекты реализации**  

#### **1. Архитектура и паттерны**  
- **REST** – API приложения построено в REST-стиле:  
  - Использование HTTP-методов (GET, POST, PUT, DELETE).  
  - Ресурсо-ориентированные URL (`/users`, `/products`).  
- **MVC (Model-View-Controller)** – реализован через **Spring MVC**:  
  - **Контроллеры** (`@Controller`, `@RestController`) обрабатывают запросы.  
  - **Модель** (сущности `User`, `Product`, сервисы `UserService`) содержит бизнес-логику.  
  - **Представление** (Thymeleaf-шаблоны) рендерит HTML.  
- **DAO (Data Access Object)** – работа с БД через `JdbcTemplate` и репозитории (`UserRepository`).  

#### **2. Основные технологии**  
- **Spring Framework** – основа приложения:  
  - **Spring Core (DI, IoC)** – управление зависимостями (`@Autowired`, `@Component`).  
  - **Spring MVC** – обработка HTTP-запросов (`@GetMapping`, `@PostMapping`).  
  - **Spring JDBC** – упрощённая работа с БД (`JdbcTemplate`).  
  - **Spring Validation** – валидация данных (`@Valid`, `@Size`, `@NotNull`).  
- **Thymeleaf** – шаблонизация HTML.  
- **PostgreSQL** – реляционная СУБД для хранения данных.  

#### **3. Работа с данными**  
- **JDBC API + JdbcTemplate** – абстракция над "сырым" JDBC для удобного выполнения SQL-запросов.  
- **Валидация** – встроенная (`@Valid`, `@Email`) и кастомная (например, аннотация `@UniqueUsername`).  

#### **4. Стек проекта**  
| Категория       | Технологии / Компоненты                                                                 |
|----------------|---------------------------------------------------------------------------------------|
| **Backend**    | Java, Spring (Core, MVC, JDBC, Validation)                                            |
| **Frontend**   | Thymeleaf (рендеринг HTML на сервере)                                                 |
| **База данных**| PostgreSQL, Spring JDBC (JdbcTemplate)                                                |
| **API**        | REST (CRUD: GET, POST, PUT, DELETE)                                                   |
| **Валидация**  | Bean Validation (`jakarta.validation`), кастомные аннотации                           |

