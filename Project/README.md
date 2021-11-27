# Метео.Hub

## Что есть:
Ветка master - до дедлайна
Ветка dev - после, с доп функционалом и документацией

Приложение отображения погоды на основе: https://openweathermap.org/api/one-call-api
Бесплатное API отдающее погодные данные по координатам. 

3 экрана:
* Экран со списком (уменьшенный набор атрибутов)
* Экран с детальной информацией по пункту списка (полный набор атрибутов, но не все, что предоставляет API)
* Экран с настройками (можно выбрать город двумя способами)

### Use case:

Пользователь может просматривать погоду на главном экране, выбрав предварительно свой город
Город можно выбрать по GPS, так и поиском по названию в списке
Также можно увидеть подробную информацию по дню

### Логика:
Язык: Kotlin

Architecture:
* MVVM + костыли

Threading:
* RxJava

Network:
* Retrofit

Storage:
* Room
* Preferences

DI:
* Dagger + костыли

Location:
* LocationManager

Feature:
* Самостоятельное обратное геокодирование без бэка
* Расчет дистанций по great circle

### UI:
* Activity
* RecyclerView
* SearchView
* TableLayout
* Gradient background

## API doc:
api.openweathermap.org/data/2.5/onecall?lat=55.727649&lon=37.536683&exclude=minutely,hourly,alerts&units=metric&lang=ru&appid=4e58c5be8ff989deef7e876753dfb670<br/>
Апи отдает данные за текущий день, а также на неделю вперед

### Использованные атрибуты:
В списке:
* dt - дата в формате "EEEE, d MMMM"
* dayTemp - температура днем, градусы
* nightTemp - температура ночью, градусы
* description - текстовое описание, пример: "Облачно"
	
В детальной информации, в дополнение:
* pressure - давление, сконвертированне в мм. рт. столба
* humidity - влажность в %
* windSpeed - скорость ветра в м/с
* sunrise - время восхода, в формате "HH:MM"
* sunset - время захода, в формате "HH:MM"
* windDeg - направление ветра, в градусах
* dewPoint - точка росы, в градусах
    
### Скрины:
<a href="url"><img src="https://github.com/Bagaviev/AndroidSchool2021.2/blob/master/Project/scr1.jpeg" align="left" height="500" width="250" ></a>
<a href="url"><img src="https://github.com/Bagaviev/AndroidSchool2021.2/blob/master/Project/scr2.jpeg" align="left" height="500" width="250" ></a>
<a href="url"><img src="https://github.com/Bagaviev/AndroidSchool2021.2/blob/master/Project/scr3.jpeg" align="left" height="500" width="250" ></a>