package shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ShoppingListUI {
    // Program's Main Frame
    static JFrame list_frame = new JFrame("Shopping List");
    // Main Frame Properties
    public static final int FRAME_WIDTH = 405;
    public static final int FRAME_HEIGHT = 500;

    // Pictures Declaration
    public static final String FRAME_BG = "src/main//resources/frame_bg.jpg";
    public static final String SAVE_BG = "src/main//resources/save_bg.png";
    public static final String SEARCH_BG = "src/main//resources/search_bg.png";
    public static final String PDF_BG = "src/main//resources/pdf_bg.png";

    // Buttons Declaration
    // Save Button Properties
    public static final int SAVE_X = 80;
    public static final int SAVE_Y = 10;
    public static final int SAVE_SIZE = 45;
    // Search Button Properties
    public static final int SEARCH_X = 160;
    public static final int SEARCH_Y = 10;
    public static final int SEARCH_SIZE = 45;
    // PDF Button Properties
    private static final int PDF_X = 240;
    public static final int PDF_Y = 10;
    public static final int PDF_SIZE = 45;

    // Text Fields Declaration
    // Name Text Field Properties
    public static final int NAME_X = 40;
    public static final int NAME_Y = 100;
    public static final int NAME_W = 250;
    public static final int NAME_H = 50;
    // Quantity Text Field Properties
    public static final int QUANTITY_X = 300;
    public static final int QUANTITY_Y = 100;
    public static final int QUANTITY_W = 70;
    public static final int QUANTITY_H = 50;

    // Label Declaration
    public static final int LABEL_Y = 80;
    public static final int LABEL_H = 15;

    // Shopping List Objects
    private static ShoppingListService shoppingList;
    private static final List<Item> itemList = new LinkedList<>();

    public static final int ITEMS_NUM = 5;

    // Items Name Text Box
    static JTextField[] itemNames = new JTextField[ITEMS_NUM];

    // Items Quantity Text Box
    static JTextField[] itemQuantities = new JTextField[ITEMS_NUM];

    public static void main(String[] args){
        shoppingList = new ShoppingListServiceImpl(new ShoppingListDAOImpl());

        for (int i=0; i<ITEMS_NUM; i++){
            itemNames[i] = newTextField(NAME_X, NAME_Y + (56*i), NAME_W, NAME_H);
            itemQuantities[i] = newTextField(QUANTITY_X, QUANTITY_Y + (56*i), QUANTITY_W, QUANTITY_H);
        }

        // Labels Above Text Fields
        newLabel("Name", NAME_X, NAME_W);
        newLabel(" Quantity", QUANTITY_X -10, QUANTITY_W +10);

        // Save To Database Button
        JButton save = newButton(SAVE_X, SAVE_Y, SAVE_SIZE, "Save");
        JLabel saveBG = setBackground(SAVE_BG, SAVE_X, SAVE_Y, SAVE_SIZE, SAVE_SIZE);
        save.add(saveBG);
        save.addActionListener(e -> saveItems());
        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                save.setBorder((BorderFactory.createLineBorder(Color.decode("#6a6267"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                save.setBorder((BorderFactory.createLineBorder(Color.decode("#e9dfde"))));
            }
        });

        // Search Button
        JButton search = newButton(SEARCH_X, SEARCH_Y, SEARCH_SIZE, "Search");
        JLabel searchBG = setBackground(SEARCH_BG, SEARCH_X, SEARCH_Y, SEARCH_SIZE, SEARCH_SIZE);
        search.add(searchBG);
        search.addActionListener(e -> findAllItems());
        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                search.setBorder((BorderFactory.createLineBorder(Color.decode("#6a6267"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                search.setBorder((BorderFactory.createLineBorder(Color.decode("#ffffff"))));
            }
        });

        // Save To PDF Button
        JButton pdf = newButton(PDF_X, PDF_Y, PDF_SIZE, ".PDF");
        JLabel pdfBG = setBackground(PDF_BG, PDF_X, PDF_Y, PDF_SIZE, PDF_SIZE);
        pdf.add(pdfBG);
        pdf.addActionListener(e ->
                JOptionPane.showMessageDialog(list_frame,
                        "Can't Save As PDF Yet :)",
                        "Save PDF", JOptionPane.WARNING_MESSAGE)
        );

        pdf.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                pdf.setBorder((BorderFactory.createLineBorder(Color.decode("#6a6267"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pdf.setBorder((BorderFactory.createLineBorder(Color.decode("#ffffff"))));
            }
        });

        // Background Image Of Main Frame
        JLabel backGround = setBackground(FRAME_BG, 0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // Add Panel To The Main Frame
        list_frame.add(save);
        list_frame.add(search);
        list_frame.add(pdf);
        for (int i=0; i<ITEMS_NUM; i++) {
            list_frame.add(itemNames[i]);
            list_frame.add(itemQuantities[i]);
        }
        list_frame.add(backGround);

        // Main Frame Configuration
        list_frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        list_frame.setResizable(false);
        list_frame.setLayout(null);
        list_frame.setVisible(true);
        list_frame.setLocationRelativeTo(null);
        list_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private static void newLabel(String Name, int x, int width) {
        JLabel nameLabel = new JLabel(Name);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setBounds(x,LABEL_Y, width,LABEL_H);
        nameLabel.setForeground(Color.white); //
        nameLabel.setBackground(Color.decode("#000000"));
        nameLabel.setOpaque(true);
        nameLabel.setFont(new Font("Times New Romance", Font.BOLD, 14));
        list_frame.add(nameLabel);
    }
    private static JButton newButton(int x, int y, int size, String text){
        JButton button = new JButton(text);
        button.setBounds(x, y, size, size);
        button.setBackground(Color.decode("#ffffff"));
        button.setBorder((BorderFactory.createLineBorder(Color.decode("#ffffff"))));
        return button;
    }
    private static JTextField newTextField(int x, int y, int width, int height){
        JTextField itemField = new JTextField();
        itemField.setBounds(x, y, width, height);
        itemField.setEnabled(true);
        itemField.setEditable(true);
        itemField.setBackground(Color.white);
        itemField.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        itemField.setHorizontalAlignment(SwingConstants.CENTER);
        return itemField;
    }
    private static JLabel setBackground(String path, int x, int y ,int width, int height){
        JLabel bg;
        ImageIcon img = new ImageIcon(path);
        bg = new JLabel("", img, JLabel.CENTER);
        bg.setBounds(x, y, width, height);
        return bg;
    }
    private static boolean emptyList(){
        int emptyCount = 0;

        for (int i=0; i<ITEMS_NUM; i++)
            if(emptyItemNameOrQuantity(itemNames[i], itemQuantities[i]) == -2)
                emptyCount++;

        return (emptyCount == ITEMS_NUM);
    }
    private static int emptyItemNameOrQuantity(JTextField nameField, JTextField quantityField){
        String name = nameField.getText();
        String quantity = quantityField.getText();

        if ((name == null || name.isEmpty()))
            if ((quantity == null || quantity.isEmpty()))
                return -2;
            else
                return -1;
        else
            if ((quantity == null || quantity.isEmpty()))
                return 0;
            else
                return 1;
    }
    private static void saveItems() {
        shoppingList.clearList();

        for (Item item : itemList) {
            itemList.remove(item);
        }

        boolean[] isAdded = new boolean[ITEMS_NUM];
        String[] names = new String[ITEMS_NUM];
        String[] quantities = new String[ITEMS_NUM];

        for (int i = 0; i < ITEMS_NUM; i++) {
            names[i] = itemNames[i].getText();
            quantities[i] = itemQuantities[i].getText();
        }

        if (emptyList()) {
            JOptionPane.showMessageDialog(list_frame, "List Is Empty!\nPlease Insert At Least 1 Name & Quantity", "Inane warning", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {
            for (int i = 0; i < ITEMS_NUM; i++) {
                if (emptyItemNameOrQuantity(itemNames[i], itemQuantities[i]) == -1){
                    JOptionPane.showMessageDialog(list_frame, "Item " + (i + 1) + " Name Is Empty!",  "Wrong Item Entered!", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if (emptyItemNameOrQuantity(itemNames[i], itemQuantities[i]) == 0){
                    JOptionPane.showMessageDialog(list_frame, "Item " + (i + 1) + " Quantity Is Empty!",  "Wrong Item Entered!", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if (emptyItemNameOrQuantity(itemNames[i], itemQuantities[i]) == 1) {
                    try {
                        itemList.add(new Item(names[i], Integer.parseInt(quantities[i])));
                        isAdded[i] = true;
                    }catch (NumberFormatException e){
                        JOptionPane.showMessageDialog(list_frame, "Quantity Is Not A Correct Number", "Wrong Quantity", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
        }

        int result = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To Save The List?", "Save List", JOptionPane.YES_NO_OPTION);
        if (result == 0) {
            try {
                shoppingList.saveItems(itemList);
                JOptionPane.showMessageDialog(list_frame, "List Saved In Database!");
                for (int j=0; j<ITEMS_NUM; j++)
                    if (isAdded[j])
                        JOptionPane.showMessageDialog(list_frame, names[j] + " - " + quantities[j]);
            } catch (ItemQuantityException e) {
                JOptionPane.showMessageDialog(list_frame, "Quantity Is Less Than 0 Or Greater Than 100", "Wrong Quantity", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private static void findAllItems() {
        int numOfRecords = shoppingList.countRecords();

        if (numOfRecords == 0)
            JOptionPane.showMessageDialog(list_frame, "No List Is Saved",  "Inane warning", JOptionPane.ERROR_MESSAGE);

        else{
            List<Item> items = shoppingList.findAllItems();
            String[] names = new String[numOfRecords];
            int[] quantities = new int[numOfRecords];
            String[] message = new String[(numOfRecords/8)+1];
            int i =0;

            Arrays.fill(message, new String(""));

            for (Item item : items) {
                names[i] = item.getName();
                quantities[i] = item.getQuantity();
                i++;
            }

            for (int j=0; j<numOfRecords; j++) {
                message[j/8] += "Item " + (j+1) + ":\n" +
                                "Name: " + names[j] + ",\n" +
                                "Quantity: " + quantities[j] +
                                "\n __________________\n";
                }

            JOptionPane.showMessageDialog(list_frame, message[0]);
            for (int l=1; l<message.length; l++)
                if (message[l] !=null || !(message[l].isEmpty()))
                    JOptionPane.showMessageDialog(list_frame, message[l]);

            JOptionPane.showMessageDialog(list_frame, "Search Finished!");
        }
    }
}