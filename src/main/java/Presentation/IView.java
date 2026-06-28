package Presentation;

import Domain.GameObject.Item.Item;
import Domain.IGameModel;

import java.io.IOException;
import java.util.List;

public interface IView {

    String getInput() throws IOException;
    void render(IGameModel gameModel) throws IOException;
    void close() throws IOException;

    void clearScreen() throws IOException;

    void renderInventory(List<Item> items);

    void renderMainMenu(List<String> strings);

    void renederStatistics(List<String> statsSummary);

    String getTextInput(String enterYourName);

    void renderGameOver(String s);


    void showInfo();

//    boolean isClosed();

}
