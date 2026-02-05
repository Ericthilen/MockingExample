### Refaktureringsdokumentation: PaymentProcessor

Syftet med refaktureringen var att göra `PaymentProcessor` testbar genom att bryta dess hårda beroenden och tillämpa Dependency Injection.

#### Identifierade problem
Tidigare var koden utkommenterad men den visade på flera problem för testbarhet:
1. **Hårda beroenden:** Den använde statiska anrop till `PaymentApi`, `DatabaseConnection` och `EmailService`. Detta gjorde det omöjligt att isolera klassen vid tester.
2. **Statisk konfiguration:** API-nyckeln var hårdkodad som en konstant.
3. **Dold sidoeffekt:** Databasanrop och e-postutskick skedde direkt i metoden utan möjlighet att mocka dem.

#### Genomförda förändringar
1. **Extrahering av gränssnitt:**
   - `PaymentApiClient`: För kommunikation med betaltjänsten.
   - `PaymentDatabase`: För lagring av betalningsstatus.
   - `PaymentEmailService`: För utskick av bekräftelser.
2. **Dependency Injection:**
   - Klassen tar nu emot alla sina beroenden via konstruktorn:
     ```java
     public PaymentProcessor(String apiKey, PaymentApiClient paymentApiClient, PaymentDatabase paymentDatabase, PaymentEmailService emailService)
     ```
3. **Separation av logik:**
   - Metoden `processPayment` använder nu de injicerade tjänsterna istället för statiska anrop.
   - API-nyckeln injiceras vid skapandet, vilket tillåter olika nycklar för olika miljöer (t.ex. test-nycklar).

#### Testbarhet
Genom dessa ändringar kan vi nu skriva enhetstester i `PaymentProcessorTest` där vi:
- Mockar `PaymentApiClient` för att simulera både lyckade och misslyckade betalningar.
- Verifierar att rätt anrop görs till `PaymentDatabase`.
- Verifierar att e-post endast skickas vid lyckade betalningar.
- Testar logiken helt utan att faktiskt anropa en extern tjänst eller databas.
