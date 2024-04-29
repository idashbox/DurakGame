package cardPack;

import java.awt.*;
import java.util.Objects;

public class RenderedCard extends Card {
    protected boolean isHighSuit;
    protected Image image;
    protected String imagePath;


    public RenderedCard(Suit suit, Rank rank, boolean v) {
        super(suit, rank, v);
        isHighSuit = false;
        setSize(73, 97);
    }

    public  void setImage(){
        if (!visible) {
            image = Toolkit.getDefaultToolkit().getImage("picture_data/b3.gif");
        } else {
            imagePath = "picture_data/" + this + ".gif";
            image = Toolkit.getDefaultToolkit().getImage(imagePath);
        }
    }

    public void paint(Graphics g) {
        if (!visible) {
            image = Toolkit.getDefaultToolkit().getImage("picture_data/b3.gif");
            g.drawImage(image, 0, 0, this);
        } else {
            imagePath = "picture_data/" + this + ".gif";
            image = Toolkit.getDefaultToolkit().getImage(imagePath);
            g.drawImage(image, 0, 0, this);
        }
    }

    public static RenderedCard createRenderedCard(String stringCard){
        String[] stringsOfCard = stringCard.split("");
        String suit = stringsOfCard[0];
        String rank;
        if (stringsOfCard.length == 3 && Objects.equals(stringsOfCard[2], "0")){
            rank = stringsOfCard[1] + stringsOfCard[2];
        }else {
            rank = stringsOfCard[1];
        }
        RenderedCard card = new RenderedCard(stringToSuit(suit), stringToRank(rank), true);
        card.setImage();
        return card;
    }

    public void update(Graphics g) {
        paint(g);
    }
}
