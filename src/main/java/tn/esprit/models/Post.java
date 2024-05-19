package tn.esprit.models;

public class Post {

    private Account account;
    private PostAudience audience;

    private String HashtagP,DescriptionP,visibilite,ImageP;
    private String DateP , country;
    private int idPost ,idc;
    private int totalReactions;
    private int nbComments;
    private int nbShares;

    //constructeur
    public Post() {
    }

    public Post(int idPost, int idC,String DescriptionP, String HashtagP,String visibilite,String ImageP) {
        this.idPost = idPost;
        this.idc = idC;
        this.DescriptionP = DescriptionP;
        this.HashtagP = HashtagP;
        this.visibilite = visibilite;
        this.ImageP = ImageP;


    }

    public Post(int idC,String DescriptionP, String HashtagP,String visibilite,String ImageP) {

        this.idc = idC;
        this.DescriptionP = DescriptionP;
        this.HashtagP = HashtagP;
        this.visibilite = visibilite;
        this.ImageP = ImageP;

    }

    public Post(int idc,String country, String HashtagP, String DescriptionP, String visibilite, String ImageP, String DateP) {
        this.idc = idc;
        this.country = country;
        this.HashtagP = HashtagP;
        this.DescriptionP = DescriptionP;
        this.visibilite = visibilite;
        this.ImageP = ImageP;
        this.DateP = DateP;
    }

    public String getCountry() {
        return country;
    }

    public String getDateP() {
        return DateP;
    }

    public String getDescriptionP() {
        return DescriptionP;
    }

    public String getHashtagP() {
        return HashtagP;
    }


    public int getIdPost() {
        return idPost;
    }

    public String getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(String visibilite) {
        this.visibilite = visibilite;
    }



    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }


    public void setId(int idPost) {
        this.idPost = idPost;
    }

    public void setImageP(String imageP) {
        ImageP = imageP;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public void setHashtagP(String hashtagP) {
        HashtagP = hashtagP;
    }

    public void setDescriptionP(String descriptionP) {
        DescriptionP = descriptionP;
    }

    public void setDateP(String dateP) {
        DateP = dateP;
    }



    public String getImageP() {
        return ImageP;
    }

    public int getIdc() {
        return idc;
    }

    public int getTotalReactions() {
        return totalReactions;
    }

    public void setTotalReactions(int totalReactions) {
        this.totalReactions = totalReactions;
    }

    public int getNbComments() {
        return nbComments;
    }

    public void setNbComments(int nbComments) {
        this.nbComments = nbComments;
    }

    public int getNbShares() {
        return nbShares;
    }

    public void setNbShares(int nbShares) {
        this.nbShares = nbShares;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }



    public PostAudience getAudience() {
        return audience;
    }

    public void setAudience(PostAudience audience) {
        this.audience = audience;
    }

    @Override
    public String toString() {
        return "Post{" +
                "account=" + account +
                ", audience=" + audience +
                ", HashtagP='" + HashtagP + '\'' +
                ", DescriptionP='" + DescriptionP + '\'' +
                ", visibilite='" + visibilite + '\'' +
                ", ImageP='" + ImageP + '\'' +
                ", DateP='" + DateP + '\'' +
                ", country='" + country + '\'' +
                ", idPost=" + idPost +
                ", idc=" + idc +
                ", totalReactions=" + totalReactions +
                ", nbComments=" + nbComments +
                ", nbShares=" + nbShares +
                '}';
    }
}


