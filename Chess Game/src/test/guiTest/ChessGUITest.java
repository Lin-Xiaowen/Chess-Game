package test.guiTest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JButtonFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.ChessGUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.matcher.JButtonMatcher.withText;
import static org.junit.Assert.*;

public class ChessGUITest {
    private FrameFixture window;
    private JFrame frame;

    @Before
    public void setUp() {
        // Create and show the Chess Game GUI on the EDT
        frame = GuiActionRunner.execute(new GuiQuery<JFrame>() {
            protected JFrame executeInEDT() {
                return new ChessGUI().gameContainer;
            }
        });

        // Use FEST-Swing to create a FrameFixture for the JFrame
        window = new FrameFixture(frame);
        window.show(); // shows the frame to test
    }

    @After
    public void tearDown() {
        // Clean up resources
        window.cleanUp();
    }

    @Test
    public void testChessGameFrame() {
        // Verify the title of the JFrame
        assertThat(window.target.getTitle()).isEqualTo("Chess Game");

        // Example: Assert the visibility of the JFrame
        assertThat(window.target.isVisible()).isTrue();

        // Add more assertions as needed based on your requirements
    }

    @Test
    public void testCreateAndAddGameContainer() throws Exception {
        // Use reflection to access and invoke the private method
        ChessGUI chessGame = new ChessGUI();
        Method privateMethod = ChessGUI.class.getDeclaredMethod("createAndAddGameContainer");
        privateMethod.setAccessible(true);
        privateMethod.invoke(chessGame);

        // Use reflection to access the gameContainer field
        Field gameContainerField = ChessGUI.class.getDeclaredField("gameContainer");
        gameContainerField.setAccessible(true);
        JFrame gameContainer = (JFrame) gameContainerField.get(chessGame);

        // Verify the content pane of the gameContainer
        Container contentPane = gameContainer.getContentPane();
        assertThat(contentPane).isInstanceOf(Container.class);

        // Assuming the first JPanel in the hierarchy contains the game screen
        JPanel gameScreenPanel = (JPanel) contentPane.getComponent(0); // Assuming it's the first child panel
        assertThat(gameScreenPanel).isNotNull();

        // Verify the layout and border of the gameScreen JPanel
        assertThat(gameScreenPanel.getLayout()).isInstanceOf(BorderLayout.class); // Verify BorderLayout
        assertThat(gameScreenPanel.getBorder()).isInstanceOf(EmptyBorder.class); // Verify EmptyBorder
    }

    @Test
    public void testCreateAndAddChessBoard() throws Exception {
        // Use reflection to access and invoke the private method
        ChessGUI chessGame = new ChessGUI();
        Method privateMethod = ChessGUI.class.getDeclaredMethod("createAndAddChessBoard");
        privateMethod.setAccessible(true);
        privateMethod.invoke(chessGame);

        // Use reflection to access the gameScreen field
        Field gameScreenField = ChessGUI.class.getDeclaredField("gameScreen");
        gameScreenField.setAccessible(true);
        JPanel gameScreen = (JPanel) gameScreenField.get(chessGame);

        // Verify the layout of the gameScreen JPanel
        assertThat(gameScreen.getLayout()).isInstanceOf(BorderLayout.class); // Verify BorderLayout

        // Verify the presence of the chessBoard JPanel within gameScreen
        Component[] components = gameScreen.getComponents();
        assertThat(components).hasSize(3); // Assuming there's one child panel

        if (components.length > 0 && components[0] instanceof JPanel) {
            JPanel chessBoardPanel = (JPanel) components[0];

            // Verify the layout of the chessBoard JPanel
            assertThat(chessBoardPanel.getLayout()).isInstanceOf(GridLayout.class); // Verify GridLayout

            // Verify the number of components (chess boxes) within chessBoardPanel
            Component[] chessBoxes = chessBoardPanel.getComponents();
            assertThat(chessBoxes).hasSize(8 * 8); // Assuming a 8x8 grid for chess boxes
        } else {
            fail("Expected JPanel not found within gameScreen.");
        }
    }

