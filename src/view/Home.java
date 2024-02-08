package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import component.Card;

public class Home extends JPanel {
    public static final Dimension MDCard = new Dimension(100,100);
    public static final Dimension STATUS_DIMENSION = new Dimension(700, 400);
    public static final Dimension MainPanel = new Dimension(700,720);

    private Image man =  new ImageIcon("../resources/avatar.png").getImage() ;

    //components
    public Card TopSeller,TopAuthor,TotalBought ,card2,card3,card4;
    JPanel grid,status ;
    JScrollPane gridPane,statusPane ;




    private void initComponents(){
     //  initComponents();
     //initialize card components
     TotalBought = new Card("Total Sells",MDCard,Color.WHITE,man);
     TopSeller = new Card("Best Seller",MDCard,Color.WHITE,man);
     TopAuthor = new Card("Customers",MDCard,Color.WHITE,man);
     card2 = new Card("Total",MDCard,Color.WHITE,man);
     card3 = new Card("Top",MDCard,Color.WHITE,man);
     card4 = new Card("Total",MDCard,Color.WHITE,man);

     TotalBought.setLayout(new BorderLayout());
     TopSeller.setLayout(new BorderLayout());
     TopAuthor.setLayout(new BorderLayout());

     JLabel bestSeller = new JLabel("Ana");
     bestSeller.setFont(new Font("Arial)",Font.BOLD,60));
     bestSeller.setBounds(65,100,100 ,100 );
     JLabel totalSells = new JLabel("22K units");
     totalSells.setFont(new Font("Arial",Font.BOLD,60));
     JLabel bestAuthor = new JLabel("Tom Smith");
     bestAuthor.setFont(new Font("Arial",Font.BOLD,50));    

    TotalBought.add(totalSells,BorderLayout.SOUTH);
    TopSeller.add(bestSeller,BorderLayout.SOUTH);
    TopAuthor.add(bestAuthor,BorderLayout.SOUTH);
        //initialize panels
     grid  = new JPanel();
     status  = new JPanel(new GridLayout(4,2,30,30));
     
        //initialize scrollpane
     gridPane = new JScrollPane(grid);
     statusPane = new JScrollPane(status);
    }

    private void setCards(){
    status.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    grid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    grid.setPreferredSize(new Dimension(getWidth(), 150));
    grid.setLayout(new GridLayout(1,3,30,30));

    //adding some contents to the panels
    grid.add(TotalBought);
    grid.add(TopAuthor);
    grid.add(TopSeller);
    Card sellerCard = new Card("Best Sellers", STATUS_DIMENSION, Color.GRAY,man);
    Card reportCard  =new Card("Recent Sells", STATUS_DIMENSION, Color.GRAY,man);
    Card monetaryCard =new Card("Monetary Report", STATUS_DIMENSION, Color.GRAY,man);
    //status.add(new Card("bla bla..", STATUS_DIMENSION, Color.CYAN,man));

    //sellerTable  = new JTable(new DefaultTableModel("Publisher", "Title"))


    //----
    
    status.add(sellerCard);
    status.add(reportCard);
    status.add(monetaryCard);
    
      
    }

    public Home(){
        this.setPreferredSize(MainPanel);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //initialize the components
        initComponents();
   
      //setting the border properties of panels    
        setCards();
   
    



    this.add(gridPane,BorderLayout.NORTH);
    this.add(statusPane,BorderLayout.CENTER);
    
    }
    
}
