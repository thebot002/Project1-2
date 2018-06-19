package com.golf2k18.states.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.golf2k18.StateManager;
import com.golf2k18.handlers.Bot;
import com.golf2k18.handlers.Player;
import com.golf2k18.handlers.ai.HoleInOneAI;
import com.golf2k18.handlers.ai.SpareBot;
import com.golf2k18.handlers.ai.WIP;
import com.golf2k18.objects.Course;
import com.golf2k18.states.SubMenu;
import com.golf2k18.states.game.Game;

import java.util.ArrayList;

/**
 * The menu to select which ai the user wants to see playing the game, AI1 or SpareBot.
 */
public class AISelection extends SubMenu {
    private Table content;
    private static final String TITLE = "AISelection";

    private ArrayList<String> bots;
    private ArrayList<Bot> concreteBots;

    private Course course;

    public AISelection(StateManager manager, Course course) {
        super(manager);

        bots = new ArrayList<>();
        concreteBots = new ArrayList<>();
        bots.add("HoleInOneAI");
        concreteBots.add(new HoleInOneAI());
        bots.add("SpareBot");
        concreteBots.add(new SpareBot());
        bots.add("WIP");
        concreteBots.add(new WIP());

        this.course = course;
    }

    @Override
    protected void createContent() {
        content = new Table();

        for (int i = 0; i < bots.size(); i++) {
            TextButton ai = new TextButton(bots.get(i),StateManager.skin);
            Player player = concreteBots.get(i);
            ai.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    manager.push(new Game(manager,course,player));
                }
            });
            content.add(ai).expandX().fillX();
            content.row();
        }
    }

    @Override
    protected Table getContent() {
        return content;
    }

    @Override
    protected String getTitle() {
        return TITLE;
    }
}
