package Presentation;

import Datalayer.GameRecords;
import Domain.GameConstants;
import Domain.GameObject.Creature.Enemy.Enemy;
import Domain.GameObject.Creature.Player.Player;
import Domain.GameObject.Item.Item;
import Domain.GameVector;
import Domain.IGameModel;
import Domain.Map.Corridor;
import Domain.Map.Level;
import Domain.Map.Map;
import Domain.Map.Room;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ConsoleView implements IView {
    private Screen screen;
    private GameObjectRender gameObjectRender;
    private boolean isClosed = false;

    public ConsoleView() throws IOException {
        var factory = new DefaultTerminalFactory();
        factory.setInitialTerminalSize(new TerminalSize(100, 35));

        Font myFont = new Font("Lucida Console", Font.PLAIN, 24);
        SwingTerminalFontConfiguration fontConfig = SwingTerminalFontConfiguration.newInstance(myFont);
        factory.setTerminalEmulatorFontConfiguration(fontConfig);
        this.screen = factory.createScreen();
        this.screen.startScreen();
        this.screen.setCursorPosition(null);
        gameObjectRender = new GameObjectRender(screen);

    }

    @Override
    public String getInput() throws IOException {
        String command = null;
        KeyStroke keyStroke = screen.readInput();
        if (keyStroke.getKeyType() == KeyType.Character) {
            command = switch (Character.toLowerCase(keyStroke.getCharacter())) {
                case 'w','ц' -> "w";
                case 'a','ф' -> "a";
                case 's','ы' -> "s";
                case 'd','в' -> "d";
                case 'q','й' -> "q";
                case 'h','р' -> "h";
                case 'j','о' -> "j";
                case 'k','л' -> "k";
                case 'e','у' -> "e";
                case 'i','ш' -> "i";
                case '0' -> "0";
                case '1' -> "1";
                case '2' -> "2";
                case '3' -> "3";
                case '4' -> "4";
                case '5' -> "5";
                case '6' -> "6";
                case '7' -> "7";
                case '8' -> "8";
                case '9' -> "9";
                default -> null;
            };
        }
        return command;
    }

    private void renderGame(IGameModel gameModel) throws IOException {
        screen.clear();
        Level level = gameModel.getLevel();
        var endPoint = level.getEndPoint();
        Map map = gameModel.getMap();
        Player player = gameModel.getPlayer();
        for (var room : map.getRoomList()) {
            renderRoom(room, player.getPosition());
        }

        for (var corridor : map.getCorridorList()) {
            renderCorridor(corridor);
        }

        for (var room : map.getRoomList()) {
            renderDoors(room);
        }

        renderEnemies(gameModel.getEnemies());
        renderItems(gameModel.getItems());
        screen.setCharacter(endPoint.getX(), endPoint.getY(), new TextCharacter('≡', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT));
        gameObjectRender.render(player);

        for (var room : map.getRoomList()) {
            renderFog(room, player.getPosition());
        }

        for (var corridor : map.getCorridorList()) {
            renderFog(corridor, player.getPosition());
        }

        renderLogs(gameModel.getLogger().getLogs());

        renderStats(player, level);


        screen.refresh();
    }

    @Override
    public void render(IGameModel gameModel) throws IOException {
        renderGame(gameModel);

    }

    @Override
    public void close() throws IOException {
        screen.stopScreen();
    }

    @Override
    public void clearScreen() throws IOException {
        screen.clear();
        screen.refresh();
    }

    @Override
    public void renderInventory(List<Item> items) {
        TextGraphics text = screen.newTextGraphics();

        int x = 1, y = 1, width = 50;

        for (int i = 0; i < items.size() + 2; i++) {
            text.putString(x, y + i, " ".repeat(width));
        }

        text.putString(x, y, "--- SELECT ITEM (1-9) ---");

        if (items.isEmpty()) {
            text.putString(x, y + 1, "Your backpack is empty...");
        } else {
            for (int i = 0; i < items.size(); i++) {
                int index = i + 1;
                text.putString(x, y + i + 1, index + ") " + items.get(i).getName());
            }
        }

        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void renderMainMenu(List<String> strings) {
        try {
            screen.clear();

            TerminalSize size = screen.getTerminalSize();
            int cols = size.getColumns();
            int rows = size.getRows();

            TextGraphics text = screen.newTextGraphics();

            int maxLength = 0;
            for (String s : strings) {
                if (s.length() > maxLength) {
                    maxLength = s.length();
                }
            }
            int commonX = Math.max(0, (cols - maxLength) / 2);

            String title = "=== ROGUE ===";
            int titleX = Math.max(0, (cols - title.length()) / 2);
            int titleY = 3;
            text.putString(titleX, titleY, title);

            for (int i = 0; i < strings.size(); i++) {
                String option = strings.get(i);
                int optionY = (rows / 6) + i;
                text.putString(commonX, optionY, option);
            }

            String hint = "Press 1, 2 or 3";
            int hintX = Math.max(0, (cols - hint.length()) / 2);
            int hintY = (rows / 6) + strings.size() + 2;
            text.putString(hintX, hintY, hint);

            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void renederStatistics(List<String> statsSummary) {
        try {
            screen.clear();

            TextGraphics graphics = screen.newTextGraphics();
            TerminalSize size = screen.getTerminalSize();

            int startRow = size.getRows() / 6;
            int centerColumn = size.getColumns() / 2;

            int i = 0;
            for (var line: statsSummary) {
                int startCol = centerColumn - (line.length() / 2);
                graphics.putString(startCol, startRow + i, line);
                i++;
            }

            String prompt = "Press any key to exit...";
            graphics.putString(centerColumn - (prompt.length() / 2), startRow + statsSummary.size() + 2, prompt);

            screen.refresh();
            screen.readInput();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStringByRoom(Room room, int x, int y, boolean inside) {
        if (!room.isVisited() && !inside) return " ";
        if ((x == 0 || x == room.getWidth() - 1) && (y == 0 || y == room.getHeight() - 1)) return "+";
        if (x == 0 || x == room.getWidth() - 1) return "|";
        if (y == 0 || y == room.getHeight() - 1) return "-";
        if (inside) return ".";

        return " ";
    }

    private void renderFog(Room room, GameVector position) {
        if (!room.containsWithWalls(position)) {
            for (int y = GameConstants.WALL_THICKNESS; y < room.getHeight() - GameConstants.WALL_THICKNESS; y++) {
                for (int x = GameConstants.WALL_THICKNESS; x < room.getWidth() - GameConstants.WALL_THICKNESS; x++) {
                    screen.setCharacter(room.getX() + x, room.getY() + y, new TextCharacter(' '));
                }
            }
            char d = ' ';
            if (room.isVisited()) {
                d = '/';
            }
            for (var door : room.getDoors()) {
                screen.setCharacter(door.getX(), door.getY(), new TextCharacter(d));

            }
        }
    }

    private void renderFog(Corridor corridor, GameVector position) {
        if (!corridor.contains(position)) {
            char fill = ' ';
            if (corridor.isVisited())
                fill = '#';
            for (int i = 1; i < 4; i++) {
                GameVector startPoint = corridor.getPoint(i - 1);
                GameVector endPoint = corridor.getPoint(i);

                for (int y = Math.min(startPoint.getY(), endPoint.getY()); y <= Math.max(startPoint.getY(), endPoint.getY()); y++) {
                    for (int x = Math.min(startPoint.getX(), endPoint.getX()); x <= Math.max(startPoint.getX(), endPoint.getX()); x++) {
                        if ((x == corridor.getFirstPoint().getX() && y == corridor.getFirstPoint().getY()) || (x == corridor.getLastPoint().getX() && y == corridor.getLastPoint().getY()))
                            continue;
                        screen.setCharacter(x, y, new TextCharacter(fill));
                    }
                }
            }
        }
    }

    private void renderRoom(Room room, GameVector position) {
        if (room.isVisited()) {
            for (int y = 0; y < GameConstants.WALL_THICKNESS; y++) {
                for (int x = 1; x < room.getWidth() - 1; x++) {
                    screen.setCharacter(room.getX() + x, room.getY() + y, new TextCharacter('-'));
                    screen.setCharacter(room.getX() + x, room.getY() + room.getHeight() - y - 1, new TextCharacter('-'));
                }
            }

            for (int y = 1; y < room.getHeight() - 1; y++) {
                for (int x = 0; x < GameConstants.WALL_THICKNESS; x++) {
                    screen.setCharacter(room.getX() + x, room.getY() + y, new TextCharacter('|'));
                    screen.setCharacter(room.getX() + room.getWidth() - x - 1, room.getY() + y, new TextCharacter('|'));
                }
            }

            for (int y = 0; y <= 1; y++) {
                for (int x = 0; x <= 1; x++) {
                    screen.setCharacter(room.getX() + x * (room.getWidth() - 1), room.getY() + y * (room.getHeight() - 1), new TextCharacter('+'));
                }
            }


        }

        if (room.containsWithWalls(position)) {
            for (int y = GameConstants.WALL_THICKNESS; y < room.getHeight() - GameConstants.WALL_THICKNESS; y++) {
                for (int x = GameConstants.WALL_THICKNESS; x < room.getWidth() - GameConstants.WALL_THICKNESS; x++) {
                    screen.setCharacter(room.getX() + x, room.getY() + y, new TextCharacter('.'));
                }
            }
        }

    }


    private void renderDoors(Room room) {
        if (room.isVisited()) {
            for (var door : room.getDoors()) {
                screen.setCharacter(door.getX(), door.getY(), new TextCharacter('/'));
            }
        }
    }

    private void renderCorridor(Corridor corridor) {
        char fill = ' ';
        if (corridor.isVisited())
            fill = '#';
        for (int i = 1; i < 4; i++) {
            GameVector startPoint = corridor.getPoint(i - 1);
            GameVector endPoint = corridor.getPoint(i);

            for (int y = Math.min(startPoint.getY(), endPoint.getY()); y <= Math.max(startPoint.getY(), endPoint.getY()); y++) {
                for (int x = Math.min(startPoint.getX(), endPoint.getX()); x <= Math.max(startPoint.getX(), endPoint.getX()); x++) {
                    screen.setCharacter(x, y, new TextCharacter(fill));
                }
            }
        }
        if (corridor.isVisited()) {

            screen.setCharacter(corridor.getFirstPoint().getX(), corridor.getFirstPoint().getY(), new TextCharacter('/'));
            screen.setCharacter(corridor.getLastPoint().getX(), corridor.getLastPoint().getY(), new TextCharacter('/'));
        }
    }


    private void renderLogs(Collection<String> logs) {
        TextGraphics text = screen.newTextGraphics();
        int i = 0;
        int logWidth = 45;
        for (var log : logs) {
            String paddedLog = String.format("%-" + logWidth + "s", log);
            text.putString(GameConstants.MAP_WIDTH + 10, i++, paddedLog);
        }
    }

    private void renderEnemies(Collection<Enemy> enemies) {
        for (var enemy : enemies) {
            gameObjectRender.render(enemy);

        }
    }

    private void renderItems(Collection<Item> items) {
        for (var item : items) {
            gameObjectRender.render(item);
        }
    }


    private void renderStats(Player player, Level level) {
        TextGraphics text = screen.newTextGraphics();

        int statsY = GameConstants.MAP_HEIGHT + 2;
        int statsX = 2;

        text.putString(statsX, statsY, "".repeat(80));

        String statsRow = String.format(
                "Level : %d/%d | HP: %d/%d | Str: %d | Agi: %d | Gold: %d",
                level.getNumberLevel(),
                GameConstants.MAX_LEVEL,
                player.getHealth(),
                player.getMaxhp(),
                player.getStrength(),
                player.getAgility(),
                player.getBackpack().getGold());
        text.putString(statsX, statsY, statsRow);
    }

    public Screen getScreen() {
        return screen;
    }

    public String getTextInput(String prompt) {
        try {
            TextGraphics graphics = screen.newTextGraphics();
            TerminalSize size = screen.getTerminalSize();

            int row = size.getRows() / 4;
            int col = (size.getColumns() - prompt.length()) / 2;

            StringBuilder input = new StringBuilder();

            while (true) {
                screen.clear();

                String currentLine = prompt + " " + input.toString() + "_";
                graphics.putString(col, row, currentLine);
                screen.refresh();

                KeyStroke keyStroke = screen.readInput();

                if (keyStroke.getKeyType() == KeyType.Enter) {
                    if (input.length() > 0) return input.toString();
                } else if (keyStroke.getKeyType() == KeyType.Backspace) {
                    if (input.length() > 0) {
                        input.deleteCharAt(input.length() - 1);
                    }
                } else if (keyStroke.getKeyType() == KeyType.Character) {
                    if (input.length() < 20) {
                        input.append(keyStroke.getCharacter());
                    }
                } else if (keyStroke.getKeyType() == KeyType.Escape) {
                }
            }
        } catch (IOException e) {
            return "Player";
        }
    }

    @Override
    public void renderGameOver(String s) {
        try {
            screen.clear();
            TextGraphics text = screen.newTextGraphics();
            TerminalSize size = screen.getTerminalSize();

            String title = "=== GAME OVER ===";
            text.putString((size.getColumns() - title.length()) / 2, size.getRows() / 4, title);
            text.putString((size.getColumns() - s.length()) / 2, size.getRows() / 4 + 1, s);
            String hint = "Press any key to see statistics...";
            text.putString((size.getColumns() - hint.length()) / 2, size.getRows() / 4 + 2, hint);

            screen.refresh();
            screen.readInput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showInfo(){
        try {

        screen.clear();
        TextGraphics text = screen.newTextGraphics();
        TerminalSize size = screen.getTerminalSize();
        var lines = new String[]{
                "Welcome to Rogue",
                " ",
                "Movement: WASD     ",
                "h - switch a weapon",
                "j - eat a food     ",
                "k - drink an potion",
                "e - read a scroll  ",
                "i - show this page ",
                ""
        };
        int i = 0;
        for (var line : lines) {
            text.putString((size.getColumns() - line.length()) / 2, size.getRows() / 4 + i, line);
            i++;
        }
        String hint = "Press any key to close...";
        text.putString((size.getColumns() - hint.length()) / 2, size.getRows() / 4 + i +2, hint);
        screen.refresh();
        screen.readInput();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
