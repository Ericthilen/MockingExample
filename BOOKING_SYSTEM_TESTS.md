### Testdokumentation: BookingSystemTest

Dessa tester verifierar funktionaliteten i `BookingSystem` och syftar till att säkerställa hög tillförlitlighet och korrekt felhantering. Testtäckningen uppgår till över 90%.

#### Testade scenarier

##### 1. Rumsbokning (`bookRoom`)
- **Lyckad bokning:** Verifierar att ett ledigt rum kan bokas, sparas i databasen och att en bekräftelse skickas.
- **Ogiltiga parametrar:** 
  - Testar med `null` för starttid, sluttid och rums-id (parametriserat test).
- **Tidsvalidering:**
  - Försök att boka tid i dåtid (ska kasta `IllegalArgumentException`).
  - Försök att boka där sluttid är före starttid (ska kasta `IllegalArgumentException`).
- **Tillgänglighet:**
  - Rum som inte existerar (ska kasta `IllegalArgumentException`).
  - Rum som redan är upptaget under den valda perioden (ska returnera `false`).
- **Robusthet:**
  - Verifierar att bokningen genomförs även om notifieringstjänsten kastar ett undantag.

##### 2. Sökning efter lediga rum (`getAvailableRooms`)
- **Filtrering:** Verifierar att endast rum som faktiskt är lediga under den angivna perioden returneras.
- **Validering:** 
  - Testar ogiltiga tider (null eller felaktig ordning) via parametriserade tester.

##### 3. Avbokning (`cancelBooking`)
- **Lyckad avbokning:** Verifierar att en existerande bokning kan tas bort och att en bekräftelse skickas.
- **Felscenarier:**
  - Boknings-id som inte finns (ska returnera `false`).
  - Försök att avboka en påbörjad eller avslutad bokning (ska kasta `IllegalStateException`).
- **Robusthet:**
  - Verifierar att avbokningen genomförs även om notifieringstjänsten kastar ett undantag.

#### Tekniska detaljer
- **Ramverk:** JUnit 5, AssertJ, Mockito.
- **Mocks:** `TimeProvider`, `RoomRepository`, `NotificationService`.
- **Coverage:** >90% instruktionstäckning.
