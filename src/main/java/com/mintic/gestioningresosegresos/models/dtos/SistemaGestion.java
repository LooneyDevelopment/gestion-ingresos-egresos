package com.mintic.gestioningresosegresos.models.dtos;

import lombok.Data;

@Data
public class SistemaGestion {
    private String img;
    private String title;
    private String url;

    public SistemaGestion(String title, String url, String img) {
        this.title = title;
        this.url = url;
        this.img = img;
    }
}
