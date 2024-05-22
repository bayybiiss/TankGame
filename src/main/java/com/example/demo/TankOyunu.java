package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TankOyunu extends Application {
    private Pane anaPanel;
    private TankOyunuElemanlari tankOyunuElemanlari;
    private int skor = 0;
    private Label skorEtiketi;
    private boolean oyunBitti = false;
    private List<Circle> mermiler;
    private boolean oyuncuTankiEklendi = false;

    @Override
    public void start(Stage primaryStage) {
        anaPanel = new Pane();
        Scene sahne = new Scene(anaPanel, 600, 400);
        primaryStage.setScene(sahne);
        primaryStage.setTitle("Tank Oyunu");
        anaPanel.getChildren().clear();
        tankOyunuElemanlari = new TankOyunuElemanlari(anaPanel);
        oyuncuTankiniOlustur();
        dusmanTanklariniOlustur();
        skorEtiketiOlustur();

        sahne.setOnKeyPressed(event -> {
            if (!oyunBitti) {
                if (event.getCode() == KeyCode.UP) {
                    tankiHareketEttir(tankOyunuElemanlari.getOyuncuTanki(), 0, -5);
                } else if (event.getCode() == KeyCode.DOWN) {
                    tankiHareketEttir(tankOyunuElemanlari.getOyuncuTanki(), 0, 5);
                } else if (event.getCode() == KeyCode.LEFT) {
                    tankiHareketEttir(tankOyunuElemanlari.getOyuncuTanki(), -5, 0);
                } else if (event.getCode() == KeyCode.RIGHT) {
                    tankiHareketEttir(tankOyunuElemanlari.getOyuncuTanki(), 5, 0);
                } else if (event.getCode() == KeyCode.ENTER) {
                    atesEt(tankOyunuElemanlari.getOyuncuTanki(), -1, Color.BLUE);
                }
            }
        });

        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!oyunBitti) {
                    guncelle();
                }
            }
        }.start();
    }

    // oyuncuTankiniOlustur() metodu, oyuncu tankını oluşturur ve sahneye ekler.
    // Eğer tank zaten eklenmişse tekrar eklemez.
    private void oyuncuTankiniOlustur() {
        anaPanel.getChildren().clear();
        tankOyunuElemanlari.getOyuncuTanki(); // Oyuncu tankını oluştur ve eğer zaten eklenmişse tekrar ekleme
        oyuncuTankiEklendi = true;
    }


    // dusmanTanklariniOlustur() metodu, düşman tanklarını oluşturur ve sahneye ekler.
    // Mermiler listesini sıfırlar ve düşman tank resimlerini yükler.
    private void dusmanTanklariniOlustur() {
        tankOyunuElemanlari = new TankOyunuElemanlari(anaPanel);
        List<ImageView> dusmanTanklari = tankOyunuElemanlari.getDusmanTanklari();
        mermiler = new ArrayList<>();
        Random random = new Random();

        List<Image> dusmanTankResimleri = new ArrayList<>();
        for (ImageView dusmanTanki : tankOyunuElemanlari.getDusmanTanklari()) {
            Image dusmanTankResim = (Image) dusmanTanki.getImage();
            dusmanTankResimleri.add(dusmanTankResim);
        }

        for (int i = 0; i < 5; i++) {
            Image dusmanTankResim = dusmanTankResimleri.get(i);
            ImageView dusmanTanki = new ImageView(dusmanTankResim);
            dusmanTanki.setFitWidth(60);
            dusmanTanki.setFitHeight(60);
            dusmanTanki.setX(random.nextDouble() * 600);
            dusmanTanki.setY(random.nextDouble() * 100);
            dusmanTanklari.add(dusmanTanki);
            anaPanel.getChildren().add(dusmanTanki);
        }
    }

    // skorEtiketiOlustur() metodu, skor etiketini oluşturur ve sahneye ekler.
    private void skorEtiketiOlustur() {
        skorEtiketi = new Label("Skor: " + skor);
        skorEtiketi.setLayoutX(10);
        skorEtiketi.setLayoutY(10);
        anaPanel.getChildren().add(skorEtiketi);
    }

    // dusmanTankiniYenidenOlustur() metodu, parametre olarak verilen düşman tankını yeniden oluşturur.
    private void dusmanTankiniYenidenOlustur(ImageView tank) {
        anaPanel.getChildren().remove(tank);
        List<ImageView> dusmanTanklari = tankOyunuElemanlari.getDusmanTanklari();
        dusmanTanklari.remove(tank);

        Image dusmanTankResim = new Image("C:/demo/image/dustman.png");
        ImageView yeniDusmanTanki = new ImageView(dusmanTankResim);
        yeniDusmanTanki.setFitWidth(60);
        yeniDusmanTanki.setFitHeight(60);
        Random random = new Random();
        yeniDusmanTanki.setX(random.nextDouble() * 600);
        yeniDusmanTanki.setY(random.nextDouble() * 100);
        dusmanTanklari.add(yeniDusmanTanki);
        anaPanel.getChildren().add(yeniDusmanTanki);
    }

    // tankiHareketEttir() metodu, bir ImageView tankını belirli bir mesafe kadar hareket ettirir.
    private void tankiHareketEttir(ImageView tank, double deltaX, double deltaY) {
        double yeniX = tank.getX() + deltaX;
        double yeniY = tank.getY() + deltaY;

        if (yeniX >= 0 && yeniX + tank.getFitWidth() <= anaPanel.getWidth() &&
                yeniY >= 0 && yeniY + tank.getFitHeight() <= anaPanel.getHeight()) {
            tank.setX(yeniX);
            tank.setY(yeniY);
        }
    }

    // atesEt() metodu, bir ImageView tankından mermi atar ve mermiyi sahneye ekler.
    private void atesEt(ImageView tank, int yon, Color renk) {
        Circle mermi = new Circle(5, renk);
        mermi.setCenterX(tank.getX() + tank.getFitWidth() / 2);
        mermi.setCenterY(tank.getY() - 30);
        mermiler.add(mermi);
        anaPanel.getChildren().add(mermi);
    }

    // guncelle() metodu, her frame'de oyunun güncellenmesi için çağrılır.
    // Düşman tanklarının hareket etmesi, mermilerin hareket etmesi, çarpışmaların kontrol edilmesi burada gerçekleşir.
    private void guncelle() {
        List<ImageView> dusmanTanklari = tankOyunuElemanlari.getDusmanTanklari();

        for (ImageView dusmanTanki : dusmanTanklari) {
            dusmanTankiRastgeleHareketEttir(dusmanTanki, 2);
            if (Math.random() < 0.01) {
                atesEt(dusmanTanki, 1, Color.RED);
            }
        }

        Iterator<Circle> mermiIterator = mermiler.iterator();
        while (mermiIterator.hasNext()) {
            Circle mermi = mermiIterator.next();
            mermi.setCenterY(mermi.getCenterY() + (mermi.getFill().equals(Color.RED) ? 3 : -3));

            Iterator<ImageView> tankIterator = dusmanTanklari.iterator();
            while (tankIterator.hasNext()) {
                ImageView tank = tankIterator.next();
                if (mermi.getBoundsInParent().intersects(tank.getBoundsInParent())) {
                    if (mermi.getFill().equals(Color.BLUE)) {
                        anaPanel.getChildren().remove(mermi);
                        tankIterator.remove();
                        anaPanel.getChildren().remove(tank);
                        skor++;
                        skorEtiketi.setText("Skor: " + skor);

                        dusmanTankiniYenidenOlustur(tank);
                    } else if (mermi.getFill().equals(Color.RED) && tank.getBoundsInParent().intersects(tankOyunuElemanlari.getOyuncuTanki().getBoundsInParent())) {
                        oyunBitti = true;
                        oyunBittiEkraniGoster();
                    }
                    break;
                }
            }

            if (mermi.getCenterY() < 0 || mermi.getCenterY() > 400) {
                anaPanel.getChildren().remove(mermi);
                mermiIterator.remove();
            }
        }

        for (ImageView dusmanTanki : dusmanTanklari) {
            if (dusmanTanki.getBoundsInParent().intersects(tankOyunuElemanlari.getOyuncuTanki().getBoundsInParent())) {
                oyunBitti = true;
                oyunBittiEkraniGoster();
            }
        }

        for (Circle mermi : mermiler) {
            if (mermi.getFill().equals(Color.RED) && mermi.getBoundsInParent().intersects(tankOyunuElemanlari.getOyuncuTanki().getBoundsInParent())) {
                oyunBitti = true;
                oyunBittiEkraniGoster();
            }
        }
    }

    // dusmanTankiRastgeleHareketEttir() metodu, bir düşman tankını rastgele hareket ettirir.
    private void dusmanTankiRastgeleHareketEttir(ImageView tank, double hiz) {
        double deltaX = Math.random() * hiz * 2 - hiz;
        double deltaY = Math.random() * hiz * 2 - hiz;
        tankiHareketEttir(tank, deltaX, deltaY);
    }

    // oyunBittiEkraniGoster() metodu, oyunun bittiğini belirten bir ekran gösterir.
    // Yeniden başla butonuna basıldığında oyun sıfırlanır.
    private void oyunBittiEkraniGoster() {
        Label oyunBittiEtiketi = new Label("Oyun Bitti! Skorunuz: " + skor);
        oyunBittiEtiketi.setLayoutX(200);
        oyunBittiEtiketi.setLayoutY(200);
        oyunBittiEtiketi.setStyle("-fx-font-size: 20;");
        anaPanel.getChildren().add(oyunBittiEtiketi);

        Button yenidenBaslaButton = new Button("Yeniden Başla");
        yenidenBaslaButton.setLayoutX(250);
        yenidenBaslaButton.setLayoutY(250);
        anaPanel.getChildren().add(yenidenBaslaButton);

        yenidenBaslaButton.setOnAction(event -> {
            anaPanel.getChildren().clear();
            oyuncuTankiniOlustur();
            dusmanTanklariniOlustur();
            skor = 0;
            skorEtiketi = new Label("Skor: " + skor);
            skorEtiketi.setLayoutX(10);
            skorEtiketi.setLayoutY(10);
            anaPanel.getChildren().add(skorEtiketi);
            anaPanel.getChildren().remove(oyunBittiEtiketi);
            anaPanel.getChildren().remove(yenidenBaslaButton);
            oyunBitti = false;
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
