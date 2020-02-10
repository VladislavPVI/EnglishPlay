# Play-English
Цель проекта:
создание Android-приложения для помощи в изучении английского языка с использованием объектно-ориентированного языка программирования Java.

**Разработка производилась в Android Studio. База данных - SQLite. Для озвучивания слов используется Google Text-to-Speech. 
Материалы для приложения были подготвлены с помощью: Autodesk 3ds Max, Adobe Photoshop. Разработан лексический анализатор.**

Под помощью в изучении английского языка подразумеваются различные режимы, которые позволят пользователю, как получить определённые знания, так и проверить, насколько хорош его уровень языка. Дизайн, различные развлекательные моменты и озвучивание слов должны дать понять пользователю, что изучение языка это не всегда скучно.

## Внешний вид и взаимодействие с пользователем
<img src="https://github.com/VladislavPVI/Play-English/blob/master/docs/%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%BE%D0%B5%20%D0%BE%D0%BA%D0%BD%D0%BE.jpg" width="202.33" height="360" /> | <img src="https://github.com/VladislavPVI/Play-English/blob/master/docs/%D0%B0%D0%BD%D0%B8%D0%BC%D0%B0%D1%86%D0%B8%D1%8F.jpg" width="202.33" height="360" />
------------ | -------------

После запуска приложения пользователю открывается начальное окно, где ему необходимо нажать кнопку Start. После нажатия на копку появляется ролик с названием игры, после просмотра которого пользователь должен нажать на кнопку Пропустить, чтобы перейти в главное меню приложения.

В главном меню приложения пользователю нужно выбрать один из четырёх вариантов:

<img src="https://github.com/VladislavPVI/Play-English/blob/master/docs/%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%BE%D0%B5%20%D0%BC%D0%B5%D0%BD%D1%8E.jpg" width="202.33" height="360" /> | <img src="https://github.com/VladislavPVI/Play-English/blob/master/docs/level%201.jpg" width="202.33" height="360" /> | <img src="https://github.com/VladislavPVI/Play-English/blob/master/docs/level%202.jpg" width="202.33" height="360" />
------------ | ------------- | -------------
Главное меню | Простой уровень | Сложный уровень

-	Уровни: главные режимы игры.
-	Статистика: календарь (можно узнать, сколько очков было получено в определённый день).
-	Словарь: редактор базы данных. Можно вносить новые слова, а так же смотреть уже имеющиеся.
-	Бонус: создан для развлечения. Каждое нажатие на экран приведёт к появлению главного героя, который будет летать по экрану, отталкиваясь от границ (не переусердствуйте).

## Уровни
При нажатии на кнопку, пользователю предоставляется возможность выбора уровня сложности, а так же есть возможность просмотра вводного видео для каждого из них. В дальнейшем режимы игры будем распределять по двум уровням сложности.
### Простой уровень
-	Составь предложение: пользователю предлагается составить правильное предложение из предложенных слов. Работа режима основана на лексическом анализаторе. Используется база данных. Есть возможность использовать подсказку (всплывает верное предложение). Для проверки введённого пользователем предложения необходимо нажать кнопку Check. Стереть предложение можно с помощью нажатия на кнопку Delete. Попробовать снова – Restart.
-	Поймай слово: пользователю предлагается поймать главным героем английское слово на одном из трёх блюд падающих с верхней части экрана. Русское слово на теле главного героя должно совпадать с соответствующим английским словом на блюде.
-	Отгадай слово: сопоставить английское слово картинке, нажатием на одну из трёх кнопок.

<img src="https://github.com/VladislavPVI/Play-English/blob/master/docs/%D1%81%D0%BE%D1%81%D1%82%D0%B0%D0%B2%D1%8C%20%D0%BF%D1%80%D0%B5%D0%B4%D0%BB.jpg" width="202.33" height="360" /> | <img src="https://github.com/VladislavPVI/Play-English/blob/master/docs/%D0%BF%D0%BE%D0%B9%D0%BC%D0%B0%D0%B9%20%D1%81%D0%BB%D0%BE%D0%B2%D0%BE.jpg" width="202.33" height="360" /> | <img src="https://github.com/VladislavPVI/Play-English/blob/master/docs/%D0%BE%D1%82%D0%B3%D0%B0%D0%B4%D0%B0%D0%B9%20%D1%81%D0%BB%D0%BE%D0%B2%D0%BE.jpg" width="202.33" height="360" /> 
------------ | ------------- | -------------
Составь предложение | Поймай слово | Отгадай слово

### Сложный уровень
-	Составь предложение: есть набор определенных предложений, каждый раз пользователю предлагается поставить слова этого предложения в правильном порядке.
-	Поймай слово: тоже самое, что и в простом уровне сложности. Единственное отличие – увеличение скорости падения блюд при каждом правильном пойманном слове.
-	Синонимы: даны 3 пары (слово и его синоним). Задача игрока правильно сопоставить эти пары.

<img src="https://github.com/VladislavPVI/Play-English/blob/master/docs/Screenshot_20200210-235730_Play%20English.jpg" width="202.33" height="360" /> | <img src="https://github.com/VladislavPVI/Play-English/blob/master/docs/Screenshot_20200211-000838_Play%20English.jpg" width="202.33" height="360" /> | <img src="https://github.com/VladislavPVI/Play-English/blob/master/docs/Screenshot_20200210-235854_Play%20English.jpg" width="202.33" height="360" /> 
------------ | ------------- | -------------
Составь предложение | Поймай слово | Найди синонимы
