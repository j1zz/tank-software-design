package ru.mipt.bit.platformer.AI;

import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.strategy.NotRecommendingAI;

import org.awesome.ai.AI;
import ru.mipt.bit.platformer.AI.commands.Down;
import ru.mipt.bit.platformer.AI.commands.Left;
import ru.mipt.bit.platformer.AI.commands.Right;
import ru.mipt.bit.platformer.AI.commands.Up;
import ru.mipt.bit.platformer.util.objects.Player;
import ru.mipt.bit.platformer.util.objects.Tree;

import java.util.ArrayList;
import java.util.List;

public class AIMovement implements AiInterface {
    private GameState gameState;
    private AI ai;
    private final List<Command> commands;

    public AIMovement(List<Tree> trees, List<Player> tanks, int width, int height){
        ai = new NotRecommendingAI();

        EnvSet creator = new EnvSet();
        gameState = creator.envSet(trees, tanks, width, height);

        commands = new ArrayList<>();
    }

    @Override
    public void executeCommand() {
        System.out.println("Execution in progress");
        recommendCommandList();
        for (Command command : commands) {
            command.execute();
        }
    }

    Command commandFromRecommendation(Recommendation recommendation){
        Player actor = (Player) recommendation.getActor().getSource();
        Action action = recommendation.getAction();
        if (action == MoveEast) {return new Right(actor);}
        if (action == MoveWest) {return new Left(actor);}
        if (action == MoveSouth) {return new Down(actor);}
        return new Up(actor);
    }

    public void recommendCommandList() {
        List<Recommendation> recommendations = ai.recommend(gameState);
        for (Recommendation rec : recommendations) {
            commands.add(commandFromRecommendation(rec));
        }
    }

}