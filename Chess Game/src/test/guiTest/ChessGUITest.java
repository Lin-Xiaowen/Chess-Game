package test.guiTest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JButtonFixture;
import org.fest.swing.fixture.JPanelFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.ChessGUI;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.junit.Assert.assertEquals;

public class ChessGUITest {
    private FrameFixture window;

    @Before
    public void setUp() {
        // Create and show the Chess Game GUI on the EDT
        JFrame frame = GuiActionRunner.execute(new GuiQuery<JFrame>() {
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
    public void testCreateToolbar() throws Exception {
        // Obtain the instance of the class containing createToolbar() method
        ChessGUI chessGame = new ChessGUI();

        // Use reflection to access the private method createToolbar()
        Method createToolbarMethod = ChessGUI.class.getDeclaredMethod("createToolbar");
        createToolbarMethod.setAccessible(true);
        createToolbarMethod.invoke(chessGame); // Invoke the private method

        // Now interact with the GUI components added by createToolbar()
        JButtonFixture newButtonFixture = window.button("New");
        JButtonFixture undoButtonFixture = window.button("Undo");

        // Assert the properties of the buttons
        assertEquals("New", newButtonFixture.target.getText());
        assertEquals("Undo", undoButtonFixture.target.getText());

        // Assert the dimensions and fonts
        assertEquals(new Dimension(300, 100), newButtonFixture.target.getPreferredSize());
        assertEquals(new Dimension(300, 100), undoButtonFixture.target.getPreferredSize());
        assertEquals(new Font("Arial", Font.PLAIN, 40), newButtonFixture.target.getFont());
        assertEquals(new Font("Arial", Font.PLAIN, 40), undoButtonFixture.target.getFont());

    }

}