    @Test
    public void testSetPlayerBlackPane() throws Exception {
        // Use reflection to access and invoke the private method
        ChessGUI chessGame = new ChessGUI();
        Method privateMethod = ChessGUI.class.getDeclaredMethod("setPlayerBlackPane");
        privateMethod.setAccessible(true);
        privateMethod.invoke(chessGame);

        // Use reflection to access the playerBlack field
        Field playerBlackField = ChessGUI.class.getDeclaredField("playerBlack");
        playerBlackField.setAccessible(true);
        JPanel playerBlack = (JPanel) playerBlackField.get(chessGame);

        // Verify that playerBlack is initialized and not null
        assertThat(playerBlack).isNotNull();

        // Verify the layout of playerBlack
        assertThat(playerBlack.getLayout()).isInstanceOf(BoxLayout.class);
        assertThat(((BoxLayout) playerBlack.getLayout()).getAxis()).isEqualTo(BoxLayout.Y_AXIS);

        // Verify the presence and properties of child components in playerBlack
        Component[] components = playerBlack.getComponents();
        assertThat(components).hasSize(3); // Assuming there are 3 child components

        // Verify the JLabel for playerBlackName
        JLabel playerBlackName = (JLabel) components[0];
        assertThat(playerBlackName.getText()).isEqualTo("Rob");
        assertThat(playerBlackName.getFont()).isEqualTo(new Font("Arial", Font.BOLD, 65));
        assertThat(playerBlackName.getAlignmentX()).isEqualTo(Component.CENTER_ALIGNMENT);

        // Verify the JLabel for playerBlackImage
        JLabel playerBlackImage = (JLabel) components[1];
        assertThat(playerBlackImage.getIcon()).isInstanceOf(ImageIcon.class);
        assertThat(playerBlackImage.getAlignmentX()).isEqualTo(Component.CENTER_ALIGNMENT);

        // Verify the JButton for playerBlackForfeitButton
        JButton playerBlackForfeitButton = (JButton) components[2];
        assertThat(playerBlackForfeitButton.getText()).isEqualTo("FORFEIT");
        assertThat(playerBlackForfeitButton.getFont()).isEqualTo(new Font("Arial", Font.BOLD, 40));
        assertThat(playerBlackForfeitButton.getPreferredSize()).isEqualTo(new Dimension(300, 100));
        assertThat(playerBlackForfeitButton.getAlignmentX()).isEqualTo(Component.CENTER_ALIGNMENT);
    }

    @Test
    public void testSetPlayerWhitePane() throws Exception {
        // Use reflection to access and invoke the private method
        ChessGUI chessGame = new ChessGUI();
        Method privateMethod = ChessGUI.class.getDeclaredMethod("setPlayerWhitePane");
        privateMethod.setAccessible(true);
        privateMethod.invoke(chessGame);

        // Use reflection to access the playerWhite field
        Field playerWhiteField = ChessGUI.class.getDeclaredField("playerWhite");
        playerWhiteField.setAccessible(true);
        JPanel playerWhite = (JPanel) playerWhiteField.get(chessGame);

        // Verify that playerWhite is initialized and not null
        assertThat(playerWhite).isNotNull();

        // Verify the layout of playerWhite
        assertThat(playerWhite.getLayout()).isInstanceOf(BoxLayout.class);
        assertThat(((BoxLayout) playerWhite.getLayout()).getAxis()).isEqualTo(BoxLayout.Y_AXIS);

        // Verify the presence and properties of child components in playerWhite
        Component[] components = playerWhite.getComponents();
        assertThat(components).hasSize(3); // Assuming there are 3 child components

        // Verify the JLabel for playerWhiteName
        JLabel playerWhiteName = (JLabel) components[0];
        assertThat(playerWhiteName.getText()).isEqualTo("Amaze");
        assertThat(playerWhiteName.getFont()).isEqualTo(new Font("Arial", Font.BOLD, 65));
        assertThat(playerWhiteName.getAlignmentX()).isEqualTo(Component.CENTER_ALIGNMENT);

        // Verify the JLabel for playerWhiteImage
        JLabel playerWhiteImage = (JLabel) components[1];
        assertThat(playerWhiteImage.getIcon()).isInstanceOf(ImageIcon.class);
        assertThat(playerWhiteImage.getAlignmentX()).isEqualTo(Component.CENTER_ALIGNMENT);

        // Verify the JButton for playerWhiteForfeitButton
        JButton playerWhiteForfeitButton = (JButton) components[2];
        assertThat(playerWhiteForfeitButton.getText()).isEqualTo("FORFEIT");
        assertThat(playerWhiteForfeitButton.getFont()).isEqualTo(new Font("Arial", Font.BOLD, 40));
        assertThat(playerWhiteForfeitButton.getPreferredSize()).isEqualTo(new Dimension(300, 100));
        assertThat(playerWhiteForfeitButton.getAlignmentX()).isEqualTo(Component.CENTER_ALIGNMENT);
    }

    @Test
    public void testCreateImages() throws Exception {
        ChessGUI chessGame = new ChessGUI();
        // Get the class of the object containing the chessPieceImages field
        Class<?> clazz = ChessGUI.class; // Replace YourClassUnderTest with your actual class name

        // Get the declared field "chessPieceImages"
        Field field = clazz.getDeclaredField("chessPieceImages");

        // Since chessPieceImages is not public, make it accessible
        field.setAccessible(true);

        // Now you can get or set the value of chessPieceImages
        Image[][] chessPieceImagesArray = (Image[][]) field.get(chessGame); // Replace chessGame with your instance

        // Use the chessPieceImagesArray for testing or manipulation
        // Example usage:
        assertThat(chessPieceImagesArray).isNotNull();
        assertThat(chessPieceImagesArray).hasSize(2);
        assertThat(chessPieceImagesArray[0]).hasSize(6);
        assertThat(chessPieceImagesArray[1]).hasSize(6);

    }

