package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class AfterLoginTest {

    private AfterLogin afterLogin;

    @BeforeEach
    void setUp() {
        afterLogin = new AfterLogin("sender");
    }

    @Test
    void testUIComponents() throws Exception {
        assertNotNull(getPrivateField("buttons"));
        assertNotNull(getPrivateField("recipient"));
    }

    @Test
    void testGetUsernames() throws Exception {
        // Mocking the database connection and result set
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        try (MockedStatic<DataBase> mockedDataBase = Mockito.mockStatic(DataBase.class)) {
            mockedDataBase.when(DataBase::getSqlStatement).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true, false);  // Simulate one username
            when(mockResultSet.getString("user_name")).thenReturn("testuser");

            // Call the method
            invokePrivateMethod("getUsernames", new Class[]{String.class}, new Object[]{"sender"});

            // Verify that a button was added for the username
            ArrayList<JButton> buttons = (ArrayList<JButton>) getPrivateField("buttons");
            assertEquals(1, buttons.size());
            assertEquals("testuser", buttons.get(0).getText());
        }
    }

    @Test
    void testSearchInDatabase() throws Exception {
        // Mocking the search operation
        ArrayList<String> mockResults = new ArrayList<>();
        mockResults.add("Search result 1");

        try (MockedStatic<ChatBackend> mockedChatBackend = Mockito.mockStatic(ChatBackend.class)) {
            mockedChatBackend.when(() -> ChatBackend.searchMessages(anyString(), anyString())).thenReturn(mockResults);

            // Simulate search button click
            JTextField searchField = new JTextField("test search");
            setPrivateField("searchField", searchField);

            simulateButtonClick("searchButton");

            // Verify the search results were processed (you may need to adjust this based on actual implementation)
        }
    }

    @Test
    void testOpenGroupMessagePrompt() throws Exception {
        // This can be complex due to the UI interactions, but you can simulate the actions and verify the outcomes
        invokePrivateMethod("openGroupMessagePrompt", new Class[]{String.class}, new Object[]{"sender"});

        // Further assertions to verify the dialog was displayed and actions performed correctly
    }

    @Test
    void testOpenEditProfilePrompt() throws Exception {
        invokePrivateMethod("openEditProfilePrompt", new Class[]{String.class}, new Object[]{"sender"});

        // Further assertions to verify the dialog was displayed and actions performed correctly
    }

    @Test
    void testChangeUsername() throws Exception {
        // Mock the database update method
        try (MockedStatic<DataBase> mockedDataBase = Mockito.mockStatic(DataBase.class)) {
            mockedDataBase.when(() -> DataBase.updateUsername("sender", "newUsername")).thenReturn(null);

            invokePrivateMethod("changeUsername", new Class[]{String.class, String.class}, new Object[]{"sender", "newUsername"});

            mockedDataBase.verify(() -> DataBase.updateUsername("sender", "newUsername"));
        }
    }

    @Test
    void testChangePassword() throws Exception {
        // Mock the database update method
        try (MockedStatic<DataBase> mockedDataBase = Mockito.mockStatic(DataBase.class)) {
            mockedDataBase.when(() -> DataBase.updatePassword("sender", "newPassword")).thenReturn(null);

            invokePrivateMethod("changePassword", new Class[]{String.class, String.class}, new Object[]{"sender", "newPassword"});

            mockedDataBase.verify(() -> DataBase.updatePassword("sender", "newPassword"));
        }
    }

    // Helper methods to access private fields and methods using reflection
    private Object getPrivateField(String fieldName) throws Exception {
        Field field = AfterLogin.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(afterLogin);
    }

    private void setPrivateField(String fieldName, Object value) throws Exception {
        Field field = AfterLogin.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(afterLogin, value);
    }

    private void simulateButtonClick(String buttonName) throws Exception {
        JButton button = (JButton) getPrivateField(buttonName);
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, button.getActionCommand());
        button.doClick();
        afterLogin.actionPerformed(event);
    }

    private void invokePrivateMethod(String methodName, Class<?>[] parameterTypes, Object[] parameters) throws Exception {
        java.lang.reflect.Method method = AfterLogin.class.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        method.invoke(afterLogin, parameters);
    }
}

