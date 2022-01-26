package tac.model;

public class DiscoverBannerModel {

    private String bannerImage;
    private int bannerType;

    public DiscoverBannerModel(String bannerImage, int bannerType) {
        this.bannerImage = bannerImage;
        this.bannerType = bannerType;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }
}
