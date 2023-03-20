
# Mini Social Network Management

	Proiect facut pentru gestionarea unei retele de socializare minimaliste. Codul a fost implementat in limbajul de programare Java, folosind baza de date PostgresSQL pentru gestionarea datelor aplicatiei. Codul este construit pe baza unei arhitecturi stratificate, fiecare clasa jucand un rol important in reusita functionarii codului. Entitatile principale sunt reprezentate de Utilizator si Friendship, fiecaruia implementandu-se functii CRUD si validatori/exceptii. Urmeaza clasele de RepositoryDB care fac legatura intre cod si baza de date Postgres. Folosind comenzi SQL, am creat functii pentru add, delete, findById, findAll, update. Toate aceste functii principale ajuta la crearea functionalitatilor pentru aplicatie in clasa Service, unde reunim si folosim ambele clase de Repository in diferite scopuri. Pentru comunicarea cu userul, exista clasa UI, o interfata minimalista in consola, user friendly, unde utilizatorul poate sa gestioneze in mai multe moduri utilizatorii si prieteniile acestora din baza de date. 
