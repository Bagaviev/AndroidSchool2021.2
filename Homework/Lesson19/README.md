# Дз №19

## Что есть:
Приложение отображения погоды на основе: https://openweathermap.org/api/one-call-api
Бесплатное API отдающее погодные данные по координатам. Будет развитие далее в проекте с парой новых фич.

2 экрана:
* Экран со списком (уменьшенный набор атрибутов, )
* Экран с детальной информацией (полный набор атрибутов (не все, что предоставляет API))

Threading:
* RxJava

Architecture:
* MVVM

## API doc:
api.openweathermap.org/data/2.5/onecall?lat=55.727649&lon=37.536683&exclude=minutely,hourly,alerts&units=metric&lang=ru&appid=4e58c5be8ff989deef7e876753dfb670<br/>
Апи отдает данные за текущий день, а также на неделю вперед

### Использованные атрибуты:
В списке:
* dt - дата в формате "EEEE, d MMMM"
* dayTemp - температура днем, градусы
* nightTemp - температура ночью, градусы
* description - текстовое описание, пример: "Облачно"
	
В детальной информации
* pressure - давление, сконвертированне в мм. рт. столба
* humidity - влажность в %
* windSpeed - скорость ветра в м/с

### Скрины:
<a href="url"><img src="https://github.com/Bagaviev/AndroidSchool2021.2/blob/master/Homework/Lesson19/list.jpeg" align="left" height="500" width="250" ></a>
<a href="url"><img src="https://github.com/Bagaviev/AndroidSchool2021.2/blob/master/Homework/Lesson19/detail.jpeg" align="left" height="500" width="250" ></a>