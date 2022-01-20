insert into docentenverantwoordelijkheden(docentId, verantwoordelijkheidId) values
((select id from docenten where voornaam = 'testM'), (select id from verantwoordelijkheden where naam = 'test'));