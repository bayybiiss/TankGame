package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TankOyunuElemanlari {

    private Pane anaPanel;
    private ImageView oyuncuTanki=null;
    private List<ImageView> dusmanTanklari;




    public TankOyunuElemanlari(Pane anaPanel) {
        this.anaPanel = anaPanel;
        dusmanTanklari = new ArrayList<>();
        oyuncuTankiniOlustur();
        dusmanTanklariniOlustur();
    }

    private void oyuncuTankiniOlustur() {
        Image oyuncuTankResim = new Image("C:/demo/image/oyuncu.png");
        oyuncuTanki = new ImageView(oyuncuTankResim);
        oyuncuTanki.setPreserveRatio(false);
        oyuncuTanki.setSmooth(true);
        oyuncuTanki.setCache(true);
        oyuncuTanki.setX(300);
        oyuncuTanki.setY(350);
        anaPanel.getChildren().add(oyuncuTanki);
    }


    private void dusmanTanklariniOlustur() {
        Random random = new Random();
        Image dusmanTankResim = new Image("C:/demo/image/dustman.png");

        for (int i = 0; i < 5; i++) {
            ImageView dusmanTanki = new ImageView(dusmanTankResim);
            dusmanTanki.setFitWidth(60);
            dusmanTanki.setFitHeight(60);
            dusmanTanki.setX(random.nextDouble() * 600);
            dusmanTanki.setY(random.nextDouble() * 100);
            dusmanTanklari.add(dusmanTanki);
            anaPanel.getChildren().add(dusmanTanki);
        }
    }


    public ImageView getOyuncuTanki() {
        if (oyuncuTanki == null) {
            oyuncuTanki = new ImageView(new Image("C:/demo/image/oyuncu.png"));
            oyuncuTanki.setFitWidth(60);
            oyuncuTanki.setFitHeight(60);
            oyuncuTanki.setX(300);
            oyuncuTanki.setY(350);
            anaPanel.getChildren().add(oyuncuTanki);
        }
        return oyuncuTanki;
    }



    public List<ImageView> getDusmanTanklari() {
        return dusmanTanklari;
    }

    // Diğer ihtiyaç duyulan metodları ekleyebilirsiniz.
}

