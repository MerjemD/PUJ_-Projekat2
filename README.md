# PUJ_-Projekat2

# Life Management System

## Opis projekta

Life Management System je desktop aplikacija razvijena u Javi (Swing) koja korisnicima omogućava praćenje svakodnevnih navika i aktivnosti kroz više trackera. Projekat je rađen u edukativne svrhe kao studentski projekat, s ciljem povezivanja rada s bazom podataka, grafičkog korisničkog interfejsa i osnovnih principa objektno-orijentisanog programiranja.

Aplikacija podržava rad sa više tipova korisnika (USER i ADMIN), kao i rad s temama (themes) koje se primjenjuju na cijelu aplikaciju.



## Korištene tehnologije

* **Java (Swing GUI)** – izrada grafičkog interfejsa
* **MongoDB** – baza podataka
* **MongoDB Java Driver** – komunikacija s bazom
* **Apache PDFBox** – export podataka u PDF format
* **JFreeChart** – grafički prikaz statistike
* **Maven** – upravljanje zavisnostima



## Funkcionalnosti aplikacije

### 1. Autentifikacija

* Prijava korisnika (USER)
* Prijava administratora (ADMIN)
* Razdvojene forme za user i admin login



### 2. Korisnički dio (USER)

Nakon prijave, korisniku je dostupan **Main Menu** sa sljedećim opcijama:

* **Sleep Tracker** – unos i pregled zapisa o snu
* **Water Tracker** – praćenje unosa vode
* **Screen Time Tracker** – praćenje vremena provedenog pred ekranom
* **Finance Tracker** – osnovno praćenje finansijskih transakcija
* **Export u PDF** – izvoz korisničkih podataka u PDF fajl

Svaki tracker ima:

* unos novih zapisa
* pregled postojećih zapisa
* čuvanje podataka u MongoDB bazu

Tema koju korisnik odabere prilikom registracije primjenjuje se na sve forme.



### 3. Administratorski dio (ADMIN)

Admin dashboard je podijeljen u više logičkih cjelina:

#### Upravljanje korisnicima

* pregled svih korisnika (JTable)
* kreiranje novih korisnika
* kreiranje novih administratora
* brisanje korisnika
* promjena lozinke za bilo kojeg korisnika

#### Statistika trackera

* pregled broja zapisa po trackerima za odabranog korisnika
* grafički prikaz statistike (bar chart)

#### Admin akcije

* export svih korisnika u **PDF format**


## Struktura projekta (paketi)

* `views` – sve Swing forme (Login, MainMenu, AdminDashboard, Trackeri)
* `views.trackers` – forme za pojedinačne trackere
* `services` – poslovna logika i rad s bazom
* `models` – model klase (User, SleepRecord, WaterRecord, itd.)
* `database` – MongoDB konekcija
* `utils` – pomoćne klase (ThemeManager)



## Baza podataka

Korištena je MongoDB baza pod nazivom:


lifeManagementDB


Kolekcije:

* `users`
* `sleep`
* `water`
* `screen`
* `transactions`

Svaki zapis je vezan za korisnika putem polja `username`.



## Pokretanje projekta

1. Pokrenuti MongoDB server (`mongodb://localhost:27017`)
2. Otvoriti projekat u IntelliJ IDEA
3. Provjeriti da su Maven dependencies učitane
4. Pokrenuti početnu formu (Login form)





