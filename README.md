# Тестовое задание "Android разработчик на Kotlin"

## Задание:
У вас два смартфона. Телефон А выполняет роль клиента, телефон Б выполняет роль сервера.

### Клиент. 
UI на Compose: кнопка Config и кнопка Начать/Пауза. В кофиге указывает ip и порт сервера, нажимаем Сохранить, потом можно редактировать и сохранять.

### Сервер. 
UI на Compose: кнопка Config для выбора порта, кнопка Начать для запуска сервера, кнопка Выключить для выключения сервера, кнопка Логи для просмотра логов из локальной БД.

При нажатии на Старт приложение открывает гугл хром на устройстве и обращается к серверу. Сервер получает информацию, что хром открыт, начинает отправлять клиенту параметры для Gesture (свайпы вверх-вниз разной длины). Клиент свайпит в хроме (использовать Android Accessibility Service) и отчитывается серверу о результатах. Сервер всё пишет в локальную базу SQLite. Так происходит до момента, когда на клиенте будет нажата Пауза. При нажатии на Старт процесс продолжается. Сервер может обслуживать произвольное количество клиентов, используя корутины. Сервер и клиент общаются по протоколу websocket.

Для сетевого взаимодействия использовать Ktor.

### Что хочется увидеть в проекте ?

1. Понимание модульности в проекте(в нашем случае это два приложения рамках одного проекта клиент и сервер). 
2. Применения шаблона проектирования Singleton.
3. Умение рефакторить собственный код.
4. Использование DI решений.
5. Отсутствие закомментированного кода.
6. Разделение сервисов на слои.

## Реализация:
### Стек: 
- Clean architecture
- MVVM
- LiveData
- Compose
- Ktor
- Gson
- Room
- Shared preferences

### Клиент.
<img src="https://github.com/Jaroslav-89/BhTestIsmaev/blob/dev/client_main_screen.jpg" width="270" height="600" alt="Client main screen">

<img src="https://github.com/Jaroslav-89/BhTestIsmaev/blob/dev/client_open_config.jpg" width="270" height="600" alt="Client open config">

### Сервер. 
<img src="https://github.com/Jaroslav-89/BhTestIsmaev/blob/dev/server_main_screen.jpg" width="270" height="600" alt="Server main screen">

<img src="https://github.com/Jaroslav-89/BhTestIsmaev/blob/dev/server_open_config.jpg)" width="270" height="600" alt="Server open config">

<img src="https://github.com/Jaroslav-89/BhTestIsmaev/blob/dev/server_log_screen.jpg" width="270" height="600" alt="Server log screen">

### Для работы приложения Клиента требуется дать разрешение в разделе настроек Специальные возможности/Доступность
