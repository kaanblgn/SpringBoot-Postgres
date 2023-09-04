# Banka Uygulaması README

Bu README dosyası, Java ve Spring Boot kullanarak geliştirilen bir banka uygulamasını tanıtmak için tasarlanmıştır. Uygulama, hesap sahipleri, hesaplar, işlemler ve banka nesnelerini içerir ve temel bankacılık işlemlerini gerçekleştirmenize olanak tanır.

## Proje Kapsamı

Proje, aşağıdaki nesneleri içermektedir:

- **Hesap Sahibi (User)**
- **Hesap (Account)**
- **Transaction**
- **Banka (Bank)**

### Temel Fonksiyonlar

Uygulama, aşağıdaki temel fonksiyonları sunar:

- **Hesap Yaratma - CreateAccount**: Yeni hesaplar oluşturabilirsiniz.
- **Hesaba Para Yatırma - DepositToAccount**: Hesaba para yatırabilirsiniz.
- **Hesaptan Para Çekme - WithdrawFromAccount**: Hesaptan para çekebilirsiniz.
- **Hesap Bakiyesi Sorgulama - AccountBalance**: Hesap bakiyesini sorgulayabilirsiniz.
- **İşlem Tarihçesi Sorgulama - AccountTransactions**: Hesap için işlem tarihçesini sorgulayabilirsiniz.

### Nasıl Başlamalı

Projeyi çalıştırmak için aşağıdaki adımları izleyebilirsiniz:

1. Projeyi clone'layın
2. Maven ile mvn clean package -DskipTests spring-boot:repackage komutunu çalıştırarak target jar'ını oluşturun. (Testlerin niye geçildiğini açık sorunlar kısmında bulabilirsiniz)
3. Container network'ünü ayağa kaldırmak için docker-compose up -d komutunu çalıştırın
4. Network kaldırdıktan sonra, Postman koleksiyonu içerisindeki örnek istekleri kullanarak uygulamayı test etmeye başlayabilirsiniz.

## Kullanılan Teknolojiler ve Bağımlılıklar

Proje aşağıdaki teknolojileri ve bağımlılıkları kullanmaktadır:

- **Java 17**
- **Spring Boot 3.1.3**
- **Spring Boot Starter Data JPA**
- **Spring Boot Starter Web**
- **PostgreSQL 13**
- **Hibernate 6.2.5.Final**
- **Lombok**
- **Flyway Core**
- **Spring Boot Starter Test**
- **Spring Boot Starter Validation**
- **JUnit**
- **Spring Boot Starter Log4j2**
- **Slf4j**

**Katmanlı Mimari:**

Proje, N katmanlı bir mimari yapısını benimsemektedir. Bu katmanlar, uygulamanın işleyişini düzenler ve sorumlulukları farklılaştırır. 

1. **Controller Katmanı:** HTTP isteklerini dinler ve gelen istekleri işlemek için ilgili iş katmanına yönlendirir. Rest API'leri tanımlar ve kullanıcıların uygulama ile iletişim kurmasını sağlar.

2. **Business (İş) Katmanı:** Veri doğrulama ve iş mantığı kurallarının yer aldığı katmandır.

3. **Repository (Veritabanı Erişim) Katmanı:** Bu katman, veritabanı ile iletişim kurar. Veri tabanına erişim, sorguların oluşturulması ve veritabanı işlemlerinin gerçekleştirilmesi bu katmanın sorumluluğundadır.

**Migrationlar ve Veri Tabanı İşlemleri:**

Proje, veri tabanı işlemleri için PostgreSQL veritabanını kullanmaktadır. Veritabanının şema ve tabloları Flyway tarafından otomatik olarak oluşturulur ve yönetilir, hibernate tarafından tablo üretilmez, sadece entity validasyonu yapılır.

- **V1__init_schema.sql:** banking schema yaratılması.

- **V2__create_tables.sql:** "bank," "users,", "account" ve "transactions" entity'lerine ait tabloların yaratılması.

- **V3__populate_data:** Tüm dört tablo için örnek veri üretimi.

**Unique Constraintler:**

1. **bank Tablosu:**

   - `name` alanı benzersizdir (UNIQUE), yani aynı isme sahip birden fazla banka kaydı olamaz.

2. **users Tablosu:**

   - `email` alanı benzersizdir (UNIQUE), yani aynı e-posta adresiyle birden fazla kullanıcı kaydı olamaz.

   - `phone_number` alanı benzersizdir (UNIQUE), yani aynı telefon numarasıyla birden fazla kullanıcı kaydı olamaz.

   - `unique_identity_type_no_combination` adlı özel bir kısıtlama vardır. Bu kısıtlama, kullanıcıların kimlik türü (`identity_type`) ve kimlik numarası (`identity_no`) kombinasyonlarının benzersiz olmasını sağlar. Yani aynı kimlik türü ve kimlik numarasıyla birden fazla kullanıcı kaydı olamaz.

3. **account Tablosu:**

   - `users_id` ve `bank_id` alanlarının kombinasyonu benzersizdir (UNIQUE), yani aynı kullanıcı ve banka kombinasyonuyla birden fazla hesap kaydı olamaz.

