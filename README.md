## Информация о проекте
Необходимо организовать систему учета для питомника в котором живут домашние и Pack animals. 

## Задание

1. Создать диаграмму классов с родительским классом "Животные", и двумя подклассами: "Pets" и "Pack animals".
В составы классов которых в случае Pets войдут классы: собаки, кошки, хомяки, а в класс Pack animals войдут: Лошади, верблюды и ослы).
Каждый тип животных будет характеризоваться (например, имена, даты рождения, выполняемые команды и т.д)
(https://github.com/GodrickRickord69/ProgrammRecordAnimal/blob/Resources/classDiagram.drawio)

2. Создать иерархию классов в Java, который будет повторять диаграмму классов созданную в задаче 1(Диаграмма классов).

3. Написать программу на Java, которая будет имитировать реестр **домашних животных**. 
Должен быть реализован следующий функционал:
    
   1. Добавление нового животного
        - Реализовать функциональность для добавления новых животных в реестр.       
 Животное должно определяться в правильный класс (например, "собака", "кошка", "хомяк" и т.д.)
        
 
   2. Список команд животного
        - Вывести список команд, которые может выполнять добавленное животное (например, "сидеть", "лежать").
        
   3. Обучение новым командам
        - Добавить возможность обучать животных новым командам.
   4. Вывести список животных по дате рождения

4. Навигация по меню
        - Реализовать консольный пользовательский интерфейс с меню для навигации между вышеуказанными функциями.
        
5. Счетчик животных
Создать механизм, который позволяет вывести на экран общее количество созданных животных любого типа (Как домашних, так и вьючных), то есть при создании каждого нового животного счетчик увеличивается на “1”. 

6. Работа с MySQL. После создания диаграммы классов, база данных "Human Friends" должна быть структурирована в соответствии с этой диаграммой. Например, можно создать таблицы, которые будут соответствовать классам "Pets" и "Pack animals", и в этих таблицах будут поля, которые характеризуют каждый тип животных (например, имена, даты рождения, выполняемые команды и т.д.).

```sql
DROP database IF EXISTS Human_friends_new;
CREATE DATABASE Human_friends_new;
USE Human_friends_new;
```

7. Создать таблицы, соответствующие иерархии из вашей диаграммы классов.

```sql
DROP TABLE IF EXISTS animal_classes;
CREATE TABLE animal_classes
(
	Id INT AUTO_INCREMENT PRIMARY KEY, 
	Class_name VARCHAR(100)
);

INSERT INTO animal_classes (Class_name)
VALUES ('vijuch'),
('home');
```

```sql
CREATE TABLE vijuch_animals
(
	  Id INT AUTO_INCREMENT PRIMARY KEY,
    Kind_animal VARCHAR (100),
    Class_id INT,
    FOREIGN KEY (Class_id) REFERENCES animal_classes (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO vijuch_animals (Kind_animal, Class_id)
VALUES ('Horeses', 1),
('Donkey', 1),  
('Camels', 1); 
```

```sql
CREATE TABLE home_animals
(
	  Id INT AUTO_INCREMENT PRIMARY KEY,
    Kind_name VARCHAR (100),
    Class_id INT,
    FOREIGN KEY (Class_id) REFERENCES animal_classes (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO home_animals (Kind_name, Class_id)
VALUES ('Cats', 2),
('Dogs', 2),  
('Hamsters', 2);
```

8. Заполнить таблицы данными о животных, их командах и датами рождения.

```sql
CREATE TABLE cats 
(       
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(100), 
    Birthday DATE,
    Commands VARCHAR(100),
    Kind_id int,
    Foreign KEY (Kind_id) REFERENCES home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO cats (Name, Birthday, Commands, Kind_id)
VALUES ('Garik', '2021-05-01', 'pryzhok', 1),
('Kostik', '2022-03-05', "lezhat", 1),  
('Margosha', '2019-10-01', NULL, 1),
('Sneg', '2023-09-02', NULL, 1); 


CREATE TABLE dogs 
(       
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(100), 
    Birthday DATE,
    Commands VARCHAR(100),
	Kind_id int,
    Foreign KEY (Kind_id) REFERENCES home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO dogs (Name, Birthday, Commands, Kind_id)
VALUES ('Muhtar', '2020-04-16', 'lezhat, sidet, golos', 2),
('Barbos', '2022-01-23', "lezhat, sidet, lapu", 2),  
('Djesi', '2024-11-03', NULL, 2), 
('Dag', '2020-05-05', "lezhat, sidet, golos, fas, fu", 2);

CREATE TABLE hamsters 
(       
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(100), 
    Birthday DATE,
    Commands VARCHAR(100),
    Kind_id int,
    Foreign KEY (Kind_id) REFERENCES home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO hamsters (Name, Birthday, Commands, Kind_id)
VALUES ('Zheny', '2021-02-12', NULL, 3),
('Krepysh', '2020-03-27', "vpered", 3),  
('Busy', '2023-06-13', NULL, 3), 
('Dzhery', '2024-05-10', NULL, 3);

CREATE TABLE horses 
(       
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(100), 
    Birthday DATE,
    Commands VARCHAR(100),
    Kind_id int,
    Foreign KEY (Kind_id) REFERENCES vijuch_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO horses (Name, Birthday, Commands, Kind_id)
VALUES ('Veriat', '2018-05-12', "shagom, golopom", 1),
('Faraon', '2019-03-21', "shagom, golopom, rysiju", 1),  
('Ostvind', '2017-05-12', "shagom, golopom, rysiju", 1), 
('Plotva', '2020-01-15', "shagom, golopom, rysiju", 1);

CREATE TABLE donkeys 
(       
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(100), 
    Birthday DATE,
    Commands VARCHAR(100),
    Kind_id int,
    Foreign KEY (Kind_id) REFERENCES vijuch_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO donkeys (Name, Birthday, Commands, Kind_id)
VALUES ('Buharskij', '2019-03-12', NULL, 2),
('Tatarik', '2020-03-12', "", 2),  
('Bili', '2021-09-19', "", 2), 
('Toba', '2023-10-10', NULL, 2);

CREATE TABLE Camels 
(       
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(20), 
    Birthday DATE,
    Commands VARCHAR(50),
    Kind_id int,
    Foreign KEY (Kind_id) REFERENCES vijuch_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO Camels (Name, Birthday, Commands, Kind_id)
VALUES ('Vasja', '2021-05-25', 'stop', 3),
('Argo', '2020-03-12', "nazad", 3),  
('Kazbek', '2021-09-19', "", 3), 
('Sultan', '2023-10-10', NULL, 3);
```

9. Удалить записи о верблюдах и объединить таблицы лошадей и ослов.

```sql
SET SQL_SAFE_UPDATES = 0;
DELETE FROM camels;

SELECT Name, Birthday, Commands FROM horses
UNION SELECT  Name, Birthday, Commands FROM donkeys;
```

10. Создать новую таблицу для животных в возрасте от 1 до 3 лет и вычислить их возраст с точностью до месяца.

```sql
CREATE TABLE yang_animal AS
SELECT Name, Birthday, Commands, tipe, TIMESTAMPDIFF(MONTH, Birthday, CURDATE()) AS Age_in_month
FROM Animals WHERE Birthday BETWEEN ADDDATE(curdate(), INTERVAL -3 YEAR) AND ADDDATE(CURDATE(), INTERVAL -1 YEAR);
 
SELECT * FROM yang_animal;
```

11. Объединить все созданные таблицы в одну, сохраняя информацию о принадлежности к исходным таблицам.

```sql
DROP TABLE IF EXISTS Animals;
CREATE TABLE Animals AS 
SELECT *, 'Horses' as tipe FROM horses
UNION SELECT *, 'Donkeys' AS tipe FROM donkeys
UNION SELECT *, 'Dogs' AS tipe FROM dogs
UNION SELECT *, 'Cats' AS tipe FROM cats
UNION SELECT *, 'Hamsters' AS tipe FROM hamsters
UNION SELECT *, 'Camels' AS tipe FROM Camels
;
```
