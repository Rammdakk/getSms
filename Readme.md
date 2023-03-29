# GetSms - сервис для аренды номеров.

За основу взят был сервис [Vak-Sms](https://vak-sms.com) и использовалось его API.<br>
Часть API была не публична и взята из источников сайта, но большую часть можно
найти [тут](https://vak-sms.com/api/vak/)<br>

## Что сделано:

На текущий момент полностью реализованы окна входа/регистрации, а также окно выбора номеров.

### Окно авторизации:

На текущий момент авторизация сделана навтивно в приложении, а восстановление пароля и регистрация
происходит через WebView. <br>

### Главное окно

Содержит в себе два окна - все сервисы и номера в аренде, а также отображает баланс и кнопку для
пополнения.

#### Все сервисы

Реализован выбор страны, и отображение подходящих номеров, осталось реализовать покупку номера по
клику.

#### Номера в аренде

Экран в процессе реализации

## Цвета<br>

Background_main - ![#171a3b](https://placehold.co/15x15/171a3b/171a3b.png) `#171a3b`<br>
text - ![#f69495](https://placehold.co/15x15/f69495/f69495.png) `#f69495`<br>
background2 - ![#252950](https://placehold.co/15x15/f03c15/252950.png) `#252950`<br>
button - ![#283261](https://placehold.co/15x15/283261/283261.png) `#283261`<br>
green cell - ![#59c355](https://placehold.co/15x15/59c355/59c355.png) `#59c355`<br>
red cell - ![#ff685c](https://placehold.co/15x15/ff685c/ff685c.png) `#ff685c`<br>
orange cell -  ![#ffbd5f](https://placehold.co/15x15/ffbd5f/ffbd5f.png) `#ffbd5f`<br>
<br>

## Полезные сссылка:

### Иконки

[IconPack](https://www.svgrepo.com/collection/yandex-ui-filled-icons/2)

### Картинки

Сервисы (пример) - https://vak-sms.com/static/service/ab.png<br>
Страны (пример) - https://vak-sms.com/static//country/dk.png