4. **account_transaction Tablosu:**

   - Primary key dışında yoktur.
  
## Entity İlişkileri

### User

- Birden fazla account'a sahip olabilir (one-to-many).

### Bank

- Birden fazla account'a sahip olabilir.

### Account

- Bir user'a ve bir bank'e aittir (many-to-one), birden fazla transaction içerebilir (one-to-many)

### Transaction

- Bir account'a aittir.

## İlişkiler ve FetchType Kullanımı

bidirectional ilişkiler kullanılmıştır. n+1 sorunundan kaçınmak için FetchType değerleri "lazy" olarak ayarlanmıştır.

## Cascade Kullanımı

Silme işlemleri, "fintech" (finans teknolojileri) projesinin gereksinimlerine göre dikkatli bir şekilde ele alınmıştır. Bu nedenle, cascade özellikleri belirli bir işlem tipine izin vermemektedir. Özellikle, veritabanındaki verilerin silinmesini kontrol etmek için cascade özellikleri verilmemiştir.

Ancak, örnek bir silme işlemi göstermek için **User -> Account -> Transaction** sıralamasını takip eden bir silme işlemi için bir endpoint mevcuttur. Bu, projenin gereksinimlerine uygun şekilde özelleştirilebilir ve yönetilebilir.

Bu yaklaşım, veri bütünlüğünü koruma ve iş mantığına daha fazla esneklik sağlama amacı gütmektedir.

**Pessimistic Lock (Pesimistik Kilitleme):**

Para yatırma (Deposit) ve para çekme (Withdraw) işlemleri için pesimistik kilitleme yöntemi kullanılması uygun görülmüştür. Bu yöntem, JPA repository içinde özel sorgular (custom query) kullanılarak `@Lock(LockModeType.PESSIMISTIC_WRITE)` ile gerçekleştirilmiştir. Bu sayede aynı kaynağa eş zamanlı erişimde sorunlar önlenmektedir.

**DTO ve Entity Dönüşümleri:**

Proje içinde, DTO ve Entity sınıfları arasında dönüşümleri kolaylaştırmak için özel bir Mapper sınıfı yazılmıştır. Bu Mapper sınıfı ileride ModelMapper ile değiştirilebilir.

**Exception Handling (Hata Yakalama):**

`CrudException` adlı bir exception sınıfı bulunmaktadır. Bu sınıf, CRUD işlemleri sırasında ortak bir hata yapısını kullanarak boilerplate kod miktarını azaltmak için oluşturulmuştur. Özellikle özelleştirilmiş hata mesajları ve dönüş tipleri üzerinde geliştirme yapılması planlanmaktadır. Bu exceptionlar, `@RestControllerAdvice` ile işlenmektedir.

**Enum Alanları Kontrol Etme (Validation):**

Request DTO'lar içinde bulunan enum alanlarının doğrulanması için özel Annotation tabanlı validatorlar yazılmıştır. Şu an için sadece 2 enum için uygulanan bu doğrulayıcılar ileride genelleştirilebilir.

**Açık Sorunlar ve İleride Yapılacaklar:**

1. **Test Sorunu:** Test klasörü içerisinde deposit ve withdraw metodlarının lockları için bir integration test bulunmaktadır. Ancak bu test, @Transactional olmasına rağmen rollback yapmamakta ve inceleme altındadır. Bu sorunun giderilmesi veya H2 veritabanına geçiş yapılması düşünülmektedir.

2. **Log Eksikliği:** Business katmanındaki kontrollerde fırlatılan özel hatalar açıklamalar ile birlikte sunulmuştur. Ancak bu hataların loglanması için bir mekanizma eklenmesi planlanmaktadır.

3. **Şifre Encryptleme:** Kullanıcı şifreleri, mevcut yapıda encryptlenmeden veritabanına kaydedilmektedir. Güvenlik açısından daha iyi bir uygulama için şifrelerin encrypted haliyle kaydedilmesi planlanmaktadır. Bu amaçla projeye bir güvenlik (security) bileşeni eklenmesi gerekmektedir.

4. **Rate Limiter:** Mevcut projede rate limiter (istek sıklığını sınırlama) bulunmamaktadır. Bu özelliğin eklenmesi için çalışmalar yapılacaktır.

5. **Custom Mapper -> ModelMapper:** Custom mapper, modelmapper ile değiştirilecektir. Aslen bir bean olarak injected bir objectMapper kullanılıyordu, ancak custom DTO'ların sayısının artması nedeniyle custom mapper kullanımından vazgeçilmiştir.

6. **Creational Stamp ve Update Stampler:** Entity'lerde kullanılan creational stamp'ler ve update stampler, genel olarak pre ve post persist yapıları incelenecek ve geliştirilecektir.

7. **Test Artırımı:** Testlerin sayısı artırılacak ve test kapsamı genişletilecektir. Projedeki test coverage yükseltilecektir.

8. **Swagger:** Endpointlerinin dokümantasyonu için en kısa sürede eklenecetir.

