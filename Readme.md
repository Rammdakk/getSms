# GetSms - сервис для аренды номеров.

За основу взят был сервис [Vak-Sms](https://vak-sms.com) и использовалось его API.<br>
Часть API была не публична и взята из источников сайта, но большую часть можно
найти [тут](https://vak-sms.com/api/vak/).<br>


### Окно авторизации:

На текущий момент авторизация сделана навтивно в приложении, а восстановление пароля и регистрация
происходит через WebView.
C webView убраны все лишние элементы, таким образом кроме целевого действия ничего сделать не
возможно, а внейшний вид приближен к нативному<br>

<img src="https://user-images.githubusercontent.com/68683848/228680043-46a71ef5-7bb2-402c-ba5a-58629b08ee0d.png" width="170" /> <img src="https://user-images.githubusercontent.com/68683848/229636597-a16760c1-8e9c-406e-b2d5-8fcf5c96dc92.png" width="170" />


### Главное окно

Содержит в себе два окна - все сервисы и номера в аренде, а также отображает баланс и кнопку для
пополнения.

#### - Все сервисы
На данном окне можно выбрать страну и затем приобрести номер для нужного сервиса. <br>

<img src="https://user-images.githubusercontent.com/68683848/228679367-353b83c3-36fd-497e-8cf6-3d6a010638f4.png" width="170" />

#### - Номера в аренде
На данной странице отображаются купленные номера, информация по кодам обновляется раз в 15 секунд при наличии номеров и раз в минуту при отсутсвии, чтоб детектить номера, которые пришли с веба, например.<br>

<img src="https://user-images.githubusercontent.com/68683848/229636240-798d1936-2a2a-4bb8-a3d5-ebfc10ef0c14.png" width="170" />

### Ошибки

Информирование об ошибках оформлено в виде всплывающего окна внизу экрана.<br>

<img src="https://user-images.githubusercontent.com/68683848/229637624-18d2ea4f-f0b2-441e-9910-de329f7f58a9.png" width="170" /> <img src="https://user-images.githubusercontent.com/68683848/229637636-3358513a-db1f-45aa-8d92-cc0bad0c772a.png" width="170" />

## Информация о дизайн составляющей

### Цвета<br>

background - ![#171a3b](https://placehold.co/15x15/171a3b/171a3b.png) `#171a3b`<br>
text - ![#f69495](https://placehold.co/15x15/f69495/f69495.png) `#f69495`<br>
background2 - ![#252950](https://placehold.co/15x15/252950/252950.png) `#252950`<br>
button - ![#283261](https://placehold.co/15x15/283261/283261.png) `#283261`<br>
green cell - ![#59c355](https://placehold.co/15x15/59c355/59c355.png) `#59c355`<br>
red cell - ![#ff685c](https://placehold.co/15x15/ff685c/ff685c.png) `#ff685c`<br>
orange cell -  ![#ffbd5f](https://placehold.co/15x15/ffbd5f/ffbd5f.png) `#ffbd5f`<br>
<br>

### Полезные сссылки:

#### - Иконки

[IconPack](https://www.svgrepo.com/collection/yandex-ui-filled-icons/2)

#### - Картинки

Сервисы (пример) - https://vak-sms.com/static/service/ab.png<br>
Страны (пример) - https://vak-sms.com/static//country/dk.png

