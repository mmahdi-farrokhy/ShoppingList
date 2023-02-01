package shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class ShoppingListUI {
    // List Objects
    private static ShoppingListService shoppingList;
    private static PdfService pdfService;
    private static final List<Item> itemList = new LinkedList<>();
    private static final int ITEMS_NUM = 5;
    private static final boolean[] isAdded = new boolean[ITEMS_NUM];
    private static final String[] names = new String[ITEMS_NUM];
    private static final String[] quantities = new String[ITEMS_NUM];
    private static int NUM_OF_RECORDS;
    // Colors Declaration
    public static final Color HOVER_BORDER_COLOR = Color.decode("#6a6267");
    public static final Color EXIT_BORDER_COLOR = Color.decode("#ffffff");

    // Messages And Titles
    public static final String SAVE_CONFIRM_MSG = "Are You Sure You Want To Save The List?";
    public static final String SAVE_CONFIRM_TITLE = "Save List";
    public static final String SAVED_MSG = "List Saved In Database!";
    public static final String SAVED_TITLE = "Saved Successfully";
    public static final String QUANTITY_RANGE_EX_MXG = "Quantity Is Less Than 0 Or Greater Than 100";
    public static final String QUANTITY_RANGE_EX_TITLE = "Wrong Quantity";
    public static final String QUANTITY_INPUT_EX_MXG = "Quantity Is Not A Correct Number";
    public static final String QUANTITY_INPUT_EX_TITLE = "Wrong Quantity";
    public static final String SAVE_AS_PDF_MSG = "List Saved As List.pdf";
    public static final String SAVE_AS_PDF_TITLE = "Save PDF";
    private static final String EMPTY_LIST_MSG = "List Is Empty!\nPlease Insert At Least 1 Name & Quantity";
    private static final String EMPTY_NAME_MSG = "Name Is Empty! - Item: ";
    private static final String EMPTY_QUANTITY_MSG = "Quantity Is Empty! - Item: ";
    public static final String NO_LIST_MSG = "No List Is Saved";
    public static final String SEARCH_FINISHED_MSG = "Search Finished!";
    public static final String WARNING_TITLE = "Notice!";
    public static final String INFO_TITLE = "Information";
    private static final String WRONG_INPUT_TITLE = "Wrong Data Entered!";

    // Font Declaration
    public static final String FONT = "Times New Romance";
    private static final int FONT_SIZE = 14;

    // Program's Main Frame
    static JFrame list_frame = new JFrame("Shopping List");
    private static final int FRAME_WIDTH = 405;
    private static final int FRAME_HEIGHT = 500;

    // Pictures Declaration
    private static final String FRAME_BG = "src/main//resources/frame_bg.jpg";
    private static final String SAVE_BG = "src/main//resources/save_bg.png";
    private static final String SEARCH_BG = "src/main//resources/search_bg.png";
    private static final String PDF_BG = "src/main//resources/pdf_bg.png";

    // Buttons Declaration
    // Save Button
    private static final int SAVE_X = 80;
    private static final int SAVE_Y = 10;
    private static final int SAVE_SIZE = 45;

    // Search Button
    private static final int SEARCH_X = 160;
    private static final int SEARCH_Y = 10;
    private static final int SEARCH_SIZE = 45;

    // PDF Button
    private static final int PDF_X = 240;
    private static final int PDF_Y = 10;
    private static final int PDF_SIZE = 45;

    // Text Fields Declaration
    // Items Name Text Box
    private static final JTextField[] itemNames = new JTextField[ITEMS_NUM];
    private static final int NAME_X = 40;
    private static final int NAME_Y = 100;
    private static final int NAME_W = 250;
    private static final int NAME_H = 50;

    // Items Quantity Text Box
    private static final JTextField[] itemQuantities = new JTextField[ITEMS_NUM];
    private static final int QUANTITY_X = 300;
    private static final int QUANTITY_Y = 100;
    private static final int QUANTITY_W = 70;
    private static final int QUANTITY_H = 50;

    // Label Declaration
    private static final int LABEL_Y = 80;
    private static final int LABEL_H = 15;

    // Components Steps
    private static final int ITEM_STEP = 56;
    private static final int LABEL_STEP = 10;

    public static void main(String[] args){
        shoppingList = new ShoppingListServiceImplementer(new ShoppingListDAOImplementer());
        pdfService = new PdfServiceImplementer();

        for (int i=0; i<ITEMS_NUM; i++){
            itemNames[i] = newTextField(NAME_X, NAME_Y + (ITEM_STEP *i), NAME_W, NAME_H);
            itemQuantities[i] = newTextField(QUANTITY_X, QUANTITY_Y + (ITEM_STEP *i), QUANTITY_W, QUANTITY_H);
        }

        // Labels Above Text Fields
        newLabel("Name", NAME_X, NAME_W);
        newLabel(" Quantity", QUANTITY_X - LABEL_STEP, QUANTITY_W + LABEL_STEP);

        // Save To Database Button
        JButton save = newButton(SAVE_X, SAVE_Y, SAVE_SIZE, SAVE_BG, "Save");
        save.addActionListener(e -> saveItems());
        // Search Button
        JButton search = newButton(SEARCH_X, SEARCH_Y, SEARCH_SIZE, SEARCH_BG, "Search");
        search.addActionListener(e -> findAllItems());
        // Save As PDF Button
        JButton pdf = newButton(PDF_X, PDF_Y, PDF_SIZE, PDF_BG, "PDF");
        pdf.addActionListener(e -> saveAsPdf());

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

        frameConfig();
    }
    private static void frameConfig() {
        // Main Frame Configuration
        list_frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        list_frame.setResizable(false);
        list_frame.setLayout(null);
        list_frame.setVisible(true);
        list_frame.setLocationRelativeTo(null);
        list_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private static void saveAsPdf() {
        StringBuilder text = new StringBuilder();
        if (emptyList()) {
            showMessage(EMPTY_LIST_MSG + "\nSave A List First", WARNING_TITLE, MessageType.WARNING);
            return;
        }
        else
            for (Item item : itemList) {
                text.append(item.getName()).append(" - ").append(item.getQuantity()).append("\n");
            }

        pdfService.saveAsPdf(text.toString());
        showMessage(SAVE_AS_PDF_MSG, SAVE_AS_PDF_TITLE, MessageType.WARNING);
    }
    private static void newLabel(String Name, int x, int width) {
        JLabel nameLabel = new JLabel(Name);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setBounds(x,LABEL_Y, width,LABEL_H);
        nameLabel.setForeground(Color.white); //
        nameLabel.setBackground(Color.BLACK);
        nameLabel.setOpaque(true);
        nameLabel.setFont(new Font(FONT, Font.BOLD, FONT_SIZE));
        list_frame.add(nameLabel);
    }
    private static JButton newButton(int x, int y, int size, String bg_path, String text){
        JButton button = new JButton(text);
        button.setBounds(x, y, size, size);
        button.setBackground(Color.white);
        button.setBorder((BorderFactory.createLineBorder(Color.white)));

        JLabel saveBG = setBackground(bg_path, x, y, size, size);
        button.add(saveBG);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBorder((BorderFactory.createLineBorder(HOVER_BORDER_COLOR)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBorder((BorderFactory.createLineBorder(EXIT_BORDER_COLOR)));
            }
        });

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
            if(checkItem(itemNames[i], itemQuantities[i]) == ItemStatus.EMPTY_ITEM)
                emptyCount++;

        return (emptyCount == ITEMS_NUM);
    }
    private static ItemStatus checkItem(JTextField nameField, JTextField quantityField){
        String name = nameField.getText();
        String quantity = quantityField.getText();
        boolean emptyName = ((name == null) || (name.isEmpty()));
        boolean emptyQuantity = ((quantity == null) || (quantity.isEmpty()));

        if (emptyName)
            if (emptyQuantity)
                return ItemStatus.EMPTY_ITEM;
            else
                return ItemStatus.EMPTY_NAME;
        else
            if (emptyQuantity)
                return ItemStatus.EMPTY_QUANTITY;
            else
                return ItemStatus.CORRECT_ITEM;
    }
    private static void saveItems() {
        shoppingList.clearList();
        clearItemList();
        getItems();

        if (addItemsToList())
        {
            int result = JOptionPane.showConfirmDialog(null, SAVE_CONFIRM_MSG, SAVE_CONFIRM_TITLE, JOptionPane.YES_NO_OPTION);
            if (result == 0) {
                try {
                    shoppingList.saveItems(itemList);
                    showMessage(SAVED_MSG, SAVED_TITLE, MessageType.INFO);
                    for (int j = 0; j < ITEMS_NUM; j++)
                        if (isAdded[j])
                            showMessage(names[j] + " - " + quantities[j], INFO_TITLE, MessageType.INFO);
                } catch (ItemQuantityException e) {
                    showMessage(QUANTITY_RANGE_EX_MXG, QUANTITY_RANGE_EX_TITLE, MessageType.ERROR);
                }
            }
        }
    }
    private static boolean addItemsToList() {
        if (emptyList()) {
            showMessage(EMPTY_LIST_MSG, WRONG_INPUT_TITLE,MessageType.ERROR);
            return false;
        }
        else {
            for (int i = 0; i < ITEMS_NUM; i++) {
                if (checkItem(itemNames[i], itemQuantities[i]) == ItemStatus.EMPTY_NAME){
                    showMessage(EMPTY_NAME_MSG + (i+1), WRONG_INPUT_TITLE,MessageType.WARNING);
                    return false;
                }
                else if (checkItem(itemNames[i], itemQuantities[i]) == ItemStatus.EMPTY_QUANTITY){
                    showMessage(EMPTY_QUANTITY_MSG + (i+1), WRONG_INPUT_TITLE,MessageType.WARNING);
                    return false;
                }
                else if (checkItem(itemNames[i], itemQuantities[i]) == ItemStatus.CORRECT_ITEM) {
                    try {
                        itemList.add(new Item(names[i], Integer.parseInt(quantities[i])));
                        isAdded[i] = true;
                    }catch (NumberFormatException e){
                        showMessage(QUANTITY_INPUT_EX_MXG, QUANTITY_INPUT_EX_TITLE,MessageType.ERROR);
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private static void getItems() {
        for (int i = 0; i < ITEMS_NUM; i++) {
            names[i] = itemNames[i].getText();
            quantities[i] = itemQuantities[i].getText();
        }
    }
    private static void clearItemList() {
        for (Item item : itemList) {
            itemList.remove(item);
        }
    }
    private static void findAllItems() {
        NUM_OF_RECORDS = shoppingList.countRecords();

        if (NUM_OF_RECORDS == 0)
            showMessage(NO_LIST_MSG, WARNING_TITLE, MessageType.ERROR);
        else{
            List<Item> items = shoppingList.findAllItems();
            String savedList = getSavedList(items);
            showMessage(savedList, INFO_TITLE, MessageType.INFO);
            showMessage(SEARCH_FINISHED_MSG, INFO_TITLE, MessageType.INFO);
        }
    }
    private static String getSavedList(List<Item> items) {
        int i =0;
        StringBuilder savedList = new StringBuilder();
        String[] savedNames = new String[NUM_OF_RECORDS];
        int[] savedQuantities = new int[NUM_OF_RECORDS];

        NUM_OF_RECORDS = shoppingList.countRecords();

        for (Item item : items) {
            savedNames[i] = item.getName();
            savedQuantities[i] = item.getQuantity();
            i++;
        }

        for (int j = 0; j< NUM_OF_RECORDS; j++) {
            savedList.append("Item ").append((j + 1)).append(":\n")
            .append("Name: ").append(savedNames[j]).append(",\n")
            .append("Quantity: ").append(savedQuantities[j])
            .append("\n __________________\n");
            }

        return savedList.toString();
    }
    private static void showMessage(String message, String title, MessageType type) {
        if (type == MessageType.ERROR)
            JOptionPane.showMessageDialog(list_frame, message, title, JOptionPane.ERROR_MESSAGE);
        else if (type == MessageType.INFO)
            JOptionPane.showMessageDialog(list_frame, message, title, JOptionPane.INFORMATION_MESSAGE);
        else if (type == MessageType.WARNING)
            JOptionPane.showMessageDialog(list_frame, message, title, JOptionPane.WARNING_MESSAGE);
    }
}