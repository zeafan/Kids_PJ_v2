package com.app.mohamedgomaa.kids_pj.Qaraan;

import java.io.Serializable;

/**
 * Created by Mohamed Gooma on 6/24/2017.
 */

public class Activity_Regition_Qaraan_items implements Serializable {
    int id,Id_img,num_ayaa,num_views,soora_saved;
    String name_Soora;

    public Activity_Regition_Qaraan_items(int id, int id_img, int num_ayaa, int num_views, int soora_saved, String name_Soora) {
        this.id = id;
        Id_img = id_img;
        this.num_ayaa = num_ayaa;
        this.num_views = num_views;
        this.soora_saved = soora_saved;
        this.name_Soora = name_Soora;
    }
}
