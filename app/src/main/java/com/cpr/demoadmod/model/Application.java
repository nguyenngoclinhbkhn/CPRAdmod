package com.cpr.demoadmod.model;

public class Application {
    private int id;
    private String name;
    private String packageName;
    private String logo;
    private  String bgStartColor;
    private String bgEndColor;
    private String bgBtnUnclickedColor;
    private String bgBtnClickedColor;
    private String colorTitle;
    private String colorTextButton;
    private String cover;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBgStartColor() {
        return bgStartColor;
    }

    public void setBgStartColor(String bgStartColor) {
        this.bgStartColor = bgStartColor;
    }

    public String getBgEndColor() {
        return bgEndColor;
    }

    public void setBgEndColor(String bgEndColor) {
        this.bgEndColor = bgEndColor;
    }

    public String getBgBtnUnclickedColor() {
        return bgBtnUnclickedColor;
    }

    public void setBgBtnUnclickedColor(String bgBtnUnclickedColor) {
        this.bgBtnUnclickedColor = bgBtnUnclickedColor;
    }

    public String getBgBtnClickedColor() {
        return bgBtnClickedColor;
    }

    public void setBgBtnClickedColor(String bgBtnClickedColor) {
        this.bgBtnClickedColor = bgBtnClickedColor;
    }

    public String getColorTitle() {
        return colorTitle;
    }

    public void setColorTitle(String colorTitle) {
        this.colorTitle = colorTitle;
    }

    public String getColorTextButton() {
        return colorTextButton;
    }

    public void setColorTextButton(String colorTextButton) {
        this.colorTextButton = colorTextButton;
    }

    public Application(int id, String name, String packageName, String logo,
                       String bgStartColor, String bgEndColor, String bgBtnUnclickedColor,
                       String bgBtnClickedColor, String colorTitle, String colorTextButton, String cover) {
        this.id = id;
        this.name = name;
        this.packageName = packageName;
        this.logo = logo;
        this.bgStartColor = bgStartColor;
        this.bgEndColor = bgEndColor;
        this.bgBtnUnclickedColor = bgBtnUnclickedColor;
        this.bgBtnClickedColor = bgBtnClickedColor;
        this.colorTitle = colorTitle;
        this.colorTextButton = colorTextButton;
        this.cover = cover;
    }
}
