import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AsiYonetimi {
    static class Asi{
        public String adsoyad;
        public String tarih;
        public String asitercihi;
        public int no;

        public Asi(String adsoyad, String tarih, int no,String asitercihi) {
            this.adsoyad = adsoyad;
            this.tarih = tarih;
            this.no = no;
            this.asitercihi=asitercihi;
        }
    }
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Asi> asilar = new ArrayList<Asi>();
    public static void AsilariOku(){
        asilar = new ArrayList<Asi>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("asiListesi.txt"));
            String satir = reader.readLine();
            while(satir!=null){
                String[] veriler = satir.split(",");
                int no = Integer.parseInt(veriler[0].trim());
                String asitercihi="",tarih="", adsoyad = veriler[1].trim();
                if(veriler.length==4){
                    tarih = veriler[2].trim();
                    asitercihi = veriler[3].trim();
                }
                asilar.add(new Asi(adsoyad,tarih,no,asitercihi));
                satir = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void AsilariYaz(){
        AsilariSirala();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("asiListesi.txt"));
            for (Asi asi:asilar){
                if(asi.asitercihi.equals("")){
                    writer.write(asi.no+", "+asi.adsoyad+", \n");
                }
                else{
                    writer.write(asi.no+", "+asi.adsoyad+", "+asi.tarih+", "+asi.asitercihi+"\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void AsilariSirala(){
        Asi[] asidizi = new Asi[asilar.size()];
        for (int i=0;i<asidizi.length;i++){
            asidizi[i] = asilar.get(i);
        }
        for (int i=0;i<asilar.size();i++){
            for (int j=i;j<asilar.size();j++){
                if(asidizi[i].no>asidizi[j].no){
                    Asi gecici = asidizi[i];
                    asidizi[i] = asidizi[j];
                    asidizi[j] = gecici;
                }
            }
        }
        asilar = new ArrayList<Asi>();
        for (int i=0;i<asidizi.length;i++){
            asilar.add(asidizi[i]);
        }
    }
    public static void YeniKayit(){
        String adsoyad;
        int no;
        System.out.print("Ogrenci No:");
        no=sc.nextInt();
        sc.nextLine();
        System.out.print("Adi ve soyadi: ");
        adsoyad = sc.nextLine();
        asilar.add(new Asi(adsoyad,"",no,""));
        System.out.println("\nKaydiniz basarili bir sekilde yapilmistir...\n");
    }
    public static void RandevuAl(){
        int no;
        System.out.print("Ogrenci No:");
        no = sc.nextInt();
        sc.nextLine();
        Asi aranan=null;
        for (Asi asi:asilar){
            if(asi.no==no){
                aranan = asi;
                break;
            }
        }
        if(aranan==null){
            System.out.println("Ogrenci bulunamadi.");
        }
        else{
            System.out.println(aranan.adsoyad+":\n");
            while (true){
                System.out.println("     ASI TERCIHI     ");
                System.out.println("---------------------");
                System.out.println("Biontech           :1");
                System.out.println("Sinovac            :2");
                System.out.println("Sputnik            :3");
                System.out.print("Lutfen tercihinizi giriniz:");
                int asi = sc.nextInt();
                sc.nextLine();
                if(asi==1){
                    aranan.asitercihi ="Biontech";
                }
                else if(asi==2){
                    aranan.asitercihi="Sinovac";
                }
                else if(asi==3){
                    aranan.asitercihi="Sputnik";
                }
                else{
                    System.out.println("HATA");
                }
                if(asi>=1&&asi<=3){
                    break;
                }
            }
            System.out.println("Lutfen randevu tarihini giriniz:");
            aranan.tarih=sc.nextLine();
            System.out.println("Randevunuz olusturulmustur...\n");
        }
    }
    public static void Listele(){
        int i=1;
        System.out.println(String.format("%-10s %-20s %-40s %-20s %-20s","Sira No","Ogrenci Numarasi","Adi Soyadi","Asi Firmasi","Tarih"));
        for (Asi asi:asilar){
            if(!asi.tarih.equals("")){
                System.out.println(String.format("%-10d %-20d %-40s %-20s %-20s",i,asi.no,asi.adsoyad,asi.asitercihi,asi.tarih));
                i++;
            }
        }
    }
    public static void Menu(){
        AsilariOku();
        int secim=-1;
        while(secim!=0){
            System.out.println("             MENÜ             ");
            System.out.println("------------------------------");
            System.out.println("Yeni Kayit Icin             :1");
            System.out.println("Randevu almak icin          :2");
            System.out.println("Randevulari Listelemek Icin :3");
            System.out.println("Cıkis Icin                  :0");
            System.out.println();
            System.out.print("LUTFEN SECIMINIZI GIRINIZ:");
            secim = sc.nextInt();
            sc.nextLine();
            if(secim==1){
                YeniKayit();
                AsilariYaz();
            }
            else if(secim==2){
                RandevuAl();
                AsilariYaz();
            }
            else if(secim==3){
                Listele();
            }
            else if (secim!=0){
                System.out.println("HATA");
            }
        }
    }
    public static void main(String[] args) {
        Menu();
    }
}
