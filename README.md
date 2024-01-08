**Задание:**

Есть множество (массив, где порядок не важен) целых чисел в диапазоне от 1 до 300.
Количество чисел - до 1000.  

Напишите функцию сериализации / десериализации в строку, чтобы итоговая строка была компактной.  

Цель задачи - максимально сжать данные относительно простой сериализации без алгоритма сжатия (хотя бы 50% в среднем).  

Сериализованная строка должна содержать только ASCII символы. Можно использовать любой язык программирования.

Вместе с решением нужно прислать набор тестов: 
исходная строка, сжатая строка, коэффициент сжатия.

Примеры тестов: 
- простейшие короткие
- случайные - 50 чисел
- 100 чисел
- 500 чисел
- 1000 чисел 
- граничные - все числа 1 знака, все числа из 2х знаков, все числа из 3х знаков, каждого числа по 3 - всего чисел 900.