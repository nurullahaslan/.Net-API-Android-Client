1) Kullanılan API .Net core ile hazırlandı verileri Json olarak geri döndürüyor.
2) UI kısmı android uygulaması olarak hazırlandı.
3) Kullanılan veri tabanı MSSQL
4) API "localhost:PORT/api/People" konumunda çalışmakta ve API ın bir sunucuda çalıştığını simule etmek için 
   ngrok programlıyla localhost public e açılıp uygulamaya link olarak eklendi.
   (Not: ngrok her çalıştığında random subdomain oluşuyor.)
5) Ngrok programını iis express ile kullanırken sorun yaşamamak için aşağıdaki komut ile cmd ile başlatınız.
   
	ngrok http https://localhost:44350 -host-header="localhost:44350" (IIS hangi portu atadıysa onu kullanın.)
6) Veritabanı connection stringi Startup.cs sınıfında mevcut kullanıcı veritabanı bağlantısı oluştururken düzenlemeli.
7) Veritabanı yedeğinde sorun olması durumunda tekrar oluşturulması için tablo tasarımı görseli mevcuttur.
