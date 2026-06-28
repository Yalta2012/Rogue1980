package Controller;

import Datalayer.GameRecords;
import Datalayer.SaveManager;
import Domain.GameModel;
import Domain.GameObject.Item.*;
import Domain.GameVector;
import Domain.IGameModel;
import Presentation.ConsoleView;
import Presentation.IView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final IView view = new ConsoleView();
    private IGameModel gameModel = new GameModel(true, "");
    private SaveManager saveManager = new SaveManager();

    public Controller() throws IOException {
    }


    public void run() throws IOException {
        if (!showMainMenu()) {
            view.close();
        }
        view.clearScreen();
        view.render(gameModel);
        while (!gameModel.getEndGame()) {
            var command = parseInput(view.getInput());
            if (command == null) continue;
            if (command.startsWith("move")) {
                boolean changeLevel = gameModel.tryTurn(parseDirection(command));
                if (changeLevel) {
                    view.clearScreen();
                    view.render(gameModel);
                    saveManager.save(gameModel, "save.json");
                } else {
                    view.render(gameModel);
                }
            } else if (command.startsWith("use")) {
                handleUseCommand(command);
            } else if (command.equals("info")) {
                view.showInfo();
                view.render(gameModel);
            } else if (command.equals("quit")) {
                break;
            }
        }

        handleGameOver();
        results();
        view.close();

    }



    private void results(){
        List<String> statsSummary = gameModel.getStatistics().getSummary();

        GameRecords currentResult = new GameRecords(
                gameModel.getPlayer().getName(),
                gameModel.getScore(),
                gameModel.getLevel().getNumberLevel()
        );
        saveManager.SaveRecord(currentResult);

        List<GameRecords> allRecords = saveManager.loadRecords();
        List<String> finalScreen = new ArrayList<>();
        finalScreen.addAll(statsSummary);
        finalScreen.add("");
        finalScreen.add("=== HALL OF FAME (TOP 5) ===");

        for (int i = 0; i < Math.min(allRecords.size(), 5); i++) {
            GameRecords r = allRecords.get(i);
            finalScreen.add(String.format("%d. %s - Score: %d (Lvl: %d)",
                    i + 1, r.name, r.score, r.levelReached));
        }

        view.renederStatistics(finalScreen);


    }

    private String parseInput(String input) {
        if (input == null) return null;
        return switch (input) {
            case "w" -> "move_up";
            case "a" -> "move_left";
            case "s" -> "move_down";
            case "d" -> "move_right";
            case "q" -> "quit";
            case "h" -> "use_weapon";
            case "j" -> "use_food";
            case "k" -> "use_potion";
            case "e" -> "use_scroll";
            case "i" -> "info";
            default -> null;
        };

    }

    public GameVector parseDirection(String input) {
        return switch (input) {
            case "move_up" -> GameVector.UP;
            case "move_left" -> GameVector.LEFT;
            case "move_down" -> GameVector.DOWN;
            case "move_right" -> GameVector.RIGHT;
            default -> null;
        };
    }

    private void handleUseCommand(String command) throws IOException {
        switch (command) {
            case "use_weapon" -> handleInventoryMode(Weapon.class);
            case "use_food" -> handleInventoryMode(Food.class);
            case "use_potion" -> handleInventoryMode(Potion.class);
            case "use_scroll" -> handleInventoryMode(Scrolls.class);
        }
    }

    public void handleInventoryMode(Class<? extends Item> type) throws IOException {
        List<Item> items = gameModel.getPlayer().getBackpack().getItemList().stream()
                .filter(type::isInstance)
                .toList();

        view.clearScreen();
        view.renderInventory(items);

        String choice = view.getInput();
        if (choice != null && !choice.isEmpty()) {
            try {
                int index = Character.getNumericValue(choice.charAt(0)) - 1;
                if (index >= 0 && index < items.size()) {
                    Item selected = items.get(index);
                    gameModel.useItem(selected);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        view.clearScreen();
        view.render(gameModel);
    }

    private boolean showMainMenu() throws IOException {
        while (true) {
            view.clearScreen();
            view.renderMainMenu(List.of("1. New Game", "2. Load Game", "3. Exit"));

            String choice = view.getInput();
            if (choice == null) continue;

            switch (choice) {
                case "1" -> {
                    String name = view.getTextInput("Enter your name");
                    this.gameModel = new GameModel(true, name);
                    return true;
                }
                case "2" -> {
                    handleLoadGame();
                    return true;
                }
                case "3", "q" -> {
                    return false;
                }
            }
        }
    }

    private void handleLoadGame() {
        GameModel loadedModel = saveManager.load("save.json", GameModel.class);

        if (loadedModel != null) {
            this.gameModel = loadedModel;
        } else {
            System.out.println("Save file not found or corrupted.");
        }
    }

    private void handleGameOver(){
        if (gameModel.getPlayer().getHealth() <= 0) {
            view.renderGameOver("Sorry, " + gameModel.getPlayer().getName() + "... You died.");
        } else if (gameModel.getLevel().getNumberLevel() > 21) {
            view.renderGameOver("VICTORY!");
        } else {
            view.renderGameOver("Game over");
        }
    }
}
