# USG Challenge - Mobil Uygulama

Kullanıcının Türkiye’deki şehirleri ve bu şehirlere ait önemli konumları listeleyebildiği,favorilere ekleyebildiği ve haritada görüntüleyebildiği bir mobil uygulamadır.
Kullanıcı uygulamayı açtığında önce bir splash ekranı görüntülenir, ardından ana sayfa açılır. Ana sayfada Türkiye’deki şehirlerin listesi yer alır. Kullanıcı bir şehre tıkladığında, ilgili satır genişler ve o şehre ait önemli konumlar listelenir.
Konumlara tıklandığında detay sayfası açılır. Detay sayfasından harita sayfasına geçiş
yapar. Konumun yerini haritada görür. İsterse yol tarifi için dış bir konum uygulamasına yönlenebilir. Cihazında konum izni vererek kendi konumunu
da haritada görebilir. Kullanıcı ana sayfada şehir satırındaki oka tıklayarak ilgili şehirdeki
tüm önemli konumların gösterildiği haritayı açar. Kullanıcı konumları favorilerine ekleyebilir.
Ana sayfada sağ üstteki (kalp) butona tıklayarak favorilerine ulaşabilir. 
Uygulama toplamda 6 sayfadan oluşmalıdır. Bu sayfalar şunlardır:
- Splash Sayfası
- Ana Sayfa
- Detay Sayfası
- Şehir Harita Sayfası
- Konum Harita Sayfası
- Favorilerim Sayfası

# Kullanılan Teknolojiler ve Kütüphaneler

Kotlin:
  - Proje, Kotlin programlama dili ile geliştirilmiştir. Kotlin, Android uygulama geliştirme için modern ve güçlü bir dildir.
    
Jetpack Compose:
  - Kullanıcı arayüzü bileşenleri, Jetpack Compose kullanılarak oluşturulmuştur. Bu, modern Android uygulamaları için deklaratif bir UI framework'üdür.
    
Koin:
  - Bağımlılık yönetimi için Koin kütüphanesi kullanılmıştır. Koin, Kotlin için hafif bir bağımlılık enjeksiyon kütüphanesidir.
    
Google Maps API:
  - Harita ve konum hizmetleri için Google Maps API kullanılmaktadır. Bu, harita üzerinde konumları görüntülemek ve kullanıcı konumunu almak için kullanılır.
    
Room:
  - Yerel veritabanı yönetimi için Room kütüphanesi kullanılmıştır. Room, SQLite veritabanı ile etkileşimde bulunmayı kolaylaştıran bir kütüphanedir.
    
Coroutines:
  - Asenkron programlama için Kotlin Coroutines kullanılmıştır. Bu, arka planda uzun süren işlemleri yönetmek için kullanılır.
    
Lifecycle:
  - Android uygulama yaşam döngüsü yönetimi için Android Lifecycle kütüphanesi kullanılmaktadır. Bu, bileşenlerin yaşam döngüsünü yönetmeyi kolaylaştırır.
    
Material Design:
  - Uygulama, Material Design prensiplerine uygun bir kullanıcı arayüzü tasarımı sunmaktadır. Bu, kullanıcı deneyimini artırmak için modern tasarım öğeleri kullanır.
    
Activity Result API:
  - Kullanıcıdan izin almak için Activity Result API kullanılmaktadır. Bu, kullanıcı etkileşimlerini yönetmek için modern bir yöntemdir.
    
Flow:
  - Verilerin akışını yönetmek için Kotlin Flow kullanılmıştır. Bu, reaktif programlama paradigmasını destekler.
    
Ktor:
  - Ağ istekleri ve API entegrasyonu için Ktor kütüphanesi kullanılmaktadır. Ktor, asenkron HTTP istemcisi olarak işlev görür ve RESTful API'lerle etkileşimde bulunmayı kolaylaştırır.

# Mimari Yapı
  MVVM (Model-View-ViewModel) Deseni:
    Proje, MVVM mimari desenini kullanmaktadır. Bu desen, kullanıcı arayüzü (View), iş mantığı (ViewModel) ve veri (Model) katmanlarını ayırarak uygulamanın daha düzenli ve test edilebilir olmasını sağlar.
    
Model:
  - Uygulamanın veri katmanını temsil eder. Veritabanı, API çağrıları ve veri işleme gibi işlemleri içerir. Örneğin, LocationDetailRepositoryImpl ve CityRepositoryImpl gibi sınıflar, veri kaynaklarına erişim sağlar.
    
View:
   - Kullanıcı arayüzünü temsil eder. Jetpack Compose kullanılarak oluşturulan bileşenler, kullanıcı etkileşimlerini yönetir. Örneğin, CityMapScreen ve LocationMapScreen gibi bileşenler, kullanıcı arayüzünü oluşturur.
     
ViewModel:
   - İş mantığını yönetir ve View ile Model arasında bir köprü görevi görür. Kullanıcı etkileşimlerini işler ve verileri View'a iletir. Örneğin, CityMapViewModel ve LocationMapViewModel sınıfları, ilgili ekranların iş mantığını yönetir.
     
