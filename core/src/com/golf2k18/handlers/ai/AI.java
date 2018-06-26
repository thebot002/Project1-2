package com.golf2k18.handlers.ai;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.math.Interpolation;
        import com.badlogic.gdx.math.Vector3;
        import com.badlogic.gdx.scenes.scene2d.InputEvent;
        import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.scenes.scene2d.ui.*;
        import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
        import com.badlogic.gdx.utils.Scaling;
        import com.badlogic.gdx.utils.viewport.ScalingViewport;
        import com.golf2k18.StateManager;
        import com.golf2k18.engine.Engine;
        import com.golf2k18.handlers.Bot;
        import com.golf2k18.objects.Ball;
        import com.golf2k18.states.game.Game;

        //comment

public class AI extends Bot {
    private Ball simuBall;
    private Engine engine;
    private Vector3 velocity;

    private Stage running;
    private Label running_label;

    private int ballCounter = 0;

    public AI() {
        this.simuBall = new Ball(new Vector3());
        this.velocity = new Vector3();
        createStage();
    }

    private void createStage(){
        running = new Stage(new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Table table = new Table();
        table.setFillParent(true);

        running_label = new Label("", StateManager.skin);
        table.add(running_label).expand().center().bottom().pad(10f);

        running.addActor(table);
    }


    @Override
    public void handleInput(Game gameState) {
        Vector3 closestPos = simuBall.getPosition().cpy();
        if(simuBall.isStopped() || engine.isGoal())
        {
            System.out.println(closestPos);
            running_label.setText("Simulating..."+String.valueOf(ballCounter));
            if(engine != null && engine.isGoal()){
                running_label.setText("");
                gameState.getBall().hit(velocity);
                velocity = null;
                hitCount++;
                return;
            }
            simuBall = new Ball(gameState.getBall().getPosition().cpy());
            engine = new Engine(gameState.getTerrain(),simuBall, StateManager.settings.getSolver());
            Vector3 goalDifference = new Vector3();
            ballCounter++;
            if(velocity.len() == 0){
                velocity = gameState.getTerrain().getHole().cpy().sub(gameState.getBall().getPosition());
                velocity.scl(.5f);
            }
            else
            {
                goalDifference.x = gameState.getTerrain().getHole().cpy().x - closestPos.x;
                goalDifference.y = gameState.getTerrain().getHole().cpy().y - closestPos.y;
                goalDifference.scl(0.2f);

                velocity.add(goalDifference);
            }
            simuBall.hit(velocity);
        }
        else
        {
            engine.updateBall(Gdx.graphics.getDeltaTime());
            if(simuBall.getPosition().dst(gameState.getTerrain().getHole()) < closestPos.dst(gameState.getTerrain().getHole()) && simuBall.getVelocity().len() < engine.getGoalTolerance())
            {
                closestPos.set(simuBall.getPosition());
            }
        }
        super.handleInput(gameState);
    }

    @Override
    public void renderInput() {
        running.act();
        running.draw();
        super.renderInput();
    }
}
