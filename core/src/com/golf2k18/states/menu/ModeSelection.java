package com.golf2k18.states.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.golf2k18.StateManager;
import com.golf2k18.handlers.Human;
import com.golf2k18.objects.Course;
import com.golf2k18.states.SubMenu;
import com.golf2k18.states.game.Game;

public class ModeSelection extends SubMenu {
    private static final String TITLE = "MODE";
    private Table content;

    private Course course;

    ModeSelection(StateManager manager, Course course) {
        super(manager);
        this.course = course;
    }

    @Override
    protected void createContent() {
        content = new Table();

        TextButton single = new TextButton("Single Player",StateManager.skin);
        single.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.push(new Game(manager,course,new Human()));
            }
        });
        content.add(single).fillX().expandX().left();
        content.row();

        TextButton multi = new TextButton("Multi Player",StateManager.skin);
        multi.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.print("To be implemented");
                //manager.push(game.start(manager,new Human()));
            }
        });
        content.add(multi).fillX().padTop(10f).left();
        content.row();

        TextButton bot = new TextButton("Bot",StateManager.skin);
        bot.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.push(new AISelection(manager,course));
            }
        });
        content.add(bot).fillX().padTop(10f).left();
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