    @Test
    public void testDrawPiecesForNewGame() throws Exception {
        ChessGUI chessGame = new ChessGUI();
        // Get the class of the object containing the chessPieceImages field
        Class<?> clazz = ChessGUI.class; // Replace YourClassUnderTest with your actual class name

        // Get the declared field "chessPieceImages"
        Field chessPieceImagesField = clazz.getDeclaredField("chessPieceImages");
        Field chessBoxesField = clazz.getDeclaredField("chessBoxes");

        // Since chessPieceImages is not public, make it accessible
        chessPieceImagesField.setAccessible(true);
        chessBoxesField.setAccessible(true);

        // Now you can get or set the value of chessPieceImages
        Image[][] chessPieceImagesArray = (Image[][]) chessPieceImagesField.get(chessGame);
        JButton[][] chessBoxes = (JButton[][]) chessBoxesField.get(chessGame);


        // Call the method to draw pieces
        chessGame.drawPiecesForNewGame();

        // Verify specific buttons have the correct icons
        assertIcon(chessBoxes[6][0], chessPieceImagesArray[ChessGUI.WHITE][ChessGUI.PAWN]); // White pawn at row 6, col 0
        assertIcon(chessBoxes[1][0], chessPieceImagesArray[ChessGUI.BLACK][ChessGUI.PAWN]); // Black pawn at row 1, col 0

        // White face pieces on top row
        assertIcon(chessBoxes[7][0], chessPieceImagesArray[ChessGUI.WHITE][ChessGUI.ROOK]); // White rook at row 7, col 0
        assertIcon(chessBoxes[7][1], chessPieceImagesArray[ChessGUI.WHITE][ChessGUI.KNIGHT]); // White knight at row 7, col 1
        assertIcon(chessBoxes[7][2], chessPieceImagesArray[ChessGUI.WHITE][ChessGUI.BISHOP]); // White bishop at row 7, col 2
        assertIcon(chessBoxes[7][3], chessPieceImagesArray[ChessGUI.WHITE][ChessGUI.QUEEN]); // White queen at row 7, col 3
        assertIcon(chessBoxes[7][4], chessPieceImagesArray[ChessGUI.WHITE][ChessGUI.KING]); // White king at row 7, col 4
        assertIcon(chessBoxes[7][5], chessPieceImagesArray[ChessGUI.WHITE][ChessGUI.BISHOP]); // White bishop at row 7, col 5
        assertIcon(chessBoxes[7][6], chessPieceImagesArray[ChessGUI.WHITE][ChessGUI.KNIGHT]); // White knight at row 7, col 6
        assertIcon(chessBoxes[7][7], chessPieceImagesArray[ChessGUI.WHITE][ChessGUI.ROOK]); // White rook at row 7, col 7

        // Black face pieces on bottom row
        assertIcon(chessBoxes[0][0], chessPieceImagesArray[ChessGUI.BLACK][ChessGUI.ROOK]); // Black rook at row 0, col 0
        assertIcon(chessBoxes[0][1], chessPieceImagesArray[ChessGUI.BLACK][ChessGUI.KNIGHT]); // Black knight at row 0, col 1
        assertIcon(chessBoxes[0][2], chessPieceImagesArray[ChessGUI.BLACK][ChessGUI.BISHOP]); // Black bishop at row 0, col 2
        assertIcon(chessBoxes[0][3], chessPieceImagesArray[ChessGUI.BLACK][ChessGUI.QUEEN]); // Black queen at row 0, col 3
        assertIcon(chessBoxes[0][4], chessPieceImagesArray[ChessGUI.BLACK][ChessGUI.KING]); // Black king at row 0, col 4
        assertIcon(chessBoxes[0][5], chessPieceImagesArray[ChessGUI.BLACK][ChessGUI.BISHOP]); // Black bishop at row 0, col 5
        assertIcon(chessBoxes[0][6], chessPieceImagesArray[ChessGUI.BLACK][ChessGUI.KNIGHT]); // Black knight at row 0, col 6
        assertIcon(chessBoxes[0][7], chessPieceImagesArray[ChessGUI.BLACK][ChessGUI.ROOK]); // Black rook at row 0, col 7
    }

    // Helper method to assert icon on a JButton
    private void assertIcon(JButton button, Image expectedIcon) {
        Icon icon = button.getIcon();
        BufferedImage image1 = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        icon.paintIcon(null, image1.getGraphics(), 0, 0);

        BufferedImage image2 = new BufferedImage(expectedIcon.getWidth(null), expectedIcon.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image2.createGraphics();
        g2d.drawImage(expectedIcon, 0, 0, null);
        g2d.dispose();

        for(int i = 0; i < image1.getHeight(); i++){
            for(int j = 0; j < image1.getWidth(); j++){
                assertEquals(image1.getRGB(i, j), image2.getRGB(i, j));
            }
        }
    }


}