Koin ile Bağımlılık Yönetimi:
  - Proje, Koin kütüphanesini kullanarak bağımlılık enjeksiyonunu yönetir. Bu, bileşenlerin bağımlılıklarını kolayca yönetmeyi ve test etmeyi sağlar. Örneğin, AppModule.kt dosyasında, ViewModel'ler ve diğer bileşenler için bağımlılıklar tanımlanmıştır.
    
Reactive Programming (Reaktif Programlama):
  - Proje, Kotlin Coroutines ve Flow kullanarak reaktif programlama paradigmasını benimsemektedir. Bu, asenkron veri akışlarını yönetmeyi ve UI güncellemelerini kolaylaştırmayı sağlar. Örneğin, state değişkenleri, UI bileşenlerinin güncellenmesini sağlamak için kullanılır.

# Clean Architecture Katmanları
Presentation Layer (Sunum Katmanı):

  - Kullanıcı arayüzü bileşenleri ve kullanıcı etkileşimlerini yönetir. Bu katman, genellikle ViewModel'ler ve UI bileşenlerini içerir.
    Örneğin, CityMapScreen, LocationMapScreen gibi Composable bileşenler, kullanıcı arayüzünü oluşturur. Bu bileşenler, kullanıcıdan gelen etkileşimleri alır ve ilgili ViewModel'lere iletir.
    ViewModel'ler (örneğin, CityMapViewModel, LocationMapViewModel), UI durumunu yönetir ve iş mantığını içerir.
  
Domain Layer (Alan Katmanı):

  - Uygulamanın iş mantığını ve kurallarını içerir. Bu katman, uygulamanın temel işlevselliğini tanımlar ve diğer katmanlarla etkileşimde bulunur.
    Domain katmanında, use case'ler (örneğin, GetCityWithLocationsUseCase, GetLocationForMapUseCase) bulunur. Bu use case'ler, belirli bir işlevselliği yerine getirmek için gerekli olan tüm işlemleri kapsar.
    Ayrıca, domain katmanında repository arayüzleri (örneğin, CityMapRepository, LocationMapRepository) bulunur. Bu arayüzler, veri erişim katmanıyla etkileşimde bulunmak için kullanılır.
  
Data Layer (Veri Katmanı):

  - Uygulamanın veri kaynaklarını yönetir. Bu katman, API çağrıları, veritabanı işlemleri ve diğer veri kaynaklarıyla etkileşimde bulunur.
    Veri katmanında, repository implementasyonları (örneğin, CityMapRepositoryImpl, LocationMapRepositoryImpl) bulunur. Bu sınıflar, domain katmanındaki arayüzleri uygular ve veri kaynaklarına erişim sağlar.
    Ayrıca, veri katmanında yerel veritabanı işlemleri için Room kütüphanesi kullanılır. Örneğin, FavoriteLocationDao gibi DAO'lar, veritabanı işlemlerini yönetir.

# Presentation Katmanı
Bu projede, her bir ekran (screen) belirli bir mimari yapıya sahiptir ve bu yapı, Presentation katmanında Action, Screen, State ve ViewModel bileşenlerini içerir.

Action (Eylem):
  - Action, kullanıcı etkileşimlerini temsil eder. Kullanıcı bir butona tıkladığında veya bir seçim yaptığında, bu etkileşimler bir eylem olarak tanımlanır.
    Örneğin, CityMapAction sınıfı, NavigateBack, SelectLocation, FocusUserLocation gibi eylemleri içerir. Bu eylemler, kullanıcı etkileşimlerini temsil eder ve ViewModel'e iletilir.
  
Screen (Ekran):
  - Screen, kullanıcı arayüzünü temsil eder. Bu bileşen, kullanıcıya gösterilen UI bileşenlerini içerir ve kullanıcı etkileşimlerini alır.
    Örneğin, CityMapScreen ve LocationMapScreen gibi Composable fonksiyonlar, kullanıcı arayüzünü oluşturur. Bu fonksiyonlar, ViewModel'den gelen durumu (state) alır ve kullanıcı etkileşimlerini işlemek için gerekli eylemleri tetikler.
  
State (Durum):
  - State, ekranın mevcut durumunu temsil eder. Bu, UI bileşenlerinin nasıl görüneceğini ve hangi verilerin gösterileceğini belirler.
    Örneğin, CityMapState sınıfı, şehir bilgilerini, kullanıcı konumunu, yüklenme durumunu ve hata mesajlarını içerir. Bu durum, ViewModel'den alınır ve UI bileşenlerinde gösterilir.
  
ViewModel:
 - ViewModel, iş mantığını yönetir ve Action ile State arasında bir köprü görevi görür. Kullanıcı etkileşimlerini işler ve durumu günceller.
   Örneğin, CityMapViewModel sınıfı, kullanıcıdan gelen eylemleri işler ve durumu günceller. onAction fonksiyonu, gelen eylemleri alır ve uygun işlemleri gerçekleştirir. Örneğin, loadCityWithLocations() fonksiyonu, şehir bilgilerini yüklerken checkLocationPermissionAndGps() fonksiyonu, konum izni ve GPS durumunu kontrol eder.

# Ekran Görüntüleri

https://github.com/user-attachments/assets/f36640d7-1304-4297-9a43-cd84095088ce

https://github.com/user-attachments/assets/6b77e7a6-3980-41e1-8e74-dc7ff1adff21



