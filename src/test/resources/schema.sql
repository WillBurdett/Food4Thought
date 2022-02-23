Create table mealss(id int primary key, name varchar(50),allergy_info varchar(50),difficulty varchar(50),ingredients varchar(50),steps varchar(50),meal_time varchar(50));

CREATE table chefss (id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(255),email VARCHAR(255),location VARCHAR(255),price INT);
CREATE table matchess (id INT PRIMARY KEY,chefs_id INT REFERENCES chefs(id), meals_id INT REFERENCES meals(id));

