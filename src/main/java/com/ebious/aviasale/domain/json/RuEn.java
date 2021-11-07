package com.ebious.aviasale.domain.json;

public class RuEn {
    private String ru;
    private String en;

    public RuEn() {
    }

    public RuEn(String ru, String en) {
        this.ru = ru;
        this.en = en;
    }

    public String getRu() {
        return ru;
    }

    public void setRu(String ru) {
        this.ru = ru;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    @Override
    public String toString() {
        return "RuEn{" +
                "ru='" + ru + '\'' +
                ", en='" + en + '\'' +
                '}';
    }
}
